package br.com.kernneo.client.caravana.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_credenciamento_servico")
public class CredenciamentoServicoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_credenciamento")
	private CaravanaCredenciamentoModel credenciamento;

	@ManyToOne
	@JoinColumn(name = "id_servico")
	private CaravanaServicoModel servico;

	public CaravanaCredenciamentoModel getCredenciamento() {
		return credenciamento;
	}

	public void setCredenciamento(CaravanaCredenciamentoModel credenciamento) {
		this.credenciamento = credenciamento;
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

		return record;
	}

}
