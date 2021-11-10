/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Entity
@Table(name = "mesa_recebimento")
public class MesaRecebimentoModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private MesaModel mesa;

    @ManyToOne
    @JoinColumn(name = "id_caixa")
    private CaixaModel caixa;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;

    private BigDecimal valor;

    private Date dataHora;
    private String descricao;

    @Transient
    private boolean recebido;

    public UsuarioModel getUsuario() {
	return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
	this.usuario = usuario;
    }

    @Enumerated(EnumType.STRING)
    private MovimentacaoTypes movimentacaoTypes;

    public MesaModel getMesa() {
	return mesa;
    }

    public void setMesa(MesaModel mesa) {
	this.mesa = mesa;
    }

    public FormaDePagamento getFormaDePagamento() {
	return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
	this.formaDePagamento = formaDePagamento;
    }

    public BigDecimal getValor() {
	return valor;
    }

    public void setValor(BigDecimal valor) {
	this.valor = valor;
    }

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

    public MovimentacaoTypes getMovimentacaoTypes() {
	return movimentacaoTypes;
    }

    public void setMovimentacaoTypes(MovimentacaoTypes movimentacaoTypes) {
	this.movimentacaoTypes = movimentacaoTypes;
    }

    public ClienteModel getCliente() {
	return cliente;
    }

    public void setCliente(ClienteModel cliente) {
	this.cliente = cliente;
    }

    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("formaDePagamento", formaDePagamento);
	record.setAttribute("valorDouble", getValor().doubleValue());
	if (getFormaDePagamento() == FormaDePagamento.convenio) {
	    String nomeCliente = "";
	    if (getCliente() != null) {
		nomeCliente = getCliente().getNome();
	    }
	    record.setAttribute("formaDePagamento", formaDePagamento + " - " + nomeCliente);
	}
	if (getMesa() != null) {
	    record.setAttribute("mesa", getMesa().getId());
	    record.setAttribute("mesaNumero", getMesa().getNumero());

	} else {
	    record.setAttribute("mesa", "null");
	}

	if (getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa) {
	    record.setAttribute("complemento", "Entrada");
	    if (valor != null) {
		record.setAttribute("valor", "+ " + StringUtils.getDoubleFormatadoEmDinheiro(valor));
	    }

	} else if (getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente) {
	    record.setAttribute("complemento", "Saída - Troco para o Cliente");
	    if (valor != null) {
		record.setAttribute("valor", "- " + StringUtils.getDoubleFormatadoEmDinheiro(valor));
	    }

	} else if (getMovimentacaoTypes() == MovimentacaoTypes.desconto) {
	    record.setAttribute("complemento", getDescricao());
	    if (valor != null) {
		record.setAttribute("valor", "- " + StringUtils.getDoubleFormatadoEmDinheiro(valor));
	    }

	}  else if (getMovimentacaoTypes() == MovimentacaoTypes.taxa) {
	    record.setAttribute("complemento", getDescricao());
	    if (valor != null) {
		record.setAttribute("valor", "+ " + StringUtils.getDoubleFormatadoEmDinheiro(valor));
	    }

	}
	
	if ( getDataHora() != null) { 
	    record.setAttribute("dataHora", StringUtils.getDataHoraFormatada(getDataHora()));
	}

	return record;
    }

    public boolean isRecebido() {
	return recebido;
    }

    public void setRecebido(boolean recebido) {
	this.recebido = recebido;
    }

}
