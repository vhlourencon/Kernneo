package br.com.kernneo.client.caravana.model;

import java.util.Date;

public class CaravanaComprovanteModel {

	private String participanteId;
	private String participanteNome;
	private String participanteCNS;
	private String participanteTelefone;
	private Date participanteDataNasc;
	private Date agendamentoData;
	private String agendamentoHora;
	


	public String getParticipanteId() {
		return participanteId;
	}

	public void setParticipanteId(String participanteId) {
		this.participanteId = participanteId;
	}

	public String getParticipanteNome() {
		return participanteNome;
	}

	public void setParticipanteNome(String participanteNome) {
		this.participanteNome = participanteNome;
	}

	public String getParticipanteCNS() {
		return participanteCNS;
	}

	public void setParticipanteCNS(String participanteCNS) {
		this.participanteCNS = participanteCNS;
	}

	public String getParticipanteTelefone() {
		return participanteTelefone;
	}

	public void setParticipanteTelefone(String participanteTelefone) {
		this.participanteTelefone = participanteTelefone;
	}

	public Date getParticipanteDataNasc() {
		return participanteDataNasc;
	}

	public void setParticipanteDataNasc(Date participanteDataNasc) {
		this.participanteDataNasc = participanteDataNasc;
	}

	public Date getAgendamentoData() {
		return agendamentoData;
	}

	public void setAgendamentoData(Date agendamentoData) {
		this.agendamentoData = agendamentoData;
	}

	public String getAgendamentoHora() {
		return agendamentoHora;
	}

	public void setAgendamentoHora(String agendamentoHora) {
		this.agendamentoHora = agendamentoHora;
	}

}
