package br.com.kernneo.client.model.relatorio;

import br.com.kernneo.client.model.ProdutoModel;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.Record;

public class RelatorioVendaPeriodoModel implements IsSerializable {

    private Double quantidade = 0.0;
    private ProdutoModel produto;
    
    public RelatorioVendaPeriodoModel() { 
	
    }

    public RelatorioVendaPeriodoModel(ProdutoModel produtoModel, Double quantidade) {
	setQuantidade(quantidade);
	setProduto(produtoModel);

    }

    public Double getQuantidade() {
	return quantidade;
    }

    public void setQuantidade(Double quantidade) {
	this.quantidade = quantidade;
    }

    public ProdutoModel getProduto() {
	return produto;
    }

    public void setProduto(ProdutoModel produto) {
	this.produto = produto;
    }

    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("produtoNome", produto.getNome());
	record.setAttribute("quantidade", getQuantidade());
	record.setAttribute("produtoCodigo", produto.getCodigo());

	return record;
    }

}
