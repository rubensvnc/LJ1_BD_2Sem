package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {

    public void atualizar(Agenda agenda) {
        String sql = "UPDATE agenda SET profissional_id = ?, servico_id = ?, hora_agendado = ?, status_conclusao = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, agenda.getProfissionalId());
            pstmt.setInt(2, agenda.getServicoId());
            pstmt.setTime(3, Time.valueOf(agenda.getHoraAgendado()));
            pstmt.setString(4, agenda.getStatusConclusao());
            pstmt.setInt(5, agenda.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPago(int agendaId) {
        String sql = "SELECT pago FROM agenda WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, agendaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getBoolean("pago");
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void atualizarPago(int agendaId, boolean pago) {
        String sql = "UPDATE agenda SET pago = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, pago);
            stmt.setInt(2, agendaId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<AgendamentoDTO> listarConcluidosNaoPagosPorProfissional(int profissionalId) {
        List<AgendamentoDTO> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.hora_agendado, u.nome as cliente_nome, p.nome as profissional_nome, s.nome as servico_nome, a.status_conclusao " +
                "FROM agenda a " +
                "JOIN usuario u ON a.cliente_id = u.id " +
                "JOIN usuario p ON a.profissional_id = p.id " +
                "JOIN servico s ON a.servico_id = s.id " +
                "WHERE a.profissional_id = ? AND a.status_conclusao = 'CONCLUIDO' AND a.pago = FALSE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profissionalId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AgendamentoDTO dto = new AgendamentoDTO();
                dto.setId(rs.getInt("id"));
                dto.setHoraAgendado(rs.getTime("hora_agendado").toLocalTime());
                dto.setClienteNome(rs.getString("cliente_nome"));
                dto.setProfissionalNome(rs.getString("profissional_nome"));
                dto.setServicoNome(rs.getString("servico_nome"));
                dto.setStatusConclusao(rs.getString("status_conclusao"));
                lista.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void inserir(Agenda agenda) {
        String sql = "INSERT INTO agenda (cliente_id, profissional_id, servico_id, hora_agendado, status_conclusao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, agenda.getClienteId());
            pstmt.setInt(2, agenda.getProfissionalId());
            pstmt.setInt(3, agenda.getServicoId());
            pstmt.setTime(4, Time.valueOf(agenda.getHoraAgendado()));
            pstmt.setString(5, agenda.getStatusConclusao());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                agenda.setId(rs.getInt(1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<AgendamentoDTO> listarPorCliente(int clienteId) {
        List<AgendamentoDTO> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id, a.hora_agendado, u.nome as cliente_nome, p.nome as profissional_nome, s.nome as servico_nome, a.status_conclusao " +
                "FROM agenda a " +
                "JOIN usuario u ON a.cliente_id = u.id " +
                "JOIN usuario p ON a.profissional_id = p.id " +
                "JOIN servico s ON a.servico_id = s.id " +
                "WHERE a.cliente_id = ? ORDER BY a.hora_agendado";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clienteId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AgendamentoDTO dto = new AgendamentoDTO();
                dto.setId(rs.getInt("id"));
                dto.setHoraAgendado(rs.getTime("hora_agendado").toLocalTime());
                dto.setClienteNome(rs.getString("cliente_nome"));
                dto.setProfissionalNome(rs.getString("profissional_nome"));
                dto.setServicoNome(rs.getString("servico_nome"));
                dto.setStatusConclusao(rs.getString("status_conclusao"));
                agendamentos.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return agendamentos;
    }

    public List<AgendamentoDTO> listarTodos() {
        List<AgendamentoDTO> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id, a.hora_agendado, u.nome as cliente_nome, p.nome as profissional_nome, s.nome as servico_nome, a.status_conclusao " +
                "FROM agenda a " +
                "JOIN usuario u ON a.cliente_id = u.id " +
                "JOIN usuario p ON a.profissional_id = p.id " +
                "JOIN servico s ON a.servico_id = s.id ORDER BY a.hora_agendado";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                AgendamentoDTO dto = new AgendamentoDTO();
                dto.setId(rs.getInt("id"));
                dto.setHoraAgendado(rs.getTime("hora_agendado").toLocalTime());
                dto.setClienteNome(rs.getString("cliente_nome"));
                dto.setProfissionalNome(rs.getString("profissional_nome"));
                dto.setServicoNome(rs.getString("servico_nome"));
                dto.setStatusConclusao(rs.getString("status_conclusao"));
                agendamentos.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return agendamentos;
    }

    public List<AgendamentoDTO> listarPorStatus(String status) {
        List<AgendamentoDTO> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id, a.hora_agendado, u.nome as cliente_nome, p.nome as profissional_nome, s.nome as servico_nome, a.status_conclusao " +
                "FROM agenda a " +
                "JOIN usuario u ON a.cliente_id = u.id " +
                "JOIN usuario p ON a.profissional_id = p.id " +
                "JOIN servico s ON a.servico_id = s.id " +
                "WHERE a.status_conclusao = ? ORDER BY a.hora_agendado";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AgendamentoDTO dto = new AgendamentoDTO();
                dto.setId(rs.getInt("id"));
                dto.setHoraAgendado(rs.getTime("hora_agendado").toLocalTime());
                dto.setClienteNome(rs.getString("cliente_nome"));
                dto.setProfissionalNome(rs.getString("profissional_nome"));
                dto.setServicoNome(rs.getString("servico_nome"));
                dto.setStatusConclusao(rs.getString("status_conclusao"));
                agendamentos.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return agendamentos;
    }

    public void atualizarStatus(int agendaId, String novoStatus) {
        String sql = "UPDATE agenda SET status_conclusao = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, agendaId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Agenda buscarPorId(int id) {
        String sql = "SELECT * FROM agenda WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Agenda a = new Agenda();
                a.setId(rs.getInt("id"));
                a.setClienteId(rs.getInt("cliente_id"));
                a.setProfissionalId(rs.getInt("profissional_id"));
                a.setServicoId(rs.getInt("servico_id"));
                a.setHoraAgendado(rs.getTime("hora_agendado").toLocalTime());
                a.setStatusConclusao(rs.getString("status_conclusao"));
                return a;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public double calcularFaturamentoPorStatus(String status) {
        String sql = "SELECT SUM(s.valor) FROM agenda a JOIN servico s ON a.servico_id = s.id WHERE a.status_conclusao = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }
}