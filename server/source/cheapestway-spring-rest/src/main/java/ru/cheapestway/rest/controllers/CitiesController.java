package ru.cheapestway.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.cheapestway.rest.dao.ClickHouseData;
import ru.cheapestway.rest.models.MessageWS;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("cities")
public class CitiesController {
    @Autowired
    private KafkaTemplate<String, MessageWS> kafkaTemplate;

    @GetMapping
    public void sendCities(@RequestParam(name = "userID") String userID) {
        try {
            kafkaTemplate.send("to-from-rest", new MessageWS(userID, ClickHouseData.getCities())).get();
        } catch (InterruptedException | ExecutionException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}