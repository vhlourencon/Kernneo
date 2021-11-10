package br.com.kernneo.server.caravana.evento.permissaoAgendamento;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.CaravanaUsuarioModel;
import br.com.kernneo.client.caravana.model.EventoPermissaoAgendamentoModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class EventoPermissaoAgendamentoDAO extends GenericDAO<EventoPermissaoAgendamentoModel> {

	public ArrayList<EventoPermissaoAgendamentoModel> obterTodosPorEvento(CaravanaEventoModel eventoModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.EventoPermissaoAgendamentoModel e where e.evento=:evento and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setBoolean("deletado", false);
		ArrayList<EventoPermissaoAgendamentoModel> lista = (ArrayList<EventoPermissaoAgendamentoModel>) select.list();

		return lista;
	}

	public ArrayList<EventoPermissaoAgendamentoModel> obterTodosPorEventoEusuario(CaravanaEventoModel eventoModel, CaravanaUsuarioModel usuarioModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.EventoPermissaoAgendamentoModel e where e.evento=:evento and e.usuarioPermitido=:usuario and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setEntity("usuario", usuarioModel);
		select.setBoolean("deletado", false);
		ArrayList<EventoPermissaoAgendamentoModel> lista = (ArrayList<EventoPermissaoAgendamentoModel>) select.list();

		return lista;
	}
}
