package br.com.kernneo.server.band.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.kernneo.client.band.model.BandExibicaoModel;
import br.com.kernneo.client.band.model.BandProgramaModel;
import br.com.kernneo.server.band.dao.BandExibicaoDAO;

public class BandExibicao extends BandNegocio<BandExibicaoModel, BandExibicaoDAO> {

	public BandExibicao() {
		super();
		dao = new BandExibicaoDAO();
	}

	public ArrayList<BandExibicaoModel> obterTodosPorDataECodigoPi(Date dataInicial, Date dataFinal, String codigoPi) throws Exception {

		ArrayList<BandExibicaoModel> listaDeExibidos = new ArrayList<BandExibicaoModel>();

		ArrayList<String> listaDeArquivosDoPeriodo = obterArquivosPorPeriodo(dataInicial, dataFinal);

		for (String arquivo : listaDeArquivosDoPeriodo) {
			BufferedReader buffeRedArquivo = new BufferedReader(new InputStreamReader(new FileInputStream("c:\\kernneo\\relatorio\\" + arquivo), "UTF-16LE"));
			String line = null;
			while ((line = buffeRedArquivo.readLine()) != null) {

				String codigo = line.substring(0, 38).trim();

				BandExibicaoModel BandExibicaoModel = new BandExibicaoModel();
				BandExibicaoModel.setCodigo(codigo);

				if (BandExibicaoModel.getCodigo().trim().equals(codigoPi.trim())) {
					String descricao = line.substring(38, 66).trim();
					String situacao = line.substring(66, 79).trim();
					String data = line.substring(76, 93).trim();

					String horaAux = line.substring(93, 108).trim();
					String hora = horaAux.substring(0, horaAux.indexOf(";"));
					String miliSegundosHoraExibicao = horaAux.substring(horaAux.indexOf(";"), horaAux.length());

					String duracao = line.substring(108, 136).trim();
					String categoria = line.substring(136, line.length()).trim();

					SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

					String dataHora = data + " " + horaAux;
					Date dataAux = formatData.parse(dataHora);

					BandExibicaoModel.setDescricao(descricao);
					BandExibicaoModel.setSituacao(situacao);
					BandExibicaoModel.setDataHoraExibicao(dataAux);
					BandExibicaoModel.setDataStr(data);
					BandExibicaoModel.setHoraStr(hora);
					BandExibicaoModel.setMiliSegundosHoraExibicao(miliSegundosHoraExibicao);
					BandExibicaoModel.setCategoria(categoria);
					BandExibicaoModel.setDuracao(duracao);

					ArrayList<BandProgramaModel> listaDePrograma = new BandPrograma().obterProgramasPorDataEHora(BandExibicaoModel.getDataHoraExibicao());
					
					
					
					
					

					if (listaDePrograma != null && listaDePrograma.size() > 0) {
						BandExibicaoModel.setPrograma(listaDePrograma.get(0));
					}
					
//					System.out.println(BandExibicaoModel.getDataHoraExibicao() + " " +  BandExibicaoModel.getPrograma().getId() + " - " + BandExibicaoModel.getPrograma().getTitulo());
//					System.out.println("--------");

					listaDeExibidos.add(BandExibicaoModel);

				}

			}
		}
		return listaDeExibidos;

	}

	public ArrayList<String> obterArquivosPorPeriodo(Date dataInicial, Date dataFinal) {

		ArrayList<String> listaDeArquivosNoPeriodo = new ArrayList<String>();

		Calendar calendarDataIni = Calendar.getInstance();
		calendarDataIni.setTime(dataInicial);
		calendarDataIni.set(Calendar.HOUR_OF_DAY, 0);
		calendarDataIni.set(Calendar.MINUTE, 0);
		calendarDataIni.set(Calendar.SECOND, 0);

		Calendar calendarDataFim = Calendar.getInstance();
		calendarDataFim.setTime(dataFinal);
		calendarDataFim.set(Calendar.HOUR_OF_DAY, 23);
		calendarDataFim.set(Calendar.MINUTE, 59);
		calendarDataFim.set(Calendar.SECOND, 59);

		while (calendarDataIni.getTime().before(calendarDataFim.getTime()) == true) {

			ArrayList<String> listaDeArquivosNaData = obterArquvosPorData(calendarDataIni.getTime());

			for (String string : listaDeArquivosNaData) {
				listaDeArquivosNoPeriodo.add(string);
			}

			calendarDataIni.add(Calendar.DATE, 1);

		}

		return listaDeArquivosNoPeriodo;
	}

	public ArrayList<String> obterArquvosPorData(Date dataParaOterArquivos) {

		File folder = new File("c:\\kernneo\\relatorio");
		File[] listOfFiles = folder.listFiles();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
		String comecoDoArquivo = simpleDateFormat.format(dataParaOterArquivos);

		ArrayList<String> listaDeArquivosNaData = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String nomeDoArquivo = listOfFiles[i].getName();
				if (nomeDoArquivo != null) {
					if (nomeDoArquivo.startsWith(comecoDoArquivo)) {
						listaDeArquivosNaData.add(nomeDoArquivo);
					}
				}
			}
		}
		return listaDeArquivosNaData;
	}

	@Override
	public Exception validar(BandExibicaoModel vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
