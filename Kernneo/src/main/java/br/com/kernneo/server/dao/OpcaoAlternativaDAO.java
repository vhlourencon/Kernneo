package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.server.ConnectFactory;

public class OpcaoAlternativaDAO extends GenericDAO<OpcaoAlternativaModel> {

	public ArrayList<OpcaoAlternativaModel> obterTodosPorOpcao(OpcaoModel opcaoModel) throws SQLException {

		String sql = "select o FROM br.com.kernneo.client.model.OpcaoAlternativaModel o where o.opcao=:opcao and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("opcao", opcaoModel);
		select.setBoolean("deletado", false);
		ArrayList<OpcaoAlternativaModel> lista = (ArrayList<OpcaoAlternativaModel>) select.list();

		return lista;
	}

}
