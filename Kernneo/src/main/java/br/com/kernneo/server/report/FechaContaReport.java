package br.com.kernneo.server.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.model.ItemImpressaoModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.utils.Properties;
import br.com.kernneo.server.Moeda;
import br.com.kernneo.server.negocio.MesaRecebimento;

public class FechaContaReport extends AbstractReport {

	private MesaModel mesa;
	private UsuarioModel funcionario;

	public FechaContaReport(UsuarioModel funcionario, MesaModel mesa, String localImpressao) {
		this.mesa = mesa;
		this.funcionario = funcionario;

	}

	@Override
	public StringBuffer geraConteudo(LocalDeImpressao localDeImpressao) {
		StringBuffer conteudo = new StringBuffer();

		printCabecalho(localDeImpressao, conteudo);
		printConteudo(conteudo);
		printRodape(conteudo);
		cortaPapel(conteudo);
		return conteudo;
	}

	protected void printCabecalho(LocalDeImpressao localDeImpressao, StringBuffer conteudo) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
		Date dataAtual = new Date();

		// TODO Adicionar numero do caixa no cupom
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append(Properties.NOME_RESTAURANTE);
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("CONFERENCIA DE PRODUTOS");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("*****CUPOM PARA SIMPLES CONFERENCIA*****");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");

		/*
		 * ps.print((char) 29); ps.print((char) 249); ps.print((char) 32);
		 * ps.print((char) 0);
		 * 
		 * ps.print((char) 27); ps.print((char) 116); ps.print((char) 8);
		 */
		// TODO: Bug: Negrito, expandido e etc não estão saindo corretamente na
		// impressora
		/*
		 * expandido(ps, this.localProducao + " - " +
		 * horaFormat.format(dataAtual));
		 */
		conteudo.append(localDeImpressao.toString() + " - " + horaFormat.format(dataAtual));
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("ID: " + mesa.getId());
		conteudo.append("\n");

