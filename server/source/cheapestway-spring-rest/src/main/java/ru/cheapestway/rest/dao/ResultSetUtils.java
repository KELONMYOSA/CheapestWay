package ru.cheapestway.rest.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cheapestway.rest.models.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class ResultSetUtils {
    public static String lowcostersToJsonAsString(ResultSet rs) throws SQLException, IOException {
        ArrayList<Lowcosters> lowcostersList = new ArrayList<>();
        while (rs.next()) {
            Lowcosters lowcosters = new Lowcosters(rs.getString(1),
                    Integer.parseInt(rs.getString(2)));
            lowcostersList.add(lowcosters);
        }
        rs.close();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, lowcostersList);
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String avgPriceToJsonAsString(ResultSet rs) throws SQLException, IOException {
        ArrayList<AvgPrice> avgPriceList = new ArrayList<>();
        while (rs.next()) {
            AvgPrice avgPrice = new AvgPrice(Integer.parseInt(rs.getString(1)),
                    Float.parseFloat(rs.getString(2)));
            avgPriceList.add(avgPrice);
        }
        rs.close();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, avgPriceList);
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String cheapAirlinesToJsonAsString(ResultSet rs) throws SQLException, IOException {
        ArrayList<CheapAirlines> cheapAirlinesList = new ArrayList<>();
        while (rs.next()) {
            CheapAirlines cheapAirlines = new CheapAirlines(rs.getString(1),
                    Float.parseFloat(rs.getString(2)));
            cheapAirlinesList.add(cheapAirlines);
        }
        rs.close();

        ArrayList<CheapAirlines> outAirlines = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            outAirlines.add(cheapAirlinesList.get(i));
        }

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, outAirlines);
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String flightNumbersToJsonAsString(ResultSet rs) throws SQLException, IOException {
        ArrayList<FlightNumbers> flightNumbersList = new ArrayList<>();
        while (rs.next()) {
            FlightNumbers flightNumbers = new FlightNumbers(Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    Integer.parseInt(rs.getString(3)),
                    Integer.parseInt(rs.getString(4)));
            flightNumbersList.add(flightNumbers);
        }
        rs.close();

        ArrayList<Lowcosters> flightNumbersOut = new ArrayList<>();
        while (flightNumbersList.size() > 0) {
            int n = 0;
            String airline = flightNumbersList.get(0).getAirline();
            for (int i = 0; i < flightNumbersList.size(); i++) {
                if (flightNumbersList.get(i).getAirline().equals(airline)){
                    n++;
                    flightNumbersList.remove(i);
                    i = i - 1;
                }
            }
            flightNumbersOut.add(new Lowcosters(airline, n));
        }

        flightNumbersOut.sort(Comparator.comparing(Lowcosters::getValue).reversed());

        ArrayList<Lowcosters> outAirlines = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            outAirlines.add(flightNumbersOut.get(i));
        }

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, outAirlines);
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String pricesOnDateToJsonAsString(ResultSet rs) throws SQLException, IOException {
        ArrayList<PricesOnDate> pricesOnDateList = new ArrayList<>();
        while (rs.next()) {
            PricesOnDate pricesOnDate = new PricesOnDate(rs.getString(1),
                    Integer.parseInt(rs.getString(2)));
            pricesOnDateList.add(pricesOnDate);
        }
        rs.close();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, pricesOnDateList);
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String pricesLatestToJsonAsString(ResultSet rs) throws SQLException, IOException {
        ArrayList<PricesLatest> pricesLatestList = new ArrayList<>();
        while (rs.next()) {
            PricesLatest pricesLatest = new PricesLatest(rs.getString(1),
                    Integer.parseInt(rs.getString(2)),
                    rs.getString(3));
            pricesLatestList.add(pricesLatest);
        }
        rs.close();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, pricesLatestList);
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String citiesToJsonAsString(ResultSet rs) throws SQLException, IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, citiesList(rs));
        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static ArrayList<City> citiesList(ResultSet rs) throws SQLException, IOException {
        ArrayList<City> cityList = new ArrayList<>();
        while (rs.next()) {
            City city = new City(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    Float.parseFloat(rs.getString(6)),
                    Float.parseFloat(rs.getString(7)));
            cityList.add(city);
        }
        rs.close();

        return cityList;
    }

    public static void printRs(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetadata = rs.getMetaData();
        for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
            System.out.printf("%-19s|", rs.getMetaData().getColumnName(i));
        }
        System.out.println();
        printSeparator(rsMetadata.getColumnCount());
        while (rs.next()) {
            for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
                System.out.printf("%-19s|", rs.getString(i));
            }
            System.out.println();
        }
        printSeparator(rsMetadata.getColumnCount());
    }

    private static void printSeparator(int columns) {
        for (int i = 1; i <= 20 * columns; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
