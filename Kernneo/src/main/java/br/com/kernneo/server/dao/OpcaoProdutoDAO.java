package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoCategoriaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.client.model.OpcaoProdutoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.ConnectFactory;

public class OpcaoProdutoDAO extends GenericDAO<OpcaoProdutoModel> {

	public ArrayList<OpcaoProdutoModel> obterTodosPorProduto(ProdutoModel model) throws SQLException {

		String sql = "select o FROM br.com.kernneo.client.model.OpcaoProdutoModel o where o.produto=:produto and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("produto", model);
		select.setBoolean("deletado", false);
		ArrayList<OpcaoProdutoModel> lista = (ArrayList<OpcaoProdutoModel>) select.list();

		return lista;
	}

}
