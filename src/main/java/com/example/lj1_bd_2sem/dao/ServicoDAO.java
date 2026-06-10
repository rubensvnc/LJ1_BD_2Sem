package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    public List<Servico> listarTodos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setValor(rs.getDouble("valor"));
                s.setDuracao(rs.getInt("duracao"));
                servicos.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return servicos;
    }
    public Servico buscarPorId(int id) {
        String sql = "SELECT * FROM servico WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setValor(rs.getDouble("valor"));
                s.setDuracao(rs.getInt("duracao"));
                return s;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public void inserir(Servico servico) {
        String sql = "INSERT INTO servico (nome, valor, duracao) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, servico.getNome());
            pstmt.setDouble(2, servico.getValor());
            pstmt.setInt(3, servico.getDuracao());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) servico.setId(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public void atualizar(Servico servico) {
        String sql = "UPDATE servico SET nome = ?, valor = ?, duracao = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, servico.getNome());
            pstmt.setDouble(2, servico.getValor());
            pstmt.setInt(3, servico.getDuracao());
            pstmt.setInt(4, servico.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public void deletar(int id) {
        String sql = "DELETE FROM servico WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}