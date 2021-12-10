package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "conta_bancaria")
public class ContaBancariaModel extends GenericModel {

	private String nome;
	@ManyToOne
	@JoinColumn(name = "id_banco")
	private BancoModel banco;

	@ManyToOne(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "id_movimentacao_inicial")
	private MovimentacaoModel movimentacaoInicial;

	private BigDecimal chequeEspecial;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BancoModel getBanco() {
		return banco;
	}

	public void setBanco(BancoModel banco) {
		this.banco = banco;
	}

	public MovimentacaoModel getMovimentacaoInicial() {
		return movimentacaoInicial;
	}

	public void setMovimentacaoInicial(MovimentacaoModel movimentacaoInicial) {
		this.movimentacaoInicial = movimentacaoInicial;
	}

	public BigDecimal getChequeEspecial() {
		return chequeEspecial;
	}

	public void setChequeEspecial(BigDecimal chequeEspecial) {
		this.chequeEspecial = chequeEspecial;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();

		return record;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNome();
	}

}
