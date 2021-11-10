package br.com.kernneo.server.caravana.agendamento;

import br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaComprovanteModel;
import br.com.kernneo.client.utils.StringUtils;

public class ConverteAgendamentoComprovanteModel {

	public static CaravanaComprovanteModel converte(CaravanaAgendamentoModel agendamentoModel) {

		CaravanaComprovanteModel comprovanteModel = new CaravanaComprovanteModel();
		comprovanteModel.setParticipanteId(String.format("%06d", agendamentoModel.getParticipante().getId()));
		comprovanteModel.setParticipanteNome(agendamentoModel.getParticipante().getNome().toUpperCase());
		comprovanteModel.setParticipanteCNS(StringUtils.reformatCartaoSus(agendamentoModel.getParticipante().getCartaoSus()));
		comprovanteModel.setParticipanteTelefone(StringUtils.reformatPhone(agendamentoModel.getParticipante().getTelefone()));
		comprovanteModel.setParticipanteDataNasc(agendamentoModel.getParticipante().getDataDeNascimento());
		comprovanteModel.setAgendamentoData(agendamentoModel.getPermissaoAgendamento().getDataHoraInicial());
		comprovanteModel.setAgendamentoHora(agendamentoModel.getPermissaoAgendamento().getHorario1turno());
		if (agendamentoModel.getPermissaoAgendamento().isDivididoEmTurno()) {
			if (agendamentoModel.isSegundoTurno()) {
				comprovanteModel.setAgendamentoHora(agendamentoModel.getPermissaoAgendamento().getHorario2turno());
			}

		}

		return comprovanteModel;

	}

}
