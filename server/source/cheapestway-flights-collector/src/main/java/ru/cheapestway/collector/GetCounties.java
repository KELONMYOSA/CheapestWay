package ru.cheapestway.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.producer.KafkaProducer;
import ru.cheapestway.collector.models.Countries;

import static io.restassured.RestAssured.given;
import static ru.cheapestway.collector.Main.KAFKA_SERVER;

public class GetCounties {
    private static final String TOPIC_NAME = "to-clickhouse-countries";

    public static void sendCountries() {
        String response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://api.travelpayouts.com/data/ru/countries.json")
                .then()
                .statusCode(200).extract().response().asString();

        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Countries[] listOfCounties = mapper.readValue(response, Countries[].class);
            final KafkaProducer producer = Producer.openProducer(KAFKA_SERVER);
            for (int i = 0; i < listOfCounties.length; i++) {
                String jsonOut = "{\n" +
                        "\"code\":\"" + listOfCounties[i].getCode() + "\",\n" +
                        "\"name\":\"" + listOfCounties[i].getName() + "\",\n" +
                        "\"currency\":\"" + listOfCounties[i].getCurrency() + "\",\n" +
                        "\"en\":\"" + listOfCounties[i].getName_translations().getEn() + "\"\n" +
                        "}";
                Producer.sendMessage(producer, TOPIC_NAME, "countries", jsonOut);
            }
            Producer.closeProducer(producer);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}