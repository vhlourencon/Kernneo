package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.docs.MobileDevelopment;

import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.client.types.MovimentacaoTypes;

@Table
@Entity(name = "movimentacao")
public class MovimentacaoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_movimentacao_pai")
	private MovimentacaoModel movimentacaoPai;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriaModel categoria;

	@ManyToOne
	@JoinColumn(name = "id_conta")
	private ContaBancariaModel conta;

	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	private FornecedorModel fornecedor;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	private BigDecimal valor;
	private Date dataHora;
	private String descricao;

	private boolean repetir;
	private int repetirQtde;

	private boolean contaMovimentacaoInicial;

	@Enumerated(EnumType.STRING)
	private MovimentacaoFinanceiraTypes tipo;

	public MovimentacaoModel getMovimentacaoPai() {
		return movimentacaoPai;
	}

	public void setMovimentacaoPai(MovimentacaoModel movimentacaoPai) {
		this.movimentacaoPai = movimentacaoPai;
	}

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public ContaBancariaModel getConta() {
		return conta;
	}

	public void setConta(ContaBancariaModel conta) {
		this.conta = conta;
	}

	public FornecedorModel getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorModel fornecedor) {
		this.fornecedor = fornecedor;
	}

	public BigDecimal getValor() {
		if (valor != null) {
			valor = valor.setScale(2, RoundingMode.HALF_EVEN);
		}

		return valor;
	}

	public void setValor(BigDecimal valor) {
		if (valor != null) {
			valor = valor.setScale(2, RoundingMode.HALF_EVEN);
		}
		this.valor = valor;
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

	public boolean isRepetir() {
		return repetir;
	}

	public void setRepetir(boolean repetir) {
		this.repetir = repetir;
	}

	public int getRepetirQtde() {
		return repetirQtde;
	}

	public void setRepetirQtde(int repetirQtde) {
		this.repetirQtde = repetirQtde;
	}

	public MovimentacaoFinanceiraTypes getTipo() {
		return tipo;
	}

	public void setTipo(MovimentacaoFinanceiraTypes tipo) {
		this.tipo = tipo;
	}

	@Override
	public Record toRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isContaMovimentacaoInicial() {
		return contaMovimentacaoInicial;
	}
	
	
	

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public void setContaMovimentacaoInicial(boolean contaMovimentacaoInicial) {
		this.contaMovimentacaoInicial = contaMovimentacaoInicial;
	}

}
