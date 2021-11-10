package br.com.kernneo.server.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.utils.Properties;
import br.com.kernneo.server.Moeda;

public class ContasReceberReport extends AbstractReport {

    private UsuarioModel funcionario;
    private ContaModel filtroContaModel;

    private ArrayList<ContaModel> listaDeConta = new ArrayList<ContaModel>();

    public ContasReceberReport(UsuarioModel funcionario, ArrayList<ContaModel> listaDeItens, ContaModel filtroContaModel) {
	this.funcionario = funcionario;
	this.listaDeConta = listaDeItens;
	this.filtroContaModel = filtroContaModel;

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

	// TODO Adicionar numero do caixa no cupom
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(Properties.NOME_RESTAURANTE);
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("****  RELATORIO DE CONTAS A RECEBER ****");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");

	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	String nomeDoAtendente = "- TODOS CLIENTES -";
	if (filtroContaModel.getCliente() != null) {
	    nomeDoAtendente = filtroContaModel.getCliente().getNome();
	}
	conteudo.append("Clientes: " + nomeDoAtendente);
	conteudo.append("\n");
	conteudo.append("Periodo -> ");
	conteudo.append("\n");
	conteudo.append("   - inicio: " + dateFormat.format(filtroContaModel.getDataHora()));
	conteudo.append("\n");
	conteudo.append("   - fim:    " + dateFormat.format(filtroContaModel.getDataHoraQuitado()));
	conteudo.append("\n");

    }

    protected void printConteudo(StringBuffer conteudo) {
	conteudo.append(separadorDuplo);
	conteudo.append("\n");

	DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

	BigDecimal bigDecimalTotal = BigDecimal.ZERO;
	BigDecimal bigDecimalTotalRecebido = BigDecimal.ZERO;
	BigDecimal bigDecimalTotalAreceber = BigDecimal.ZERO;

	for (ContaModel contaModel : listaDeConta) {

	    bigDecimalTotal = bigDecimalTotal.add(contaModel.getValor());
	    if (contaModel.isQuitado()) {
		bigDecimalTotalRecebido = bigDecimalTotalRecebido.add(contaModel.getValor());
	    }

	    String nomeClienteCompleto = contaModel.getCliente().getNome();
	    
	    conteudo.append("(" + dateFormat.format(contaModel.getDataHora())+ ")");
	    conteudo.append("\n");

	    conteudo.append(nomeClienteCompleto);
	    conteudo.append("\n");

	    String valorConta = Moeda.mascaraDinheiro(contaModel.getValor(), Moeda.DINHEIRO_REAL);
	    String recebido = "NÂO";
	    if (contaModel.isQuitado()) {
		recebido = "SIM";
	    }

	   
	  
	    String infoProduto = "Valor: " + valorConta + " | Recebido: " + recebido;
	    conteudo.append(infoProduto);
	    conteudo.append("\n");
	    conteudo.append(separadorSimples);
	    conteudo.append("\n");

	}

	bigDecimalTotalAreceber = bigDecimalTotal.subtract(bigDecimalTotalRecebido);

	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("TOTAL:                      " + Moeda.mascaraDinheiro(bigDecimalTotal, Moeda.DINHEIRO_REAL));
	conteudo.append("\n");
	conteudo.append("(-)TOTAL RECEBIDO:          " + Moeda.mascaraDinheiro(bigDecimalTotalRecebido, Moeda.DINHEIRO_REAL));
	conteudo.append("\n");
	conteudo.append(" -> À RECEBER:              " + Moeda.mascaraDinheiro(bigDecimalTotalAreceber, Moeda.DINHEIRO_REAL));
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
