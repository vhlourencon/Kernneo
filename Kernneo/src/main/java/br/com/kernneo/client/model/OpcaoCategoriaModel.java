package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.types.OpcaoTypes;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "opcao_categoria")
public class OpcaoCategoriaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private OpcaoModel opcao;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriaModel categoria;

	
	public OpcaoModel getOpcao() {
		return opcao;
	}

	public void setOpcao(OpcaoModel opcao) {
		this.opcao = opcao;
	}

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());

		return record;
	}

}
