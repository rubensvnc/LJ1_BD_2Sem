package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Venda;
import java.sql.*;
import java.util.List;

public class VendaDAO {
    public void salvarVenda(List<Venda> vendaList) {
        String sql = "INSERT INTO venda (cliente_id, produto_id, qtd, preco_venda) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Venda v : vendaList) {
                stmt.setInt(1, v.getClienteId());
                stmt.setInt(2, v.getProdutoId());
                stmt.setInt(3, v.getQtd());
                stmt.setDouble(4, v.getPrecoVenda());
                stmt.execute();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public double calcularFaturamentoPorTipo(String tipo) {
        String sql = "SELECT SUM(v.preco_venda) FROM venda v " +
                "JOIN produto p ON v.produto_id = p.id " +
                "WHERE p.tipo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }
}