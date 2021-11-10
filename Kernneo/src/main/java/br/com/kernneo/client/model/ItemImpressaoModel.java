package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.smartgwt.client.data.Record;

public class ItemImpressaoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private ProdutoModel produto;
	
	
	private boolean conjugado; 
	
	
	private String nomeDoProduto; 

	private Double quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;
	private BigDecimal valorTotalSemDesconto; 

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		if (valorUnitario != null) {
			valorUnitario = valorUnitario.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		}
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorTotal() {
		
		if (valorTotal != null) {
			valorTotal = valorTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		}
		
		
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	

	public String getNomeDoProduto() {
		return nomeDoProduto;
	}

	public void setNomeDoProduto(String nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
	}

	@Override
	public Record toRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isConjugado() {
		return conjugado;
	}

	public void setConjugado(boolean conjugado) {
		this.conjugado = conjugado;
	}

	public BigDecimal getValorTotalSemDesconto() {
		
		if (valorTotalSemDesconto != null) {
			valorTotalSemDesconto = valorTotalSemDesconto.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		}
		return valorTotalSemDesconto;
	}

	public void setValorTotalSemDesconto(BigDecimal valorTotalSemDesconto) {
		this.valorTotalSemDesconto = valorTotalSemDesconto;
	}

}
