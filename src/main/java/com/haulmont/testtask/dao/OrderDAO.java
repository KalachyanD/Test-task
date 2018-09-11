package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.dto.FullNameDTO;
import com.haulmont.testtask.dao.dto.OrderDTO;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Status;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    private static OrderDAO instance;

    private OrderDAO() {
    }

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public List<OrderDTO> LoadAll() throws SQLException {
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        String selectSQL = "SELECT o.id AS oID, o.description AS description, o.datestart AS datestart, o.datefinish" +
                " AS datefinish, o.cost AS cost, o.status AS status, cl.id AS clID,cl.name AS clName, cl.surname AS" +
                " clSurname,cl.Patronymic AS clPatronymic,m.id AS mID,m.name AS mName,m.surname AS mSurname," +
                " m.Patronymic AS mPatronymic FROM ORDERS AS o JOIN CLIENT AS cl ON o.client_id = cl.id JOIN" +
                " MECHANIC AS m ON o.mechanic_id = m.id";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsOrders = preparedStatement.executeQuery();
        OrderDTO currentOrder = null;
        while (rsOrders.next()) {
            Long orderID = rsOrders.getLong("oID");
            String description = rsOrders.getString("description");

            FullNameDTO client = new FullNameDTO(
                    rsOrders.getLong("clID"),
                    rsOrders.getString("clName"),
                    rsOrders.getString("clSurname"),
                    rsOrders.getString("clPatronymic"));

            FullNameDTO mechanic = new FullNameDTO(
                    rsOrders.getLong("mID"),
                    rsOrders.getString("mName"),
                    rsOrders.getString("mSurname"),
                    rsOrders.getString("mPatronymic"));

            LocalDate startDate = rsOrders.getDate("dateStart").toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = startDate.format(formatter);
            startDate = LocalDate.parse(text, formatter);

            LocalDate endDate = rsOrders.getDate("dateFinish").toLocalDate();
            text = endDate.format(formatter);
            endDate = LocalDate.parse(text, formatter);

            Double cost = rsOrders.getDouble("cost");
            Status status = Status.valueOf(rsOrders.getString("status"));
            currentOrder = new OrderDTO(orderID, description, client, mechanic, startDate, endDate, cost, status);
            orders.add(currentOrder);
        }
        return orders;
    }

    public OrderDTO load(Long id) throws SQLException {
        String selectSQL = "SELECT o.id AS oID, o.description AS description, o.datestart AS datestart, o.datefinish" +
                " AS datefinish, o.cost AS cost, o.status AS status, cl.id AS clID,cl.name AS clName, cl.surname AS" +
                " clSurname,cl.Patronymic AS clPatronymic,m.id AS mID,m.name AS mName,m.surname AS mSurname," +
                " m.Patronymic AS mPatronymic FROM ORDERS AS o JOIN CLIENT AS cl ON o.client_id = cl.id JOIN" +
                " MECHANIC AS m ON o.mechanic_id = m.id WHERE o.id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, id);
        ResultSet rsOrders = preparedStatement.executeQuery();
        OrderDTO currentOrder = null;
        if (rsOrders.next()) {
            Long orderID = rsOrders.getLong("oID");
            String description = rsOrders.getString("description");

            FullNameDTO client = new FullNameDTO(
                    rsOrders.getLong("clID"),
                    rsOrders.getString("clName"),
                    rsOrders.getString("clSurname"),
                    rsOrders.getString("clPatronymic"));

            FullNameDTO mechanic = new FullNameDTO(
                    rsOrders.getLong("mID"),
                    rsOrders.getString("mName"),
                    rsOrders.getString("mSurname"),
                    rsOrders.getString("mPatronymic"));

            LocalDate startDate = rsOrders.getDate("dateStart").toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = startDate.format(formatter);
            startDate = LocalDate.parse(text, formatter);

            LocalDate endDate = rsOrders.getDate("dateFinish").toLocalDate();
            text = endDate.format(formatter);
            endDate = LocalDate.parse(text, formatter);

            Double cost = rsOrders.getDouble("cost");
            Status status = Status.valueOf(rsOrders.getString("status"));
            currentOrder = new OrderDTO(orderID, description, client, mechanic, startDate, endDate, cost, status);
            return currentOrder;
        }
        return currentOrder;
    }

    public void store(String description, Long clientId, Long mechanicId, LocalDate startDate,
                      LocalDate endDate, Double cost, Status status) throws SQLException {
        String insertTableSQL = "INSERT INTO ORDERS"
                + "(DESCRIPTION, CLIENT_ID, MECHANIC_ID, DATESTART, DATEFINISH, COST, STATUS) VALUES"
                + "(?,?,?,?,?,?,?)";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, description);
        preparedStatement.setLong(2, clientId);
        preparedStatement.setLong(3, mechanicId);
        preparedStatement.setDate(4, Date.valueOf(startDate));
        preparedStatement.setDate(5, Date.valueOf(endDate));
        preparedStatement.setDouble(6, cost);
        preparedStatement.setString(7, status.toString());
        preparedStatement.executeUpdate();
    }

    public void update(Long orderId, String description, Long clientId, Long mechanicId, LocalDate startDate,
                       LocalDate endDate, Double cost, Status status) throws SQLException {
        String updateTableSQL = "UPDATE ORDERS SET DESCRIPTION= ?, CLIENT_ID= ?, MECHANIC_ID= ?, DATESTART= ?, DATEFINISH= ?, COST= ?, STATUS = ? WHERE id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, description);
        preparedStatement.setLong(2, clientId);
        preparedStatement.setLong(3, mechanicId);
        preparedStatement.setDate(4, Date.valueOf(startDate));
        preparedStatement.setDate(5, Date.valueOf(endDate));
        preparedStatement.setDouble(6, cost);
        preparedStatement.setString(7, status.toString());
        preparedStatement.setLong(8, orderId);
        preparedStatement.executeUpdate();
    }

    public void delete(Long orderId) throws SQLException {
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        String deleteSQL = "DELETE FROM ORDERS WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, orderId);
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate();
    }

    public List<OrderDTO> filter(String filterDescription, Client filterClient, Status filterStatus)
            throws SQLException {
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        String selectSQL = "SELECT o.id AS oID, o.description AS description, o.datestart AS datestart, o.datefinish" +
                " AS datefinish, o.cost AS cost, o.status AS status, cl.id AS clID, cl.name AS clName, cl.surname AS" +
                " clSurname,cl.Patronymic AS clPatronymic,m.id AS mID,m.name AS mName,m.surname AS mSurname," +
                " m.Patronymic AS mPatronymic " +
                "FROM ORDERS o JOIN CLIENT cl ON o.client_id = cl.id " +
                "JOIN MECHANIC m ON o.mechanic_id = m.id WHERE 1=1 ";

        StringBuilder stringBuilder = new StringBuilder(selectSQL);
        Map<Integer, String> indexValueMap = new HashMap<>();
        byte index = 0;

        if (filterDescription != null && !filterDescription.isEmpty()) {
            stringBuilder.append(" AND ").append("description = ? ");
            indexValueMap.put((int)++index, filterDescription);
        }
        if (filterClient != null && filterClient.getId() != null) {
            stringBuilder.append(" AND ").append("cl.id = ? ");
            indexValueMap.put((int)++index, filterClient.getId().toString());
        }
        if (filterStatus != null) {
            stringBuilder.append(" AND ").append("status = ? ");
            indexValueMap.put((int)++index, filterStatus.name());
        }

        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(stringBuilder.toString());

        indexValueMap.forEach((key, value) -> {
            try {
                preparedStatement.setString(key, value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ResultSet rsOrders = preparedStatement.executeQuery();
        OrderDTO currentOrder = null;
        while (rsOrders.next()) {
            Long orderId = rsOrders.getLong("oID");
            String description = rsOrders.getString("description");

            FullNameDTO client = new FullNameDTO(
                    rsOrders.getLong("clID"),
                    rsOrders.getString("clName"),
                    rsOrders.getString("clSurname"),
                    rsOrders.getString("clPatronymic"));

            FullNameDTO mechanic = new FullNameDTO(
                    rsOrders.getLong("mID"),
                    rsOrders.getString("mName"),
                    rsOrders.getString("mSurname"),
                    rsOrders.getString("mPatronymic"));

            LocalDate startDate = rsOrders.getDate("dateStart").toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = startDate.format(formatter);
            startDate = LocalDate.parse(text, formatter);

            LocalDate endDate = rsOrders.getDate("dateFinish").toLocalDate();
            text = endDate.format(formatter);
            endDate = LocalDate.parse(text, formatter);

            Double cost = rsOrders.getDouble("cost");
            Status status = Status.valueOf(rsOrders.getString("status"));
            currentOrder = new OrderDTO(orderId, description, client, mechanic, startDate, endDate, cost, status);
            orders.add(currentOrder);
        }
        return orders;
    }
}
