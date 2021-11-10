package br.com.kernneo.server.dao;

import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.ConnectFactory;

public class EstoqueDAO extends GenericDAO<EstoqueModel> {

    public EstoqueModel obterEstoquePorProduto(ProdutoModel produtoModel) throws SQLException {
	String sql = "select u FROM br.com.kernneo.client.model.EstoqueModel u where u.produto=:produto and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setEntity("produto", produtoModel);
	select.setBoolean("deletado", false);
	EstoqueModel model = (EstoqueModel) select.uniqueResult();

	return model;
    }

}
