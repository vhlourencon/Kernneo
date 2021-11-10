package br.com.kernneo.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe que representa o ECF via ACBR no sistema e todas suas funcionalidades.
 *
 * @author Pedro H. Lira
 */
public class ACBR {

    private Socket acbr;
    private PrintWriter saida;
    private DataInputStream entrada;

    /**
     * Construtor padrao.
     */
    public ACBR() {
	this.acbr = null;
	this.saida = null;
	this.entrada = null;
    }

    private void conectar() throws Exception {
	try {
	    acbr = new Socket("localhost", 3434);
	    saida = new PrintWriter(acbr.getOutputStream());
	    entrada = new DataInputStream(acbr.getInputStream());
	    lerDados();
	} catch (IOException ex) {
	    // LOG.error("Nao foi possivel se conectar ao ACBrMonitor", ex);
	    throw new Exception("Verifique se as configuraõçes estão corretas e se está ativo no sistema.");
	}
    }

    // @Override
    // public String[] enviar(EComando comando, String... parametros) {
    // return enviar(comando.toString(), parametros);
    // }

    /**
     * @throws Exception
     * @see #enviar(br.com.phdss.EComando, java.lang.String...)
     */
    public String[] enviar(String comando, String... parametros) throws Exception {
	String[] resp = new String[] { "", "" };
	StringBuilder acao = new StringBuilder(comando);

	if (parametros != null && parametros.length > 0) {
	    acao.append("(");
	    for (String param : parametros) {
		acao.append(param).append(",");
	    }
	    acao.deleteCharAt(acao.length() - 1).append(")");
	}

	try {
	 
	    conectar();
	    
	    saida.print(acao.toString() + "\r\n.\r\n");
	    // saida.print(acao.toString() + "\r" + (char)3);

	    saida.flush();

	    String dados = lerDados();
	    System.out.println(acao.toString());
	    System.out.println(dados);
	    System.out.println("");
	    if ("".equals(dados)) {
		resp[0] = "OK";
	    } else if (dados.contains(":")) {
		String[] ret = dados.split(":", 2);
		resp[0] = ret[0].trim();
		resp[1] = ret[1].trim();

	    } else {
		resp[0] = "OK";
		resp[1] = dados.trim();
	    }

	    acbr.close();

	    return resp;

	} catch (Exception ex) {

	    throw ex;
	}

    }

    /**
     * Metodo que faz a leitura do retorno do ECF.
     *
     * @return uma String da resposta.
     */
    private String lerDados() {
	StringBuilder sb = new StringBuilder();
	try {
	    byte b;
	    while ((b = (byte) entrada.read()) != 3) {
		sb.append(new String(new byte[] { b }));
	    }
	    return sb.toString();
	} catch (IOException ex) {
	    return "ERRO + " + ex.getMessage();
	}
    }

    public DataInputStream getEntrada() {
	return entrada;
    }

    public void setEntrada(DataInputStream entrada) {
	this.entrada = entrada;
    }

}