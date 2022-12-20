package ru.cheapestway.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static final String DB_URL;
    public static final String CLICKHOUSE_USER;
    public static final String CLICKHOUSE_PASSWORD;
    public static final String KAFKA_SERVER;
    public static final String NODEJS_SERVER;

    static {
        try {
            DB_URL = ProjectConfig.props().getProperty("dbUrl");
            CLICKHOUSE_USER = ProjectConfig.props().getProperty("clickhouseUser");
            CLICKHOUSE_PASSWORD = ProjectConfig.props().getProperty("clickhousePassword");
            KAFKA_SERVER = ProjectConfig.props().getProperty("kafkaServer");
            NODEJS_SERVER = ProjectConfig.props().getProperty("nodeJsServer");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
