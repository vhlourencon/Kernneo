package br.com.kernneo.server.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.model.relatorio.RelatorioVendaPeriodoModel;
import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.utils.Properties;
import br.com.kernneo.server.Moeda;

public class VendasReport extends AbstractReport {

	private UsuarioModel funcionario;
	private Date dataInicial;
	private Date dataFinal;

	private ArrayList<RelatorioVendaPeriodoModel> listaDeItens = new ArrayList<RelatorioVendaPeriodoModel>();

	public VendasReport(UsuarioModel funcionario, ArrayList<RelatorioVendaPeriodoModel> listaDeItens, Date dataInicial, Date dataFinal) {
		this.funcionario = funcionario;
		this.listaDeItens = listaDeItens;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;

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
		DateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");

		// TODO Adicionar numero do caixa no cupom
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append(Properties.NOME_RESTAURANTE);
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("   *****  RELATORIO DE VENDAS *****");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		
		conteudo.append("Periodo -> ");
		conteudo.append("\n");
		conteudo.append("   - inicio: " + dateFormat.format(dataInicial));
		conteudo.append("\n");
		conteudo.append("   - fim:    " + dateFormat.format(dataFinal));
		conteudo.append("\n");

	}

	protected void printConteudo(StringBuffer conteudo) {
		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("COD  PRODUTO                       QTDE");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
		conteudo.append("\n");

		String sobra = null;
		
		
		Double quantidadeDeItensVendidos = 0.0; 

		for (RelatorioVendaPeriodoModel relatorioVendaPeriodoModel : listaDeItens) {
			quantidadeDeItensVendidos = quantidadeDeItensVendidos + relatorioVendaPeriodoModel.getQuantidade(); 

			String codProduto = " " + relatorioVendaPeriodoModel.getProduto().getCodigo().toString();

			String nomeProduto = "";
			String nomeAux = relatorioVendaPeriodoModel.getProduto().getNome();
			if (nomeAux.length() >= 29) {
				nomeProduto = nomeAux.substring(0, 28);

				sobra = nomeAux.substring(28, nomeAux.length());
				if (sobra.length() > 60) {
					sobra = sobra.substring(0, 59);
				}
			} else {
				if (nomeAux.length() != 28) {
					nomeProduto = nomeAux + getEspaco(28 - nomeAux.length());
				}
				sobra = null;
			}

			nomeProduto = getEspaco(5 - codProduto.length()) + nomeProduto;

			String pedidoQuantidade = String.format("%.2f", relatorioVendaPeriodoModel.getQuantidade());
			pedidoQuantidade = pedidoQuantidade + getEspaco(6 - pedidoQuantidade.length());
			if (pedidoQuantidade.contains(",00")) {
				pedidoQuantidade = pedidoQuantidade.replace(",00", "");
				pedidoQuantidade = pedidoQuantidade + getEspaco(4);
			}

			conteudo.append(codProduto + nomeProduto + "   " + pedidoQuantidade);
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

		String total = "";
		if (total.startsWith(",")) {
			total = "0" + total;
		}
		
		
		String qtdeItensVendidosStr = String.format("%.2f", quantidadeDeItensVendidos);
		if (qtdeItensVendidosStr.contains(",00")) {
			qtdeItensVendidosStr = qtdeItensVendidosStr.replace(",00", "");
		}

		conteudo.append(separadorDuplo);
		conteudo.append("\n");
		conteudo.append("TOTA: " + qtdeItensVendidosStr + " Itens Vendidos");
		conteudo.append("\n");
		conteudo.append(separadorDuplo);
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