		if (mesa.getTipo() == MesaTipo.mesa) {
			conteudo.append("Mesa: " + mesa.getNumero() + " - " + dateFormat.format(dataAtual));
			conteudo.append("\n");
			conteudo.append("Atendente: " + funcionario.getNome());
			conteudo.append("\n");

		} else if (mesa.getTipo() == MesaTipo.entrega) {
			conteudo.append("Pedido: " + mesa.getId() + " - " + dateFormat.format(dataAtual));
			conteudo.append("\n");
			conteudo.append("Atendente: " + funcionario.getNome());
			conteudo.append("\n");
			String nomeCliente = mesa.getCliente().getNome();
			if (nomeCliente.length() > 30) {
				nomeCliente = nomeCliente.substring(0, 29);
			}
			String telefoneCliente = mesa.getCliente().getTelefone();
			if (telefoneCliente == null) {
				telefoneCliente = "";

			}

			String celularCliente = mesa.getCliente().getCelular();

			if (celularCliente == null) {
				celularCliente = "";

			}
			conteudo.append("Cliente: " + nomeCliente);
			conteudo.append("\n");
			conteudo.append("Telefone: " + telefoneCliente);
			conteudo.append("\n");
			conteudo.append("Celular: " + celularCliente);
			conteudo.append("\n");
			if (mesa.getEnderecoCliente() == null) {

				conteudo.append("Endereço: *** RETIRAR NO LOCAL *** ");
				conteudo.append("\n");
			} else {
				String auxEndereco = mesa.getEnderecoCliente().toString();

				String auxEndereco1 = null;
				String auxEndereco2 = null;
				String auxEndereco3 = null;
				String auxEndereco4 = null;
				String auxEndereco5 = null;

				if (auxEndereco.length() > 150) {
					auxEndereco1 = auxEndereco.substring(0, 30);
					auxEndereco2 = auxEndereco.substring(30, 70);
					auxEndereco3 = auxEndereco.substring(70, 110);
					auxEndereco4 = auxEndereco.substring(110, 150);
					auxEndereco5 = auxEndereco.substring(150, auxEndereco.length());

				} else if (auxEndereco.length() > 110) {
					auxEndereco1 = auxEndereco.substring(0, 30);
					auxEndereco2 = auxEndereco.substring(30, 70);
					auxEndereco3 = auxEndereco.substring(70, 110);
					auxEndereco4 = auxEndereco.substring(110, auxEndereco.length());

				} else if (auxEndereco.length() > 70) {
					auxEndereco1 = auxEndereco.substring(0, 30);
					auxEndereco2 = auxEndereco.substring(30, 70);
					auxEndereco3 = auxEndereco.substring(70, auxEndereco.length());

				} else if (auxEndereco.length() > 30) {
					auxEndereco1 = auxEndereco.substring(0, 30);
					auxEndereco2 = auxEndereco.substring(30, auxEndereco.length());
				} else if (auxEndereco.length() < 30) {
					auxEndereco1 = auxEndereco.substring(0, auxEndereco.length());

				}

				conteudo.append("Endereço: " + auxEndereco1 + "\n");
				if (auxEndereco2 != null) {
					conteudo.append("" + auxEndereco2 + "\n");
				}
				if (auxEndereco3 != null) {
					conteudo.append("" + auxEndereco3 + "\n");
				}
				if (auxEndereco4 != null) {
					conteudo.append("" + auxEndereco4 + "\n");
				}

				if (auxEndereco5 != null) {
					conteudo.append("" + auxEndereco5 + "\n");

				}
			}

		} else if (mesa.getTipo() == MesaTipo.balcao) {
			conteudo.append("Id: " + mesa.getId());
			conteudo.append("\n");
			conteudo.append("Pedido: " + mesa.getNumero() + " - " + dateFormat.format(dataAtual));
			conteudo.append("\n");

		}

	}

	protected void printConteudo(StringBuffer conteudo) {
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("PRODUTO          QTDE   VALOR.UN VALOR");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");

		String sobra = null;
		
		
		for (ItemImpressaoModel pedido : mesa.getListaDeItensAgrupados()) {

			String nomeProduto = pedido.getNomeDoProduto();
			String nomeAux = pedido.getNomeDoProduto();
			if (nomeProduto.length() >= 15) {
				nomeProduto = nomeAux.substring(0, 14);

				sobra = nomeAux.substring(14, nomeAux.length());
				if (sobra.length() > 40) {
					sobra = sobra.substring(0, 39);
				}
			} else {
				if (nomeProduto.length() != 14) {
					nomeProduto = nomeProduto + getEspaco(15 - nomeProduto.length() - 1);
				}
				sobra = null;
			}

			String pedidoQuantidade = String.format("%.2f", pedido.getQuantidade());
			pedidoQuantidade = pedidoQuantidade + getEspaco(6 - pedidoQuantidade.length());
			if (pedidoQuantidade.contains(",00")) {
				pedidoQuantidade = pedidoQuantidade.replace(",00", "");
				pedidoQuantidade = pedidoQuantidade + getEspaco(4);
			}

			String valorUniario = Moeda.mascaraDinheiro(pedido.getValorUnitario(), Moeda.DINHEIRO_REAL_SEM_SIMBOLO);
			valorUniario = valorUniario + getEspaco(4 + (4 - valorUniario.length()));

			String valorTotal = Moeda.mascaraDinheiro(pedido.getValorTotalSemDesconto(), Moeda.DINHEIRO_REAL_SEM_SIMBOLO);

			conteudo.append(nomeProduto + "    " + pedidoQuantidade + valorUniario + valorTotal);
			conteudo.append("\n");
			if (sobra != null) {
				conteudo.append(sobra);
				conteudo.append("\n");
				conteudo.append(separadorSimples);
				conteudo.append("\n");
			} else {
				conteudo.append(separadorSimples);
				conteudo.append("\n");
			}
		}

		String subTotal = Moeda.mascaraDinheiro(mesa.getCalcSubTotal(), Moeda.DINHEIRO_REAL);
		if (subTotal.startsWith(",")) {
			subTotal = "0" + subTotal;
		}
		
		
		String taxa = Moeda.mascaraDinheiro(mesa.getCalcValorTotalDeTaxas(), Moeda.DINHEIRO_REAL);
		if (taxa.startsWith(",")) {
			taxa = "0" + taxa;
		}

		String desconto = Moeda.mascaraDinheiro(mesa.getCalcTotalDeDesconto(), Moeda.DINHEIRO_REAL);
		if (desconto.startsWith(",")) {
			desconto = "0" + desconto;
		}

		String parcial = Moeda.mascaraDinheiro(mesa.getCalcTotalDeEntradas(), Moeda.DINHEIRO_REAL);
		if (parcial.startsWith(",")) {
			parcial = "0" + parcial;
		}

		String total = Moeda.mascaraDinheiro(mesa.getCalcValorTotalDaMesa(), Moeda.DINHEIRO_REAL);
		if (total.startsWith(",")) {
			total = "0" + total;
		}

		String faltaReceber = Moeda.mascaraDinheiro(mesa.getCalcFaltaReceberDoCliente(), Moeda.DINHEIRO_REAL);
		if (faltaReceber.startsWith(",")) {
			faltaReceber = "0" + faltaReceber;
		}

		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("SUBTOTAL  (+)                  " + subTotal);
		conteudo.append("\n");
		conteudo.append("TX. EXTRA (+)                  " + taxa);
		conteudo.append("\n");
		conteudo.append("DESCONTO  (-)                  " + desconto);
		conteudo.append("\n");

		if (mesa.getTipo() == MesaTipo.entrega) {
			String taxaDeEntrega = Moeda.mascaraDinheiro(mesa.getCalcTaxaDeEntregaEmDinheiro(), Moeda.DINHEIRO_REAL);

			if (taxaDeEntrega.startsWith(",")) {
				taxaDeEntrega = "0" + taxaDeEntrega;
			}
			conteudo.append("TX. DE ENTREGA  (+)      " + taxaDeEntrega);
			conteudo.append("\n");
		} else {
			String servico = Moeda.mascaraDinheiro(mesa.getCalcTaxaDeServicoEmDinheiro(), Moeda.DINHEIRO_REAL);
			if (servico.startsWith(",")) {
				servico = "0" + servico;
			}
			conteudo.append("SERVICO   (+)                  " + servico);
			conteudo.append("\n");
		}

		conteudo.append(separadorSimples);
		conteudo.append("\n");
		conteudo.append("TOTAL                          " + total);
		conteudo.append("\n");
		conteudo.append("(-) RECEBIDO                   " + parcial);
		conteudo.append("\n");
		conteudo.append(separadorSimples);
		conteudo.append("\n");

		for (MesaRecebimentoModel mesaRecebimento : mesa.getListaDeRecedimentos()) {
			if (mesaRecebimento.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa) {

				String valorRecebido = Moeda.mascaraDinheiro(mesaRecebimento.getValor(), Moeda.DINHEIRO_REAL);
				if (valorRecebido.startsWith(",")) {
					valorRecebido = "0" + faltaReceber;
				}
				String formaDePAgamento = mesaRecebimento.getFormaDePagamento().toString() + getEspaco(20 - mesaRecebimento.getFormaDePagamento().toString().length());
				conteudo.append("-> " + formaDePAgamento + " = " + valorRecebido);
				conteudo.append("\n");
			}
		}
		conteudo.append(separadorSimples);
		conteudo.append("\n");
		conteudo.append("- FALTA RECEBER                " + faltaReceber);
		conteudo.append("\n");

		BigDecimal bigDecimalTroco = BigDecimal.ZERO;
		for (MesaRecebimentoModel mesaRecebimentoModel : mesa.getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente) {
				bigDecimalTroco = bigDecimalTroco.add(mesaRecebimentoModel.getValor());
			}
		}

		String troco = Moeda.mascaraDinheiro(bigDecimalTroco, Moeda.DINHEIRO_REAL);
		if (troco.startsWith(",")) {
			troco = "0" + faltaReceber;
		}
		conteudo.append("- TROCO                        " + troco);
		conteudo.append("\n");

		ArrayList<MesaRecebimentoModel> listaDePAgamentosEmComvenio = new ArrayList<MesaRecebimentoModel>();
		for (MesaRecebimentoModel mesaRecebimentoModel : mesa.getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.convenio) {
				listaDePAgamentosEmComvenio.add(mesaRecebimentoModel);
			}
		}

		if (listaDePAgamentosEmComvenio.size() > 0) {
			conteudo.append("\n");
			conteudo.append(separadorDuplo);
			conteudo.append("\n");
			conteudo.append("PAGAMENTOS EM CONVÊNIO");
			conteudo.append("\n");
			conteudo.append(separadorDuplo);
			conteudo.append("\n");

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			for (MesaRecebimentoModel mesaRecebimentoModel : listaDePAgamentosEmComvenio) {

				String valorConvenio = Moeda.mascaraDinheiro(mesaRecebimentoModel.getValor(), Moeda.DINHEIRO_REAL);
				if (valorConvenio.startsWith(",")) {
					valorConvenio = "0" + parcial;
				}
				conteudo.append("\n");
				conteudo.append(separadorSimples);
				conteudo.append("\n");
				conteudo.append(" -> Cliente: " + mesaRecebimentoModel.getCliente().getNome());
				conteudo.append("\n");
				conteudo.append(" -> Valor: " + valorConvenio);
				conteudo.append("\n");
				conteudo.append(" -> Data: " + dateFormat.format(new Date()));
				conteudo.append("\n");
				conteudo.append(separadorSimples);
				conteudo.append("\n");
				conteudo.append("\n");
				conteudo.append("\n");
				conteudo.append("\n");
				conteudo.append("________________________________________");
				conteudo.append("\n");
				conteudo.append("-------- ASSINATURA DO CLIENTE ---------");
				conteudo.append("\n");
				conteudo.append(separadorDuplo);
				conteudo.append("\n");
				conteudo.append("****************************************");
				conteudo.append("\n");
			}
		}
		conteudo.append(separadorSimples);
		conteudo.append("\n");
		conteudo.append("Desenvolvido por  |-Kernneo Tecnologia-|");
		conteudo.append("\n");
		conteudo.append("****** - > www.kernneo.com.br <- *******");
		conteudo.append("\n");
	
		
	}

	public String getEspaco(int tamanho) {
		String espaco = " ";
		for (int i = 1; i < tamanho; i++) {
			espaco = espaco + " ";
		}
		return espaco;
	}

}
