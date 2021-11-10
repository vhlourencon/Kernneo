package br.com.kernneo.server.caravana.credenciamento;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaCatParticipanteModel;
import br.com.kernneo.client.caravana.model.CaravanaCredenciamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.CaravanaParticipanteModel;
import br.com.kernneo.client.caravana.model.CredenciamentoServicoModel;
import br.com.kernneo.client.caravana.model.EventoCategoriaPartModel;
import br.com.kernneo.client.caravana.model.RelatorioCredenciamentoCategoriaModel;
import br.com.kernneo.client.caravana.types.CaravanaSituacaoAgendamentoTypes;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.client.utils.Properties;
import br.com.kernneo.server.band.negocio.BandNegocio;
import br.com.kernneo.server.caravana.agendamento.CaravanaAgendamento;
import br.com.kernneo.server.caravana.credenciamento.servico.CredenciamentoServico;
import br.com.kernneo.server.caravana.evento.catParticipantes.CaravanaEventoCatParticipantes;
import br.com.kernneo.server.caravana.participante.CaravanaParticipante;

public class CaravanaCredenciamento extends BandNegocio<CaravanaCredenciamentoModel, CaravanaCredenciamentoDAO> {

	public CaravanaCredenciamentoModel verificarParticipanteNoEvento(CaravanaEventoModel eventoModel, CaravanaParticipanteModel participanteModel) throws Exception {
		CaravanaCredenciamentoModel caravanaCredenciamentoModel = new CaravanaCredenciamentoModel();
		caravanaCredenciamentoModel.setEvento(eventoModel);
		caravanaCredenciamentoModel.setParticipante(participanteModel);
		caravanaCredenciamentoModel.setParticipanteJaCredenciou(false);

		if (participanteModel.getId() == null) {
			return caravanaCredenciamentoModel;

		} else {

			CaravanaCredenciamentoModel credenciamentoAux = dao.obterCredenciamentoDePatcipantePorDia(eventoModel, participanteModel, new Date());
			if (credenciamentoAux == null) {

				ArrayList<CaravanaCredenciamentoModel> listaDeCredenciamentoNoEvento = dao.obterTodosCredenciamentosDoPArticipanteNoEvento(eventoModel, participanteModel);
				if (listaDeCredenciamentoNoEvento != null && listaDeCredenciamentoNoEvento.size() > 0) {
					caravanaCredenciamentoModel.setParticipanteJaCredenciou(true);
					caravanaCredenciamentoModel.setCategoriaParticipante(listaDeCredenciamentoNoEvento.get(listaDeCredenciamentoNoEvento.size() - 1).getCategoriaParticipante());
				}
				return caravanaCredenciamentoModel;
			} else {
				throw new Exception("Participante já foi credenciado hoje!");
			}
		}

	}

	public ArrayList<RelatorioCredenciamentoCategoriaModel> obterRelatorioDeCredenciamentoCategoriaDeHoje(CaravanaEventoModel eventoModel) throws Exception {

		ArrayList<RelatorioCredenciamentoCategoriaModel> lista = dao.obterRelatorioDeCredenciamentoCategoriaPorData(eventoModel, new Date());

		return lista;

	}

	public RelatorioCredenciamentoCategoriaModel obterQuantidadeCredenciamentoPorCategoriaDeHoje(CaravanaEventoModel eventoModel, CaravanaCatParticipanteModel catParticipanteModel) throws Exception {

		RelatorioCredenciamentoCategoriaModel relatorioCredenciamentoCategoriaModel = dao.obterQuantidadeCredenciamentoPorCategoriaEData(eventoModel, catParticipanteModel, new Date());

		return relatorioCredenciamentoCategoriaModel;

	}

