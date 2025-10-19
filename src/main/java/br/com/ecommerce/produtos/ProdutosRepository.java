package br.com.ecommerce.produtos;

import br.com.ecommerce.db.ConnectionProvider;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutosRepository {
    private final ConnectionProvider provider;

    public ProdutosRepository(ConnectionProvider provider) {
        this.provider = provider;
    }

    // Busca os N primeiros produtos da tabela `produtos`
    public List<Produto> listarPrimeiros(int limit) {
        String sql = """
            SELECT id, nome, descricao, preco, quantidade_estoque, data_cadastro
            FROM produtos
            ORDER BY id
            LIMIT ?
        """;
        List<Produto> lista = new ArrayList<>();

        try (Connection con = provider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setPreco(rs.getBigDecimal("preco"));
                    p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

                    Timestamp ts = rs.getTimestamp("data_cadastro");
                    p.setDataCadastro(ts != null ? ts.toLocalDateTime() : null);

                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return lista;
    }
}
