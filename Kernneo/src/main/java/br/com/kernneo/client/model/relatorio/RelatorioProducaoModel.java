package br.com.kernneo.client.model.relatorio;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RelatorioProducaoModel implements IsSerializable {

	private String quantidade;
	private String produto;

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

}
