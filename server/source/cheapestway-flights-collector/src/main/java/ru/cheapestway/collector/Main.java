package ru.cheapestway.collector;

import java.io.IOException;

import static ru.cheapestway.collector.GetTickets.sendAllTickets;


public class Main {
    public static final String DB_URL;
    public static final String KAFKA_SERVER;
    public static final String CLICKHOUSE_USER;
    public static final String CLICKHOUSE_PASSWORD;
    public static final String TRAVELPAYOUTS_TOKEN;

    static {
        try {
            DB_URL = ProjectConfig.props().getProperty("dbUrl");
            KAFKA_SERVER = ProjectConfig.props().getProperty("kafkaServer");
            CLICKHOUSE_USER = ProjectConfig.props().getProperty("clickhouseUser");
            CLICKHOUSE_PASSWORD = ProjectConfig.props().getProperty("clickhousePassword");
            TRAVELPAYOUTS_TOKEN = ProjectConfig.props().getProperty("travelpayoutsToken");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        while (true) {
            sendAllTickets();
        }
    }
}
