package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Cliente;
import java.sql.*;

public class ClienteDAO {
    public void registrarCliente(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (id, balanca) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getId());
            stmt.setDouble(2, c.getBalanca());
            stmt.executeUpdate();
        }
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setBalanca(rs.getDouble("balanca"));
                return c;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void atualizarSaldo(int clienteId, double novoSaldo) {
        String sql = "UPDATE cliente SET balanca = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, novoSaldo);
            pstmt.setInt(2, clienteId);
            int rows = pstmt.executeUpdate();
            System.out.println("Atualização de saldo: " + rows + " linha(s) afetada(s) para cliente " + clienteId + " -> " + novoSaldo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void recarregarSaldo(int clienteId, double valor) {
        Cliente c = buscarPorId(clienteId);
        if (c != null) {
            atualizarSaldo(clienteId, c.getBalanca() + valor);
        }
    }
}