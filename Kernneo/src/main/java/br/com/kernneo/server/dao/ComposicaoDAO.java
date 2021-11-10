package br.com.kernneo.server.dao;

import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.ComposicaoModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.ConnectFactory;

public class ComposicaoDAO extends GenericDAO<ComposicaoModel> {

    public ComposicaoModel obterComposicaoPorProduto(ProdutoModel produtoModel) throws SQLException{
	String sql = "select u FROM br.com.kernneo.client.model.ComposicaoModel u where u.produto=:produto and deletado=:deletado";
	Session session = ConnectFactory.getSession(); 
	Query select = session.createQuery(sql);
	select.setEntity("produto", produtoModel);
	select.setBoolean("deletado", false);
	ComposicaoModel model = (ComposicaoModel) select.uniqueResult();
	
	return model;
    }

}
