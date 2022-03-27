package ru.akirakozov.sd.refactoring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

    static Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:sqlite:test.db";
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTables() throws SQLException {
        try (Connection c = getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

}