package ru.cheapestway.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cheapestway.rest.dao.ClickHouseData;
import ru.cheapestway.rest.models.MessageWS;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("pricesondate")
public class PricesOnDateController {
    @Autowired
    private KafkaTemplate<String, MessageWS> kafkaTemplate;

    @GetMapping
    private void sendPricesOnDate(@RequestParam(name = "userID") String userID,
                         @RequestParam(name = "departure") String departure,
                         @RequestParam(name = "arrival") String arrival,
                         @RequestParam(name = "date") String date) {
        try {
            kafkaTemplate.send("to-from-rest", new MessageWS(userID, ClickHouseData.getPricesOnDate(departure, arrival, date))).get();
        } catch (InterruptedException | ExecutionException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
