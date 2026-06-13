package com.example.lj1_bd_2sem.dao;

import com.example.lj1_bd_2sem.DatabaseConnection;
import com.example.lj1_bd_2sem.model.Produto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO {

    public List<Produto> listarProdutosMercado(){
        String sql = "SELECT * FROM produto " +
                "WHERE tipo = 'MERCADO'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            List<Produto> produtoList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setPrecoBase(rs.getDouble("preco_base"));
                p.setValidade(rs.getObject("validade", LocalDate.class));
                p.setTipo(rs.getString("tipo"));
                p.setQtd(rs.getInt("qtd"));

                produtoList.add(p);
            }
            return produtoList;
        } catch (SQLException e) {
            System.err.println("Erro ao listar datas bloqueadas: " + e.getMessage());
        }
        return null;
    }


    public List<Produto> listarProdutosFarmacia(){
        String sql = "SELECT * FROM produto " +
                "WHERE tipo = 'FARMACIA'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            List<Produto> produtoList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setPrecoBase(rs.getDouble("preco_base"));
                p.setValidade(rs.getObject("validade", LocalDate.class));
                p.setTipo(rs.getString("tipo"));
                p.setQtd(rs.getInt("qtd"));

                produtoList.add(p);
            }
            return produtoList;
        } catch (SQLException e) {
            System.err.println("Erro ao listar datas bloqueadas: " + e.getMessage());
        }
        return null;
    }

    public List<Integer> recuperarIds(List<String> codigo_barras){
        String sql = "SELECT id FROM produto " +
                "WHERE codigo_barras = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            List<Integer> ids = new ArrayList<>();
            for (String codigo: codigo_barras){
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, codigo);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    ids.add(rs.getInt("id"));
                }
            }

            return ids;
        } catch (SQLException e) {
            System.err.println("Erro ao listar datas bloqueadas: " + e.getMessage());
        }
        return null;
    }

    public int salvarProduto(Produto p) throws SQLException{
        String sql = "INSERT INTO produto (codigo_barras, nome, preco_base, validade, tipo, qtd) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getCodigoBarras());
            stmt.setString(2, p.getNome());
            stmt.setDouble(3, p.getPrecoBase());
            stmt.setObject(4, p.getValidade());
            stmt.setString(5, p.getTipo());
            stmt.setInt(6, p.getQtd());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1);
                        p.setId(idGerado);
                        return idGerado;
                    } else {
                        throw new SQLException("Falha ao obter o ID gerado.");
                    }
                }
            } else {
                throw new SQLException("Falha ao inserir produto, nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
            throw e;
        }
    }

    public void atualizarValoresProduto(List<Produto> produtoList) throws SQLException{
        String sql = "UPDATE produto SET qtd = ? WHERE codigo_barras = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (Produto p: produtoList){
                stmt.setInt(1, p.getQtd());
                stmt.setString(2, p.getCodigoBarras());
                stmt.execute();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            throw e;
        }
    }

}
