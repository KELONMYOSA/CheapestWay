package ru.cheapestway.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.producer.KafkaProducer;
import ru.cheapestway.collector.models.Airlines;

import static io.restassured.RestAssured.given;
import static ru.cheapestway.collector.Main.KAFKA_SERVER;
import static ru.cheapestway.collector.Producer.*;

public class GetAirlines {
    private static final String TOPIC_NAME = "to-clickhouse-airlines";

    public static void sendAirlines() {
        String response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://api.travelpayouts.com/data/ru/airlines.json")
                .then()
                .statusCode(200).extract().response().asString();

        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Airlines[] listOfAirlines = mapper.readValue(response.replaceAll("\\\\\"", ""), Airlines[].class);
            final KafkaProducer producer = openProducer(KAFKA_SERVER);
            for (int i = 0; i < listOfAirlines.length; i++) {
                String jsonOut = "{\n" +
                        "\"code\":\"" + listOfAirlines[i].getCode() + "\",\n" +
                        "\"name\":\"" + listOfAirlines[i].getName() + "\",\n" +
                        "\"en\":\"" + listOfAirlines[i].getName_translations().getEn() + "\",\n" +
                        "\"is_lowcost\":" + listOfAirlines[i].getIs_lowcost() + "\n" +
                        "}";
                sendMessage(producer, TOPIC_NAME, "airlines", jsonOut);
            }
            closeProducer(producer);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
