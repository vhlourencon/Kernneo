package br.com.kernneo.server.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.utils.Properties;

public class CaixaResumidoReport extends AbstractReport {

    private ArrayList<CaixaModel> listaDeCaixas;
    private Date dataInicial;
    private Date dataFinal;

    public CaixaResumidoReport(ArrayList<CaixaModel> listaDeCaixas, Date dataInicial, Date dataFinal) {
	this.listaDeCaixas = listaDeCaixas;
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
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");

	// TODO Adicionar numero do caixa no cupom
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(Properties.NOME_RESTAURANTE);
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("********* RELATORIO DE CAIXA *********");
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

	BigDecimal bigDecimalSaldoTotal = BigDecimal.ZERO;
	BigDecimal bigDecimalSaldoMesas = BigDecimal.ZERO;
	BigDecimal bigDecimalDescontos = BigDecimal.ZERO;
	BigDecimal bigDecimalEntrada = BigDecimal.ZERO;
	BigDecimal bigDecimalSaida = BigDecimal.ZERO;
	BigDecimal bigDecimalCartaoCredito = BigDecimal.ZERO;
	BigDecimal bigDecimalCartaoDebito = BigDecimal.ZERO;
	BigDecimal bigDecimalCheque = BigDecimal.ZERO;
	BigDecimal bigDecimalConvenio = BigDecimal.ZERO;
	BigDecimal bigDecimalAlelo = BigDecimal.ZERO;
	BigDecimal bigDecimalDinheiro = BigDecimal.ZERO;

	int quantidadeDePessoas = 0;
	int quantidadeDeMesas = 0;

	for (CaixaModel caixaModel : listaDeCaixas) {
	    bigDecimalSaldoTotal = bigDecimalSaldoTotal.add(caixaModel.getSaldo());
	    bigDecimalSaldoMesas = bigDecimalSaldoMesas.add(caixaModel.getSaldoMesas());
	    bigDecimalDescontos = bigDecimalDescontos.add(caixaModel.getSaldoDescontos());
	    bigDecimalEntrada = bigDecimalEntrada.add(caixaModel.getSaldoEntrada());
	    bigDecimalSaida = bigDecimalSaida.add(caixaModel.getSaldoSaida());
	    bigDecimalCartaoCredito = bigDecimalCartaoCredito.add(caixaModel.getSaldoCartaoCredito());
	    bigDecimalCartaoDebito = bigDecimalCartaoDebito.add(caixaModel.getSaldoCartaoDebito());
	    bigDecimalCheque = bigDecimalCheque.add(caixaModel.getSaldoCheque());
	    bigDecimalConvenio = bigDecimalConvenio.add(caixaModel.getSaldoConvenio());
	    bigDecimalAlelo = bigDecimalAlelo.add(caixaModel.getSaldoAlelo());
	        
	    
	    bigDecimalDinheiro = bigDecimalDinheiro.add(caixaModel.getSaldoDinheiro());

	    quantidadeDeMesas = quantidadeDeMesas + caixaModel.getQuantidadeDeMesas();
	    quantidadeDePessoas = quantidadeDePessoas + caixaModel.getQuantidadeDePessoas();

	}

	DecimalFormat df = new DecimalFormat("#,###.00");

	String entrada = df.format(bigDecimalEntrada);
	if (entrada.startsWith(",")) {
	    entrada = "0" + entrada;
	}

	String saida = df.format(bigDecimalSaida);
	if (saida.startsWith(",")) {
	    saida = "0" + saida;
	}

	BigDecimal totalEntradaMenosSaida = bigDecimalEntrada.subtract(bigDecimalSaida);
	String entradaMenosSaida = "";
	if (totalEntradaMenosSaida.compareTo(BigDecimal.ZERO) < 0) {
	    totalEntradaMenosSaida.multiply(new BigDecimal(-1));
	    entradaMenosSaida = "-";
	}

	String entradaMenosSaidAux = df.format(totalEntradaMenosSaida);
	if (entradaMenosSaidAux.startsWith(",")) {
	    entradaMenosSaidAux = "0" + entradaMenosSaidAux;
	}
	entradaMenosSaida = entradaMenosSaida + entradaMenosSaidAux;

	String descontosMesas = df.format(bigDecimalDescontos);
	if (descontosMesas.startsWith(",")) {
	    descontosMesas = "0" + descontosMesas;
	}

	String saldoMesaComDescontos = df.format(bigDecimalSaldoMesas.subtract(bigDecimalDescontos));
	if (saldoMesaComDescontos.startsWith(",")) {
	    saldoMesaComDescontos = "0" + saldoMesaComDescontos;
	}

	String saldoDinheiro = df.format(bigDecimalDinheiro);
	if (saldoDinheiro.startsWith(",")) {
	    saldoDinheiro = "0" + saldoDinheiro;
	}

	String saldoDebito = df.format(bigDecimalCartaoDebito);
	if (saldoDebito.startsWith(",")) {
	    saldoDebito = "0" + saldoDebito;
	}

	String saldoCredito = df.format(bigDecimalCartaoCredito);
	if (saldoCredito.startsWith(",")) {
	    saldoCredito = "0" + saldoCredito;
	}

	String saldoCheque = df.format(bigDecimalCheque);
	if (saldoCheque.startsWith(",")) {
	    saldoCheque = "0" + saldoCheque;
	}
	
	String saldoConvenio = df.format(bigDecimalConvenio);
	if (saldoConvenio.startsWith(",")) {
		saldoConvenio = "0" + saldoConvenio;
	}
	
	
	String saldoAlelo = df.format(bigDecimalAlelo);
	if (saldoAlelo.startsWith(",")) {
		saldoAlelo = "0" + saldoAlelo;
	}

	String saldoMesas = df.format(bigDecimalSaldoMesas);
	if (saldoMesas.startsWith(",")) {
	    saldoMesas = "0" + saldoMesas;
	}
	
	

	String saldoTotal = df.format(bigDecimalSaldoTotal);
	if (saldoTotal.startsWith(",")) {
	    saldoTotal = "0" + saldoTotal;
	}

	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("           ENTRADAS E SAIDAS        ");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("(+) ENTRADAS                   " + entrada);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("(-) SAIDAS                     " + saida);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(" -> TOTAL                      " + entradaMenosSaida);
	conteudo.append("\n");
	conteudo.append(separadorSimples);

	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("	        MESAS               ");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("(+) SALDO                      " + saldoMesas);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("(-) DESCONTOS                  " + descontosMesas);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(" -> TOTAL                      " + saldoMesaComDescontos);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(" - Quantidade de Pessoas: " + quantidadeDePessoas);
	conteudo.append("\n");
	conteudo.append(" - Quantidade de Mesas: " + quantidadeDeMesas);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("\n");

	conteudo.append("	     SALDO FINAL              ");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("SALDO DINHEIRO                 " + saldoDinheiro);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("SALDO CARTAO DEBITO            " + saldoDebito);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("SALDO CARTAO CREDITO           " + saldoCredito);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("SALDO CONVENIO                 " + saldoConvenio);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("SALDO ALELO                    " + saldoAlelo);
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append("SALDO TOTAL                    " + saldoTotal);
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
