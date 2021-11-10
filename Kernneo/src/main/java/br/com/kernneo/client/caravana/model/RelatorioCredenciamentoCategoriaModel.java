package br.com.kernneo.client.caravana.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

public class RelatorioCredenciamentoCategoriaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_categoria_participante")
	private CaravanaCatParticipanteModel categoriaParticipante;

	private Long quantidadeDePessoas;

	public RelatorioCredenciamentoCategoriaModel(CaravanaCatParticipanteModel pCaravanaParticipanteModel, Long quantidadeDePessoas) {
		setCategoriaParticipante(pCaravanaParticipanteModel);
		setQuantidadeDePessoas(quantidadeDePessoas);

	}
	
	public RelatorioCredenciamentoCategoriaModel() {
		
	}

	public CaravanaCatParticipanteModel getCategoriaParticipante() {
		return categoriaParticipante;
	}

	public void setCategoriaParticipante(CaravanaCatParticipanteModel categoriaParticipante) {
		this.categoriaParticipante = categoriaParticipante;
	}

	public Long getQuantidadeDePessoas() {
		return quantidadeDePessoas;
	}

	public void setQuantidadeDePessoas(Long quantidadeDePessoas) {
		this.quantidadeDePessoas = quantidadeDePessoas;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("quantidadeDePessoas", getQuantidadeDePessoas());

		if (getCategoriaParticipante() != null) {
			record.setAttribute("categoriaNome", getCategoriaParticipante().getNome());
		}

		return record;
	}

}
