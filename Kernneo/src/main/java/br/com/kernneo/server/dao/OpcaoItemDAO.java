package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CstIpiModel;
import br.com.kernneo.client.model.CstPisCofinsModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.LSTModel;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.server.ConnectFactory;

public class OpcaoItemDAO extends GenericDAO<OpcaoItemModel> {

	public ArrayList<OpcaoItemModel> obterTodosPorItem(ItemModel itemModel) throws SQLException {

		String sql = "select o FROM br.com.kernneo.client.model.OpcaoItemModel o where o.item=:item and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("item", itemModel);
		select.setBoolean("deletado", false);
		ArrayList<OpcaoItemModel> lista = (ArrayList<OpcaoItemModel>) select.list();

		return lista;
	}

}
