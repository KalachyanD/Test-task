package dao;

import dao.dto.ClientMechanicDTO;
import dao.dto.OrderDTO;
import models.Status;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

            ClientMechanicDTO client = new ClientMechanicDTO(
                    rsOrders.getLong("clID"),
                    rsOrders.getString("clName"),
                    rsOrders.getString("clSurname"),
                    rsOrders.getString("clPatronymic"));

            ClientMechanicDTO mechanic = new ClientMechanicDTO(
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

    public OrderDTO load(long id) throws SQLException {
        String selectSQL = "SELECT o.id AS oID, o.description AS description, o.datestart AS datestart, o.datefinish" +
                " AS datefinish, o.cost AS cost, o.status AS status, cl.id AS clID,cl.name AS clName, cl.surname AS" +
                " clSurname,cl.Patronymic AS clPatronymic,m.id AS mID,m.name AS mName,m.surname AS mSurname," +
                " m.Patronymic AS mPatronymic FROM ORDERS AS o JOIN CLIENT AS cl ON o.client_id = cl.id JOIN" +
                " MECHANIC AS m ON o.mechanic_id = m.idWHERE o.id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, id);
        ResultSet rsOrders = preparedStatement.executeQuery();
        OrderDTO currentOrder = null;
        if (rsOrders.next()) {
            Long orderID = rsOrders.getLong("oID");
            String description = rsOrders.getString("description");

            ClientMechanicDTO client = new ClientMechanicDTO(
                    rsOrders.getLong("clID"),
                    rsOrders.getString("clName"),
                    rsOrders.getString("clSurname"),
                    rsOrders.getString("clPatronymic"));

            ClientMechanicDTO mechanic = new ClientMechanicDTO(
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

    public void store(String description, long clientID, long mechanicID, LocalDate startDate,
                      LocalDate endDate, double cost, Status status) throws SQLException {
        String insertTableSQL = "INSERT INTO ORDERS"
                + "(DESCRIPTION, CLIENT_ID, MECHANIC_ID, DATESTART, DATEFINISH, COST, STATUS) VALUES"
                + "(?,?,?,?,?,?,?)";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, description);
        preparedStatement.setLong(2, clientID);
        preparedStatement.setLong(3, mechanicID);
        preparedStatement.setDate(4, Date.valueOf(startDate));
        preparedStatement.setDate(5, Date.valueOf(endDate));
        preparedStatement.setDouble(6, cost);
        preparedStatement.setString(7, status.toString());
        preparedStatement.executeUpdate();
    }

    public void update(long orderID, String description, long clientID, long mechanicID, LocalDate startDate,
                       LocalDate endDate, double cost, Status status) throws SQLException {
        String updateTableSQL = "UPDATE ORDERS SET DESCRIPTION= ?, CLIENT_ID= ?, MECHANIC_ID= ?, DATESTART= ?, DATEFINISH= ?, COST= ?, STATUS = ? WHERE id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
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
    }

    public void delete(long orderID) throws SQLException {
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        String deleteSQL = "DELETE FROM ORDERS WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, orderID);
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate();
    }
}