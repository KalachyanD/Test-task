package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.dto.FullNameDTO;
import com.haulmont.testtask.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private static ClientDAO instance;

    private ClientDAO() {
    }

    public static ClientDAO getInstance() {
        if (instance == null) {
            instance = new ClientDAO();
        }
        return instance;
    }

    public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<Client>();
        String selectSQL = "SELECT id,name,surname,patronymic,telephoneNumber FROM CLIENT";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsClients = preparedStatement.executeQuery();
        Client currentClient = null;
        while (rsClients.next()) {
            Long clientID = rsClients.getLong("id");
            String name = rsClients.getString("name");
            String surname = rsClients.getString("surname");
            String patronymic = rsClients.getString("patronymic");
            Long telephoneNumber = rsClients.getLong("telephoneNumber");
            currentClient = new Client(clientID, name, surname, patronymic, telephoneNumber);
            clients.add(currentClient);
        }
        return clients;
    }

    public List<FullNameDTO> getAllFullName() throws SQLException {
        List<FullNameDTO> clients = new ArrayList<>();
        String selectSQL = "SELECT id,name,surname,patronymic FROM CLIENT";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        ResultSet rsClients = preparedStatement.executeQuery();
        FullNameDTO currentClient = null;
        while (rsClients.next()) {
            Long clientID = rsClients.getLong("id");
            String name = rsClients.getString("name");
            String surname = rsClients.getString("surname");
            String patronymic = rsClients.getString("patronymic");
            currentClient = new FullNameDTO(clientID, name, surname, patronymic);
            clients.add(currentClient);
        }
        return clients;
    }

    public Client get(Long clientId) throws SQLException {
        String selectSQL = "SELECT id,name,surname,patronymic,telephoneNumber FROM CLIENT WHERE ID = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
        preparedStatement.setLong(1, clientId);
        ResultSet rsClients = preparedStatement.executeQuery();
        Client currentClient = null;
        if (rsClients.next()) {
            String name = rsClients.getString("name");
            String surname = rsClients.getString("surname");
            String patronymic = rsClients.getString("patronymic");
            Long telephoneNumber = rsClients.getLong("telephoneNumber");
            currentClient = new Client(clientId, name, surname, patronymic, telephoneNumber);
        }
        return currentClient;
    }

    public void create(String name, String surname, String patronymic, Long telephoneNumber)
            throws SQLException {
        String insertTableSQL = "INSERT INTO CLIENT"
                + "(NAME, SURNAME, PATRONYMIC, TELEPHONENUMBER) VALUES"
                + "(?,?,?,?)";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setLong(4, telephoneNumber);
        preparedStatement.executeUpdate();
    }

    public void delete(Long clientId) throws SQLException {
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        String deleteSQL = "DELETE FROM CLIENT WHERE ID = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
        preparedStatement.setLong(1, clientId);

        preparedStatement.executeUpdate();
    }

    public void edit(Long clientId, String name, String surname, String patronymic, Long telephoneNumber)
            throws SQLException {
        String updateTableSQL = "UPDATE CLIENT SET NAME= ?,SURNAME= ?,PATRONYMIC= ?,TELEPHONENUMBER= ? WHERE id = ?";
        Connection dbConnection = ConnectionDB.getInstance().getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(updateTableSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, patronymic);
        preparedStatement.setLong(4, telephoneNumber);
        preparedStatement.setLong(5, clientId);
        preparedStatement.executeUpdate();
    }
}
