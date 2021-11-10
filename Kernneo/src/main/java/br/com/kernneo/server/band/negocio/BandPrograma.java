package br.com.kernneo.server.band.negocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.kernneo.client.band.model.BandLogImportacaoModel;
import br.com.kernneo.client.band.model.BandProgramaModel;
import br.com.kernneo.server.band.dao.BandProgramaDAO;

public class BandPrograma extends BandNegocio<BandProgramaModel, BandProgramaDAO> {

	public ArrayList<BandProgramaModel> obterProgramasPorDataEHora(Date dataHora) throws Exception {

		try {
			return dao.obterProgramasPorDataEHora(dataHora);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		}

	}

	public ArrayList<BandProgramaModel> obterProgramacaoDoMes(Date dataParaObter) throws Exception {

		Calendar calendarDataIni = Calendar.getInstance();
		calendarDataIni.setTime(dataParaObter);
		calendarDataIni.set(Calendar.DAY_OF_MONTH, 1);
		calendarDataIni.add(Calendar.DATE, -7);

		Calendar calendarDataFim = Calendar.getInstance();
		calendarDataFim.setTime(dataParaObter);
		calendarDataFim.set(Calendar.DAY_OF_MONTH, calendarDataFim.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendarDataFim.add(Calendar.DATE, 7);

		return dao.obterProgramasPorData(calendarDataIni.getTime(), calendarDataFim.getTime());

	}

	public void importarProgramacaoDaSemana(Date dataParaImportar) throws Exception {
		Calendar calendarDataIni = Calendar.getInstance();
		calendarDataIni.setTime(dataParaImportar);

		for (int i = 1; i <= 7; i++) {
			calendarDataIni.set(Calendar.DAY_OF_WEEK, i);
			importarProgramacao(calendarDataIni.getTime());

		}

	}
	
	public void excluirProgramacaoDaSemana(Date dataParaExcluir) throws Exception {
		Calendar calendarDataIni = Calendar.getInstance();
		calendarDataIni.setTime(dataParaExcluir);

		for (int i = 1; i <= 7; i++) {
			calendarDataIni.set(Calendar.DAY_OF_WEEK, i);
			excluirProgramacao(calendarDataIni.getTime());

		}

	}
	
	public void excluirProgramacao(Date dataParaExcluir) throws Exception { 
		ArrayList<BandLogImportacaoModel> listaDeLogImportacao = new BandLogImportacao().obterLogImportacaoNaData(dataParaExcluir);
		if ( listaDeLogImportacao != null ) { 
			for (BandLogImportacaoModel bandLogImportacaoModel : listaDeLogImportacao) {
				new BandLogImportacao().excluir(bandLogImportacaoModel);
			}
			
		} 
		
		ArrayList<BandProgramaModel> listaDePrograma = dao.obterProgramasPorData(dataParaExcluir, dataParaExcluir);
		if ( listaDePrograma != null) { 
			System.out.println(listaDePrograma.size() + " " +  dataParaExcluir);
			
			for (BandProgramaModel bandProgramaModel : listaDePrograma) {
				excluir(bandProgramaModel);
			}
		}
	}

	public void importarProgramacao(Date dataParaImportar) throws Exception {

		ArrayList<BandLogImportacaoModel> listaDeLogImportacao = new BandLogImportacao().obterLogImportacaoNaData(dataParaImportar);

		if (listaDeLogImportacao == null || listaDeLogImportacao.size() == 0) {

			BandLogImportacaoModel logImportacao = new BandLogImportacaoModel();
			logImportacao.setDataImportacao(dataParaImportar);
			logImportacao = new BandLogImportacao().salvar(logImportacao);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
			String dataConvertida = sdf.format(dataParaImportar);

			Document document = Jsoup.connect("http://www.band.uol.com.br/tv/programacao_iframe.asp?local=fuso&data=" + dataConvertida).get();

			Calendar calendarDataIni = Calendar.getInstance();
			calendarDataIni.setTime(dataParaImportar);
			calendarDataIni.set(Calendar.SECOND, 0);

			Elements elementsManha = document.body().getElementsByClass("manha");
			Elements elementsTarde = document.body().getElementsByClass("tarde");
			Elements elementsNoite = document.body().getElementsByClass("noite");
			Elements elementsMadrugada = document.body().getElementsByClass("madrugada");

			elementsManha.append(elementsTarde.toString());
			elementsManha.append(elementsNoite.toString());
			elementsManha.append(elementsMadrugada.toString());

		
			if (elementsManha.size() > 0) {

				Elements elementsDaLista = elementsManha.get(0).getElementsByTag("li");
				// elementsDaLista.remove(elementsDaLista.get(0).getElementsByClass("local"));

				// System.out.println(elementsDaLista.toString());

				ArrayList<BandProgramaModel> listaDeProgramas = new ArrayList<BandProgramaModel>();

				for (Element element : elementsDaLista) {

					if (element.getAllElements().get(0).className().trim().equals("")) {
						element.getAllElements().get(0).className();
						
						BandProgramaModel programaModel = getProgramaModel(element);

						String[] horaMinStr = programaModel.getDataHoraIniStr().split(":");
						String horaStr = horaMinStr[0];
						String minStr = horaMinStr[1];

						int hora = Integer.valueOf(horaStr);
						int min = Integer.valueOf(minStr);

						calendarDataIni.set(Calendar.HOUR_OF_DAY, hora);
						calendarDataIni.set(Calendar.MINUTE, min);

						programaModel.setDataHoraInicio(calendarDataIni.getTime());

						if (listaDeProgramas.size() > 0) {
							BandProgramaModel ultimoProgramaModel = listaDeProgramas.get(listaDeProgramas.size() - 1);

							ultimoProgramaModel.setDataHoraFim(programaModel.getDataHoraInicio());
							if (programaModel.getDataHoraInicio().before(ultimoProgramaModel.getDataHoraInicio())) {

								calendarDataIni.add(Calendar.DATE, 1);
								programaModel.setDataHoraInicio(calendarDataIni.getTime());
								ultimoProgramaModel.setDataHoraFim(programaModel.getDataHoraInicio());

							}
							;
						}
						listaDeProgramas.add(programaModel);

					}
				}

				
				calendarDataIni.set(Calendar.HOUR_OF_DAY, 5);
				calendarDataIni.set(Calendar.MINUTE, 0);
				calendarDataIni.set(Calendar.SECOND, 0);

				BandProgramaModel ultimoProgramaDogia = listaDeProgramas.get(listaDeProgramas.size() - 1);
				ultimoProgramaDogia.setDataHoraFim(calendarDataIni.getTime());

				
				BandProgramaModel programaModelContinuacao = null;
				for (BandProgramaModel programaModel : listaDeProgramas) {
					programaModel.setLogImportacao(logImportacao);
					if (DateUtils.isSameDay(programaModel.getDataHoraInicio(), programaModel.getDataHoraFim()) == false) {
						Calendar calendarAux = Calendar.getInstance();
						calendarAux.setTime(programaModel.getDataHoraFim());

						// System.out.println(calendarAux.get(Calendar.HOUR_OF_DAY)
						// + ":" + calendarAux.get(Calendar.MINUTE));

						if (calendarAux.get(Calendar.HOUR_OF_DAY) != 0 || calendarAux.get(Calendar.MINUTE) != 0) {

							Calendar calendarProgramaContinuacao = Calendar.getInstance();
							calendarProgramaContinuacao.setTime(programaModel.getDataHoraFim());
							calendarProgramaContinuacao.set(Calendar.HOUR_OF_DAY, 0);
							calendarProgramaContinuacao.set(Calendar.MINUTE, 0);
							calendarProgramaContinuacao.set(Calendar.SECOND, 0);

							programaModelContinuacao = new BandProgramaModel();
							programaModelContinuacao.setTitulo(programaModel.getTitulo());
							programaModelContinuacao.setDataHoraInicio(calendarProgramaContinuacao.getTime());
							programaModelContinuacao.setDataHoraFim(programaModel.getDataHoraFim());
							programaModelContinuacao.setLogImportacao(logImportacao);
							programaModelContinuacao.setDescricao(programaModel.getDescricao());

							salvar(programaModelContinuacao);
						}

						calendarAux.setTime(programaModel.getDataHoraInicio());
						calendarAux.set(Calendar.HOUR_OF_DAY, 23);
						calendarAux.set(Calendar.MINUTE, 59);
						calendarAux.set(Calendar.SECOND, 59);

						programaModel.setDataHoraFim(calendarAux.getTime());

					}
					salvar(programaModel);

				}
				System.out.println("passou 33");
				if (programaModelContinuacao != null) {
					listaDeProgramas.add(programaModelContinuacao);
				}
			}

		}

	}
	
//	public ArrayList<BandProgramaModel> obterProgramasPorData(Date dataInicial, Date dataFinal) throws Exception { 
//		Calendar calendarDataIni = Calendar.getInstance();
//		calendarDataIni.setTime(dataInicial);
//		calendarDataIni.set(Calendar.HOUR_OF_DAY, 0);
//		calendarDataIni.set(Calendar.MINUTE, 0);
//		calendarDataIni.set(Calendar.SECOND, 0);	
//		
//		Calendar calendarDataFim = Calendar.getInstance();
//		calendarDataFim.setTime(dataFinal);
//		calendarDataFim.set(Calendar.HOUR_OF_DAY, 23);
//		calendarDataFim.set(Calendar.MINUTE, 59);
//		calendarDataFim.set(Calendar.SECOND, 59);	
//		
//		return dao.obterProgramasPorData(calendarDataIni.getTime(), calendarDataFim.getTime());
//	}

	public BandProgramaModel getProgramaModel(Element elementDaLista) {
		Element elementHora = elementDaLista.getElementsByClass("hora").get(0);
		Element elementTitulo = elementDaLista.getElementsByClass("titulo").get(0);
		Element elementDescricao = elementDaLista.getElementsByClass("descricao").get(0);

		String descricao = elementDescricao.html();
		if (descricao.length() > 100) {
			descricao = descricao.substring(0, 99) + "...";
		}

		BandProgramaModel programaModel = new BandProgramaModel();
		programaModel.setDataHoraIniStr(elementHora.html());
		programaModel.setTitulo(elementTitulo.html());
		programaModel.setDescricao(descricao);

		return programaModel;

	}

	public BandPrograma() {
		super();
		dao = new BandProgramaDAO();
	}

	@Override
	public Exception validar(BandProgramaModel vo) {

		// TODO Auto-generated method stub
		return null;
	}

}
