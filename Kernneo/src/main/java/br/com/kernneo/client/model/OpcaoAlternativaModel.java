package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "opcao_alternativa")
public class OpcaoAlternativaModel extends GenericModel {

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private OpcaoModel opcao;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private ProdutoModel produto;

	private boolean selecionada;
	private BigDecimal valor;
	private Double quantidade = 1.0;

	public boolean isSelecionada() {
		return selecionada;
	}

	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;

	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	public OpcaoModel getOpcao() {
		return opcao;
	}

	public void setOpcao(OpcaoModel opcao) {
		this.opcao = opcao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("quantidade", getQuantidade());

		if (getProduto() != null) {
			record.setAttribute("produtoNome", getProduto().getNome());
		}
		if (getValor() != null) {

			record.setAttribute("valor", getValor());
			record.setAttribute("valorStr", StringUtils.getDoubleFormatadoEmDinheiro(getValor()));

		}

		record.setAttribute("selecionada", isSelecionada());
		if (isSelecionada()) {
			record.setAttribute("selecionadaStr", "Sim");
		} else {
			record.setAttribute("selecionadaStr", "Não");

		}
		return record;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

}
