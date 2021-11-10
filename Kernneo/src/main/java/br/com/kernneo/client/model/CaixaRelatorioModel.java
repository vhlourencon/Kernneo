package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import com.smartgwt.client.data.Record;

public class CaixaRelatorioModel extends GenericModel {
    private BigDecimal saldoDinheiro;
    private BigDecimal saldoCartaoCredito;
    private BigDecimal saldoCartaoDebito;
    private BigDecimal saldoCheque;
    private BigDecimal entrada;
    private BigDecimal saida;
    private BigDecimal saldoMesas;
    private BigDecimal saldo;
    private Date dataHoraAbertura;

    public CaixaRelatorioModel(BigDecimal saldoDinheiro, BigDecimal saldoCartaoCredito, BigDecimal saldoCartaoDebito, BigDecimal saldoCheque, BigDecimal entrada, BigDecimal saida, BigDecimal saldoMesas, BigDecimal saldo , Date dataHoraAbertura) {
	setSaldo(saldo);
	setSaida(saida);
	setEntrada(entrada);
	setSaldoCartaoCredito(saldoCartaoCredito);
	setSaldoCartaoDebito(saldoCartaoDebito);
	setSaldoCheque(saldoCheque);
	setSaldoDinheiro(saldoDinheiro);
	setDataHoraAbertura(dataHoraAbertura);
	setSaldoMesas(saldoMesas);
	
    }

    public Date getDataHoraAbertura() {
	return dataHoraAbertura;
    }

    public void setDataHoraAbertura(Date dataHoraAbertura) {
	this.dataHoraAbertura = dataHoraAbertura;
    }

    public CaixaRelatorioModel() {

    }

    public BigDecimal getSaldoDinheiro() {
	return saldoDinheiro;
    }

    public void setSaldoDinheiro(BigDecimal saldoDinheiro) {
	this.saldoDinheiro = saldoDinheiro;
    }

    public BigDecimal getSaldoCartaoCredito() {
	return saldoCartaoCredito;
    }

    public void setSaldoCartaoCredito(BigDecimal saldoCartaoCredito) {
	this.saldoCartaoCredito = saldoCartaoCredito;
    }

    public BigDecimal getSaldoCartaoDebito() {
	return saldoCartaoDebito;
    }

    public void setSaldoCartaoDebito(BigDecimal saldoCartaoDebito) {
	this.saldoCartaoDebito = saldoCartaoDebito;
    }

    public BigDecimal getSaldoCheque() {
	return saldoCheque;
    }

    public void setSaldoCheque(BigDecimal saldoCheque) {
	this.saldoCheque = saldoCheque;
    }

    public BigDecimal getEntrada() {
	return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
	this.entrada = entrada;
    }

    public BigDecimal getSaida() {
	return saida;
    }

    public void setSaida(BigDecimal saida) {
	this.saida = saida;
    }

    public BigDecimal getSaldo() {
	return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
	this.saldo = saldo;
    }

    public BigDecimal getSaldoMesas() {
	return saldoMesas;
    }

    public void setSaldoMesas(BigDecimal saldoMesas) {
	this.saldoMesas = saldoMesas;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