	public boolean credenciarParticipante(CaravanaCredenciamentoModel caravanaCredenciamentoModel, String idImpressora) throws Exception {
		RelatorioCredenciamentoCategoriaModel relatorioCredenciamentoCategoriaModel = obterQuantidadeCredenciamentoPorCategoriaDeHoje(caravanaCredenciamentoModel.getEvento(), caravanaCredenciamentoModel.getCategoriaParticipante());
		if (relatorioCredenciamentoCategoriaModel != null && relatorioCredenciamentoCategoriaModel.getQuantidadeDePessoas() != null) {

			ArrayList<EventoCategoriaPartModel> listaDeEventoCategoriaPartModels = new CaravanaEventoCatParticipantes().obterTodosPorEvento(caravanaCredenciamentoModel.getEvento());
			if (listaDeEventoCategoriaPartModels != null) {

				EventoCategoriaPartModel eventoCategoriaPartModelAux = null;
				for (EventoCategoriaPartModel eventoCategoriaPartModel : listaDeEventoCategoriaPartModels) {

					if (eventoCategoriaPartModel.getCategoriaParticipanteModel().getId().compareTo(caravanaCredenciamentoModel.getCategoriaParticipante().getId()) == 0) {
						eventoCategoriaPartModelAux = eventoCategoriaPartModel;
						break;
					}
				}

				if (eventoCategoriaPartModelAux != null) {

					if (eventoCategoriaPartModelAux.isPermiteSemPreCadastro() == false) {
						boolean participantePreCadastrado = false;
						if (caravanaCredenciamentoModel.getParticipante().getId() != null) {
							ArrayList<CaravanaAgendamentoModel> listaDeAgendamento = new CaravanaAgendamento().obterTodosPorEventoEparticipante(caravanaCredenciamentoModel.getEvento(), caravanaCredenciamentoModel.getParticipante());
							if (listaDeAgendamento != null && listaDeAgendamento.size() > 0) {
								for (CaravanaAgendamentoModel caravanaAgendamentoModel : listaDeAgendamento) {
									if (caravanaAgendamentoModel.getPermissaoAgendamento().getEventoCategoriaParticipante().getId().compareTo(eventoCategoriaPartModelAux.getId()) == 0) {
										if (caravanaAgendamentoModel.getSituacao() == CaravanaSituacaoAgendamentoTypes.aprovado) {
											participantePreCadastrado = true;
										}
									}
								}

							}
						}

						if (participantePreCadastrado == false) {
							throw new Exception("A categoria: " + eventoCategoriaPartModelAux.getCategoriaParticipanteModel().getNome() + "  permite SOMENTE participantes AGENDADOS <br><br>**TENTE A BUSCA PELO CARTÃO SUS DO PARTICIPANTE**");
						}
					}

					// if (
					// eventoCategoriaPartModelAux.isPermitidoCadastrarNovo() ==
					// false &&
					// caravanaCredenciamentoModel.getParticipante().getId() ==
					// null) {
					// throw new Exception("Não é permitido cadastrar NOVOS
					// participantes para: " +
					// eventoCategoriaPartModelAux.getCategoriaParticipanteModel().getNome());
					// }

					Long quantidadeDeParticipanteAtual = relatorioCredenciamentoCategoriaModel.getQuantidadeDePessoas();
					Long limiteDeParticipantes = eventoCategoriaPartModelAux.getLimiteDeParticipantes();

					if (quantidadeDeParticipanteAtual != null && limiteDeParticipantes != null) {
						quantidadeDeParticipanteAtual = Long.sum(quantidadeDeParticipanteAtual, 1L);
						//quantidadeDeParticipanteAtual = quantidadeDeParticipanteAtual + 1L;

						
						if (quantidadeDeParticipanteAtual.compareTo(limiteDeParticipantes) > 0) {
							throw new Exception("Não é possivel realizar o credenciamento<br>A categoria:" + caravanaCredenciamentoModel.getCategoriaParticipante().getNome() + " atingiu o limite de: " + limiteDeParticipantes + " participantes");
						}
					}
				}

			}
		}

		if (caravanaCredenciamentoModel.getParticipante().getId() == null) {

			CaravanaParticipanteModel participanteModel = new CaravanaParticipante().salvar(caravanaCredenciamentoModel.getParticipante());
			caravanaCredenciamentoModel.setParticipante(participanteModel);
		}
		// else {
		// CaravanaParticipanteModel participanteModel = new
		// CaravanaParticipante().merge(caravanaCredenciamentoModel.getParticipante());
		// caravanaCredenciamentoModel.setParticipante(participanteModel);
		// }

		caravanaCredenciamentoModel.setDataHoraCredenciamento(new Date());
		caravanaCredenciamentoModel.getListaDeServico();
		ArrayList<CredenciamentoServicoModel> listaDeSerVicoDoPArticipante = new ArrayList<CredenciamentoServicoModel>();
		for (CredenciamentoServicoModel model : caravanaCredenciamentoModel.getListaDeServico()) {
			listaDeSerVicoDoPArticipante.add(model);
		}

		caravanaCredenciamentoModel = salvar(caravanaCredenciamentoModel);

		for (CredenciamentoServicoModel credenciamentoServicoModel : listaDeSerVicoDoPArticipante) {
			credenciamentoServicoModel.setCredenciamento(caravanaCredenciamentoModel);
			credenciamentoServicoModel = new CredenciamentoServico().salvar(credenciamentoServicoModel);
		}

		if (caravanaCredenciamentoModel.isImprimeEtiqueta()) {
			caravanaCredenciamentoModel.setIdImpressora(idImpressora);
			searchPrintAndPrintLabel(caravanaCredenciamentoModel, idImpressora);
		}

		return true;

	}

