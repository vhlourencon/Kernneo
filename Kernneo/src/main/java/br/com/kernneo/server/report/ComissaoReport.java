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
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.utils.Properties;

public class ComissaoReport extends AbstractReport {

    private UsuarioModel funcionario;
    private Date dataInicial;
    private Date dataFinal;
    private boolean completo;

    private ArrayList<ItemModel> listaDeItens = new ArrayList<ItemModel>();

    public ComissaoReport(UsuarioModel funcionario, ArrayList<ItemModel> listaDeItens, Date dataInicial, Date dataFinal, boolean completo) {
	this.funcionario = funcionario;
	this.listaDeItens = listaDeItens;
	this.dataInicial = dataInicial;
	this.dataFinal = dataFinal;
	this.completo = completo;

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
	conteudo.append("*****  RELATORIO DE VENDAS *****");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");

	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	String nomeDoAtendente = "- TODOS USUÁRIOS -";
	if (funcionario != null) {
	    nomeDoAtendente = funcionario.getNome();
	}
	conteudo.append("Atendente: " + nomeDoAtendente);
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
	if (completo) {

	    conteudo.append("PEDIDO          QTDE   VALOR  COMISSAO");
	    conteudo.append("\n");
	    conteudo.append(separadorDuplo);
	    conteudo.append("\n");
	}

	DecimalFormat df = new DecimalFormat("#,###.00");
	DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

	String sobra = null;

	BigDecimal bigDecimalTotalEmComissao = BigDecimal.ZERO;
	BigDecimal bigDecimalTotalEmItenVendidos = BigDecimal.ZERO;

	Double quantidadeDeItens = 0.0;
	Map<Long, MesaModel> listaDeMesas = new HashMap<Long, MesaModel>();

	for (ItemModel pedido : listaDeItens) {

	    listaDeMesas.put(pedido.getMesa().getId(), pedido.getMesa());

	    String nomeProduto = pedido.getLogProdutoNome();
	    if (nomeProduto.length() >= 15) {
		nomeProduto = pedido.getProduto().getNome().substring(0, 14);
		sobra = pedido.getLogProdutoNome().substring(14, pedido.getLogProdutoNome().length());
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
		pedidoQuantidade = pedidoQuantidade + getEspaco(3);
	    }

	    String valorDoPedidoEmString = df.format(pedido.getCalcValorTotal());
	    valorDoPedidoEmString = valorDoPedidoEmString + getEspaco(4 + (4 - valorDoPedidoEmString.length()));
	    if (valorDoPedidoEmString.startsWith(",")) {
		valorDoPedidoEmString = "0" + valorDoPedidoEmString;
	    }

//	    String valorComissaoEmDinheiro = df.format(pedido.getCalcValorDaTaxaDeServico());
//	    if (valorComissaoEmDinheiro.startsWith(",")) {
//		valorComissaoEmDinheiro = "0" + valorComissaoEmDinheiro;
//	    }
//
//	    bigDecimalTotalEmComissao = bigDecimalTotalEmComissao.add(pedido.getCalcValorDaTaxaDeServico());
//	    bigDecimalTotalEmItenVendidos = bigDecimalTotalEmItenVendidos.add(pedido.getCalcValorTotal());
//	    quantidadeDeItens = quantidadeDeItens + pedido.getQuantidade();

	    if (completo) {

		//conteudo.append(nomeProduto + "    " + pedidoQuantidade + valorDoPedidoEmString + valorComissaoEmDinheiro);
		conteudo.append("\n");

		String infoProduto = dateFormat.format(pedido.getDataHora()) + " | Mesa: " + pedido.getMesa().getNumero() + " | Taxa: " + pedido.getMesa().getTaxaDeServico().multiply(new BigDecimal(100)).intValue() + "%";
		if (sobra != null) {
		    conteudo.append(sobra);
		    conteudo.append("\n");
		    conteudo.append(infoProduto);
		    conteudo.append("\n");
		    conteudo.append(separadorSimples);
		    conteudo.append("\n");
		} else {
		    conteudo.append("-");
		    conteudo.append("\n");

		    conteudo.append(infoProduto);
		    conteudo.append("\n");
		    conteudo.append(separadorSimples);
		    conteudo.append("\n");
		}
	    }
	}

	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("TOTAL EM ITENS VENDIDOS:      R$" + df.format(bigDecimalTotalEmItenVendidos));
	conteudo.append("\n");
	conteudo.append("TOTAL EM COMISSAO:            R$" + df.format(bigDecimalTotalEmComissao));
	conteudo.append("\n");
	conteudo.append(" -> QTDE DE ITENS VENDIDOS:       " + quantidadeDeItens.intValue());
	conteudo.append("\n");
	conteudo.append(" -> QTDE DE MESAS ATENDIDAS:      " + listaDeMesas.size());
	conteudo.append("\n");
	conteudo.append(separadorSimples);
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
