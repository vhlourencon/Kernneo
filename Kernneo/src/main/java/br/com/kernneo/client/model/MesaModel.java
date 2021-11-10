package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.kernneo.client.exception.MesaException;
import br.com.kernneo.client.exception.MesaRecebimentoException;
import br.com.kernneo.client.types.CalculoFracaoType;
import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MesaStatus;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.types.NFCeSituacao;
import br.com.kernneo.client.types.PedidoDocumentoType;
import br.com.kernneo.client.utils.StringUtils;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;

@Entity
@Table(name = "mesa")
public class MesaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_caixa_abertura")
	private CaixaModel caixaAbertura;

	@ManyToOne
	@JoinColumn(name = "id_funcionario_atendente")
	private FuncionarioModel funcionarioAtendente;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	@ManyToOne
	@JoinColumn(name = "id_endereco_cliente")
	private EnderecoClienteModel enderecoCliente;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dataHoraDeAbertura;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dataHoraDeRecebimento;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dataHoraEntrega;

	@Enumerated(EnumType.STRING)
	private MesaTipo tipo;

	@Enumerated(EnumType.STRING)
	private CalculoFracaoType tipoDeCalculoFracionado;

	@ManyToOne
	@JoinColumn(name = "id_nfce_ultima_acao")
	@Cascade(CascadeType.ALL)
	private NFCeModel nfceUltimaEmitida;

	private BigDecimal taxaDeServico = BigDecimal.ZERO;
	private BigDecimal taxaDeEntrega = BigDecimal.ZERO;

	private BigDecimal logDesconto = BigDecimal.ZERO;
	private BigDecimal logTotal = BigDecimal.ZERO;
	private BigDecimal logSubTotal = BigDecimal.ZERO;
	private BigDecimal logTaxaDeServico = BigDecimal.ZERO;
	private BigDecimal logTaxaDeEntrega = BigDecimal.ZERO;

	private boolean configEmpresaTelaDeProdutosExtendida;
	private boolean configEmpresaTelaDeProdutosAposConsulta;
	private boolean configEmpresaRealizarPedidoSemCobrar;
	private boolean configEmpresaPermiteAlterarDocumento;
	private boolean configEmpresaCobraTxdEntregaPorBairro;
	@Enumerated(EnumType.STRING)
	private PedidoDocumentoType documentoType;

	@Enumerated(EnumType.STRING)
	private MesaStatus status;

	private Long numero;
	private int quantiadadeDePessoas;
	private String pedidoBalcaoNomeCliente;
	private String motivoCancelamento;

	@ManyToOne
	@JoinColumn(name = "id_usuario_abertura")
	private UsuarioModel usuarioAbertura;

	@ManyToOne
	@JoinColumn(name = "id_usuario_recebimento")
	private UsuarioModel usuarioRecebimento;

	@javax.persistence.Transient
	private ArrayList<ItemModel> listaDeItens;

	@javax.persistence.Transient
	private ArrayList<MesaRecebimentoModel> listaDeRecedimentos = new ArrayList<MesaRecebimentoModel>();

	@javax.persistence.Transient
	private ArrayList<NFCeModel> listaDeNFCeEmitidas = new ArrayList<NFCeModel>();

	public Date getDataHoraDeRecebimento() {
		return dataHoraDeRecebimento;
	}

	public void setDataHoraDeRecebimento(Date dataHoraDeRecebimento) {
		this.dataHoraDeRecebimento = dataHoraDeRecebimento;
	}

	public UsuarioModel getUsuarioRecebimento() {
		return usuarioRecebimento;
	}

	public void setUsuarioRecebimento(UsuarioModel usuarioRecebimento) {
		this.usuarioRecebimento = usuarioRecebimento;
	}

	public ArrayList<NFCeModel> getListaDeNFCeEmitidas() {
		if (listaDeNFCeEmitidas == null) {
			listaDeNFCeEmitidas = new ArrayList<NFCeModel>();
		}
		return listaDeNFCeEmitidas;
	}

	public void setListaDeNFCeEmitidas(ArrayList<NFCeModel> listaDeNFCeEmitidas) {
		this.listaDeNFCeEmitidas = listaDeNFCeEmitidas;
	}

	public ArrayList<ItemModel> getListaDeItens() {
		if (listaDeItens == null) {
			listaDeItens = new ArrayList<ItemModel>();
		}
		return listaDeItens;
	}

	public void setListaDeItens(ArrayList<ItemModel> listaDeItens) {
		this.listaDeItens = listaDeItens;
	}

	public ArrayList<ItemImpressaoModel> getListaDeItensAgrupados() {
		ArrayList<ItemImpressaoModel> listaAux = new ArrayList<ItemImpressaoModel>();

		for (ItemModel itemModel : getListaDeItens()) {
			
			ItemImpressaoModel itemImpressaoModel = new ItemImpressaoModel(); 
			itemImpressaoModel.setQuantidade(itemModel.getCalcQuantidade());
			itemImpressaoModel.setProduto(itemModel.getProduto());
			itemImpressaoModel.setValorUnitario(itemModel.getCalcValorUnitario());
			itemImpressaoModel.setValorTotal(itemModel.getCalcValorTotal());
			itemImpressaoModel.setNomeDoProduto(itemModel.getCalcNome());
			itemImpressaoModel.setValorTotalSemDesconto(itemModel.getCalcValorTotalSemDesconto());
			itemImpressaoModel.setConjugado(false);

			if (itemModel.getListaDeItensConjugados().size() == 0) {
				
				boolean encontrou = false; 
				for (ItemImpressaoModel itemDaListaModel : listaAux) { 
					if (itemModel.getProduto().getId().compareTo(itemDaListaModel.getProduto().getId()) == 0 && itemModel.getCalcValorUnitario().compareTo(itemDaListaModel.getValorUnitario()) == 0 && itemDaListaModel.isConjugado() ==false) {
						encontrou  = true; 
						itemDaListaModel.setQuantidade(itemModel.getCalcQuantidade() + itemDaListaModel.getQuantidade());
						itemDaListaModel.setValorTotal(itemDaListaModel.getValorTotal().add(itemModel.getCalcValorTotal()));
					}
				}
				
				if ( encontrou == false ) { 
					listaAux.add(itemImpressaoModel);
				}
			} else {
				itemImpressaoModel.setConjugado(true);
				listaAux.add(itemImpressaoModel);
			}

		}
		return listaAux;
	}

	public void addItem(ItemModel item) throws MesaException {
		item.setMesa(this);
		if (getTipo() == MesaTipo.balcao) {
			if (getStatus() == MesaStatus.mesa_recebida || getStatus() == MesaStatus.balcao_recebido) {
				throw new MesaException("Não é possivel inserir itens \nPedido já recebido!");
			}

		}

		if (isAgrupandoItem()) {

			ItemModel ultimoItemAdicionado = getUltimoItemDaLista();

			if (ultimoItemAdicionado.getQuantidade().compareTo(item.getQuantidade()) != 0) {

				Double quantidadeDoUltimoItem = ultimoItemAdicionado.getQuantidade();
				if (ultimoItemAdicionado.getCalcSomaDoAgrupamento() + item.getQuantidade() > 1.0) {
					throw new MesaException("Quantidade inválida para agrupamento");
				} else if (quantidadeDoUltimoItem.compareTo(getQuantidadeConvertidaEmDouble("1/2")) == 0) {
					throw new MesaException("Quantidade Inválida!\nContinue adicionando 1/2");
				} else if (quantidadeDoUltimoItem.compareTo(getQuantidadeConvertidaEmDouble("1/3")) == 0) {
					throw new MesaException("Quantidade Inválida!\nContinue adicionando 1/3");
				} else if (quantidadeDoUltimoItem.compareTo(getQuantidadeConvertidaEmDouble("1/4")) == 0) {
					throw new MesaException("Quantidade Inválida!\nContinue adicionando 1/4");
				} else {
					throw new MesaException("Quantidade Inválida!\nContinue adicionando  a mesma proporção");
				}
			}

			if (ultimoItemAdicionado.getProduto().getCategoria().getId().compareTo(item.getProduto().getCategoria().getId()) != 0) {
				throw new MesaException("Os itens agrupados devem ser da mesma categoria");
			}

			ultimoItemAdicionado.addItemConjugado(item);

		} else {
			item.setMesa(this);
			getListaDeItens().add(item);
		}

	}

	public String getQuantidadeConvertidaEmString(Double valor) {

		if (valor.compareTo(getQuantidadeConvertidaEmDouble("1/2")) == 0) {
			return "1/2";
		} else if (valor.compareTo(getQuantidadeConvertidaEmDouble("1/3")) == 0) {
			return "1/3";
		} else if (valor.compareTo(getQuantidadeConvertidaEmDouble("1/4")) == 0) {
			return "1/4";
		} else {
			StringBuilder numberPattern = new StringBuilder("0.00");

			String retorno = NumberFormat.getFormat(numberPattern.toString()).format(valor);
			if (retorno.endsWith(".00")) {
				retorno = retorno.substring(0, retorno.length() - 3);
			}

			return retorno;

		}

	}

	public Double getQuantidadeConvertidaEmDouble(String quantidade) throws NumberFormatException {
		Double valorQuantidade = 0.0;

		if (quantidade.indexOf("/") != -1) {

			Double quantidade1 = Double.valueOf(quantidade.substring(0, quantidade.indexOf("/")));
			Double quantidade2 = Double.valueOf(quantidade.substring(quantidade.indexOf("/") + 1, quantidade.length()));

			valorQuantidade = (quantidade1 / quantidade2);

		} else {

			valorQuantidade = Double.valueOf(quantidade);

		}

		return valorQuantidade;
	}

	// public BigDecimal getTotalPagoEmdinheiroComOtroco() {
	// BigDecimal totalPago = BigDecimal.ZERO;
	// BigDecimal entrada = getCalcTotalDeEntradasEmDinheiro();
	// BigDecimal saida = getTrocoTotalCalc();
	// if (entrada.compareTo(saida) >= 0) {
	// totalPago = entrada.subtract(saida);
	// }
	//
	// return totalPago;
	// }

	public CalculoFracaoType getTipoDeCalculoFracionado() {
		return tipoDeCalculoFracionado;
	}

	public void setTipoDeCalculoFracionado(CalculoFracaoType tipoDeCalculoFracionado) {
		this.tipoDeCalculoFracionado = tipoDeCalculoFracionado;
	}

	public boolean isConfigEmpresaTelaDeProdutosExtendida() {
		return configEmpresaTelaDeProdutosExtendida;
	}

	public void setConfigEmpresaTelaDeProdutosExtendida(boolean configEmpresaTelaDeProdutosExtendida) {
		this.configEmpresaTelaDeProdutosExtendida = configEmpresaTelaDeProdutosExtendida;
	}

	public boolean isConfigEmpresaTelaDeProdutosAposConsulta() {
		return configEmpresaTelaDeProdutosAposConsulta;
	}

	public void setConfigEmpresaTelaDeProdutosAposConsulta(boolean configEmpresaTelaDeProdutosAposConsulta) {
		this.configEmpresaTelaDeProdutosAposConsulta = configEmpresaTelaDeProdutosAposConsulta;
	}

	public Date getDataHoraEntrega() {
		return dataHoraEntrega;
	}

	public void setDataHoraEntrega(Date dataHoraEntrega) {
		this.dataHoraEntrega = dataHoraEntrega;
	}

	public FuncionarioModel getFuncionarioAtendente() {
		return funcionarioAtendente;
	}

	public void setFuncionarioAtendente(FuncionarioModel funcionarioAtendente) {
		this.funcionarioAtendente = funcionarioAtendente;
	}

	public Date getDataHoraDeAbertura() {
		return dataHoraDeAbertura;
	}

	public void setDataHoraDeAbertura(Date dataHoraDeAbertura) {
		this.dataHoraDeAbertura = dataHoraDeAbertura;
	}

	public MesaStatus getStatus() {
		return status;
	}

	public void setStatus(MesaStatus status) {
		this.status = status;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public BigDecimal getTaxaDeServico() {
		return taxaDeServico;
	}

	public void setTaxaDeServico(BigDecimal taxaDeServico) {
		this.taxaDeServico = taxaDeServico;
	}

	public BigDecimal getTaxaDeEntrega() {

		return taxaDeEntrega;
	}

	public void setTaxaDeEntrega(BigDecimal taxaDeEntrega) {
		this.taxaDeEntrega = taxaDeEntrega;
	}

	public BigDecimal getLogTotal() {
		return logTotal;
	}

	public void setLogTotal(BigDecimal logTotal) {
		this.logTotal = logTotal;
	}

	public BigDecimal getLogSubTotal() {
		return logSubTotal;
	}

	public void setLogSubTotal(BigDecimal logSubTotal) {
		this.logSubTotal = logSubTotal;
	}

	public BigDecimal getLogTaxaDeServico() {
		return logTaxaDeServico;
	}

	public void setLogTaxaDeServico(BigDecimal logTaxaDeServico) {
		this.logTaxaDeServico = logTaxaDeServico;
	}

	public BigDecimal getLogDesconto() {
		return logDesconto;
	}

	public void setLogDesconto(BigDecimal logDesconto) {
		this.logDesconto = logDesconto;
	}

	public ArrayList<MesaRecebimentoModel> getListaDeRecedimentos() {
		if (listaDeRecedimentos == null) {
			listaDeRecedimentos = new ArrayList<MesaRecebimentoModel>();
		}
		return listaDeRecedimentos;
	}

	public void setListaDeRecedimentos(ArrayList<MesaRecebimentoModel> listaDeRecedimentos) {
		this.listaDeRecedimentos = listaDeRecedimentos;
	}

	public int getQuantiadadeDePessoas() {
		return quantiadadeDePessoas;
	}

	public void setQuantiadadeDePessoas(int quantiadadeDePessoas) {
		this.quantiadadeDePessoas = quantiadadeDePessoas;
	}

	public UsuarioModel getUsuarioAbertura() {
		return usuarioAbertura;
	}

	public void setUsuarioAbertura(UsuarioModel usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}

	public EnderecoClienteModel getEnderecoCliente() {
		return enderecoCliente;
	}

	public void setEnderecoCliente(EnderecoClienteModel enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
		if (isConfigEmpresaCobraTxdEntregaPorBairro()) {
			if (enderecoCliente != null && enderecoCliente.getBairro() != null) {
				setTaxaDeEntrega(enderecoCliente.getBairro().getTaxaDeEntrega());
			} else {
				setTaxaDeEntrega(BigDecimal.ZERO);
			}
		}
	}

	public MesaTipo getTipo() {
		return tipo;
	}

	public void setTipo(MesaTipo tipo) {
		this.tipo = tipo;
	}

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("pedido", getId());
		if (enderecoCliente != null) {
			record.setAttribute("enderecoCliente", enderecoCliente.toString());
		}

		if (getCliente() != null) {
			record.setAttribute("clienteNome", getCliente().getNome());
		}

		if (getUsuarioAbertura() != null) {
			record.setAttribute("usuarioAberturaNome", getUsuarioAbertura().getNome());
		} else {
			record.setAttribute("usuarioAberturaNome", "-");
		}

		if (getDataHoraEntrega() != null) {
			record.setAttribute("dataHoraEntrega", StringUtils.getDataHoraFormatada(getDataHoraEntrega()));
		}
		if (getDataHoraDeAbertura() != null) {
			record.setAttribute("dataHoraAbertura", StringUtils.getDataHoraFormatada(getDataHoraDeAbertura()));
		}

		record.setAttribute("status", status);
		record.setAttribute("valorTotal", getLogTotal());
		if (getLogTotal() != null) {
			record.setAttribute("valorTotalEmDinheiro", StringUtils.getDoubleFormatadoEmDinheiro(getLogTotal()));
		}
		if (getTipo() != null) {
			if (getTipo() == MesaTipo.entrega) {
				if (getStatus() == MesaStatus.entrega_iniciado) {
					record.setAttribute("statusString", "Iniciado");
					record.setAttribute("statusImagem", "imagemPedidoIniciado");
				} else if (getStatus() == MesaStatus.entrega_saiu) {
					record.setAttribute("statusString", "Em entrega");
					record.setAttribute("statusImagem", "imagemPedidoEntrega");
				} else if (getStatus() == MesaStatus.entrega_recebido) {
					record.setAttribute("statusString", "Recebido");
					record.setAttribute("statusImagem", "imagemPedidoRecebido");
				} else if ( getStatus() == MesaStatus.entrega_cancelado) { 
					record.setAttribute("statusString", "Cancelado");
					record.setAttribute("statusImagem", "imagemPedidoCancelado");
				}
			}
		}

		if (getNfceUltimaEmitida() == null) {
			record.setAttribute("nfceSituacao", NFCeSituacao.nao_emitida);
		} else {
			record.setAttribute("nfceSituacao", getNfceUltimaEmitida().getNfCeSituacao());
			record.setAttribute("nfceSerie", getNfceUltimaEmitida().getSerie());
			record.setAttribute("nfceNumero", getNfceUltimaEmitida().getNumero());
			record.setAttribute("nfceDataHoraEmissao", getNfceUltimaEmitida().getDataHoraEmissao());
			record.setAttribute("nfceCstat", getNfceUltimaEmitida().getcStat());
			record.setAttribute("nfceXmotivo", getNfceUltimaEmitida().getxMotivo());

			if (getNfceUltimaEmitida().getCliente() != null) {
				record.setAttribute("nfceClienteNome", getCliente().getNome());

			} else {
				record.setAttribute("nfceClienteNome", " NÃO IDENTIFICADO");

			}
			if (getNfceUltimaEmitida().getPath() != null) {
				String nfceArquivoXMLString = getNfceUltimaEmitida().getPath();
				int indexDaUltimaBarra = nfceArquivoXMLString.lastIndexOf('\\');

				if (indexDaUltimaBarra != -1) {
					indexDaUltimaBarra = indexDaUltimaBarra + 1;
					nfceArquivoXMLString = nfceArquivoXMLString.substring(indexDaUltimaBarra, nfceArquivoXMLString.length());
					record.setAttribute("nfceArquivoXml", nfceArquivoXMLString);
				}
			}

			// record.setAttribute("nfceSituacao", getNfce().getNfCeSituacao());

		}

		return record;
	}

	public void addMesaRecebimento(MesaRecebimentoModel mesaRecebimentoModel) throws MesaRecebimentoException {

		if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa && mesaRecebimentoModel.getFormaDePagamento() != FormaDePagamento.dinheiro && (mesaRecebimentoModel.getValor().compareTo(getCalcFaltaReceberDoCliente()) > 0)) {
			throw new MesaRecebimentoException("O valor adicionado é superior ao total da mesa");
		} else {
			if (getTipo() == MesaTipo.mesa || getTipo() == MesaTipo.balcao) {
				if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente && mesaRecebimentoModel.getValor().compareTo(getCalcFaltaDevolverParaOcliente()) > 0) {
					throw new MesaRecebimentoException("O valor adicionado é maior que o troco");
				} else {
					getListaDeRecedimentos().add(mesaRecebimentoModel);
				}
			} else if (getTipo() == MesaTipo.entrega) {
				getListaDeRecedimentos().add(mesaRecebimentoModel);

			}

		}

	}

	/*
	 * Falta receber e Falta devolver
	 */
	public BigDecimal getCalcFaltaReceberDoCliente() {
		BigDecimal totalDoValorRecebido = getCalcTotalDeEntradas().subtract(getCalcTotalDeTrocoParaOcliente());
		BigDecimal totalDaMesa = getCalcValorTotalDaMesa();
		BigDecimal falta = BigDecimal.ZERO;
		if (totalDaMesa.compareTo(totalDoValorRecebido) > 0) {
			falta = totalDaMesa.subtract(totalDoValorRecebido);
		}

		falta = falta.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return falta;
	}

	public BigDecimal getCalcFaltaDevolverParaOcliente() {
		BigDecimal saida = getCalcTotalDeTrocoParaOcliente();
		// if (getTipo() == MesaTipo.mesa) {
		// saida = getSaidaTotal();
		// }
		BigDecimal totalDoValorRecebido = getCalcTotalDeEntradasComOtroco();
		BigDecimal totalDoValorRecebidoEmDinheiro = getCalcTotalDeEntradasEmDinheiro().subtract(saida);
		BigDecimal totalDaMesa = getCalcValorTotalDaMesa();

		BigDecimal troco = BigDecimal.ZERO;
		if (totalDoValorRecebido.compareTo(totalDaMesa) > 0) {
			troco = totalDoValorRecebido.subtract(totalDaMesa);

			if (totalDoValorRecebidoEmDinheiro.compareTo(troco) < 0) {
				troco = totalDoValorRecebidoEmDinheiro;

			}
		}

		troco = troco.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return troco;
	}

	/*
	 * subTotal e Total e taxa de Servico
	 */

	public BigDecimal getCalcTaxaDeEntregaEmDinheiro() {
		BigDecimal taxaDeEntrega = BigDecimal.ZERO;
		if (getTipo() == MesaTipo.entrega) {

			taxaDeEntrega = getTaxaDeEntrega();

		}

		if (taxaDeEntrega == null) {
			taxaDeEntrega = BigDecimal.ZERO;
		}

		taxaDeEntrega = taxaDeEntrega.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return taxaDeEntrega;

	}

	public BigDecimal getCalcTaxaDeServicoEmDinheiro() {
		BigDecimal servico = BigDecimal.ZERO;
		if (getTipo() == MesaTipo.mesa) {
			if (getTaxaDeServico() != null) {
				servico = getTaxaDeServico().multiply(getCalcSubTotal());
			}
		}

		servico = servico.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return servico;

	}

	public BigDecimal getCalcSubTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (ItemModel itemModel : getListaDeItens()) {
			total = total.add(itemModel.getCalcValorTotalSemDesconto());
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	public BigDecimal getCalcValorTotalDeTaxas() {
		BigDecimal total = BigDecimal.ZERO;
		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.taxa) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	public BigDecimal getCalcValorTotalDaMesa() {
		BigDecimal total = getCalcValorTotalDaMesaSemDesconto();
		total = total.add(getCalcValorTotalDeTaxas());
		total = total.subtract(getCalcTotalDeDesconto());

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	public BigDecimal getCalcValorTotalDaMesaSemDesconto() {
		BigDecimal total = getCalcSubTotal();
		if (getTipo() == MesaTipo.mesa) {
			total = total.add(getCalcTaxaDeServicoEmDinheiro());
		} else if (getTipo() == MesaTipo.entrega) {
			total = total.add(getCalcTaxaDeEntregaEmDinheiro());
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	public BigDecimal getCalcSaldo() {
		BigDecimal saldo = getCalcValorTotalDaMesa();
		saldo = saldo.subtract(getCalcTotalDeEntradasComOtroco());
		saldo = saldo.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return saldo;
	}

	/**
	 * 
	 * NÃO APAGAR
	 */
	// public BigDecimal getSaldo() {
	//
	// BigDecimal saida = getSaidaEmDinheiroTotal();
	// BigDecimal entradaRecebida = getEntradaEmDinheiroRecebida();
	// BigDecimal entradaNaoRecebida = getEntradaEmDinheiroNaoRecebida();
	// BigDecimal mesa = getTotal();
	//
	// BigDecimal saldo =
	// saida.subtract((entradaNaoRecebida.subtract(mesa)).add(entradaNaoRecebida.subtract(entradaRecebida)));
	// if (saldo.compareTo(BigDecimal.ZERO) != 0) {
	// saldo = saldo.multiply(new BigDecimal(-1));
	// }
	//
	// return saldo;
	// }

	/*
	 * Desconto
	 */
	public BigDecimal getCalcTotalDeDesconto() {
		BigDecimal total = BigDecimal.ZERO;

		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.desconto) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}
		
		for (ItemModel itemModel : getListaDeItens()) { 
			if ( itemModel.getCalcDescontoValor() != null) { 
				total = total.add(itemModel.getCalcDescontoValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	/*
	 * Entradas
	 */
	public BigDecimal getCalcTotalDeEntradas() {
		BigDecimal total = BigDecimal.ZERO;

		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	protected BigDecimal getCalcTotalDeEntradasRecebidas() {
		BigDecimal total = BigDecimal.ZERO;

		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa && (mesaRecebimentoModel.getCaixa() != null || mesaRecebimentoModel.isRecebido())) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	protected BigDecimal getCalcTotalDeEntradasEmDinheiro() {
		BigDecimal total = BigDecimal.ZERO;

		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa && mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	protected BigDecimal getCalcTotalDeEntradasEmDinheiroNaoRecebidas() {
		BigDecimal total = BigDecimal.ZERO;

		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa && (mesaRecebimentoModel.isRecebido() == false || mesaRecebimentoModel.getCaixa() == null)) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	protected BigDecimal getCalcTotalDeEntradsEmDinheiroRecebidas() {
		BigDecimal total = BigDecimal.ZERO;

		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa && (mesaRecebimentoModel.isRecebido() == true || mesaRecebimentoModel.getCaixa() != null)) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	public BigDecimal getCalTotalDeEntradasEmDinheiroComOtroco() {
		BigDecimal totalPago = BigDecimal.ZERO;
		BigDecimal entrada = getCalcTotalDeEntradasEmDinheiro();
		BigDecimal saida = getCalcTotalDeTrocoParaOcliente();
		if (entrada.compareTo(saida) >= 0) {
			totalPago = entrada.subtract(saida);
		}

		totalPago = totalPago.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return totalPago;
	}

	public BigDecimal getCalcTotalDeEntradasComOtroco() {
		BigDecimal totalDoValorRecebido = getCalcTotalDeEntradas();
		totalDoValorRecebido = totalDoValorRecebido.subtract(getCalcTotalDeTrocoParaOcliente());
		totalDoValorRecebido = totalDoValorRecebido.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return totalDoValorRecebido;
	}

	/*
	 * Troco
	 */
	public BigDecimal getCalcTotalDeTrocoParaOcliente() {
		BigDecimal total = BigDecimal.ZERO;
		for (MesaRecebimentoModel mesaRecebimentoModel : getListaDeRecedimentos()) {

			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente) {
				total = total.add(mesaRecebimentoModel.getValor());
			}
		}

		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return total;
	}

	public boolean isAgrupandoItem() {
		ItemModel ultimoItem = getUltimoItemDaLista();
		if (ultimoItem != null) {
			if (ultimoItem.getQuantidade() < 1.0 && (ultimoItem.getCalcSomaDoAgrupamento() < 1.0)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public ItemModel getUltimoItemDaLista() {
		if (getListaDeItens().size() > 0) {
			ItemModel ultimoItem = getListaDeItens().get(getListaDeItens().size() - 1);
			return ultimoItem;

		} else {
			return null;
		}

	}

	public Double getQuantidadeDoUltimoItemDaLista() {
		if (getListaDeItens().size() > 0) {
			return getListaDeItens().get(getListaDeItens().size() - 1).getQuantidade();
		} else {
			return 0.0;
		}
	}

	public int getQuantidadeDePedidosNovos() {
		int pedidosNovos = 0;

		for (ItemModel itemModel : getListaDeItens()) {
			if (itemModel.getId() == null) {
				pedidosNovos = pedidosNovos + 1;
			}
		}

		return pedidosNovos;
	}

	public BigDecimal getLogTaxaDeEntrega() {
		return logTaxaDeEntrega;
	}

	public void setLogTaxaDeEntrega(BigDecimal logTaxaDeEntrega) {
		this.logTaxaDeEntrega = logTaxaDeEntrega;
	}

	public CaixaModel getCaixaAbertura() {
		return caixaAbertura;
	}

	public void setCaixaAbertura(CaixaModel caixaAbertura) {
		this.caixaAbertura = caixaAbertura;
	}

	public PedidoDocumentoType getDocumentoType() {
		return documentoType;
	}

	public void setDocumentoType(PedidoDocumentoType documentoType) {
		this.documentoType = documentoType;
	}

	public boolean isConfigEmpresaPermiteAlterarDocumento() {
		return configEmpresaPermiteAlterarDocumento;
	}

	public void setConfigEmpresaPermiteAlterarDocumento(boolean configEmpresaPermiteAlterarDocumento) {
		this.configEmpresaPermiteAlterarDocumento = configEmpresaPermiteAlterarDocumento;
	}

	public boolean isConfigEmpresaRealizarPedidoSemCobrar() {
		return configEmpresaRealizarPedidoSemCobrar;
	}

	public void setConfigEmpresaRealizarPedidoSemCobrar(boolean configEmpresaRealizarPedidoSemCobrar) {
		this.configEmpresaRealizarPedidoSemCobrar = configEmpresaRealizarPedidoSemCobrar;
	}

	public NFCeModel getNfceUltimaEmitida() {
		return nfceUltimaEmitida;
	}

	public void setNfceUltimaEmitida(NFCeModel nfceUltimaEmitida) {
		this.nfceUltimaEmitida = nfceUltimaEmitida;
	}

	public String getPedidoBalcaoNomeCliente() {
		return pedidoBalcaoNomeCliente;
	}

	public void setPedidoBalcaoNomeCliente(String pedidoBalcaoNomeCliente) {
		this.pedidoBalcaoNomeCliente = pedidoBalcaoNomeCliente;
	}

	public boolean isConfigEmpresaCobraTxdEntregaPorBairro() {
		return configEmpresaCobraTxdEntregaPorBairro;
	}

	public void setConfigEmpresaCobraTxdEntregaPorBairro(boolean configEmpresaCobraTxdEntregaPorBairro) {
		this.configEmpresaCobraTxdEntregaPorBairro = configEmpresaCobraTxdEntregaPorBairro;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

}
