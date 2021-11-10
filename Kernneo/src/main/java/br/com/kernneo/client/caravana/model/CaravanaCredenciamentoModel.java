package br.com.kernneo.client.caravana.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.utils.StringUtils;

@Table
@Entity(name = "caravana_credenciamento")
public class CaravanaCredenciamentoModel extends GenericModel {

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraCredenciamento;

	@ManyToOne
	@JoinColumn(name = "id_participante")
	private CaravanaParticipanteModel participante;

	@ManyToOne
	@JoinColumn(name = "id_categoria_participante")
	private CaravanaCatParticipanteModel categoriaParticipante;

	@ManyToOne
	@JoinColumn(name = "id_usuario_credenciamento")
	private CaravanaUsuarioModel usuarioCredenciamento;

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private CaravanaEventoModel evento;

	@Transient
	private ArrayList<CredenciamentoServicoModel> listaDeServico;

	private boolean imprimeEtiqueta;
	private String idImpressora;

	@Transient
	private boolean participanteJaCredenciou;

	public boolean isParticipanteJaCredenciou() {
		return participanteJaCredenciou;
	}

	public void setParticipanteJaCredenciou(boolean participanteJaCredenciou) {
		this.participanteJaCredenciou = participanteJaCredenciou;
	}

	public Date getDataHoraCredenciamento() {
		return dataHoraCredenciamento;
	}

	public ArrayList<CredenciamentoServicoModel> getListaDeServico() {
		if (listaDeServico == null) {
			listaDeServico = new ArrayList<CredenciamentoServicoModel>();
		}
		return listaDeServico;
	}

	public void setListaDeServico(ArrayList<CredenciamentoServicoModel> listaDeServico) {
		this.listaDeServico = listaDeServico;
	}

	public void setDataHoraCredenciamento(Date dataHoraCredenciamento) {
		this.dataHoraCredenciamento = dataHoraCredenciamento;
	}

	public CaravanaParticipanteModel getParticipante() {
		
		return participante;
	}

	public void setParticipante(CaravanaParticipanteModel participante) {
		this.participante = participante;
	}

	public CaravanaCatParticipanteModel getCategoriaParticipante() {
		return categoriaParticipante;
	}

	public void setCategoriaParticipante(CaravanaCatParticipanteModel categoriaParticipante) {
		this.categoriaParticipante = categoriaParticipante;
	}

	public CaravanaEventoModel getEvento() {
		return evento;
	}

	public void setEvento(CaravanaEventoModel evento) {
		this.evento = evento;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		if (getParticipante() != null) {
			record.setAttribute("participanteNome", getParticipante().getNome());

			if (getParticipante().getDataDeNascimento() != null) {
				record.setAttribute("participanteDataDeNascimento", DateTimeFormat.getFormat("dd/MM/yyyy").format(getParticipante().getDataDeNascimento()));
			}

			record.setAttribute("participanteTelefone", StringUtils.reformatPhone(getParticipante().getTelefone()));
			record.setAttribute("participanteCidadeNome", getParticipante().getCidade().getNome() + " - " + getParticipante().getCidade().getEstado().getSigla());
		}

		if (getUsuarioCredenciamento() != null) {
			record.setAttribute("usuarioCredenciamentoNome", getUsuarioCredenciamento().getNome());
		}

		if (getCategoriaParticipante() != null) {
			record.setAttribute("categoriaNome", getCategoriaParticipante().getNome());
		}

		if (getDataHoraCredenciamento() != null) {
			record.setAttribute("dataHoraCredenciamento", DateTimeFormat.getFormat("dd/MM/yyyy - HH:mm").format(getDataHoraCredenciamento()));
		}

		return record;
	}

	public CaravanaUsuarioModel getUsuarioCredenciamento() {
		return usuarioCredenciamento;
	}

	public void setUsuarioCredenciamento(CaravanaUsuarioModel usuarioCredenciamento) {
		this.usuarioCredenciamento = usuarioCredenciamento;
	}

	public boolean isImprimeEtiqueta() {
		return imprimeEtiqueta;
	}

	public void setImprimeEtiqueta(boolean imprimeEtiqueta) {
		this.imprimeEtiqueta = imprimeEtiqueta;
	}

	public String getIdImpressora() {
		return idImpressora;
	}

	public void setIdImpressora(String idImpressora) {
		this.idImpressora = idImpressora;
	}

}
