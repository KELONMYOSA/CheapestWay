package ru.cheapestway.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.producer.KafkaProducer;
import ru.cheapestway.collector.models.Airports;

import static io.restassured.RestAssured.given;
import static ru.cheapestway.collector.Main.KAFKA_SERVER;

public class GetAirports {
    private static final String TOPIC_NAME = "to-clickhouse-airports";

    public static void sendAirports() {
        String response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://api.travelpayouts.com/data/ru/airports.json")
                .then()
                .statusCode(200).extract().response().asString();

        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Airports[] listOfAirports = mapper.readValue(response.replaceAll("\\\\\"", ""), Airports[].class);
            final KafkaProducer producer = Producer.openProducer(KAFKA_SERVER);
            for (int i = 0; i < listOfAirports.length; i++) {
                if (listOfAirports[i].getIata_type().contains("airport")) {
                    String jsonOut = "{\n" +
                            "\"code\":\"" + listOfAirports[i].getCode() + "\",\n" +
                            "\"name\":\"" + listOfAirports[i].getName() + "\",\n" +
                            "\"time_zone\":\"" + listOfAirports[i].getTime_zone() + "\",\n" +
                            "\"country_code\":\"" + listOfAirports[i].getCountry_code() + "\",\n" +
                            "\"city_code\":\"" + listOfAirports[i].getCity_code() + "\",\n" +
                            "\"en\":\"" + listOfAirports[i].getName_translations().getEn() + "\",\n" +
                            "\"iata_type\":\"" + listOfAirports[i].getIata_type() + "\",\n" +
                            "\"flightable\":" + listOfAirports[i].getFlightable() + ",\n" +
                            "\"lon\":" + listOfAirports[i].getCoordinates().getLon() + ",\n" +
                            "\"lat\":" + listOfAirports[i].getCoordinates().getLat() + "\n" +
                            "}";
                    Producer.sendMessage(producer, TOPIC_NAME, "airports", jsonOut);
                }
            }
            Producer.closeProducer(producer);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
