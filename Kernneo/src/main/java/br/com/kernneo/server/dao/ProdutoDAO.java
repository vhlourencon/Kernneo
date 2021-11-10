package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.ConnectFactory;

public class ProdutoDAO extends GenericDAO<ProdutoModel> {

	public ArrayList<ProdutoModel> obterTodosAtivosPorCategoria(CategoriaModel categoria) throws SQLException {
		String sql = "select p FROM br.com.kernneo.client.model.ProdutoModel p  where categoria=:categoria and ativo=:ativo and deletado=:deletado";
		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setBoolean("ativo", true);
		select.setBoolean("deletado", false);
		select.setEntity("categoria", categoria);
		ArrayList<ProdutoModel> lista = (ArrayList<ProdutoModel>) select.list();

		return lista;
	}

	public ProdutoModel obterPorCodigo(Long codigo) {
		String sql = "select p FROM br.com.kernneo.client.model.ProdutoModel p where p.codigo=:codigo and deletado=:deletado";
		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setLong("codigo", codigo);
		select.setBoolean("deletado", false);
		ProdutoModel model = (ProdutoModel) select.uniqueResult();

		return model;
	}

	public Long obterUltimoCodigo() throws SQLException {

		Session session = ConnectFactory.getSession();

		org.hibernate.Query select = session.createQuery("SELECT max(codigo) from br.com.kernneo.client.model.ProdutoModel where deletado=:deletado ");
		select.setBoolean("deletado", false);

		Long maxId = (Long) select.uniqueResult();
		if (maxId == null) {
			maxId = 0L;
		}

		return maxId;

	}

}
