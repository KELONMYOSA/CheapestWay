package ru.cheapestway.collector;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetUtils {
    public static List<String> getColumnArray(ResultSet rs, Integer columnIndex) throws SQLException {
        ResultSetMetaData rsMetadata = rs.getMetaData();
        List<String> stringArray = new ArrayList<String>();
        int i = 0;
        while (rs.next()) {
            stringArray.add(rs.getString(columnIndex));
            i++;
        }
        return  stringArray;
    }

    public static void printRs(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetadata = rs.getMetaData();
        for (int i = 1; i<= rsMetadata.getColumnCount(); i++) {
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
        for (int i = 1; i<= 20*columns; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
