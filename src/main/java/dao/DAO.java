package dao;

import models.Client;
import models.Mechanic;
import models.Order;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date.*;

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

    public Client loadClient(long clientID) throws SQLException {
        String selectSQL = "SELECT * FROM CLIENT WHERE ID = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, clientID);
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

    public void storeClient(String name, String surname, String patronymic, int telephoneNumber)
            throws SQLException {
        String insertTableSQL = "INSERT INTO CLIENT"
                + "(NAME, SURNAME, PATRONYMIC, TELEPHONENUMBER) VALUES"
                + "(?,?,?,?)";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setInt(4, telephoneNumber);
        System.out.println(preparedStatement.toString());
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void deleteClient(long clientID) throws SQLException {
        Connection dbConnection = getDBConnection();
        String deleteSQL = "DELETE FROM CLIENT WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, clientID);

        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void updateClient(long clientID, String name, String surname, String patronymic, int telephoneNumber)
            throws SQLException {
        String updateTableSQL = "UPDATE CLIENT SET NAME= ?,SURNAME= ?,PATRONYMIC= ?,TELEPHONENUMBER= ? WHERE id = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setInt(4, telephoneNumber);
        preparedStatement.setLong(5, clientID);
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

    public Mechanic loadMechanic(long mechanicID) throws SQLException {
        String selectSQL = "SELECT * FROM MECHANIC WHERE ID = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, mechanicID);
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

    public void updateMechanic(long mechanicID, String name, String surname, String patronymic, double hourlypay)
            throws SQLException {
        String updateTableSQL = "UPDATE MECHANIC SET NAME= ?,SURNAME= ?,PATRONYMIC= ?,HOURLYPAY= ? WHERE id = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setDouble(4, hourlypay);
        preparedStatement.setLong(5, mechanicID);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void storeMechanic(String name, String surname, String patronymic, double hourlypay)
            throws SQLException {
        String insertTableSQL = "INSERT INTO MECHANIC"
                + "(NAME, SURNAME, PATRONYMIC, HOURLYPAY) VALUES"
                + "(?,?,?,?)";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setDouble(4, hourlypay);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void deleteMechanic(long mechanicID) throws SQLException {
        Connection dbConnection = getDBConnection();
        String deleteSQL = "DELETE FROM MECHANIC WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, mechanicID);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
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

            LocalDate startDate = rsOrders.getDate("dateStart").toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = startDate.format(formatter);
            startDate = LocalDate.parse(text, formatter);

            LocalDate endDate = rsOrders.getDate("dateFinish").toLocalDate();
            text = endDate.format(formatter);
            endDate = LocalDate.parse(text, formatter);

            double cost = rsOrders.getDouble("cost");
            Order.Status status = Order.Status.valueOf(rsOrders.getString("status"));
            currentOrder = new Order(orderID, description, client, mechanic, startDate, endDate, cost, status);
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

    public Order loadOrder(long id) throws SQLException {
        String selectSQL = "SELECT * FROM ORDERS WHERE ID = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, id);
        ResultSet rsOrders = preparedStatement.executeQuery();
        Order currentOrder = null;
        if (rsOrders.next()) {
            long orderID = rsOrders.getLong("id");
            String description = rsOrders.getString("description");
            long clientID = rsOrders.getLong("client_id");
            Client client = loadClient(clientID);
            long mechanicID = rsOrders.getLong("mechanic_id");
            Mechanic mechanic = loadMechanic(mechanicID);

            LocalDate startDate = rsOrders.getDate("dateStart").toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = startDate.format(formatter);
            startDate = LocalDate.parse(text, formatter);

            LocalDate endDate = rsOrders.getDate("dateFinish").toLocalDate();
            text = endDate.format(formatter);
            endDate = LocalDate.parse(text, formatter);

            double cost = rsOrders.getDouble("cost");
            Order.Status status = Order.Status.valueOf(rsOrders.getString("status"));
            currentOrder = new Order(orderID, description, client, mechanic, startDate, endDate, cost, status);
            return currentOrder;
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
        return currentOrder;
    }

    public void storeOrder(String description, long clientID, long mechanicID, LocalDate startDate,
                           LocalDate endDate, double cost, Order.Status status) throws SQLException {
        String insertTableSQL = "INSERT INTO ORDERS"
                + "(DESCRIPTION, CLIENT_ID, MECHANIC_ID, DATESTART, DATEFINISH, COST, STATUS) VALUES"
                + "(?,?,?,?,?,?,?)";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, description);
        preparedStatement.setLong(2, clientID);
        preparedStatement.setLong(3, mechanicID);
        preparedStatement.setDate(4, Date.valueOf(startDate));
        preparedStatement.setDate(5, Date.valueOf(endDate));
        preparedStatement.setDouble(6, cost);
        preparedStatement.setString(7, status.toString());
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void updateOrder(long orderID, String description, long clientID, long mechanicID, LocalDate startDate,
                            LocalDate endDate, double cost, Order.Status status) throws SQLException {
        String updateTableSQL = "UPDATE ORDERS SET DESCRIPTION= ?, CLIENT_ID= ?, MECHANIC_ID= ?, DATESTART= ?, DATEFINISH= ?, COST= ?, STATUS = ? WHERE id = ?";
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, description);
        preparedStatement.setLong(2, clientID);
        preparedStatement.setLong(3, mechanicID);
        preparedStatement.setDate(4, Date.valueOf(startDate));
        preparedStatement.setDate(5, Date.valueOf(endDate));
        preparedStatement.setDouble(6, cost);
        preparedStatement.setString(7, status.toString());
        preparedStatement.setLong(8, orderID);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void deleteOrder(long orderID) throws SQLException {
        Connection dbConnection = getDBConnection();
        String deleteSQL = "DELETE FROM ORDERS WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, orderID);
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