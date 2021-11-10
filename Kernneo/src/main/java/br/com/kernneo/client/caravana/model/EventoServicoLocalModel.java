package br.com.kernneo.client.caravana.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_evento_servico_local")
public class EventoServicoLocalModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private CaravanaEventoModel evento;

	@ManyToOne
	@JoinColumn(name = "id_local")
	private CaravanaLocalModel local;

	@ManyToOne
	@JoinColumn(name = "id_servico")
	private CaravanaServicoModel servico;

	public CaravanaEventoModel getEvento() {
		return evento;
	}

	public void setEvento(CaravanaEventoModel evento) {
		this.evento = evento;
	}

	public CaravanaLocalModel getLocal() {
		return local;
	}

	public void setLocal(CaravanaLocalModel local) {
		this.local = local;
	}

	public CaravanaServicoModel getServico() {
		return servico;
	}

	public void setServico(CaravanaServicoModel servico) {
		this.servico = servico;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		if (getServico() != null) {
			record.setAttribute("servicoNome", getServico().getNome());
		}

		if (getLocal() != null) {
			record.setAttribute("localNome", getLocal().getNome());
		}

		return record;
	}

}
