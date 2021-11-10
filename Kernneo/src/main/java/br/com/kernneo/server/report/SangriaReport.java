package br.com.kernneo.server.report;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.utils.Properties;

public class SangriaReport extends AbstractReport {

    private MesaRecebimentoModel recebimentoModel;
    private UsuarioModel funcionario;

    private ArrayList<ItemModel> listaDeITemAgrupado = new ArrayList<ItemModel>();

    public SangriaReport(UsuarioModel funcionario, MesaRecebimentoModel recebimentoModel) {
	this.recebimentoModel = recebimentoModel;
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
	conteudo.append("********** SANGRIA DE CAIXA **********");
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
	conteudo.append(localDeImpressao.toString() + " - " + dateFormat.format(dataAtual) + " - " + horaFormat.format(dataAtual));
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("Caixa Nº: " + recebimentoModel.getCaixa().getId());
	conteudo.append("\n");
	conteudo.append("Atendente: " + funcionario.getNome());
	conteudo.append("\n");

    }

    protected void printConteudo(StringBuffer conteudo) {
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("VALOR DA SANGRIA");
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");

	DecimalFormat df = new DecimalFormat("#,###.00");

	String valorSangria = df.format(recebimentoModel.getValor().doubleValue());
	if (valorSangria.startsWith(",")) {
	    valorSangria = "0" + valorSangria;
	}

	conteudo.append(" - > R$" + valorSangria);
	conteudo.append("\n");

	String obs = recebimentoModel.getDescricao();

	int count = 7;
	conteudo.append(" OBS: ");
	if (obs != null) {
	    for (int i = 0; i < obs.length(); i++) {
		conteudo.append(obs.charAt(i));

		if (count == 40) {
		    conteudo.append("\n");
		    count = 0;
		}

		count++;
	    }
	}

	conteudo.append("\n");
	conteudo.append(separadorSimples);
	conteudo.append("\n");
	conteudo.append(separadorDuplo);
	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("\n");
	conteudo.append("________________________________________");
	conteudo.append("\n");
	conteudo.append("------ ASSINATURA DO FUNCIONARIO -------");
	conteudo.append("\n");
	conteudo.append("\n");
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
