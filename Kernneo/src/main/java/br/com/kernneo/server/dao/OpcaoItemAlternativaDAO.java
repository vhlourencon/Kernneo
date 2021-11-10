package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.server.ConnectFactory;

public class OpcaoItemAlternativaDAO extends GenericDAO<OpcaoItemAlternativaModel> {

	public ArrayList<OpcaoItemAlternativaModel> obterTodosPorOpcaoItem(OpcaoItemModel opcaoItemModel) throws SQLException {

		String sql = "select o FROM br.com.kernneo.client.model.OpcaoItemAlternativaModel o where o.opcaoItem=:opcaoItem and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("opcaoItem", opcaoItemModel);
		select.setBoolean("deletado", false);
		ArrayList<OpcaoItemAlternativaModel> lista = (ArrayList<OpcaoItemAlternativaModel>) select.list();

		return lista;
	}

}
