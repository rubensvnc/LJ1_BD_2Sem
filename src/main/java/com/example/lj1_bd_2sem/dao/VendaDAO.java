package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Venda;

import java.sql.*;
import java.util.List;

public class VendaDAO {

    public void salvarVenda(List<Venda> vendaList){
        String sql = "INSERT INTO venda (produto_id, qtd, preco_venda) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Venda v: vendaList) {
                stmt.setInt(1, v.getProdutoId());
                stmt.setInt(2, v.getQtd());
                stmt.setDouble(3, v.getPrecoVenda());

                stmt.execute();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuario: " + e.getMessage());
        }
    }
}
