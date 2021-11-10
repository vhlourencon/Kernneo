package br.com.kernneo.client.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MovimentacaoTypes;

@Table
@Entity(name = "movimentacao")
public class MovimentacaoModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_caixa")
    private CaixaModel caixa;
    private Date dataHora;
    private String descricao;
    private Double valor;
    private FormaDePagamento formaDePagamento;
    private String observacao;
    
    
  

    @Enumerated(EnumType.STRING)
    private MovimentacaoTypes movimentacaoTypes;

    public CaixaModel getCaixa() {
	return caixa;
    }

    public void setCaixa(CaixaModel caixa) {
	this.caixa = caixa;
    }

    public Date getDataHora() {
	return dataHora;
    }

    public void setDataHora(Date dataHora) {
	this.dataHora = dataHora;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public Double getValor() {
	return valor;
    }

    public void setValor(Double valor) {
	this.valor = valor;
    }

    public FormaDePagamento getFormaDePagamento() {
	return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
	this.formaDePagamento = formaDePagamento;
    }

    public String getObservacao() {
	return observacao;
    }

    public void setObservacao(String observacao) {
	this.observacao = observacao;
    }

    public MovimentacaoTypes getMovimentacaoTypes() {
	return movimentacaoTypes;
    }

    public void setMovimentacaoTypes(MovimentacaoTypes movimentacaoTypes) {
	this.movimentacaoTypes = movimentacaoTypes;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
