package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.server.ConnectFactory;

public class CategoriaDAO extends GenericDAO<CategoriaModel> {

	public ArrayList<CategoriaModel> obterTodasAtivas() throws SQLException {
		String sql = "select p FROM br.com.kernneo.client.model.CategoriaModel p where ativo=:ativo and deletado=:deletado";
		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setBoolean("ativo", true);
		select.setBoolean("deletado", false);
		ArrayList<CategoriaModel> lista = (ArrayList<CategoriaModel>) select.list();

		return lista;
	}

}
