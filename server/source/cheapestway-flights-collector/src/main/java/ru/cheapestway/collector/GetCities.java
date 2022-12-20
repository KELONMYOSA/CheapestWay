package ru.cheapestway.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.producer.KafkaProducer;
import ru.cheapestway.collector.models.Cities;

import static io.restassured.RestAssured.given;
import static ru.cheapestway.collector.Main.KAFKA_SERVER;

public class GetCities {
    private static final String TOPIC_NAME = "to-clickhouse-cities";

    public static void sendCities() {
        String response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://api.travelpayouts.com/data/ru/cities.json")
                .then()
                .statusCode(200).extract().response().asString();

        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Cities[] listOfCities = mapper.readValue(response, Cities[].class);
            final KafkaProducer producer = Producer.openProducer(KAFKA_SERVER);
            for (int i = 0; i < listOfCities.length; i++) {
                String jsonOut = "{\n" +
                        "\"code\":\"" + listOfCities[i].getCode() + "\",\n" +
                        "\"name\":\"" + listOfCities[i].getName() + "\",\n" +
                        "\"time_zone\":\"" + listOfCities[i].getTime_zone() + "\",\n" +
                        "\"country_code\":\"" + listOfCities[i].getCountry_code() + "\",\n" +
                        "\"en\":\"" + listOfCities[i].getName_translations().getEn() + "\",\n" +
                        "\"lon\":" + listOfCities[i].getCoordinates().getLon() + ",\n" +
                        "\"lat\":" + listOfCities[i].getCoordinates().getLat() + "\n" +
                        "}";
                Producer.sendMessage(producer, TOPIC_NAME, "cities", jsonOut);
            }
            Producer.closeProducer(producer);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}