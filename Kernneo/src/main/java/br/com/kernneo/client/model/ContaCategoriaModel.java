package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.types.ContaType;

@Table
@Entity(name = "conta_categoria")
public class ContaCategoriaModel extends GenericModel {

	private String nome;

	@Enumerated(EnumType.STRING)
	private ContaType tipo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ContaType getTipo() {
		return tipo;
	}

	public void setTipo(ContaType tipo) {
		this.tipo = tipo;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());

		if (getTipo() != null) {
			record.setAttribute("tipo", getTipo().toString());
		}

		return record;
	}

}
