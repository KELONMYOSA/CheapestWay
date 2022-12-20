package ru.cheapestway.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.kafka.clients.producer.KafkaProducer;
import ru.cheapestway.collector.models.Tickets;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.cheapestway.collector.Main.KAFKA_SERVER;
import static ru.cheapestway.collector.Main.TRAVELPAYOUTS_TOKEN;

public class GetTickets {
    private static final String TOPIC_NAME = "to-clickhouse-tickets";

    public static void sendAllTickets() {
        try {
            List<String> routesDeparture = GetClickHouseData.getRoutesDeparture();
            List<String> routesArrival = GetClickHouseData.getRoutesArrival();

            KafkaProducer producer = Producer.openProducer(KAFKA_SERVER);

            String ticketsData;
            String jsonOut;
            Tickets listOfTickets;
            final ObjectMapper mapper = new ObjectMapper();

            for (int i = 0; i < routesDeparture.size(); i++) {
                ticketsData = getGraphQLResponse(TRAVELPAYOUTS_TOKEN, routesDeparture.get(i), routesArrival.get(i));
                if (ticketsData != "Invalid params or bad request") {
                    try {
                        listOfTickets = mapper.readValue(ticketsData, Tickets.class);

                        for (int j = 0; j < listOfTickets.getData().length; j++) {
                            jsonOut = "{\n" +
                                    "\"timestamp\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\n" +
                                    "\"origin\":\"" + listOfTickets.getData()[j].getOrigin() + "\",\n" +
                                    "\"destination\":\"" + listOfTickets.getData()[j].getDestination() + "\",\n" +
                                    "\"origin_airport\":\"" + listOfTickets.getData()[j].getOrigin_airport() + "\",\n" +
                                    "\"destination_airport\":\"" + listOfTickets.getData()[j].getDestination_airport() + "\",\n" +
                                    "\"price\":" + listOfTickets.getData()[j].getPrice() + ",\n" +
                                    "\"airline\":\"" + listOfTickets.getData()[j].getAirline() + "\",\n" +
                                    "\"flight_number\":\"" + listOfTickets.getData()[j].getFlight_number() + "\",\n" +
                                    "\"departure_at\":\"" + listOfTickets.getData()[j].getDeparture_at() + "\",\n" +
                                    "\"duration\":" + listOfTickets.getData()[j].getDuration() + ",\n" +
                                    "\"link\":\"" + listOfTickets.getData()[j].getLink() + "\"\n" +
                                    "}";
                            try {
                                Producer.sendMessage(producer, TOPIC_NAME, "tickets", jsonOut);
                            } catch (Exception e) {
                                boolean connected = false;
                                for (int n = 0; n < 5 && !connected; n++) {
                                    try {
                                        System.out.println("Trying to reconnect...");
                                        producer = Producer.openProducer(KAFKA_SERVER);
                                        Thread.sleep(10000);
                                        Producer.sendMessage(producer, TOPIC_NAME, "tickets", jsonOut);
                                        connected = true;
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                if (!connected) {
                                    System.out.println("Connection lost!");
                                    System.exit(1);
                                }
                            }
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Producer.closeProducer(producer);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static String getGraphQLResponse(String token, String origin, String destination) throws InterruptedException {
        String responseData = "";
        int statusCode = 429;
        boolean connected = false;
        boolean out = false;

        while (statusCode != 200 && !out) {
            for (int n = 0; n < 5 && !connected; n++) {
                try {
                    Response response = given()
                            .contentType(ContentType.JSON)
                            .param("token", token)
                            .param("origin", origin)
                            .param("destination", destination)
                            .param("departure_at", "")
                            .param("return_at", "")
                            .param("currency", "rub")
                            .param("direct", true)
                            .param("one_way", true)
                            .param("market", "ru")
                            .param("limit", 1000)
                            .param("page", 1)
                            .param("sorting", "price")
                            .param("unique", false)
                            .when()
                            .get("https://api.travelpayouts.com/aviasales/v3/prices_for_dates")
                            .then()
                            .extract().response();
                    responseData = response.asString();
                    statusCode = response.statusCode();
                    connected = true;
                    if (statusCode == 200) {
                        out = true;
                    } else {
                        if (response.jsonPath().getString("error").contains("invalid params") ||
                                response.jsonPath().getString("error").contains("bad request")) {
                            responseData = "Invalid params or bad request";
                            System.out.println("Status code: " + statusCode);
                            System.out.println("ERROR - " + response.jsonPath().getString("error") + " - SKIPPED");
                            out = true;
                        } else {
                            try {
                                System.out.println("Status code: " + statusCode);
                                System.out.println("ERROR - " + response.jsonPath().getString("error"));
                                Thread.sleep(60000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Trying to send a request...");
                    Thread.sleep(10000);
                }
            }
            if (!connected) {
                System.out.println("Can't send a request. Skipping...");
            }
        }
        return responseData;
    }
}
