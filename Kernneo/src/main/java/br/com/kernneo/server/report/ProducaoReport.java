package br.com.kernneo.server.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.OpcaoTypes;

public class ProducaoReport extends AbstractReport {

	private ArrayList<ItemModel> lista;

	private MesaModel mesa;
	private UsuarioModel funcionario;

	// private PrintStream printStream = null;

	public ProducaoReport(ArrayList<ItemModel> lista, UsuarioModel funcionario, MesaModel mesa) {
		this.lista = lista;
		System.out.println(lista.size() + "dd");
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

	private void printCabecalho(LocalDeImpressao localDeImpressao, StringBuffer conteudo) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
		Date dataAtual = new Date();

		// TODO Adicionar numero do caixa no cupom
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
		if (mesa.getTipo() == MesaTipo.mesa) {
			conteudo.append("Mesa: " + mesa.getNumero() + " - " + dateFormat.format(dataAtual));
		} else if (mesa.getTipo() == MesaTipo.entrega) {

			conteudo.append("Pedido: " + mesa.getId() + " - " + dateFormat.format(dataAtual));
			conteudo.append("\n");
			if (mesa.getEnderecoCliente() == null) {
				conteudo.append("Bairro: *** RETIRAR NO LOCAL ***");
			} else {
				conteudo.append("Bairro: " + mesa.getEnderecoCliente().getBairro().getNome());
			}
			conteudo.append("\n");
			if (mesa.getCliente() != null && mesa.getCliente().getNome() != null) {
				conteudo.append("Cliente: " + mesa.getCliente().getNome());

			} else {
				conteudo.append("Cliente: ***  SEM NOME ***");
			}

		} else if (mesa.getTipo() == MesaTipo.balcao) {
			conteudo.append("Id: " + mesa.getId());
			conteudo.append("\n");
			conteudo.append("Pedido: " + mesa.getNumero() + " - " + dateFormat.format(dataAtual));

		}
		conteudo.append("\n");
		conteudo.append("Atendente: " + funcionario.getNome());
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
	}

	private void printConteudo(StringBuffer conteudo) {

		conteudo.append("QTDE       PRODUTO");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append(separadorSimples);
		conteudo.append("\n");

		for (ItemModel pedido : lista) {
			String quantidadePedido = String.format("%.2f", pedido.getQuantidade());
			if (quantidadePedido.contains(",00")) {
				quantidadePedido = quantidadePedido.replaceAll(",00", "");

			}
			if (pedido.getListaDeItensConjugados().size() > 0) {
				quantidadePedido = mesa.getQuantidadeConvertidaEmString(pedido.getQuantidade());
			}
			conteudo.append("  " + quantidadePedido + "  " + pedido.getProduto().getNome());
			conteudo.append("\n");
			if (pedido.getListaDeItensConjugados() != null && pedido.getListaDeItensConjugados().size() > 0) {
				for (ItemModel pedidoAgrupado : pedido.getListaDeItensConjugados()) {
					// String quantidade = String.format("%.2f",
					// pedidoAgrupado.getQuantidade());
					// if (quantidade.contains(",00")) {
					// 14 quantidade = quantidade.replaceAll(",00", "");
					//
					// }
					String quantidade = mesa.getQuantidadeConvertidaEmString(pedido.getQuantidade());
					conteudo.append("   -> " + quantidade + "       " + pedidoAgrupado.getProduto().getNome());
					conteudo.append("\n");
				}

			}
			if (pedido.getListaDeOpcaoDoItem() != null) {
				int i = 1;

				for (OpcaoItemModel opcaoItemModel : pedido.getListaDeOpcaoDoItem()) {
					if (opcaoItemModel.getTipo() == OpcaoTypes.observacao) {
						conteudo.append("   - OBS " + i + ": " + opcaoItemModel.getObservacao());
						conteudo.append("\n");
						i++;

					} else if (opcaoItemModel.getTipo() == OpcaoTypes.multipla_escolha || opcaoItemModel.getTipo() == OpcaoTypes.unica_escolha) {

						boolean temSelecionada = false;
						String stringAlternativaAux = "";
						for (OpcaoItemAlternativaModel opcaoItemAlternativaModel : opcaoItemModel.getListaDeAlternativaDoItem()) {
							if (opcaoItemAlternativaModel.isSelecionada()) {
								temSelecionada = true;
								stringAlternativaAux = stringAlternativaAux + "     -> " + opcaoItemAlternativaModel.getAlternativa().getNome() + "\n";
							}
						}

						if (temSelecionada == true) {
							conteudo.append("   - Opcional: \"" + opcaoItemModel.getOpcao().getNome() + "\"");
							conteudo.append("\n");
							conteudo.append(stringAlternativaAux);
						}

					}

				}

			}
			conteudo.append("\n");
			conteudo.append(separadorSimples);
			conteudo.append("\n");

		}

		conteudo.append(separadorSimples);
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
	}
}
