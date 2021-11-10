package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.kernneo.client.exception.ItemException;
import br.com.kernneo.client.types.CalculoFracaoType;
import br.com.kernneo.client.types.DescontoType;
import br.com.kernneo.client.types.MesaStatus;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.OpcaoTypes;
import br.com.kernneo.client.utils.StringUtils;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;


@Table
@Entity(name = "item")
public class ItemModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private ProdutoModel produto;

	@ManyToOne
	@JoinColumn(name = "id_item_pai")
	@Cascade(CascadeType.REMOVE)
	private ItemModel itemPai;

	@ManyToOne
	@JoinColumn(name = "id_mesa")
	private MesaModel mesa;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioModel usuario;

	private Double quantidade;
	private Date dataHora;

	@Column(name = "produtoValor")
	private BigDecimal logProdutoValor = BigDecimal.ZERO;

	@Column(name = "produtoNome")
	private String logProdutoNome;

	@Transient
	private ArrayList<OpcaoItemModel> listaDeOpcaoDoItem;

	private DescontoType descontoTipo = DescontoType.percentual;

	private BigDecimal descontoValor;

	// private BigDecimal logTaxaDeServicoPorcentagem;
	// private BigDecimal logTaxaDeServicoDinheiro;

	public ItemModel getItemPai() {
		return itemPai;
	}

	public DescontoType getDescontoTipo() {
		return descontoTipo;
	}

	public void setDescontoTipo(DescontoType descontoTipo) {
		this.descontoTipo = descontoTipo;
	}

	public BigDecimal getDescontoValor() {
		if (descontoValor == null) {
			descontoValor = BigDecimal.ZERO;
		}
		return descontoValor;
	}

	public void setDesconto(BigDecimal descontoValor) throws Exception {
		
		if (getDescontoTipo() == DescontoType.percentual) {
			if (descontoValor.compareTo(new BigDecimal(100)) > 0) {
				throw new Exception("O valor do desconto nao pode ser maior 100%");

			}
		}

		if (getDescontoTipo() == DescontoType.valor) {
			if (descontoValor.compareTo(getCalcValorTotalSemDesconto()) > 0) {
				throw new Exception("O valor do desconto nao pode ser maior que o valor do próprio item!");
			}
		}

		if (getMesa() != null) {

			if (getMesa().getStatus() == MesaStatus.mesa_recebida || getMesa().getStatus() == MesaStatus.balcao_recebido || getMesa().getStatus() == MesaStatus.entrega_recebido) {
				throw new Exception("Não é possivel dar desconto com o pedido recebido");

			}

			BigDecimal faltaReceber = getMesa().getCalcFaltaReceberDoCliente();
			BigDecimal troco = getMesa().getCalcFaltaDevolverParaOcliente();

			if (faltaReceber != null && troco != null) {
				if (troco.compareTo(BigDecimal.ZERO) == 0 && faltaReceber.compareTo(BigDecimal.ZERO) == 0) {

					throw new Exception("Não é possivel dar desconto com os pagamentos concluidos, exclua 1 ou mais pagamento e aplique o desconto!");

				}
			}
		}

		

		setDescontoValor(descontoValor);
	}

	public void setDescontoValor(BigDecimal descontoValor) {
		this.descontoValor = descontoValor;
	}

	public void setItemPai(ItemModel itemPai) {
		this.itemPai = itemPai;
	}

	public String getCalcNome() {
		String nome = "";
		if (getListaDeItensConjugados().size() > 0) {

			nome = getMesa().getQuantidadeConvertidaEmString(getQuantidade()) + " - " + getLogProdutoNome();
			for (ItemModel mesaItemConjugadoModel : getListaDeItensConjugados()) {
				nome = nome + " | " + getMesa().getQuantidadeConvertidaEmString(mesaItemConjugadoModel.getQuantidade()) + " - " + mesaItemConjugadoModel.getLogProdutoNome();
			}
			nome = nome + " (" + getProduto().getCategoria().getCategoria_nome_portugues() + ")";
		} else {
			nome = getLogProdutoNome() + " (" + getProduto().getCategoria().getCategoria_nome_portugues() + ")";
		}

		return nome;
	}

	public String getCalcDescricao() {
		String nome = "";
		if (getListaDeItensConjugados().size() > 0) {

			nome = getMesa().getQuantidadeConvertidaEmString(getQuantidade()) + " - " + getLogProdutoNome();
			for (ItemModel mesaItemConjugadoModel : getListaDeItensConjugados()) {
				nome = nome + " | " + getMesa().getQuantidadeConvertidaEmString(mesaItemConjugadoModel.getQuantidade()) + " - " + mesaItemConjugadoModel.getLogProdutoNome();
			}
		} else {
			nome = getProduto().getDescricao();
		}

		return nome;
	}

	public String getCalcNomeComOpcionais() {
		String nome = getCalcNome();

		if (getListaDeOpcaoDoItem() != null) {
			int i = 1;
			for (OpcaoItemModel opcaoItemModel : getListaDeOpcaoDoItem()) {
				if (opcaoItemModel.getTipo() == OpcaoTypes.observacao) {
					nome = nome + "   - OBS " + i + ": " + opcaoItemModel.getObservacao();
					nome = nome + "<br>";
					i++;

				} else if (opcaoItemModel.getTipo() == OpcaoTypes.multipla_escolha || opcaoItemModel.getTipo() == OpcaoTypes.unica_escolha) {

					boolean temSelecionada = false;
					String stringAlternativaAux = "";
					for (OpcaoItemAlternativaModel opcaoItemAlternativaModel : opcaoItemModel.getListaDeAlternativaDoItem()) {
						if (opcaoItemAlternativaModel.isSelecionada()) {
							temSelecionada = true;
							stringAlternativaAux = stringAlternativaAux + "     -> " + opcaoItemAlternativaModel.getAlternativa().getNome() + "<br>";
						}
					}

					if (temSelecionada == true) {
						nome = nome + "<br>";
						nome = nome + "   - Opcional: \"" + opcaoItemModel.getOpcao().getNome() + "\"";
						nome = nome + "<br>";
						nome = nome + stringAlternativaAux;
					}

				}

			}

		}

		return nome;
	}

	public Double getCalcQuantidade() {
		if (getListaDeItensConjugados().size() == 0) {
			return getQuantidade();
		} else {
			return 1.0;
		}
	}

	public Double getCalcSomaDoAgrupamento() {

		Double somaDoAgrupamento = getQuantidade();
		for (ItemModel itemModel : getListaDeItensConjugados()) {
			somaDoAgrupamento = somaDoAgrupamento + itemModel.getQuantidade();
		}
		return somaDoAgrupamento;

	}

	public BigDecimal getCalcValorUnitario() {
		BigDecimal total = BigDecimal.ZERO;

		/*
		 * calculo especial para pizzaria do aluisio
		 */
		total = getLogProdutoValor();
		if (getListaDeItensConjugados().size() > 0) {

			/*
			 * cobra fracionado
			 */

			if (getListaDeItensConjugados().size() == 1) {
				for (ItemModel itemModel : getListaDeItensConjugados()) {
					total = total.add(itemModel.getLogProdutoValor());

				}
				total = total.setScale(2, BigDecimal.ROUND_DOWN);
				int tamanhoPraDividir = getListaDeItensConjugados().size() + 1;
				total = total.divide(new BigDecimal(tamanhoPraDividir), 2, BigDecimal.ROUND_HALF_EVEN);

			} else {
				for (ItemModel itemConjugadoModel : getListaDeItensConjugados()) {
					if (itemConjugadoModel.getLogProdutoValor().compareTo(total) == 1) {
						total = itemConjugadoModel.getLogProdutoValor();
					}
				}
			}

			// if (getMesa().getTipoDeCalculoFracionado() != null) {
			//
			// if (getMesa().getTipoDeCalculoFracionado() ==
			// CalculoFracaoType.fracionado) {
			// for (ItemModel itemModel : getListaDeItensConjugados()) {
			// total = total.add(itemModel.getLogProdutoValor());
			//
			// }
			// total = total.setScale(2, BigDecimal.ROUND_DOWN);
			// int tamanhoPraDividir = getListaDeItensConjugados().size() + 1;
			// total = total.divide(new BigDecimal(tamanhoPraDividir), 2,
			// BigDecimal.ROUND_HALF_EVEN);
			//
			// } else if (getMesa().getTipoDeCalculoFracionado() ==
			// CalculoFracaoType.maior) {
			// for (ItemModel itemConjugadoModel : getListaDeItensConjugados())
			// {
			// if (itemConjugadoModel.getLogProdutoValor().compareTo(total) ==
			// 1) {
			// total = itemConjugadoModel.getLogProdutoValor();
			// }
			// }
			// }
			// }

		}

		for (OpcaoItemModel opcaoItemModel : getListaDeOpcaoDoItem()) {

			if (opcaoItemModel.getTipo() == OpcaoTypes.multipla_escolha || opcaoItemModel.getTipo() == OpcaoTypes.unica_escolha) {
				for (OpcaoItemAlternativaModel opcaoItemAlternativaModel : opcaoItemModel.getListaDeAlternativaDoItem()) {
					if (opcaoItemAlternativaModel.isSelecionada() && opcaoItemAlternativaModel.getValor() != null) {
						total = total.add(opcaoItemAlternativaModel.getValor());
					}

				}
			}

		}
		total = total.setScale(2, BigDecimal.ROUND_DOWN);

		return total;
	}

	public BigDecimal getCalcValorTotal() {
		BigDecimal total = getCalcValorTotalSemDesconto();
		total = total.subtract(getCalcDescontoValor());
		total = total.setScale(2, BigDecimal.ROUND_DOWN);

		return total;
	}

	public BigDecimal getCalcValorTotalSemDesconto() {
		BigDecimal total = BigDecimal.ZERO;
		if (getListaDeItensConjugados().size() == 0) {
			total = getCalcValorUnitario().multiply(new BigDecimal(getQuantidade()));
		} else {
			total = getCalcValorUnitario();
		}

		total = total.setScale(2, BigDecimal.ROUND_DOWN);

		return total;
	}

	// public BigDecimal getCalcValorDaTaxaDeServico() {
	// BigDecimal total = BigDecimal.ZERO;
	// if (getMesa().getTipo() == MesaTipo.mesa) {
	// if (mesa.getTaxaDeEntrega() != null) {
	// total = getCalcValorTotal().multiply(mesa.getTaxaDeServico());
	// }
	// }
	// total = total.setScale(2, BigDecimal.ROUND_DOWN);
	//
	// return total;
	// }

	// public BigDecimal getCalcValorTotalComTaxaDeServico() {
	// BigDecimal total = getCalcValorTotal();
	// total = total.add(getCalcValorDaTaxaDeServico());
	//
	// return total;
	// }

	public BigDecimal getCalcDescontoValor() {
		BigDecimal valor = BigDecimal.ZERO;

		if (getDescontoTipo() == DescontoType.valor) {

			valor = getDescontoValor();

		} else if (getDescontoTipo() == DescontoType.percentual) {

			valor = getCalcValorTotalSemDesconto().multiply(getDescontoValor()).divide(new BigDecimal(100));
		}

		valor = valor.setScale(2, BigDecimal.ROUND_DOWN);

		return valor;
	}

	public BigDecimal getCalcDescontoPorcentagem() {
		BigDecimal valor = BigDecimal.ZERO;

		if (getDescontoTipo() == DescontoType.valor) {

			valor = getDescontoValor().multiply(new BigDecimal(100)).divide(getCalcValorTotalSemDesconto());

		} else if (getDescontoTipo() == DescontoType.percentual) {
			valor = getDescontoValor();

		}

		valor = valor.setScale(2, BigDecimal.ROUND_DOWN);

		return valor;
	}

	public String getDescontoString() {

		String descontoEmValor = "";
		String descontoEmPorcentagem = "";

		descontoEmPorcentagem = String.valueOf(getCalcDescontoPorcentagem());
		descontoEmValor = StringUtils.getDoubleFormatadoEmDinheiro(getCalcDescontoValor());

		return descontoEmPorcentagem + "%" + " | " + descontoEmValor;
	}

	@Transient
	private ArrayList<ItemModel> listaDeItensConjugados = new ArrayList<ItemModel>();

	@Transient
	private ArrayList<String> listaDeOpcao = new ArrayList<String>();

	@Transient
	private boolean selecionado;

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("quantidade", quantidade);

		record.setAttribute("calcNome", getCalcNome());
		record.setAttribute("calcQuantidade", getCalcQuantidade());
		record.setAttribute("calcDescricao", getCalcDescricao());
		record.setAttribute("calcNomeCompletoComOpcionais", getCalcNomeComOpcionais());

		record.setAttribute("calcValorUnitario", getCalcValorUnitario());
		record.setAttribute("calcValorUnitarioString", StringUtils.getDoubleFormatadoEmDinheiro(getCalcValorUnitario()));

		record.setAttribute("calcDescontoValor", getCalcDescontoValor());
		record.setAttribute("calcDescontoPorcentagem", getCalcDescontoPorcentagem());
		record.setAttribute("calcDescontoString", getDescontoString());

		// record.setAttribute("calcValorDaTaxaDeServico",
		// getCalcValorDaTaxaDeServico());
		// record.setAttribute("calcValorDaTaxaDeServicoString",
		// StringUtils.getDoubleFormatadoEmDinheiro(getCalcValorDaTaxaDeServico()));

		record.setAttribute("calcValorTotal", getCalcValorTotal());
		record.setAttribute("calcValorTotalString", StringUtils.getDoubleFormatadoEmDinheiro(getCalcValorTotal()));
		record.setAttribute("calcValorTotalSemDescontoString", StringUtils.getDoubleFormatadoEmDinheiro(getCalcValorTotalSemDesconto()));
		// record.setAttribute("calcValorTotalComTaxaDeServico",
		// getCalcValorTotalComTaxaDeServico());
		// record.setAttribute("calcValorTotalComTaxaDeServicoString",
		// StringUtils.getDoubleFormatadoEmDinheiro(getCalcValorTotalComTaxaDeServico()));

		if (getDataHora() != null) {
			record.setAttribute("dataHora", DateTimeFormat.getFormat("dd/MM/yy - HH:mm").format(getDataHora()));
		}

		if (getProduto() != null) {
			record.setAttribute("produtoNome", produto.getNome());
			record.setAttribute("produtoDescricao", produto.getDescricao());
			record.setAttribute("produtoCodigo", produto.getCodigo());
			record.setAttribute("produtoId", produto.getId());
		}

		if (getMesa() != null) {
			record.setAttribute("mesaNumero", mesa.getNumero());
			record.setAttribute("mesaTaxaDeServico", mesa.getTaxaDeServico());
			if (mesa.getTaxaDeServico() != null) {
				record.setAttribute("mesaTaxaDeSericoPorcentagem", mesa.getTaxaDeServico().multiply(new BigDecimal(100)).intValue() + "%");
			}
			record.setAttribute("mesaId", mesa.getId());

		}

		if (getUsuario() != null) {
			record.setAttribute("usuarioNome", getUsuario().getNome());
			record.setAttribute("usuarioId", getUsuario().getId());
		}

		record.setAttribute("logProdutoNome", getLogProdutoNome());
		record.setAttribute("logProdutoValor", StringUtils.getDoubleFormatadoEmDinheiro(getLogProdutoValor()));

		return record;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
		setLogProdutoValor(produto.getPrecoDeVenda());
		setLogProdutoNome(produto.getNome());
	}

	public void addItemConjugado(ItemModel itemConjugadoModel) {
		getListaDeItensConjugados().add(itemConjugadoModel);
		itemConjugadoModel.setItemPai(this);

		ItemModel itemComPreferenciaModel = null;
		for (ItemModel itemModel : getListaDeItensConjugados()) {
			if (itemComPreferenciaModel == null) {
				if (itemModel.getProduto().isOpcaoUsaConfigCategoria() == false) {
					itemComPreferenciaModel = itemModel;
				}
			}
		}
		if (itemComPreferenciaModel != null) {
			listaDeOpcaoDoItem = itemComPreferenciaModel.getListaDeOpcaoDoItem();
		}

	}

	public ArrayList<ItemModel> getListaDeItensConjugados() {
		if (listaDeItensConjugados == null) {
			listaDeItensConjugados = new ArrayList<ItemModel>();
		}
		return listaDeItensConjugados;
	}

	public void setListaDeItensConjugados(ArrayList<ItemModel> listaDeItensConjugados) {
		this.listaDeItensConjugados = listaDeItensConjugados;
	}

	public ArrayList<String> getListaDeOpcao() {
		return listaDeOpcao;
	}

	public void setListaDeOpcao(ArrayList<String> listaDeOpcao) {
		this.listaDeOpcao = listaDeOpcao;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public MesaModel getMesa() {
		return mesa;
	}

	public void setMesa(MesaModel mesa) {
		this.mesa = mesa;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getLogProdutoValor() {
		return logProdutoValor;
	}

	public void setLogProdutoValor(BigDecimal logProdutoValor) {
		this.logProdutoValor = logProdutoValor;
	}

	public String getLogProdutoNome() {
		return logProdutoNome;
	}

	public void setLogProdutoNome(String logProdutoNome) {
		this.logProdutoNome = logProdutoNome;
	}

	public int getCalcTamanhoDaListaDeOpcao() {

		int tamanho = 0;
		for (OpcaoItemModel opcaoItemModel : getListaDeOpcaoDoItem()) {

			if (opcaoItemModel.getTipo() == OpcaoTypes.multipla_escolha || opcaoItemModel.getTipo() == OpcaoTypes.unica_escolha) {
				tamanho = tamanho + opcaoItemModel.getListaDeAlternativaDoItem().size();

			} else if (opcaoItemModel.getTipo() == OpcaoTypes.observacao) {
				tamanho = tamanho + 1;
			}
		}
		return tamanho;
	}

	public int getCalcTamanhoDaListaDeOpcaoObservacao() {

		int tamanho = 0;
		for (OpcaoItemModel opcaoItemModel : getListaDeOpcaoDoItem()) {

			if (opcaoItemModel.getTipo() == OpcaoTypes.observacao) {
				tamanho = tamanho + 1;
			}
		}
		return tamanho;
	}

	public ArrayList<OpcaoItemModel> getListaDeOpcaoDoItem() {
		if (listaDeOpcaoDoItem == null) {
			listaDeOpcaoDoItem = new ArrayList<OpcaoItemModel>();

			ArrayList<OpcaoModel> listaDeOpcao = new ArrayList<OpcaoModel>();
			if (getProduto().isOpcaoUsaConfigCategoria()) {
				for (OpcaoCategoriaModel opcaoCategoriaModel : getProduto().getCategoria().getListaDeOpcaoDaCategoria()) {
					listaDeOpcao.add(opcaoCategoriaModel.getOpcao());
				}
			} else {
				for (OpcaoProdutoModel opcaoProdutoModel : getProduto().getListaDeOpcaoDoProduto()) {
					listaDeOpcao.add(opcaoProdutoModel.getOpcao());
				}
			}

			for (OpcaoModel opcaoModel : listaDeOpcao) {
				OpcaoItemModel opcaoItemModel = new OpcaoItemModel();
				opcaoItemModel.setOpcao(opcaoModel);
				opcaoItemModel.setItem(this);

				listaDeOpcaoDoItem.add(opcaoItemModel);
			}

		}
		return listaDeOpcaoDoItem;
	}

	public void setListaDeOpcaoDoItem(ArrayList<OpcaoItemModel> listaDeOpcaoDoItem) {
		this.listaDeOpcaoDoItem = listaDeOpcaoDoItem;
	}

}
