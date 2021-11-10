package br.com.kernneo.server.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.utils.Properties;
import br.com.kernneo.server.Moeda;

public class FechaCaixaReport extends AbstractReport {

    private CaixaModel caixa;

    public FechaCaixaReport(CaixaModel caixa) {
	this.caixa = caixa;

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

	// TODO Adicionar numero do caixa no cupom
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(Properties.NOME_RESTAURANTE);
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("********* FECHAMENTO DE CAIXA *********");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("Caixa Nº: " + caixa.getId());
	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("Usuario Abertura: " + caixa.getUsuarioAbertura().getNome());
	conteudo.append("\n");
	conteudo.append("->  " + dateFormat.format(caixa.getDataHoraAbertura()) + " - " + horaFormat.format(caixa.getDataHoraAbertura()));
	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("Usuario Fechmento : " + caixa.getUsuarioFechamento().getNome());
	conteudo.append("\n");
	conteudo.append(" -> " + dateFormat.format(caixa.getDataHoraFechamento()) + " - " + horaFormat.format(caixa.getDataHoraFechamento()));
	conteudo.append("\n");

    }

    protected void printConteudo(StringBuffer conteudo) {

	conteudo.append(separadorDuplo);
	conteudo.append("\n");

	String entrada = Moeda.mascaraDinheiro(caixa.getSaldoEntrada(), Moeda.DINHEIRO_REAL);
	if (entrada.startsWith(",")) {
	    entrada = "0" + entrada;
	}

	String saida = Moeda.mascaraDinheiro(caixa.getSaldoSaida(), Moeda.DINHEIRO_REAL);
	if (saida.startsWith(",")) {
	    saida = "0" + saida;
	}

	BigDecimal totalEntradaMenosSaida = caixa.getSaldoEntrada().subtract(caixa.getSaldoSaida());
	String entradaMenosSaida = "";
	if (totalEntradaMenosSaida.compareTo(BigDecimal.ZERO) < 0) {
	    totalEntradaMenosSaida.multiply(new BigDecimal(-1));
	    entradaMenosSaida = "-";
	}

	String entradaMenosSaidAux = Moeda.mascaraDinheiro(totalEntradaMenosSaida, Moeda.DINHEIRO_REAL);
	if (entradaMenosSaidAux.startsWith(",")) {
	    entradaMenosSaidAux = "0" + entradaMenosSaidAux;
	}
	entradaMenosSaida = entradaMenosSaida + entradaMenosSaidAux;

	String saldoMesas = Moeda.mascaraDinheiro(caixa.getSaldoMesas(), Moeda.DINHEIRO_REAL);
	if (saldoMesas.startsWith(",")) {
	    saldoMesas = "0" + saldoMesas;
	}

	String descontosMesas = Moeda.mascaraDinheiro(caixa.getSaldoDescontos(), Moeda.DINHEIRO_REAL);
	if (descontosMesas.startsWith(",")) {
	    descontosMesas = "0" + descontosMesas;
	}

	String saldoMesaComDescontos = Moeda.mascaraDinheiro(caixa.getSaldoMesas().subtract(caixa.getSaldoDescontos()), Moeda.DINHEIRO_REAL);
	if (saldoMesaComDescontos.startsWith(",")) {
	    saldoMesaComDescontos = "0" + saldoMesaComDescontos;
	}

	String saldoDinheiro = Moeda.mascaraDinheiro(caixa.getSaldoDinheiro(), Moeda.DINHEIRO_REAL);
	if (saldoDinheiro.startsWith(",")) {
	    saldoDinheiro = "0" + saldoDinheiro;
	}

	String saldoDebito = Moeda.mascaraDinheiro(caixa.getSaldoCartaoDebito(), Moeda.DINHEIRO_REAL);
	if (saldoDebito.startsWith(",")) {
	    saldoDebito = "0" + saldoDebito;
	}

	String saldoCredito = Moeda.mascaraDinheiro(caixa.getSaldoCartaoCredito(), Moeda.DINHEIRO_REAL);
	if (saldoCredito.startsWith(",")) {
	    saldoCredito = "0" + saldoCredito;
	}
	
	
	String saldoConvenio = Moeda.mascaraDinheiro(caixa.getSaldoConvenio(), Moeda.DINHEIRO_REAL);
	if (saldoConvenio.startsWith(",")) {
	    saldoConvenio = "0" + saldoConvenio;
	}

	String saldoAlelo = Moeda.mascaraDinheiro(caixa.getSaldoAlelo(), Moeda.DINHEIRO_REAL);
	if (saldoAlelo.startsWith(",")) {
		saldoAlelo = "0" + saldoAlelo;
	}

	String saldoTotal = Moeda.mascaraDinheiro(caixa.getSaldo(), Moeda.DINHEIRO_REAL);
	if (saldoTotal.startsWith(",")) {
	    saldoTotal = "0" + saldoTotal;
	}

	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("           ENTRADAS E SAIDAS        ");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("(+) ENTRADAS:                 " + entrada);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("(-) SAIDAS:                   " + saida);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(" -> TOTAL:                    " + entradaMenosSaida);
	conteudo.append("\n");
	conteudo.append(separadorSimples);

	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("	        MESAS               ");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("(+) SALDO:                    " + saldoMesas);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("(-) DESCONTOS:                " + descontosMesas);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(" -> TOTAL:                    " + saldoMesaComDescontos);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(" - Quantidade de Pessoas: " + caixa.getQuantidadeDePessoas());
	conteudo.append("\n");
	conteudo.append(" - Quantidade de Mesas: " + caixa.getQuantidadeDeMesas());
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("\n");

	conteudo.append("	     SALDO FINAL              ");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("- SALDO DINHEIRO:             " + saldoDinheiro);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("- SALDO CARTAO DEBITO:        " + saldoDebito);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("- SALDO CARTAO CREDITO:       " + saldoCredito);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("- SALDO ALELO:                " + saldoAlelo);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("- SALDO CONVÊNIO:             " + saldoConvenio);
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("SALDO TOTAL:                  " + saldoTotal);
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
