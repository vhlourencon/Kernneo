package br.com.kernneo.client.caravana.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.caravana.types.CaravanaSituacaoAgendamentoTypes;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.utils.StringUtils;

@Table
@Entity(name = "caravana_agendamento")
public class CaravanaAgendamentoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_permissao_agendamento")
	private EventoPermissaoAgendamentoModel permissaoAgendamento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraAgendamento;

	@ManyToOne
	@JoinColumn(name = "id_participante")
	private CaravanaParticipanteModel participante;

	@ManyToOne
	@JoinColumn(name = "id_usuario_agendamento")
	private CaravanaUsuarioModel usuarioAgendamento;

	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean jaImprimiu = false;

	// @Enumerated(EnumType.STRING)
	// private CaravanaEncaminhamentoTypes encaminhamento;
	
	private boolean segundoTurno; 
	
	@Enumerated(EnumType.STRING)
	private CaravanaSituacaoAgendamentoTypes situacao;

	private String msgSituacaoEmanalise;

	// public CaravanaEncaminhamentoTypes getEncaminhamento() {
	//
	// return encaminhamento;
	// }
	//
	// public void setEncaminhamento(CaravanaEncaminhamentoTypes encaminhamento)
	// {
	// this.encaminhamento = encaminhamento;
	// }

	public EventoPermissaoAgendamentoModel getPermissaoAgendamento() {
		return permissaoAgendamento;
	}

	public void setPermissaoAgendamento(EventoPermissaoAgendamentoModel permissaoAgendamento) {
		this.permissaoAgendamento = permissaoAgendamento;
	}

	public Date getDataHoraAgendamento() {
		return dataHoraAgendamento;
	}

	public void setDataHoraAgendamento(Date dataHoraAgendamento) {
		this.dataHoraAgendamento = dataHoraAgendamento;
	}

	public CaravanaParticipanteModel getParticipante() {
		if (participante == null) {
			participante = new CaravanaParticipanteModel();
		}
		return participante;
	}

	public void setParticipante(CaravanaParticipanteModel participante) {
		this.participante = participante;
	}

	public CaravanaUsuarioModel getUsuarioAgendamento() {
		return usuarioAgendamento;
	}

	public void setUsuarioAgendamento(CaravanaUsuarioModel usuarioAgendamento) {
		this.usuarioAgendamento = usuarioAgendamento;
	}

	public CaravanaSituacaoAgendamentoTypes getSituacao() {
		if (this.situacao == null) {
			this.situacao = CaravanaSituacaoAgendamentoTypes.reprovado;
		}
		return situacao;
	}

	public boolean isJaImprimiu() {
		return jaImprimiu;
	}

	public void setJaImprimiu(boolean jaImprimiu) {
		this.jaImprimiu = jaImprimiu;
	}

	public void setSituacao(CaravanaSituacaoAgendamentoTypes situacao) {

		this.situacao = situacao;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());

		if (getParticipante() != null) {
			record.setAttribute("participanteNome", getParticipante().getNome());
			record.setAttribute("participanteCartaoSus", getParticipante().getCartaoSus());
			record.setAttribute("participanteTelefone", StringUtils.reformatPhone(getParticipante().getTelefone()));
			record.setAttribute("participanteDataDeNascimento", StringUtils.getDataFormatada(getParticipante().getDataDeNascimento()));
		}

		if (getUsuarioAgendamento() != null) {
			record.setAttribute("usuarioAgendamentoNome", getUsuarioAgendamento().getNome());
		}
		if ( getPermissaoAgendamento() != null) { 
			record.setAttribute("horarioTurno", getPermissaoAgendamento().getHorario1turno());
			if ( isSegundoTurno())
			record.setAttribute("horarioTurno", getPermissaoAgendamento().getHorario2turno());
		}

		// if (getEncaminhamento() != null) {
		// record.setAttribute("encaminhamento",
		// getEncaminhamento().toString().toUpperCase());
		// }

		if (getSituacao() != null) {
			if (getSituacao() == CaravanaSituacaoAgendamentoTypes.em_analise) {
				record.setAttribute("situacao", "EM ANÁLISE");

			} else {
				record.setAttribute("situacao", getSituacao().toString().toUpperCase());
			}
		}

		return record;
	}

	public String getMsgSituacaoEmanalise() {
		return msgSituacaoEmanalise;
	}

	public void setMsgSituacaoEmanalise(String msgSituacaoEmanalise) {
		this.msgSituacaoEmanalise = msgSituacaoEmanalise;
	}

	public boolean isSegundoTurno() {
		return segundoTurno;
	}

	public void setSegundoTurno(boolean segundoTurno) {
		this.segundoTurno = segundoTurno;
	}

}
