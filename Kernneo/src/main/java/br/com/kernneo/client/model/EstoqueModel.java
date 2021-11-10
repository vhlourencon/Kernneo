package br.com.kernneo.client.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "estoque")
public class EstoqueModel extends GenericModel {

    @OneToOne
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;
    private double saldo;
    private double saldoMinimo;
    private boolean alertaPDV; 
    
    
    @ManyToOne
    @JoinColumn(name = "id_ultimo_fornecedor")
    private FornecedorModel ultimoFornecedor;

    @Transient
    private ArrayList<EstoqueLancamentoModel> listaDeLancamentosDeEstoque;

    public ProdutoModel getProduto() {
	return produto;
    }

    public void setProduto(ProdutoModel produto) {
	this.produto = produto;
    }

    public double getSaldo() {
	return saldo;
    }

    public void setSaldo(double saldo) {
	this.saldo = saldo;
    }

    
    
    public FornecedorModel getUltimoFornecedor() {
        return ultimoFornecedor;
    }

    public void setUltimoFornecedor(FornecedorModel ultimoFornecedor) {
        this.ultimoFornecedor = ultimoFornecedor;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

    public ArrayList<EstoqueLancamentoModel> getListaDeLancamentosDeEstoque() {
	if (listaDeLancamentosDeEstoque == null) {
	    listaDeLancamentosDeEstoque = new ArrayList<EstoqueLancamentoModel>();
	}
	return listaDeLancamentosDeEstoque;
    }

    public void setListaDeLancamentosDeEstoque(ArrayList<EstoqueLancamentoModel> listaDeLancamentosDeEstoque) {
	this.listaDeLancamentosDeEstoque = listaDeLancamentosDeEstoque;
    }

    public double getSaldoMinimo() {
	return saldoMinimo;
    }

    public void setSaldoMinimo(double saldoMinimo) {
	this.saldoMinimo = saldoMinimo;
    }

	public boolean isAlertaPDV() {
		return alertaPDV;
	}

	public void setAlertaPDV(boolean alertaPDV) {
		this.alertaPDV = alertaPDV;
	}

}
