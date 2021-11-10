package br.com.kernneo.client.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.types.MovimentacaoEstoqueTypes;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "estoque_lancamento")
public class EstoqueLancamentoModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_estoque")
    private EstoqueModel estoque;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private ItemModel item;
    
    
    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private FornecedorModel fornecedor;


    private Date dataHora;

    private double quantidade;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private MovimentacaoEstoqueTypes movimentacaoEstoqueTypes;

    public EstoqueModel getEstoque() {
	return estoque;
    }

    public void setEstoque(EstoqueModel estoque) {
	this.estoque = estoque;
    }

    public Date getDataHora() {
	return dataHora;
    }

    public void setDataHora(Date dataHora) {
	this.dataHora = dataHora;
    }

    public double getQuantidade() {
	return quantidade;
    }

    public void setQuantidade(double quantidade) {
	this.quantidade = quantidade;
    }

    public UsuarioModel getUsuario() {
	return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
	this.usuario = usuario;
    }

    public String getObservacao() {
	return observacao;
    }

    public void setObservacao(String observacao) {
	this.observacao = observacao;
    }

    public ItemModel getItem() {
	return item;
    }

    public void setItem(ItemModel item) {
	this.item = item;
    }

    
    
    public MovimentacaoEstoqueTypes getMovimentacaoEstoqueTypes() {
        return movimentacaoEstoqueTypes;
    }

    public void setMovimentacaoEstoqueTypes(MovimentacaoEstoqueTypes movimentacaoEstoqueTypes) {
        this.movimentacaoEstoqueTypes = movimentacaoEstoqueTypes;
    }
    
    

    public FornecedorModel getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorModel fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("quantidade", quantidade);
	record.setAttribute("tipo", movimentacaoEstoqueTypes.toString());

	if (dataHora != null) {
	    record.setAttribute("dataHora", dataHora.toString());
	}
	record.setAttribute("observacao", observacao);

	if (getUsuario() != null) {
	    record.setAttribute("usuarioAbertura", usuario.getNome());
	}
	
	if ( getFornecedor() != null) { 
	    record.setAttribute("fornecedorFantasia", getFornecedor().getFantasia());
	}
	return record;
    }

}
