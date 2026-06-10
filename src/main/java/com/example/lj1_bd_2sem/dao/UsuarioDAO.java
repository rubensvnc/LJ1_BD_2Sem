package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario fazerLogin(String login, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE login=? AND senha=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setPerfil(rs.getString("perfil"));
                u.setSenha(rs.getString("senha"));
                u.setLogin(rs.getString("login"));
                u.setNome(rs.getString("nome"));
                u.setTipoProfissional(rs.getString("tipo_profissional"));
                return u;
            }
        } catch (SQLException e) { throw e; }
        return null;
    }

    public List<Usuario> listarProfissionais() {
        return listarProfissionaisPorTipo(null);
    }

    public List<Usuario> listarProfissionaisPorTipo(String tipo) {
        List<Usuario> profissionais = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE perfil = 'PROFISSIONAL'";
        if (tipo != null && !tipo.isEmpty()) {
            sql += " AND tipo_profissional = ?";
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (tipo != null && !tipo.isEmpty()) {
                stmt.setString(1, tipo);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setPerfil(rs.getString("perfil"));
                u.setTipoProfissional(rs.getString("tipo_profissional"));
                profissionais.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return profissionais;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setPerfil(rs.getString("perfil"));
                u.setTipoProfissional(rs.getString("tipo_profissional"));
                return u;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public int registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, login, senha, perfil, tipo_profissional) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());
            stmt.setString(5, usuario.getTipoProfissional());
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    usuario.setId(id);
                    return id;
                }
            }
            throw new SQLException("Falha ao inserir usuário");
        } catch (SQLException e) { throw e; }
    }
}