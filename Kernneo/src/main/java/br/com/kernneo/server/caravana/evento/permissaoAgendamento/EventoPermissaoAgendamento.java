package br.com.kernneo.server.caravana.evento.permissaoAgendamento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.CaravanaUsuarioModel;
import br.com.kernneo.client.caravana.model.EventoPermissaoAgendamentoModel;
import br.com.kernneo.server.band.negocio.BandNegocio;
import br.com.kernneo.server.caravana.agendamento.CaravanaAgendamento;

public class EventoPermissaoAgendamento extends BandNegocio<EventoPermissaoAgendamentoModel, EventoPermissaoAgendamentoDAO> {

	public void salvarPorEvento(CaravanaEventoModel eventoModel) throws Exception {
		for (EventoPermissaoAgendamentoModel permissaoAgendamentoModel : eventoModel.getListaDePermissaoAgendamento()) {
			permissaoAgendamentoModel.setEvento(eventoModel);
			permissaoAgendamentoModel = merge(permissaoAgendamentoModel);

		}
	}

	public ArrayList<EventoPermissaoAgendamentoModel> obterTodosPorEvento(CaravanaEventoModel eventoModel) throws Exception {
		ArrayList<EventoPermissaoAgendamentoModel> lista = dao.obterTodosPorEvento(eventoModel);
		return lista;
	}

	public ArrayList<EventoPermissaoAgendamentoModel> obterTodosPorEventoEusuario(CaravanaEventoModel eventoModel, CaravanaUsuarioModel usuarioModel) throws Exception {

		ArrayList<EventoPermissaoAgendamentoModel> lista = dao.obterTodosPorEventoEusuario(eventoModel, usuarioModel);

		return lista;
	}

	public EventoPermissaoAgendamentoModel obterModelAtualizadoComDetalhes(EventoPermissaoAgendamentoModel model) throws Exception {
		model = new EventoPermissaoAgendamento().obterPorId(model);
		model.setListaDeAgendamento(new CaravanaAgendamento().obterTodosPorEventoPermissaoAgendamento(model));

		return model;

	}

	public EventoPermissaoAgendamento() {
		super();
		dao = new EventoPermissaoAgendamentoDAO();
	}
	
	
	public String getCalcNomeCompleto(EventoPermissaoAgendamentoModel eventoPermissaoAgendamentoModel) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String dia = "";
		if (  eventoPermissaoAgendamentoModel.getDataHoraInicial() != null) { 
			dia = sdf.format(eventoPermissaoAgendamentoModel.getDataHoraInicial());
		}
		

		String nomeCategoria = "";
		if (eventoPermissaoAgendamentoModel.getEventoCategoriaParticipante() != null) {
			nomeCategoria = eventoPermissaoAgendamentoModel.getEventoCategoriaParticipante().getCategoriaParticipanteModel().getNome();

		}

		String nomeUsuario = "";
		if (eventoPermissaoAgendamentoModel.getUsuarioPermitido() != null) {
			nomeUsuario = eventoPermissaoAgendamentoModel.getUsuarioPermitido().getNome();
		}

		String nomeCompleto = "";
		nomeCompleto += nomeCategoria + " - " + dia + " -  " + eventoPermissaoAgendamentoModel.getQuantidadeDeVagas() + " Vagas (" + nomeUsuario + ")";

		return nomeCompleto;
	}

	@Override
	public Exception validar(EventoPermissaoAgendamentoModel vo) {
		StringBuffer msg = new StringBuffer("");

		// if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
		// msg.append("O campo Nome é de preenchimento obrigatório! \n");
		// }
		//
		//
		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

}
