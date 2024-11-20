package com.example.estoquepicole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (name, price, amount, brand, tipo, cnpj) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getName());
            stmt.setDouble(2, produto.getPrice());
            stmt.setInt(3, produto.getAmount());
            stmt.setString(4, produto.getBrand());
            stmt.setString(5, produto.getTipo());
            stmt.setString(6, produto.getCnpj());
            stmt.executeUpdate();
        }
    }

    public void remover(Produto produto) throws SQLException {
        String sql = "DELETE FROM produtos WHERE name = ? AND price = ? AND amount = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getName());
            stmt.setDouble(2, produto.getPrice());
            stmt.setInt(3, produto.getAmount());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Produto produtoAntigo, Produto produtoNovo) throws SQLException {
        String sql = "UPDATE produtos SET name = ?, price = ?, amount = ? WHERE name = ? AND price = ? AND amount = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produtoNovo.getName());
            stmt.setDouble(2, produtoNovo.getPrice());
            stmt.setInt(3, produtoNovo.getAmount());
            stmt.setString(6, produtoAntigo.getName());
            stmt.setDouble(7, produtoAntigo.getPrice());
            stmt.setInt(8, produtoAntigo.getAmount());
            stmt.executeUpdate();
        }
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                String brand = rs.getString("brand");
                String tipo = rs.getString("tipo");
                String cnpj = rs.getString("cnpj");
                produtos.add(new Produto(name, price, amount, brand, tipo, cnpj));
            }
        }
        return produtos;
    }
}