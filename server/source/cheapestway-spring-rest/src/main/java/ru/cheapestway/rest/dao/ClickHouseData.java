package ru.cheapestway.rest.dao;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static ru.cheapestway.rest.Main.*;

public class ClickHouseData {
    private static Connection getConnection() throws SQLException {
        final Properties props = new Properties();
        props.setProperty("user", CLICKHOUSE_USER);
        props.setProperty("password", CLICKHOUSE_PASSWORD);

        final Connection conn;
        ClickHouseDataSource dataSource = new ClickHouseDataSource(DB_URL, props);
        conn = dataSource.getConnection();

        return conn;
    }

    public static String getCities() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM cities_output";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.citiesToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPricesOnDate(String departure, String arrival, String date) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT timestamp, price FROM tickets_output WHERE (departure_at LIKE '" + date + "%') AND (origin LIKE '" + departure + "') AND (tickets_output.destination LIKE '" + arrival + "')";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.pricesOnDateToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPricesLatest(String departure, String arrival) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT toDate(timestamp) AS timestamp, price, departure_at FROM tickets_output WHERE (origin LIKE '" + departure + "') AND (destination LIKE '" + arrival + "') AND (timestamp IN (SELECT max(toDate(timestamp)) FROM tickets_output WHERE (origin LIKE '" + departure + "') AND (destination LIKE '" + arrival + "')))";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.pricesLatestToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getLowcosters() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT is_lowcost, count(is_lowcost) AS count FROM airlines_output GROUP BY is_lowcost";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.lowcostersToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFlightNumbers() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT flight_number, airlines_output.name, min(price), min(duration) FROM tickets_output LEFT JOIN airlines_output ON airline = code WHERE airlines_output.name NOT LIKE '' GROUP BY flight_number, airlines_output.name";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.flightNumbersToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAvgPrice() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT (toDate(replaceRegexpOne(departure_at, '(\\d{4})-(\\d{2})-(\\d{2}).*', '\\\\1-\\\\2-\\\\3')) - toDate(timestamp)) AS days, round(avg(price)) AS price FROM tickets_output WHERE days >= 0  GROUP BY days";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.avgPriceToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDayOfWeekPrice() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT toDayOfWeek(timestamp) AS dayofweek, avg(price) AS price from tickets_output GROUP BY dayofweek";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.avgPriceToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCheapAirlines() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT name, (min(price / duration)) AS price FROM (SELECT flight_number, airlines_output.name, min(price) AS price, min(duration) AS duration FROM tickets_output LEFT JOIN airlines_output ON airline = code WHERE airlines_output.name NOT LIKE '' GROUP BY flight_number, airlines_output.name) GROUP BY name ORDER BY price";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            String jsonAsStringOut = ResultSetUtils.cheapAirlinesToJsonAsString(rs);
            conn.close();
            return jsonAsStringOut;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
