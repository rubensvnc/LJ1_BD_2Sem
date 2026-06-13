package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Remedio;

import java.sql.*;

public class RemedioDAO {

    public void inserirRemedio(Remedio remedio) throws SQLException {
        String sql = "INSERT INTO remedio (id_produto, usa_receita) " +
                "VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, remedio.getIdProduto());
            stmt.setBoolean(2, remedio.getUsaReceita());

            stmt.execute();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuario: " + e.getMessage());
            throw e;
        }
    }

    public Remedio buscarRemedioPorCodigoBarras(String codigoBarras) {
        String sql = "SELECT r.* FROM remedio r " +
                "INNER JOIN produto p ON r.id_produto = p.id " +
                "WHERE p.codigo_barras = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigoBarras);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Remedio remedio = new Remedio();
                    remedio.setIdProduto(rs.getInt("id_produto"));
                    remedio.setUsaReceita(rs.getBoolean("usa_receita"));
                    remedio.setCrmMedico(rs.getString("crm_medico"));
                    return remedio;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
