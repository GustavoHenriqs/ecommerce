package br.com.ecommerce;

import br.com.ecommerce.db.MySqlConnectionProvider;
import br.com.ecommerce.db.ConnectionProvider;
import br.com.ecommerce.produtos.ProdutosRepository;
import br.com.ecommerce.produtos.Produto;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectionProvider provider =
                new MySqlConnectionProvider("127.0.0.1", 3306, "ecommerce", "root", "TROQUE A SENHA PROFE");

        ProdutosRepository repo = new ProdutosRepository(provider);
        List<Produto> produtos = repo.listarPrimeiros(5);

        // print dos 5 produtos
        produtos.forEach(System.out::println);
    }
}
