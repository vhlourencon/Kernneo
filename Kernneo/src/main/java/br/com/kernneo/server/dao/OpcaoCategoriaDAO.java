package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoCategoriaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.server.ConnectFactory;

public class OpcaoCategoriaDAO extends GenericDAO<OpcaoCategoriaModel> {

	public ArrayList<OpcaoCategoriaModel> obterTodosPorCategoria(CategoriaModel model) throws SQLException {

		String sql = "select o FROM br.com.kernneo.client.model.OpcaoCategoriaModel o where o.categoria=:categoria and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("categoria", model);
		select.setBoolean("deletado", false);
		ArrayList<OpcaoCategoriaModel> lista = (ArrayList<OpcaoCategoriaModel>) select.list();

		return lista;
	}

}
