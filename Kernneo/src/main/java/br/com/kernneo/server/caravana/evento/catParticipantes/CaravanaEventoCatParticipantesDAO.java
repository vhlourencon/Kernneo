package br.com.kernneo.server.caravana.evento.catParticipantes;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.EventoCategoriaPartModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class CaravanaEventoCatParticipantesDAO extends GenericDAO<EventoCategoriaPartModel> {

	public ArrayList<EventoCategoriaPartModel> obterTodosPorEvento(CaravanaEventoModel eventoModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.EventoCategoriaPartModel e where e.evento=:evento and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setBoolean("deletado", false);
		ArrayList<EventoCategoriaPartModel> lista = (ArrayList<EventoCategoriaPartModel>) select.list();

		return lista;
	}

}
