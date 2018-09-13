package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.dto.FullNameDTO;
import com.haulmont.testtask.model.Mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicDAO {

    private static MechanicDAO instance;

    private MechanicDAO() {
    }

    public static MechanicDAO getInstance() {
        if (instance == null) {
            instance = new MechanicDAO();
        }
        return instance;
    }

    public List<Mechanic> getAll() throws SQLException {
        List<Mechanic> data = new ArrayList<Mechanic>();
        String selectSQL = "SELECT id, name, surname, patronymic, hourlyPay FROM MECHANIC";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        while (rsMechanic.next()) {
            Long mechanicId = rsMechanic.getLong("id");
            String name = rsMechanic.getString("name");
            String surname = rsMechanic.getString("surname");
            String patronymic = rsMechanic.getString("patronymic");
            Double hourlypay = rsMechanic.getDouble("hourlyPay");
            currentMechanic = new Mechanic(mechanicId, name, surname, patronymic, hourlypay);
            data.add(currentMechanic);
        }
        return data;
    }

    public List<FullNameDTO> getAllFullName() throws SQLException {
        List<FullNameDTO> data = new ArrayList<>();
        String selectSQL = "SELECT id, name, surname, patronymic FROM MECHANIC";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        FullNameDTO currentMechanic;
        while (rsMechanic.next()) {
            Long mechanicId = rsMechanic.getLong("id");
            String name = rsMechanic.getString("name");
            String surname = rsMechanic.getString("surname");
            String patronymic = rsMechanic.getString("patronymic");
            currentMechanic = new FullNameDTO(mechanicId, name, surname, patronymic);
            data.add(currentMechanic);
        }
        return data;
    }

    public Mechanic get(Long mechanicId) throws SQLException {
        String selectSQL = "SELECT * FROM MECHANIC WHERE ID = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, mechanicId);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        if (rsMechanic.next()) {
            String name = rsMechanic.getString("name");
            String surname = rsMechanic.getString("surname");
            String patronymic = rsMechanic.getString("patronymic");
            Double hourlyPay = rsMechanic.getDouble("hourlyPay");
            currentMechanic = new Mechanic(mechanicId, name, surname, patronymic, hourlyPay);
        }
        return currentMechanic;
    }

    public void edit(Long mechanicId, String name, String surname, String patronymic, Double hourlyPay)
            throws SQLException {
        String updateTableSQL = "UPDATE MECHANIC SET NAME= ?,SURNAME= ?,PATRONYMIC= ?,HOURLYPAY= ? WHERE id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setDouble(4, hourlyPay);
        preparedStatement.setLong(5, mechanicId);
        preparedStatement.executeUpdate();
    }

    public void create(String name, String surname, String patronymic, Double hourlyPay)
            throws SQLException {
        String insertTableSQL = "INSERT INTO MECHANIC"
                + "(NAME, SURNAME, PATRONYMIC, HOURLYPAY) VALUES"
                + "(?,?,?,?)";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setDouble(4, hourlyPay);
        preparedStatement.executeUpdate();
    }

    public void delete(Long mechanicId) throws SQLException {
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        String deleteSQL = "DELETE FROM MECHANIC WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, mechanicId);
        preparedStatement.executeUpdate();
    }

    public Long statistic(Long mechanicId) throws SQLException {
        String selectSQL = "SELECT COUNT(mechanic_id) AS count_orders FROM ORDERS WHERE mechanic_id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, mechanicId);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Long count = Long.valueOf(0);
        if (rsMechanic.next()) {
            count = rsMechanic.getLong("count_orders");
        }
        return count;
    }
}
