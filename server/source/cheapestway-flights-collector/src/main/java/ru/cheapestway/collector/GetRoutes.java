package ru.cheapestway.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.producer.KafkaProducer;
import ru.cheapestway.collector.models.Routes;

import static io.restassured.RestAssured.given;
import static ru.cheapestway.collector.Main.KAFKA_SERVER;

public class GetRoutes {
    private static final String TOPIC_NAME = "to-clickhouse-routes";

    public static void sendRoutes() {
        String response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://api.travelpayouts.com/data/routes.json")
                .then()
                .statusCode(200).extract().response().asString();

        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Routes[] listOfRoutes = mapper.readValue(response, Routes[].class);
            final KafkaProducer producer = Producer.openProducer(KAFKA_SERVER);
            for (int i = 0; i < listOfRoutes.length; i++) {
                String jsonOut = "{\n" +
                        "\"airline_iata\":\"" + listOfRoutes[i].getAirline_iata() + "\",\n" +
                        "\"departure_airport_iata\":\"" + listOfRoutes[i].getDeparture_airport_iata() + "\",\n" +
                        "\"arrival_airport_iata\":\"" + listOfRoutes[i].getArrival_airport_iata() + "\"\n" +
                        "}";
                Producer.sendMessage(producer, TOPIC_NAME, "routes", jsonOut);
            }
            Producer.closeProducer(producer);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}