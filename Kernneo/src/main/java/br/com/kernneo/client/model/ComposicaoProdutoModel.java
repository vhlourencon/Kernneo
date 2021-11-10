package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "composicao_produto")
public class ComposicaoProdutoModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_composicao")
    private ComposicaoModel composicao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private ProdutoModel produto;
    
  
   

    private double quantidade;

    public ComposicaoModel getComposicao() {
	return composicao;
    }

    public void setComposicao(ComposicaoModel composicao) {
	this.composicao = composicao;
    }

    public ProdutoModel getProduto() {
	return produto;
    }

    public void setProduto(ProdutoModel produto) {
	this.produto = produto;
    }

    public double getQuantidade() {
	return quantidade;
    }

    public void setQuantidade(double quantidade) {
	this.quantidade = quantidade;
    }
    
    

   


    @Override
    public Record toRecord() {
	Record record = new Record(); 
	record.setAttribute("id", getId());
	record.setAttribute("produto", produto.getNome());
	record.setAttribute("unidade", produto.getComposicao().getUnidadeTypes());
	record.setAttribute("quantidade", quantidade);
	
	return record;
    }

}
