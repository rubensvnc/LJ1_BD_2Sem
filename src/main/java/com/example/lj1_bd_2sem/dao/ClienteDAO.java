package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    public void registrarCliente(Cliente c) throws SQLException{
        String sql = "INSERT INTO cliente (id, balanca) " +
                "VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getId());
            stmt.setDouble(2, c.getBalanca());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            throw e;
        }
    }
}
