package dao;


import models.Client;
import models.Mechanic;
import models.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            int telephoneNumber = rsClients.getInt("telephoneNumber");
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

    public void storeClient(Client client) throws SQLException {
        String insertTableSQL = "INSERT INTO CLIENT"
                + "(NAME, SURNAME, PATRONYMIC, TELEPHONENUMBER) VALUES"
                + "(?,?,?,?)";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setInt(4, client.getTelephone());
        System.out.println(preparedStatement.toString());
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void deleteClient(int clientID) throws SQLException {
        Connection dbConnection = getDBConnection();
        String deleteSQL = "DELETE FROM CLIENT WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, clientID);

        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void updateClient(int clientID, String name, String surname, String patronymic, int telephoneNumber) throws SQLException {
        String updateTableSQL = "UPDATE CLIENT SET NAME= ?,SURNAME= ?,PATRONYMIC= ?,TELEPHONENUMBER= ? WHERE id = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setInt(4, telephoneNumber);
        preparedStatement.setInt(5, clientID);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public List<Mechanic> LoadAllMechanics() throws SQLException {
        List<Mechanic> data = new ArrayList<Mechanic>();
        String selectSQL = "SELECT * FROM MECHANIC";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        while (rsMechanic.next()) {
            int mechanicID = rsMechanic.getInt("id");
            String name = rsMechanic.getString("NAME");
            String surname = rsMechanic.getString("SURNAME");
            String patronymic = rsMechanic.getString("PATRONYMIC");
            int hourlypay = rsMechanic.getInt("HOURLYPAY");
            currentMechanic = new Mechanic(mechanicID, name, surname, patronymic, hourlypay);
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
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        if (rsMechanic.next()) {
            String name = rsMechanic.getString("NAME");
            String surname = rsMechanic.getString("SURNAME");
            String patronymic = rsMechanic.getString("PATRONYMIC");
            int hourlypay = rsMechanic.getInt("HOURLYPAY");
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

    public List<Order> LoadAllOrders() throws SQLException {
        List<Order> data = new ArrayList<Order>();
        String selectSQL = "SELECT * FROM ORDERS";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsOrders = preparedStatement.executeQuery();
        Order currentOrder = null;
        while (rsOrders.next()) {
            int orderID = rsOrders.getInt("id");
            String description = rsOrders.getString("description");
            int clientID = rsOrders.getInt("client_id");
            Client client = loadClient(clientID);
            int mechanicID = rsOrders.getInt("mechanic_id");
            Mechanic mechanic = loadMechanic(mechanicID);
            String format = rsOrders.getTimestamp("dateStart").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            LocalDateTime startDate = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            format = rsOrders.getTimestamp("dateFinish").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            LocalDateTime endDate = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            double cost = rsOrders.getDouble("cost");
            currentOrder = new Order(orderID, description, client, mechanic, startDate, endDate, cost, Order.Status.START);
            data.add(currentOrder);
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
        return data;
    }

    public Order loadOrder(int id) throws SQLException {
        String selectSQL = "SELECT * FROM ORDERS WHERE ID = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet rsOrders = preparedStatement.executeQuery();
        Order currentOrder = null;
        if (rsOrders.next()) {
            int orderID = rsOrders.getInt("id");
            String description = rsOrders.getString("description");
            int clientID = rsOrders.getInt("client_id");
            Client client = loadClient(clientID);
            int mechanicID = rsOrders.getInt("mechanic_id");
            Mechanic mechanic = loadMechanic(mechanicID);
            String format = rsOrders.getTimestamp("startDate").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            LocalDateTime startDate = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            format = rsOrders.getTimestamp("endDate").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            LocalDateTime endDate = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            double cost = rsOrders.getDouble("cost");
            currentOrder = new Order(orderID, description, client, mechanic, startDate, endDate, cost, Order.Status.START);
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
        return currentOrder;
    }

    public void updateOrder(int orderID, String description, int clientID, int mechanicID, LocalDateTime startDate, LocalDateTime endDate, double cost, Order.Status status) throws SQLException {
        String updateTableSQL = "UPDATE ORDERS SET DESCRIPTION= ?, CLIENT_ID= ?, MECHANIC_ID= ?, DATESTART= ?, DATEFINISH= ?, COST= ?, STATUS = ? WHERE id = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, description);
        preparedStatement.setInt(2, clientID);
        preparedStatement.setInt(3, mechanicID);
        preparedStatement.setTimestamp(4, Timestamp.valueOf(startDate));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(endDate));
        preparedStatement.setDouble(6, cost);
        preparedStatement.setInt(7, status.ordinal());
        preparedStatement.setInt(8, orderID);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void deleteOrder(int orderID) throws SQLException {
        Connection dbConnection = getDBConnection();
        String deleteSQL = "DELETE FROM ORDERS WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, orderID);
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }
}