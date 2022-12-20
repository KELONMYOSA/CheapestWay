package ru.cheapestway.collector;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.*;
import java.util.List;
import java.util.Properties;

import static ru.cheapestway.collector.Main.*;

public class GetClickHouseData {
    private static Connection getConnection() throws SQLException {
        final Properties props = new Properties();
        props.setProperty("user", CLICKHOUSE_USER);
        props.setProperty("password", CLICKHOUSE_PASSWORD);

        final Connection conn;
        ClickHouseDataSource dataSource = new ClickHouseDataSource(DB_URL, props);
        conn = dataSource.getConnection();

        System.out.println("Connected to: " + conn.getMetaData().getURL());
        return conn;
    }

    public static List<String> getRoutesDeparture() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM routes_output";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<String> routesDeparture = ResultSetUtils.getColumnArray(rs, 2);
            conn.close();
            return routesDeparture;
        }
    }

    public static List<String> getRoutesArrival() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM routes_output";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            List<String> routesArrival = ResultSetUtils.getColumnArray(rs, 3);
            conn.close();
            return routesArrival;
        }
    }
}
