package br.com.kernneo.client.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.types.UnidadeTypes;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "composicao")
public class ComposicaoModel extends GenericModel {

    @OneToOne
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;
    private boolean produtoComposto;

    @Enumerated(EnumType.STRING)
    private UnidadeTypes unidadeTypes;

    @Transient
    private ArrayList<ComposicaoProdutoModel> listaDeComposicaoDoProduto;

    public void addComposicaoDoProduto(ComposicaoProdutoModel composicaoProdutoModel) throws ProdutoException {

	getListaDeComposicaoDoProduto().add(composicaoProdutoModel);
    }

    public void addComposicaoDoProduto(ComposicaoProdutoModel composicaoProdutoModel, int posicaoDaLista) throws ProdutoException {
	StringBuffer msg = new StringBuffer("");
	if (composicaoProdutoModel.getProduto() == null) {
	    msg.append("O campo Produto  é de preenchimento obrigatório! \n");
	}

	if (composicaoProdutoModel.getQuantidade() <= 0d) {
	    msg.append("O campo quantidade não pode ser menor ou igual a 0 (zero)! \n");
	}

	if (msg.length() > 0)
	    throw new ProdutoException(msg.toString());
	else {
	    getListaDeComposicaoDoProduto().add(posicaoDaLista, composicaoProdutoModel);
	}

    }

    public ProdutoModel getProduto() {
	return produto;
    }

    public void setProduto(ProdutoModel produto) {
	this.produto = produto;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

    public boolean isProdutoComposto() {
	return produtoComposto;
    }

    public void setProdutoComposto(boolean produtoComposto) {

	this.produtoComposto = produtoComposto;
    }

    public UnidadeTypes getUnidadeTypes() {
	return unidadeTypes;
    }

    public void setUnidadeTypes(UnidadeTypes unidadeTypes) {
	this.unidadeTypes = unidadeTypes;
    }

    public ArrayList<ComposicaoProdutoModel> getListaDeComposicaoDoProduto() {
	if (listaDeComposicaoDoProduto == null) {
	    listaDeComposicaoDoProduto = new ArrayList<ComposicaoProdutoModel>();
	}
	return listaDeComposicaoDoProduto;
    }

    public void setListaDeComposicaoDoProduto(ArrayList<ComposicaoProdutoModel> listaDeComposicaoDoProduto) {
	this.listaDeComposicaoDoProduto = listaDeComposicaoDoProduto;
    }

}
