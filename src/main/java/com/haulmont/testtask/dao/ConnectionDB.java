package com.haulmont.testtask.dao;

import java.sql.*;

public class ConnectionDB {

    private static ConnectionDB instance;

    private ConnectionDB() {}

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:db", "SA", "");
            return dbConnection;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return dbConnection;
    }
}
