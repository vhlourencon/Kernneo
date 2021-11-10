package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.utils.StringUtils;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;

@Table
@Entity(name = "caixa")
public class CaixaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_usuario_abertura")
	private UsuarioModel usuarioAbertura;

	@ManyToOne
	@JoinColumn(name = "id_usuario_fechamento")
	private UsuarioModel usuarioFechamento;

	private boolean aberto;
	private BigDecimal saldo = BigDecimal.ZERO;
	private BigDecimal saldoDinheiro = BigDecimal.ZERO;
	private BigDecimal saldoCartaoCredito = BigDecimal.ZERO;
	private BigDecimal saldoCartaoDebito = BigDecimal.ZERO;
	private BigDecimal saldoCheque = BigDecimal.ZERO;
	private BigDecimal saldoConvenio = BigDecimal.ZERO;
	private BigDecimal saldoAlelo = BigDecimal.ZERO;
	

	private BigDecimal saldoEntrada = BigDecimal.ZERO;
	private BigDecimal saldoSaida = BigDecimal.ZERO;

	private BigDecimal saldoDescontos = BigDecimal.ZERO;
	private BigDecimal saldoMesas = BigDecimal.ZERO;

	private int quantidadeDePessoas;
	private int quantidadeDeMesas;

	private Long ultimoNumeroDePedido = 0L;

	private Date dataHoraAbertura;
	private Date dataHoraFechamento;

	public Date getDataHoraAbertura() {
		return dataHoraAbertura;
	}

	public void setDataHoraAbertura(Date dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public UsuarioModel getUsuarioAbertura() {
		return usuarioAbertura;
	}

	public void setUsuarioAbertura(UsuarioModel usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}

	public void addMesa(MesaModel mesa) {

		setSaldoMesas(getSaldoMesas().add(mesa.getCalcValorTotalDaMesaSemDesconto()));
		setQuantidadeDeMesas(getQuantidadeDeMesas() + 1);
		setQuantidadeDePessoas(getQuantidadeDePessoas() + mesa.getQuantiadadeDePessoas());
	}

	public void addRecebimento(MesaRecebimentoModel mesaRecebimentoModel) {
		if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa) {
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().add(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cartao_credito) {
				setSaldoCartaoCredito(getSaldoCartaoCredito().add(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cartao_debito) {
				setSaldoCartaoDebito(getSaldoCartaoDebito().add(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cheque) {
				setSaldoCheque(getSaldoCheque().add(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.convenio) {
				setSaldoConvenio(getSaldoConvenio().add(mesaRecebimentoModel.getValor()));
			}else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.alelo) {
				setSaldoAlelo(getSaldoAlelo().add(mesaRecebimentoModel.getValor()));
			}

		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente) {
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().subtract(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_caixa) {
			setSaldoEntrada(getSaldoEntrada().add(mesaRecebimentoModel.getValor()));
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().add(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.saida_caixa) {
			setSaldoSaida(getSaldoSaida().add(mesaRecebimentoModel.getValor()));
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().subtract(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.desconto) {
			setSaldoDescontos(getSaldoDescontos().add(mesaRecebimentoModel.getValor()));
		}
		setSaldo(getSaldoDinheiro().add(saldoCartaoDebito).add(saldoCartaoCredito).add(saldoCheque).add(saldoConvenio).add(saldoAlelo));

	}

	public void delRecebimento(MesaRecebimentoModel mesaRecebimentoModel) {
		if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa) {
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().subtract(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cartao_credito) {
				setSaldoCartaoCredito(getSaldoCartaoCredito().subtract(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cartao_debito) {
				setSaldoCartaoDebito(getSaldoCartaoDebito().subtract(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cheque) {
				setSaldoCheque(getSaldoCheque().subtract(mesaRecebimentoModel.getValor()));
			} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.convenio) {
				setSaldoConvenio(getSaldoConvenio().subtract(mesaRecebimentoModel.getValor()));
			}else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.alelo) {
				setSaldoAlelo(getSaldoAlelo().subtract(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente) {
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().add(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_caixa) {
			setSaldoEntrada(getSaldoEntrada().subtract(mesaRecebimentoModel.getValor()));
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().subtract(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.saida_caixa) {
			setSaldoSaida(getSaldoSaida().subtract(mesaRecebimentoModel.getValor()));
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				setSaldoDinheiro(getSaldoDinheiro().add(mesaRecebimentoModel.getValor()));
			}
		} else if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.desconto) {
			setSaldoDescontos(getSaldoDescontos().subtract(mesaRecebimentoModel.getValor()));
		}

		setSaldo(getSaldoDinheiro().add(saldoCartaoDebito).add(saldoCartaoCredito).add(saldoCheque).add(saldoConvenio).add(saldoAlelo));

	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		if (dataHoraAbertura != null) {
			record.setAttribute("dataHoraAbertura", DateTimeFormat.getFormat("HH:mm dd/MM/yyyy").format(dataHoraAbertura));
		}

		if (dataHoraFechamento != null) {
			record.setAttribute("dataHoraFechamento", DateTimeFormat.getFormat("HH:mm dd/MM/yyyy").format(dataHoraFechamento));
		}

		if (usuarioAbertura != null) {
			record.setAttribute("usuarioAberturaNome", usuarioAbertura.getNome());

		}

		if (usuarioFechamento != null) {
			record.setAttribute("usuarioFechamentoNome", usuarioFechamento.getNome());
		}

		record.setAttribute("entrada", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoEntrada()));
		record.setAttribute("saida", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoSaida()));
		record.setAttribute("saldo", StringUtils.getDoubleFormatadoEmDinheiro(getSaldo()));

		record.setAttribute("saldoCartaoCredito", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoCartaoCredito()));
		record.setAttribute("saldoCartaoDebito", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoCartaoDebito()));
		record.setAttribute("saldoCheque", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoCheque()));
		record.setAttribute("saldoConvenio", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoConvenio()));
		record.setAttribute("saldoAlelo", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoAlelo()));

		record.setAttribute("saldoDinheiro", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoDinheiro()));
		record.setAttribute("saldoDescontos", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoDescontos()));
		record.setAttribute("saldoMesas", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoMesas()));
		record.setAttribute("saldoDescontoMesas", StringUtils.getDoubleFormatadoEmDinheiro(getSaldoDescontos()));

		record.setAttribute("saldoDescontoMesasDouble", getSaldoDescontos());
		record.setAttribute("entradaDouble", getSaldoEntrada());
		record.setAttribute("saidaDouble", getSaldoSaida());
		record.setAttribute("saldoDouble", getSaldo());
		record.setAttribute("saldoMesasDouble", getSaldoMesas());
		record.setAttribute("saldoCartaoCreditoDouble", getSaldoCartaoCredito());
		record.setAttribute("saldoCartaoDebitoDouble", getSaldoCartaoDebito());
		record.setAttribute("saldoChequeDouble", getSaldoCheque());
		record.setAttribute("saldoConvenioDouble", getSaldoConvenio());
		record.setAttribute("saldoDinheiroDouble", getSaldoDinheiro());
		record.setAttribute("saldoAleloDouble", getSaldoAlelo());
		record.setAttribute("saldoDescontosDouble", getSaldoDescontos());
		

		return record;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getSaldoDinheiro() {
		return saldoDinheiro;
	}

	public void setSaldoDinheiro(BigDecimal saldoDinheiro) {
		this.saldoDinheiro = saldoDinheiro;
	}

	public BigDecimal getSaldoEntrada() {
		return saldoEntrada;
	}

	public void setSaldoEntrada(BigDecimal saldoEntrada) {
		this.saldoEntrada = saldoEntrada;
	}

	public BigDecimal getSaldoSaida() {
		return saldoSaida;
	}

	public void setSaldoSaida(BigDecimal saldoSaida) {
		this.saldoSaida = saldoSaida;
	}

	public UsuarioModel getUsuarioFechamento() {
		return usuarioFechamento;
	}

	public void setUsuarioFechamento(UsuarioModel usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}

	public Date getDataHoraFechamento() {
		return dataHoraFechamento;
	}

	public void setDataHoraFechamento(Date dataHoraFechamento) {
		this.dataHoraFechamento = dataHoraFechamento;
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

	public BigDecimal getSaldoDescontos() {
		return saldoDescontos;
	}

	public void setSaldoDescontos(BigDecimal saldoDescontos) {
		this.saldoDescontos = saldoDescontos;
	}

	public BigDecimal getSaldoMesas() {
		return saldoMesas;
	}

	public void setSaldoMesas(BigDecimal saldoMesas) {
		this.saldoMesas = saldoMesas;
	}

	public int getQuantidadeDePessoas() {
		return quantidadeDePessoas;
	}

	public void setQuantidadeDePessoas(int quantidadeDePessoas) {
		this.quantidadeDePessoas = quantidadeDePessoas;
	}

	public int getQuantidadeDeMesas() {
		return quantidadeDeMesas;
	}

	public void setQuantidadeDeMesas(int quantidadeDeMesas) {
		this.quantidadeDeMesas = quantidadeDeMesas;
	}

	public BigDecimal getSaldoConvenio() {
		return saldoConvenio;
	}

	public void setSaldoConvenio(BigDecimal saldoConvenio) {
		this.saldoConvenio = saldoConvenio;
	}

	public Long getUltimoNumeroDePedido() {
		return ultimoNumeroDePedido;
	}

	public void setUltimoNumeroDePedido(Long ultimoNumeroDePedido) {
		this.ultimoNumeroDePedido = ultimoNumeroDePedido;
	}

	public BigDecimal getSaldoAlelo() {
		if ( saldoAlelo == null) { 
			saldoAlelo = BigDecimal.ZERO;
		}
		return saldoAlelo;
	}

	public void setSaldoAlelo(BigDecimal saldoAlelo) {
		this.saldoAlelo = saldoAlelo;
	}

}