	public CaravanaCredenciamentoModel credenciarParticipanteComData(CaravanaCredenciamentoModel caravanaCredenciamentoModel, Date dataHoraCredenciamento) throws Exception {

		if (caravanaCredenciamentoModel.getParticipante() != null) {
			if (caravanaCredenciamentoModel.getParticipante().getId() == null) {
				CaravanaParticipanteModel participanteModel = new CaravanaParticipante().salvar(caravanaCredenciamentoModel.getParticipante());
				caravanaCredenciamentoModel.setParticipante(participanteModel);
			} else {
				CaravanaParticipanteModel participanteModel = new CaravanaParticipante().merge(caravanaCredenciamentoModel.getParticipante());
				caravanaCredenciamentoModel.setParticipante(participanteModel);
			}
		}

		caravanaCredenciamentoModel.setDataHoraCredenciamento(dataHoraCredenciamento);
		if (caravanaCredenciamentoModel.getId() == null) {

			caravanaCredenciamentoModel.getListaDeServico();
			ArrayList<CredenciamentoServicoModel> listaDeSerVicoDoPArticipante = new ArrayList<CredenciamentoServicoModel>();
			for (CredenciamentoServicoModel model : caravanaCredenciamentoModel.getListaDeServico()) {
				listaDeSerVicoDoPArticipante.add(model);
			}

			caravanaCredenciamentoModel = salvar(caravanaCredenciamentoModel);

			for (CredenciamentoServicoModel credenciamentoServicoModel : listaDeSerVicoDoPArticipante) {
				credenciamentoServicoModel.setCredenciamento(caravanaCredenciamentoModel);
				credenciamentoServicoModel = new CredenciamentoServico().salvar(credenciamentoServicoModel);
			}

		} else {
			caravanaCredenciamentoModel = merge(caravanaCredenciamentoModel);

		}

		if (caravanaCredenciamentoModel.isImprimeEtiqueta()) {
			searchPrintAndPrintLabel(caravanaCredenciamentoModel, caravanaCredenciamentoModel.getIdImpressora());
		}

		return caravanaCredenciamentoModel;

	}

	public boolean searchPrintAndPrintLabel(CaravanaCredenciamentoModel credenciamentoModel, String idImpressora) throws Exception {
		boolean retorno = false;

		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

		services = PrintServiceLookup.lookupPrintServices(null, null);

		String nomeDaImpressora = getNomeDaImpressora(idImpressora);
		for (int i = 0; i < services.length; i++) {

			PrintService pserv = services[i];
			if (pserv.getName().equalsIgnoreCase(nomeDaImpressora)) {
				System.out.println("selecionada -> " + pserv.getName());

				retorno = printLabel(pserv, credenciamentoModel);

			}
		}

		return retorno;
	}

