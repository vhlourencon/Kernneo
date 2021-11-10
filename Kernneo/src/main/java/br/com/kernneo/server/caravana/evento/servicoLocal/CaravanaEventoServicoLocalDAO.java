package br.com.kernneo.server.caravana.evento.servicoLocal;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.EventoServicoLocalModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class CaravanaEventoServicoLocalDAO extends GenericDAO<EventoServicoLocalModel> {

	public ArrayList<EventoServicoLocalModel> obterTodosPorEvento(CaravanaEventoModel eventoModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.EventoServicoLocalModel e where e.evento=:evento and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setBoolean("deletado", false);
		ArrayList<EventoServicoLocalModel> lista = (ArrayList<EventoServicoLocalModel>) select.list();

		return lista;
	}

}
