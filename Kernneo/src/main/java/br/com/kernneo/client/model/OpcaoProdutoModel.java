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
@Entity(name = "opcao_produto")
public class OpcaoProdutoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private OpcaoModel opcao;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private ProdutoModel produto;

	public OpcaoModel getOpcao() {
		return opcao;
	}

	public void setOpcao(OpcaoModel opcao) {
		this.opcao = opcao;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());

		return record;
	}

}
