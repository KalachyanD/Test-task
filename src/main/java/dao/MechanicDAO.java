package dao;

import models.Mechanic;

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

    public List<Mechanic> LoadAll() throws SQLException {
        List<Mechanic> data = new ArrayList<Mechanic>();
        String selectSQL = "SELECT id, name, surname, patronymic, hourlyPay FROM MECHANIC";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        while (rsMechanic.next()) {
            Long mechanicID = rsMechanic.getLong("id");
            String name = rsMechanic.getString("name");
            String surname = rsMechanic.getString("surname");
            String patronymic = rsMechanic.getString("patronymic");
            Long hourlypay = rsMechanic.getLong("hourlyPay");
            currentMechanic = new Mechanic(mechanicID, name, surname, patronymic, hourlypay);
            data.add(currentMechanic);
        }
        return data;
    }

    public Mechanic load(long mechanicID) throws SQLException {
        String selectSQL = "SELECT * FROM MECHANIC WHERE ID = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, mechanicID);
        ResultSet rsMechanic = preparedStatement.executeQuery();
        Mechanic currentMechanic = null;
        if (rsMechanic.next()) {
            String name = rsMechanic.getString("name");
            String surname = rsMechanic.getString("surname");
            String patronymic = rsMechanic.getString("patronymic");
            Long hourlypay = rsMechanic.getLong("hourlyPay");
            currentMechanic = new Mechanic(mechanicID, name, surname, patronymic, hourlypay);
        }
        return currentMechanic;
    }

    public void update(long mechanicID, String name, String surname, String patronymic, double hourlypay)
            throws SQLException {
        String updateTableSQL = "UPDATE MECHANIC SET NAME= ?,SURNAME= ?,PATRONYMIC= ?,HOURLYPAY= ? WHERE id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setDouble(4, hourlypay);
        preparedStatement.setLong(5, mechanicID);
        preparedStatement.executeUpdate();
    }

    public void store(String name, String surname, String patronymic, double hourlypay)
            throws SQLException {
        String insertTableSQL = "INSERT INTO MECHANIC"
                + "(NAME, SURNAME, PATRONYMIC, HOURLYPAY) VALUES"
                + "(?,?,?,?)";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setDouble(4, hourlypay);
        preparedStatement.executeUpdate();
    }

    public void delete(long mechanicID) throws SQLException {
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        String deleteSQL = "DELETE FROM MECHANIC WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, mechanicID);
        preparedStatement.executeUpdate();
    }
}
