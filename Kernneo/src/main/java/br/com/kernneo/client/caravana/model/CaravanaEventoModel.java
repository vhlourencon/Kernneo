package br.com.kernneo.client.caravana.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.caravana.types.CaravanaEventoStatusTypes;
import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_evento")
public class CaravanaEventoModel extends GenericModel {

	private String nome;
	private String descricao;

	private int numeroDeParticipantesPrevistos;

	private String responsavelNome;
	private String responsavelTelefone;
	private String responsavelEmail;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicial;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinal;

	@Enumerated(EnumType.STRING)
	private CaravanaEventoStatusTypes status;

	@javax.persistence.Transient
	private ArrayList<EventoServicoLocalModel> listaDeServicoLocal;

	@javax.persistence.Transient
	private ArrayList<EventoCategoriaPartModel> listaDeCategoriaDeParticipantes;

	@javax.persistence.Transient
	private ArrayList<EventoPermissaoAgendamentoModel> listaDePermissaoAgendamento;

	public ArrayList<EventoPermissaoAgendamentoModel> getListaDePermissaoAgendamento() {
		if (listaDePermissaoAgendamento == null) {
			listaDePermissaoAgendamento = new ArrayList<EventoPermissaoAgendamentoModel>();
		}
		return listaDePermissaoAgendamento;
	}

	public void setListaDePermissaoAgendamento(ArrayList<EventoPermissaoAgendamentoModel> listaDePermissaoAgendamento) {
		this.listaDePermissaoAgendamento = listaDePermissaoAgendamento;
	}

	public ArrayList<EventoServicoLocalModel> getListaDeServicoLocal() {
		if (listaDeServicoLocal == null) {
			listaDeServicoLocal = new ArrayList<EventoServicoLocalModel>();
		}
		return listaDeServicoLocal;
	}

	public void setListaDeCategoriaDeParticipantes(ArrayList<EventoCategoriaPartModel> listaDeCategoriaDeParticipantes) {
		this.listaDeCategoriaDeParticipantes = listaDeCategoriaDeParticipantes;
	}

	public void setListaDeServicoLocal(ArrayList<EventoServicoLocalModel> listaDeServicoLocal) {
		this.listaDeServicoLocal = listaDeServicoLocal;
	}

	public void addCategoriaDoParticipante(CaravanaCatParticipanteModel catParticipanteModel) throws Exception {
		StringBuffer msg = new StringBuffer("");
		if (catParticipanteModel == null) {
			msg.append("A categoria deve ser selecionada <br>");
		}

		if (msg.length() > 0)
			throw new Exception(msg.toString());
		else {
			EventoCategoriaPartModel eventoCategoriaPartModel = new EventoCategoriaPartModel();
			eventoCategoriaPartModel.setCategoriaParticipanteModel(catParticipanteModel);
			eventoCategoriaPartModel.setEvento(this);

			getListaDeCategoriaDeParticipantes().add(eventoCategoriaPartModel);
		}
	}

	public void addServicoComLocal(CaravanaServicoModel servicoModel, CaravanaLocalModel localModel) throws Exception {
		StringBuffer msg = new StringBuffer("");
		if (servicoModel == null) {
			msg.append("O serviço deve ser preenchido <br>");
		}

		if (localModel == null) {
			msg.append("O local deve ser preenchido <br>");
		}

		if (msg.length() > 0)
			throw new Exception(msg.toString());
		else {
			EventoServicoLocalModel eventoServicoLocalModel = new EventoServicoLocalModel();
			eventoServicoLocalModel.setServico(servicoModel);
			eventoServicoLocalModel.setLocal(localModel);
			eventoServicoLocalModel.setEvento(this);
			getListaDeServicoLocal().add(eventoServicoLocalModel);
		}

	}

	public void addPermissaoDeAgendamento(EventoPermissaoAgendamentoModel eventoPermissaoAgendamentoModel) throws Exception {
		StringBuffer msg = new StringBuffer("");
		if (eventoPermissaoAgendamentoModel.getDataHoraInicial() == null) {
			msg.append("O DIA deve ser preenchido <br>");
		}

		if (eventoPermissaoAgendamentoModel.getEventoCategoriaParticipante() == null) {
			msg.append("A CATEGORIA DO PARTICIPATE deve ser preenchida <br>");
		}

		if (eventoPermissaoAgendamentoModel.getUsuarioPermitido() == null) {
			msg.append("O USUÁRIO deve ser preenchida <br>");

		}

		if (msg.length() > 0)
			throw new Exception(msg.toString());
		else {
			eventoPermissaoAgendamentoModel.setEvento(this);
			getListaDePermissaoAgendamento().add(eventoPermissaoAgendamentoModel);
		}

	}

	public ArrayList<EventoCategoriaPartModel> getListaDeCategoriaDeParticipantes() {
		if (listaDeCategoriaDeParticipantes == null) {
			listaDeCategoriaDeParticipantes = new ArrayList<EventoCategoriaPartModel>();
		}
		return listaDeCategoriaDeParticipantes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getNumeroDeParticipantesPrevistos() {
		return numeroDeParticipantesPrevistos;
	}

	public void setNumeroDeParticipantesPrevistos(int numeroDeParticipantesPrevistos) {
		this.numeroDeParticipantesPrevistos = numeroDeParticipantesPrevistos;
	}

	public String getResponsavelNome() {
		return responsavelNome;
	}

	public void setResponsavelNome(String responsavelNome) {
		this.responsavelNome = responsavelNome;
	}

	public String getResponsavelTelefone() {
		return responsavelTelefone;
	}

	public void setResponsavelTelefone(String responsavelTelefone) {
		this.responsavelTelefone = responsavelTelefone;
	}

	public String getResponsavelEmail() {
		return responsavelEmail;
	}

	public void setResponsavelEmail(String responsavelEmail) {
		this.responsavelEmail = responsavelEmail;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public CaravanaEventoStatusTypes getStatus() {
		return status;
	}

	public void setStatus(CaravanaEventoStatusTypes status) {
		this.status = status;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("descricao", getDescricao());

		return record;
	}

}
