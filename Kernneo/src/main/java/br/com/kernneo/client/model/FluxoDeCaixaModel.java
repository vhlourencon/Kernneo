package br.com.kernneo.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FluxoDeCaixaModel implements IsSerializable {

    private double dinheiro;
    private double cartao;
    private double cheque;
    private double tickets;
    private double contraVale;
    private double pendura;
    private double funcionario;
    private double cortesia;
    private double convenio;
    private double total;

    public double getDinheiro() {
	return dinheiro;
    }

    public void setDinheiro(double dinheiro) {
	this.dinheiro = dinheiro;
    }

    public double getCartao() {
	return cartao;
    }

    public void setCartao(double cartao) {
	this.cartao = cartao;
    }

    public double getCheque() {
	return cheque;
    }

    public void setCheque(double cheque) {
	this.cheque = cheque;
    }

    public double getTickets() {
	return tickets;
    }

    public void setTickets(double tickets) {
	this.tickets = tickets;
    }

    public double getContraVale() {
	return contraVale;
    }

    public void setContraVale(double contraVale) {
	this.contraVale = contraVale;
    }

    public double getPendura() {
	return pendura;
    }

    public void setPendura(double pendura) {
	this.pendura = pendura;
    }

    public double getFuncionario() {
	return funcionario;
    }

    public void setFuncionario(double funcionario) {
	this.funcionario = funcionario;
    }

    public double getCortesia() {
	return cortesia;
    }

    public void setCortesia(double cortesia) {
	this.cortesia = cortesia;
    }

    public double getConvenio() {
	return convenio;
    }

    public void setConvenio(double convenio) {
	this.convenio = convenio;
    }

    public double getTotal() {
	return total;
    }

    public void setTotal(double total) {
	this.total = total;
    }

}
