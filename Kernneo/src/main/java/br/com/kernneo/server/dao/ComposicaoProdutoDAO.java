package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.ComposicaoModel;
import br.com.kernneo.client.model.ComposicaoProdutoModel;
import br.com.kernneo.client.model.EstoqueLancamentoModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.server.ConnectFactory;

public class ComposicaoProdutoDAO extends GenericDAO<ComposicaoProdutoModel> {

    public ArrayList<ComposicaoProdutoModel> obterProdutosDaComposicao(ComposicaoModel composicaoModel) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.ComposicaoProdutoModel p where p.composicao=:composicao and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setEntity("composicao", composicaoModel);
	select.setBoolean("deletado", false);
	ArrayList<ComposicaoProdutoModel> lista = (ArrayList<ComposicaoProdutoModel>) select.list();
	
	return lista;
    }

}
