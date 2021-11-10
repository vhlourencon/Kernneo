package br.com.kernneo.server.caravana.agendamento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.CaravanaParticipanteModel;
import br.com.kernneo.client.caravana.model.EventoPermissaoAgendamentoModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class CaravanaAgendamentoDAO extends GenericDAO<CaravanaAgendamentoModel> {

	public ArrayList<CaravanaAgendamentoModel> obterTodosPorEventoPermissaoAgendamento(EventoPermissaoAgendamentoModel eventoModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel e where e.permissaoAgendamento=:permissaoAgendamento and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("permissaoAgendamento", eventoModel);
		select.setBoolean("deletado", false);
		ArrayList<CaravanaAgendamentoModel> lista = (ArrayList<CaravanaAgendamentoModel>) select.list();

		return lista;
	}

	public ArrayList<CaravanaAgendamentoModel> obterAgendamentoPorEventoEinfoParticipante(CaravanaEventoModel eventoModel, String cpf, String cartaoSus, Date dataDeNascimento) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel e where e.permissaoAgendamento.evento=:evento and e.participante.cpf=:cpf and e.participante.cartaoSus=:cartaoSus and e.participante.dataDeNascimento=:dataDeNascimento and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setString("cpf", cpf);
		select.setString("cartaoSus", cartaoSus);
		select.setDate("dataDeNascimento", dataDeNascimento);
		select.setBoolean("deletado", false);
		ArrayList<CaravanaAgendamentoModel> lista = (ArrayList<CaravanaAgendamentoModel>) select.list();

		return lista;
	}

	public ArrayList<CaravanaAgendamentoModel> obterTodosPorEventoEparticipante(CaravanaEventoModel eventoModel, CaravanaParticipanteModel participanteModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel e where e.permissaoAgendamento.evento=:evento and participante=:participante and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setEntity("participante", participanteModel);
		select.setBoolean("deletado", false);
		ArrayList<CaravanaAgendamentoModel> lista = (ArrayList<CaravanaAgendamentoModel>) select.list();

		return lista;
	}

	public int obterQuantidadeDeInscritoPorPermissaoAgendamento(EventoPermissaoAgendamentoModel eventoPermissaoAgendamento) throws SQLException {

		String sql = "select count(id) FROM br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel e where e.permissaoAgendamento=:permissaoAgendamento and deletado=:deletado and situacao<>:situacao";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("permissaoAgendamento", eventoPermissaoAgendamento);
		select.setBoolean("deletado", false);
		select.setString("situacao", "reprovado");

		int quantideInteger = 0;
		Long quantidade = (Long) select.uniqueResult();

		if (quantidade != null) {
			quantideInteger = quantidade.intValue();
		}

		return quantideInteger;
	}

	public ArrayList<CaravanaAgendamentoModel> obterParticipantePorCartaoSusEevento(String cartaoSus, CaravanaEventoModel eventoModel) throws SQLException {

		String sql = "select e FROM br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel e where e.permissaoAgendamento.evento=:evento and e.participante.cartaoSus=:cartaoSus and deletado=:deletado";
		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setString("cartaoSus", cartaoSus);
		select.setBoolean("deletado", false);
		ArrayList<CaravanaAgendamentoModel> lista = (ArrayList<CaravanaAgendamentoModel>) select.list();

		return lista;
	}

}
