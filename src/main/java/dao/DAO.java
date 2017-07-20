package dao;


import models.Client;
import models.Mechanic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private static DAO instance;

    private DAO() {
    }

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
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

    public List<Client> LoadAllClients() throws SQLException {
        List<Client> data = new ArrayList<Client>();
        String selectSQL = "SELECT * FROM CLIENT";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsClients = preparedStatement.executeQuery();
        Client currentClient = null;
        while (rsClients.next()) {
            int clientID = rsClients.getInt("id");
            String name = rsClients.getString("NAME");
            String surname = rsClients.getString("SURNAME");
            String patronymic = rsClients.getString("PATRONYMIC");
            int telephoneNumber = rsClients.getInt("TELEPHONENUMBER");
            currentClient = new Client(clientID, name, surname, patronymic, telephoneNumber);
            data.add(currentClient);
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
        return data;
    }

    public Client loadClient(int clientID) throws SQLException {
        String selectSQL = "SELECT * FROM CLIENT WHERE ID = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, clientID);
        ResultSet rsClients = preparedStatement.executeQuery();
        Client currentClient = null;
        if (rsClients.next()) {
            String name = rsClients.getString("NAME");
            String surname = rsClients.getString("SURNAME");
            String patronymic = rsClients.getString("PATRONYMIC");
            int telephoneNumber = rsClients.getInt("phoneNum");
            currentClient = new Client(clientID, name, surname, patronymic, telephoneNumber);
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return currentClient;
    }

    public List<Mechanic> LoadAllMechanics() throws SQLException {
        List<Mechanic> data = new ArrayList<Mechanic>();
        String selectSQL = "SELECT * FROM MECHANIC";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        while (rsMechanic.next()) {
            int pizzaMakerID = rsMechanic.getInt("id");
            String name = rsMechanic.getString("NAME");
            String surname = rsMechanic.getString("SURNAME");
            String patronymic = rsMechanic.getString("PATRONYMIC");
            int hourlypay = rsMechanic.getInt("HOURLYPAY");
            currentMechanic = new Mechanic(pizzaMakerID, name, surname, patronymic, hourlypay);
            data.add(currentMechanic);
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
        return data;
    }

    public Mechanic loadMechanic(int mechanicID) throws SQLException {
        String selectSQL = "SELECT * FROM MECHANIC WHERE ID = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, mechanicID);
        ResultSet rsPizzaMakers = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        if (rsPizzaMakers.next()) {
            String name = rsPizzaMakers.getString("NAME");
            String surname = rsPizzaMakers.getString("SURNAME");
            String patronymic = rsPizzaMakers.getString("PATRONYMIC");
            int hourlypay = rsPizzaMakers.getInt("HOURLYPAY");
            currentMechanic = new Mechanic(mechanicID, name, surname, patronymic, hourlypay);
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return currentMechanic;
    }
}