	private static boolean printLabel(PrintService printService, CaravanaCredenciamentoModel credenciamentoModel) {
		if (printService == null || credenciamentoModel == null) {
			System.err.println("[Print Label] print service or label is invalid.");
			return false;
		}

		String nomeParticipante = "";
		String czas = new SimpleDateFormat("d MMMMM yyyy HH:mm").format(credenciamentoModel.getDataHoraCredenciamento());
		String categoriaParticipante = "";
		String cidadeParticipante = "";
		Long idParticipante = 0L;

		if (credenciamentoModel.getParticipante() != null) {
			nomeParticipante = " " + credenciamentoModel.getParticipante().getNome() + " ";
			nomeParticipante = removerAcentos(nomeParticipante);
			nomeParticipante = nomeParticipante.toUpperCase();
			idParticipante = credenciamentoModel.getParticipante().getId();
		}

		if (credenciamentoModel.getCategoriaParticipante() != null) {
			categoriaParticipante = " - " + credenciamentoModel.getCategoriaParticipante().getNome() + " ";
			categoriaParticipante = removerAcentos(categoriaParticipante);
		}

		if (credenciamentoModel.getParticipante().getCidade() != null) {
			cidadeParticipante = " - " + credenciamentoModel.getParticipante().getCidade().getNome() + " - " + credenciamentoModel.getParticipante().getCidade().getEstado().getSigla();
			cidadeParticipante = removerAcentos(cidadeParticipante);
		}
		String command = "N\n" + "B25,40,0,1,3,7,80,B,\"" + String.format("%06d", idParticipante) + "\"\n" + "A5,5,0,3,1,2,N,\"" + nomeParticipante + "\"\n" + "A230,50,0,4,1,1,N,\"" + categoriaParticipante + "\"\n" + "A230,90,0,4,1,1,N,\"" + cidadeParticipante + "\"\n" + "A150,150,0,3,1,1,N,\"" + czas + "\"\n"

				+ "P1\n";
		byte[] data;
		data = command.getBytes(StandardCharsets.US_ASCII);
		Doc doc = new SimpleDoc(data, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);

		boolean result = false;
		try {
			printService.createPrintJob().print(doc, null);
			result = true;
		} catch (PrintException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public CaravanaCredenciamentoModel obterModelCompleto(CaravanaCredenciamentoModel credenciamentoModel) throws Exception {

		credenciamentoModel.setListaDeServico(new CredenciamentoServico().obterTodosPorCredenciamento(credenciamentoModel));

		return credenciamentoModel;
	}

	public CaravanaCredenciamento() {
		super();
		dao = new CaravanaCredenciamentoDAO();
	}

	@Override
	public Exception validar(CaravanaCredenciamentoModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

	@Override
	public String getSqlFiltro(CaravanaCredenciamentoModel vo) {
		String filtro = super.getSqlFiltro(vo);

		if (vo.getEvento() != null) {
			filtro += " and id_evento=" + vo.getEvento().getId();
		}
		if (vo.getParticipante() != null && vo.getParticipante().getNome() != null && vo.getParticipante().getNome().trim().length() > 0) {
			filtro += " and g.participante.nome like('%" + vo.getParticipante().getNome() + "%')";
		}

		if (vo.getUsuarioCredenciamento() != null) {
			filtro += " and g.usuarioCredenciamento.id=" + vo.getUsuarioCredenciamento().getId() + "";
		}

		if (vo.getCategoriaParticipante() != null) {
			filtro += " and g.categoriaParticipante.id=" + vo.getCategoriaParticipante().getId() + "";
		}

		if (vo.getDataHoraCredenciamento() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dataCredenciamento = sdf.format(vo.getDataHoraCredenciamento());
			
			filtro += " and date(g.dataHoraCredenciamento)=('" + dataCredenciamento + "')";
		}
		filtro += " order by id desc";

		System.out.println(filtro);

		return filtro;
	}

	public String getSqlFiltroIdDesc(CaravanaCredenciamentoModel vo) {
		String filtro = getGenericFiltro(vo);
		if (vo.getEvento() != null) {
			filtro += " and id_evento=" + vo.getEvento().getId();
		}
		if (vo.getParticipante() != null && vo.getParticipante().getNome() != null && vo.getParticipante().getNome().trim().length() > 0) {
			filtro += " and g.participante.nome like('%" + vo.getParticipante().getNome() + "%')";
		}

		if (vo.getUsuarioCredenciamento() != null) {
			filtro += " and g.usuarioCredenciamento.id=" + vo.getUsuarioCredenciamento().getId() + "";
		}

		if (vo.getCategoriaParticipante() != null) {
			filtro += " and g.categoriaParticipante.id=" + vo.getCategoriaParticipante().getId() + "";
		}
		if (vo.getDataHoraCredenciamento() != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dataCredenciamento = sdf.format(vo.getDataHoraCredenciamento());
			if (vo.getDataHoraCredenciamento() != null) {

				filtro += " and date(g.dataHoraCredenciamento)=('" + dataCredenciamento + "')";
			}
		}
		filtro += " order by id desc";

		System.out.println(filtro);
		return filtro;
	}

	public GenericPagina<CaravanaCredenciamentoModel> obterPaginaDescPosterior(GenericPagina<CaravanaCredenciamentoModel> pagina, CaravanaCredenciamentoModel filtroModel) throws Exception {

		try {
			return dao.obterPaginaPosterior(pagina, getSqlFiltroIdDesc(filtroModel));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro ao tentar obter os dados da pagina :\n" + e.getMessage());
		}

	}

	public GenericPagina<CaravanaCredenciamentoModel> obterPaginaDescAnterior(GenericPagina<CaravanaCredenciamentoModel> pagina, CaravanaCredenciamentoModel filtroModel) throws Exception {

		try {
			return dao.obterPaginaAnterior(pagina, getSqlFiltroIdDesc(filtroModel));
		} catch (Exception e) {

			e.printStackTrace();
			throw new Exception("Ocorreu um erro ao tentar obter os dados da pagina :\n" + e.getMessage());
		}

	}

	public static String getNomeDaImpressora(String idImpressora) throws InvalidFileFormatException, IOException {

		Ini ini = new Ini(new File(Properties.getDIR_CONFIG() + "config.txt"));
		String localDeImpressao = ini.get("impressoras", idImpressora);

		return localDeImpressao;

	}

	public CaravanaCredenciamentoModel obterPorIdEverificarParticipanteNoEvento(CaravanaEventoModel eventoModel, String idParticipante) throws Exception {
		try {
			Long id = Long.valueOf(idParticipante);
			CaravanaParticipanteModel caravanaParticipanteModel = new CaravanaParticipante().obterPorId(CaravanaParticipanteModel.class, id);
			if (caravanaParticipanteModel == null) {
				throw new Exception("Participante não encontrado!");
			}
			CaravanaCredenciamentoModel caravanaCredenciamentoModel = verificarParticipanteNoEvento(eventoModel, caravanaParticipanteModel);
			return caravanaCredenciamentoModel;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		}
	}

}
