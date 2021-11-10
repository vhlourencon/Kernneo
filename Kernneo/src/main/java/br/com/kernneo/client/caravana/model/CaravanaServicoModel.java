package br.com.kernneo.client.caravana.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_servico")
public class CaravanaServicoModel extends GenericModel {

	private String nome;
	private String descricao;

//	@ManyToOne
//	@JoinColumn(name = "id_local")
//	private CaravanaLocalModel local;

	@ManyToOne
	@JoinColumn(name = "id_categoria_servico")
	private CaravanaCatServicoModel categoria;

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

	

	public CaravanaCatServicoModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CaravanaCatServicoModel categoria) {
		this.categoria = categoria;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("descricao", getDescricao());

		
		if (getCategoria() != null) {
			record.setAttribute("categoriaNome", getCategoria().getNome());
		}

		return record;
	}

}
