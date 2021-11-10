package br.com.kernneo.client.caravana.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_evento_permissao_agendamento")
public class EventoPermissaoAgendamentoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private CaravanaEventoModel evento;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private CaravanaUsuarioModel usuarioPermitido;

	@ManyToOne
	@JoinColumn(name = "id_evento_categoria_participante")
	private EventoCategoriaPartModel eventoCategoriaParticipante;

	private int quantidadeDeVagas;
	private Date dataHoraInicial;
	private Date dataHoraFinal;

	private boolean divididoEmTurno;
	private String horario1turno;
	private String horario2turno;

	public boolean isDivididoEmTurno() {
		return divididoEmTurno;
	}

	public void setDivididoEmTurno(boolean divididoEmTurno) {
		this.divididoEmTurno = divididoEmTurno;
	}

	@javax.persistence.Transient
	private ArrayList<CaravanaAgendamentoModel> listaDeAgendamento;

	public CaravanaEventoModel getEvento() {
		return evento;
	}

	public void setEvento(CaravanaEventoModel evento) {
		this.evento = evento;
	}

	public CaravanaUsuarioModel getUsuarioPermitido() {
		return usuarioPermitido;
	}

	public void setUsuarioPermitido(CaravanaUsuarioModel usuarioPermitido) {
		this.usuarioPermitido = usuarioPermitido;
	}

	public EventoCategoriaPartModel getEventoCategoriaParticipante() {
		return eventoCategoriaParticipante;
	}

	public void setEventoCategoriaParticipante(EventoCategoriaPartModel eventoCategoriaParticipante) {
		this.eventoCategoriaParticipante = eventoCategoriaParticipante;
	}

	public Date getDataHoraInicial() {
		return dataHoraInicial;
	}

	public void setDataHoraInicial(Date dataHoraInicial) {
		this.dataHoraInicial = dataHoraInicial;
	}

	public Date getDataHoraFinal() {
		return dataHoraFinal;
	}

	public void setDataHoraFinal(Date dataHoraFinal) {
		this.dataHoraFinal = dataHoraFinal;
	}

	public ArrayList<CaravanaAgendamentoModel> getListaDeAgendamento() {
		if (listaDeAgendamento == null) {
			listaDeAgendamento = new ArrayList<CaravanaAgendamentoModel>();
		}
		return listaDeAgendamento;
	}

	public void setListaDeAgendamento(ArrayList<CaravanaAgendamentoModel> listaDeAgendamento) {
		this.listaDeAgendamento = listaDeAgendamento;
	}

	public String getHorario1turno() {
		return horario1turno;
	}

	public void setHorario1turno(String horario1turno) {
		this.horario1turno = horario1turno;
	}

	public String getHorario2turno() {
		return horario2turno;
	}

	public void setHorario2turno(String horario2turno) {
		this.horario2turno = horario2turno;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("dataHoraInicial", br.com.kernneo.client.utils.StringUtils.getDataHoraFormatada(getDataHoraInicial()));
		record.setAttribute("dataInicial", br.com.kernneo.client.utils.StringUtils.getDataFormatada(getDataHoraInicial()));

		record.setAttribute("dataHoraFinal", br.com.kernneo.client.utils.StringUtils.getDataHoraFormatada(getDataHoraFinal()));
		record.setAttribute("quantidadeDeVagas", getQuantidadeDeVagas());

		if (getUsuarioPermitido() != null) {
			record.setAttribute("usuarioPermitidoNome", getUsuarioPermitido().getNome());
		}

		if (getEventoCategoriaParticipante() != null) {
			record.setAttribute("eventoCategoriaParticipanteNome", getEventoCategoriaParticipante().getCategoriaParticipanteModel().getNome());
		}
		return record;
	}

	public int getQuantidadeDeVagas() {
		return quantidadeDeVagas;
	}

	public void setQuantidadeDeVagas(int quantidadeDeVagas) {
		this.quantidadeDeVagas = quantidadeDeVagas;
	}

	// public String getCalcNomeCompleto() {
	// String dia =
	// br.com.kernneo.client.utils.StringUtils.getDataFormatada(getDataHoraInicial());
	// if (dia == null) {
	// dia = "";
	// }
	//
	// String nomeCategoria = "";
	// if (getEventoCategoriaParticipante() != null) {
	// nomeCategoria =
	// getEventoCategoriaParticipante().getCategoriaParticipanteModel().getNome();
	//
	// }
	//
	// String nomeUsuario = "";
	// if (getUsuarioPermitido() != null) {
	// nomeUsuario = getUsuarioPermitido().getNome();
	// }
	//
	// String nomeCompleto = "";
	// nomeCompleto += nomeCategoria + " - " + dia + " - " +
	// getQuantidadeDeVagas() + " Vagas (" + nomeUsuario + ")";
	//
	// return nomeCompleto;
	// }

}
