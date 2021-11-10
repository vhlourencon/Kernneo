package br.com.kernneo.server.report;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.Normalizer;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import br.com.kernneo.client.exception.ImpressoraException;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.utils.Properties;

public abstract class AbstractReport {

	protected final String separadorDuplo = "========================================";
	protected final String separadorSimples = "----------------------------------------";

	// Inicia comandos ESC/POS
	public static final char ESC = 27;
	// Inicia e finaliza negrito
	public static final char E = 69;
	public static final char F = 70;
	// ESC W n (Expandido)
	public static final char W = 87;
	// utilizados para impressao condensada
	public static final char SI = 15; // Shift In
	public static final char DC2 = 18; // Device Control 2

	public static final char _0 = 48;
	public static final char _1 = 49;

	public static final char _4 = 52;
	public static final char _5 = 53;

	public static final char CR = 13;
	public static final char LF = 10;

	public static final char HIFEN = 45; // sinal -

	public abstract StringBuffer geraConteudo(LocalDeImpressao localDeImpressao);

	protected void sublinhado(PrintStream ps, String line) {
		ps.print(ESC);
		ps.print(HIFEN);
		ps.print(_1);
		ps.println(line);
		ps.print(ESC);
		ps.print(HIFEN);
		ps.print(_0); // ps.print(CR); ps.print(LF);
	}

	protected void condensado(PrintStream ps, String line) {
		ps.print(ESC);
		ps.print(SI);
		ps.println(line);
		ps.print(DC2);
		ps.print(CR); // ps.print(LF);
	}

	protected void italico(PrintStream ps, String line) {
		ps.print(ESC);
		ps.print(_4);
		ps.println(line);
		ps.print(ESC);
		ps.print(_5); // ps.print(CR); ps.print(LF);
	}

	protected void negrito(PrintStream ps, String line) {
		ps.print(ESC);
		ps.print(E);
		ps.println(line);
		ps.print(ESC);
		ps.print(F); // ps.print(LF); //ps.print(LF);
	}

	protected void expandido(PrintStream ps, String line) {
		ps.print(ESC);
		ps.print(W);
		ps.print(_1);
		ps.println(line);
		ps.print(ESC);
		ps.print(W);
		ps.print(_0); // ps.print(CR); ps.print(LF);
	}

	protected void cortaPapel(StringBuffer conteudo) {

		conteudo.append((char) 27);
		conteudo.append((char) 109);

		// conteudo.append(""+(char)27+(char)119);

	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public void imprimir(LocalDeImpressao localDeImpressao) throws ImpressoraException {

		try {
			StringBuffer conteudo = geraConteudo(localDeImpressao);
			StringBuffer conteudoSemAcento = new StringBuffer(removerAcentos(conteudo.toString()));

			String nomeDaImpressora = getNomeDaImpressora(localDeImpressao);
			System.out.println(conteudoSemAcento);
			if (nomeDaImpressora != null && nomeDaImpressora.trim().equals("") == false) {

				
				// CRIA A STREAM A PARTIR DA STRING
				InputStream ps = new ByteArrayInputStream(conteudoSemAcento.toString().getBytes());
				
				

				// CRIA O DOCUMENTO DE IMPRESSAO
				DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		

				// LOCALIZA AS IMPRESSORAS DISPONIVEIS NO SERVIDOR/PC
				PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

				for (int i = 0; i < services.length; i++) {

					PrintService pserv = services[i];
					

					if (pserv.getName().equalsIgnoreCase(nomeDaImpressora)) {

						DocPrintJob job = services[i].createPrintJob();

						Doc doc = new SimpleDoc(ps, flavor, null);
						

						job.print(doc, null);

						ps.close();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImpressoraException("Erro ao Imprimir");

		}

	}

	public void printRodape(StringBuffer conteudo) {
		for (int i = 0; i <= 2; i++)
			conteudo.append("\n");
	}

	public static String getNomeDaImpressora(LocalDeImpressao localDeImpressaoTypes) throws InvalidFileFormatException, IOException {

		Ini ini = new Ini(new File(Properties.getDIR_CONFIG() + "config.txt"));
		String localDeImpressao = ini.get("impressoras", localDeImpressaoTypes.toString());

		return localDeImpressao;

	}

}
