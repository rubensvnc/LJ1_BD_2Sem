package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public Usuario fazerLogin(String login, String senha) throws SQLException{
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
                return u;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar datas bloqueadas: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public int registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, login, senha, perfil) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1);
                        usuario.setId(idGerado);
                        return idGerado;
                    } else {
                        throw new SQLException("Falha ao obter o ID gerado.");
                    }
                }
            } else {
                throw new SQLException("Falha ao inserir usuário, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuario: " + e.getMessage());
            throw e;
        }
    }
}
