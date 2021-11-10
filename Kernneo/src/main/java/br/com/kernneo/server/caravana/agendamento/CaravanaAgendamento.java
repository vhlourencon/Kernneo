package br.com.kernneo.server.caravana.agendamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;



import br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaComprovanteModel;
import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.CaravanaParticipanteModel;
import br.com.kernneo.client.caravana.model.EventoPermissaoAgendamentoModel;
import br.com.kernneo.client.caravana.types.CaravanaSituacaoAgendamentoTypes;
import br.com.kernneo.client.caravana.types.CaravanaUsuarioPerfilTypes;
import br.com.kernneo.server.band.negocio.BandNegocio;
import br.com.kernneo.server.caravana.CartaoSus;
import br.com.kernneo.server.caravana.evento.CaravanaEvento;
import br.com.kernneo.server.caravana.evento.permissaoAgendamento.EventoPermissaoAgendamento;
import br.com.kernneo.server.caravana.participante.CaravanaParticipante;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CaravanaAgendamento extends BandNegocio<CaravanaAgendamentoModel, CaravanaAgendamentoDAO> {

	public CaravanaAgendamento() {
		super();
		dao = new CaravanaAgendamentoDAO();
	}

	public ArrayList<CaravanaAgendamentoModel> imprimirListaDeAgendamento(ArrayList<CaravanaAgendamentoModel> listaDeAgendamento) throws Exception {

		for (CaravanaAgendamentoModel caravanaAgendamentoModel : listaDeAgendamento) {
			caravanaAgendamentoModel.setJaImprimiu(true);
			dao.alterar(caravanaAgendamentoModel);
			if (caravanaAgendamentoModel.getSituacao() != CaravanaSituacaoAgendamentoTypes.aprovado) {
				throw new Exception("Só é possivel imprimir comprovantes de inscrições aprovadas");
			}

		}

		return listaDeAgendamento;
	}

	public String imprimirComprovantes(ArrayList<CaravanaAgendamentoModel> listaDeAgendamento, ServletContext servletContext) throws Exception {

		imprimirListaDeAgendamento(listaDeAgendamento);

		List listaDeComprovante = new ArrayList<Object>();
		for (CaravanaAgendamentoModel caravanaAgendamentoModel : listaDeAgendamento) {
			CaravanaComprovanteModel caravanaComprovanteModel = ConverteAgendamentoComprovanteModel.converte(caravanaAgendamentoModel);
			listaDeComprovante.add(caravanaComprovanteModel);
		}

		JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(listaDeComprovante);

		File currDir = new File("");
		String path = currDir.getAbsolutePath()  + "\\war";;
		System.out.println(path);
		System.out.println(currDir.getPath());

		if (servletContext != null) {
			path = servletContext.getRealPath("");
		}
		

		
		File report = new File(path + "/relatorios/caravana/comprovante.jasper");

		InputStream inputStream = new FileInputStream(report);
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, null, jrds);
		jasperPrint.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
		jasperPrint.setProperty("net.sf.jasperreports.default.font.name", "SansSerif");

		String nomeArquivo = UUID.randomUUID().toString();
		nomeArquivo = nomeArquivo + ".pdf";

		JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/relatorios/caravana/print/" + nomeArquivo);

		return nomeArquivo;

	}

	@Override
	public void excluir(CaravanaAgendamentoModel model) throws Exception {

		if (model.isJaImprimiu()) {
			throw new Exception("Não é possivel excluir, o comprovante do participante já foi impresso!");
		} else {
			new CaravanaParticipante().excluir(model.getParticipante());
			super.excluir(model);
		}
	}

	@Override
	public CaravanaAgendamentoModel salvar(CaravanaAgendamentoModel vo) throws Exception {

		Exception exc = validar(vo);

		if (exc == null) {

			try {
				vo.setDataHoraAgendamento(new Date());
				vo.getParticipante().setPreCadastrado(true);
				CaravanaParticipanteModel caravanaParticipanteModel = new CaravanaParticipante().salvar(vo.getParticipante());
				vo.setParticipante(caravanaParticipanteModel);
				dao.salvar(vo);
				return vo;
			} catch (Exception e) {
				throw (Exception) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	@Override
	public void alterar(CaravanaAgendamentoModel model) throws Exception {
		if (model.isJaImprimiu()) {
			throw new Exception("Não é possivel EDITAR, o comprovante do participante já foi impresso!");
		} else {

			Exception exc = validar(model);
			if (exc == null) {
				try {
					CaravanaParticipanteModel caravanaParticipanteModel = new CaravanaParticipante().merge(model.getParticipante());

					model.setParticipante(caravanaParticipanteModel);

					dao.alterar(model);
				} catch (SQLException e) {
					throw (Exception) new Exception("Ocorreu um erro ao tentar alterar:\n" + e.getMessage());
				}
			} else {
				throw exc;
			}
		}
	}

	public CaravanaAgendamentoModel obterAgendamentoPorInfoParticipanteEevento(Long idEvento, String cpf, String cartaoSus, Date dataDeNascimento) throws Exception {
		CaravanaEventoModel eventoModel = new CaravanaEvento().obterPorId(CaravanaEventoModel.class, idEvento);
		ArrayList<CaravanaAgendamentoModel> lista = dao.obterAgendamentoPorEventoEinfoParticipante(eventoModel, cpf, cartaoSus, dataDeNascimento);
		if (lista != null && lista.size() > 0) {
			CaravanaAgendamentoModel caravanaAgendamentoModel = lista.get(0);
			return caravanaAgendamentoModel;
		} else {
			/*
			 * Rotina validação cartão SUS obtida no site:
			 * http://cartaonet.datasus.gov.br/Rotina_Java.doc
			 */
			if (CartaoSus.validaCartaoSus(cartaoSus) == false) {

				throw new Exception("Erro ao validar o cartao sus!");
			} else {
				CaravanaAgendamentoModel caravanaAgendamentoModel = new CaravanaAgendamentoModel();
				return caravanaAgendamentoModel;
			}
		}

	}

	@Override
	public CaravanaAgendamentoModel merge(CaravanaAgendamentoModel vo) throws Exception {
		if (vo.isJaImprimiu()) {
			throw new Exception("Não é possivel EDITAR, o comprovante do participante já foi impresso!");
		} else {
			Exception exc = validar(vo);
			if (exc == null) {
				try {
					CaravanaParticipanteModel caravanaParticipanteModel = new CaravanaParticipante().merge(vo.getParticipante());

					vo.setParticipante(caravanaParticipanteModel);
					dao.alterar(vo);
					return vo;
				} catch (SQLException e) {
					throw (Exception) new Exception("Ocorreu um erro ao tentar alterar:\n" + e.getMessage());
				}
			} else {
				throw exc;
			}
		}
	}

	public ArrayList<CaravanaAgendamentoModel> obterTodosPorEventoPermissaoAgendamento(EventoPermissaoAgendamentoModel eventoPermissaoAgendamentoModel) throws Exception {
		ArrayList<CaravanaAgendamentoModel> lista = dao.obterTodosPorEventoPermissaoAgendamento(eventoPermissaoAgendamentoModel);
		return lista;
	}

	public ArrayList<CaravanaAgendamentoModel> obterTodosPorEventoEparticipante(CaravanaEventoModel eventoModel, CaravanaParticipanteModel participanteModel) throws Exception {
		ArrayList<CaravanaAgendamentoModel> lista = dao.obterTodosPorEventoEparticipante(eventoModel, participanteModel);
		return lista;
	}

	public int obterQuantidadeDeInscritoPorPermissaoAgendamento(EventoPermissaoAgendamentoModel eventoPermissaoAgendamento) throws Exception {
		int quantidadeDeAgendamento = dao.obterQuantidadeDeInscritoPorPermissaoAgendamento(eventoPermissaoAgendamento);
		return quantidadeDeAgendamento;
	}

	public ArrayList<CaravanaAgendamentoModel> obterParticipantePorCartaoSusEevento(String cartaoSus, CaravanaEventoModel eventoModel) throws Exception {
		ArrayList<CaravanaAgendamentoModel> lista = dao.obterParticipantePorCartaoSusEevento(cartaoSus, eventoModel);
		return lista;
	}

	@Override
	public String getSqlFiltro(CaravanaAgendamentoModel vo) {
		String filtro = super.getSqlFiltro(vo);

		if (vo.getPermissaoAgendamento() != null) {
			filtro += " and id_permissao_agendamento=" + vo.getPermissaoAgendamento().getId();
		}
		return filtro;

	}

	@Override
	public Exception validar(CaravanaAgendamentoModel vo) {
		StringBuffer msg = new StringBuffer("");

		/*
		 * veritifcar cartao sus duplicado
		 */
		if (vo.getId() == null) {
			try {

				int qauntidadeCadastrada = obterQuantidadeDeInscritoPorPermissaoAgendamento(vo.getPermissaoAgendamento());
				EventoPermissaoAgendamentoModel eventoPermissaoAgendamentoAux = new EventoPermissaoAgendamento().obterPorId(vo.getPermissaoAgendamento());
				if (qauntidadeCadastrada >= eventoPermissaoAgendamentoAux.getQuantidadeDeVagas()) {
					msg.append("-> O agendamento " + new EventoPermissaoAgendamento().getCalcNomeCompleto(vo.getPermissaoAgendamento()) + " atingiu o limite de " + vo.getPermissaoAgendamento().getQuantidadeDeVagas() + " Vagas\n");
				} else {
					if (eventoPermissaoAgendamentoAux.isDivididoEmTurno()) {
						int metadeDasVagas = eventoPermissaoAgendamentoAux.getQuantidadeDeVagas() / 2;
						if (qauntidadeCadastrada >= metadeDasVagas) {
							vo.setSegundoTurno(true);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg.append(e.getLocalizedMessage());
			}

		}

		if (vo.getParticipante() == null) {
			msg.append("-> As informações do participante é de preenchimento obrigatório! \n");
		} else {

			if (vo.getId() == null) {
				// verticiar cartao sus ja foi agendado

				try {
					if (vo.getUsuarioAgendamento().getPerfil() != CaravanaUsuarioPerfilTypes.administrador) {
						ArrayList<CaravanaAgendamentoModel> listaDeAgendamento;
						listaDeAgendamento = obterParticipantePorCartaoSusEevento(vo.getParticipante().getCartaoSus(), vo.getPermissaoAgendamento().getEvento());
						if (listaDeAgendamento != null && listaDeAgendamento.size() > 0) {
							msg.append("-> O cartao SUS " + vo.getParticipante().getCartaoSus() + " já foi agendado em: " + new EventoPermissaoAgendamento().getCalcNomeCompleto(listaDeAgendamento.get(0).getPermissaoAgendamento()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.append(e.getLocalizedMessage());

				}

			}

			if (vo.getParticipante().getCartaoSus() == null || vo.getParticipante().getCartaoSus().length() != 15) {
				msg.append("-> O campo CARTÃO SUS está incorreto! \n");
			}

			if (vo.getParticipante().getNome() == null || vo.getParticipante().getNome().trim().length() == 0) {
				msg.append("-> O campo NOME do Participante  é de preenchimento obrigatório! \n");
			}

			if (vo.getParticipante().getEndereco() == null || vo.getParticipante().getEndereco().trim().length() == 0) {
				msg.append("-> O campo ENDEREÇO do Participante  é de preenchimento obrigatório! \n");
			}

			if (vo.getParticipante().getTelefone() == null || vo.getParticipante().getTelefone().trim().length() != 11) {

				msg.append("-> O campo TELEFONE do Participante  é de preenchimento obrigatório! \n");
				msg.append("   * Verifique se  O campo TELEFONE  está correto\n");
			} else {
				System.out.println("tamanho tel " + vo.getParticipante().getTelefone().length());
			}

			if (vo.getParticipante().getSexo() == null) {
				msg.append("-> O campo SEXO do Participante  é de preenchimento obrigatório! \n");
			}

			if (vo.getParticipante().getCidade() == null) {
				msg.append("-> O campo CIDADE do Participante  é de preenchimento obrigatório! \n");
			}

			if (vo.getParticipante().getDataDeNascimento() == null) {
				msg.append("-> O campo Data de Nascimento  do Participante  é de preenchimento obrigatório! \n");
			} else {

				vo.setSituacao(CaravanaSituacaoAgendamentoTypes.aprovado);
				vo.setMsgSituacaoEmanalise(null);
				if (vo.getUsuarioAgendamento() != null && vo.getUsuarioAgendamento().getPerfil() != CaravanaUsuarioPerfilTypes.administrador) {

					/*
					 * Regra de Negocio para cadastro de acordo com portaria
					 */

					Date dataDeNascimento = vo.getParticipante().getDataDeNascimento();
					if (getIdade(dataDeNascimento) < 55) {
						vo.setSituacao(CaravanaSituacaoAgendamentoTypes.em_analise);
						vo.setMsgSituacaoEmanalise("-> O  Participante deve possuir mais de 55 anos de idade para encaminhamento de OFTALMOLOGIA! \n");
					}

					// if (vo.getEncaminhamento() ==
					// CaravanaEncaminhamentoTypes.pterigio &&
					// getIdade(dataDeNascimento) < 55) {
					// vo.setSituacao(CaravanaSituacaoAgendamentoTypes.em_analise);
					// vo.setMsgSituacaoEmanalise("-> O Participante deve
					// possuir mais de 55 anos de idade para encaminhamento de
					// PTERIGIO! \n");
					// }
					// if (vo.getEncaminhamento() ==
					// CaravanaEncaminhamentoTypes.yag_laser &&
					// getIdade(dataDeNascimento) < 55) {
					// vo.setSituacao(CaravanaSituacaoAgendamentoTypes.em_analise);
					// vo.setMsgSituacaoEmanalise("-> O Participante deve
					// possuir mais de 55 anos de idade para encaminhamento de
					// YAG LASER! \n");
					// }
					//
					// if (vo.getEncaminhamento() ==
					// CaravanaEncaminhamentoTypes.consulta &&
					// getIdade(dataDeNascimento) < 55) {
					// vo.setSituacao(CaravanaSituacaoAgendamentoTypes.em_analise);
					// vo.setMsgSituacaoEmanalise("-> O Participante deve
					// possuir mais de 55 anos de idade para encaminhamento de
					// CONSULTA! \n");
					// }

				}

			}
		}

		if (msg.length() > 0)
			return new Exception(msg.toString());
		else

			return null;

	}

	public static int getIdade(java.util.Date dataNasc) {

		Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(dataNasc);

		// Cria um objeto calendar com a data atual

		Calendar today = Calendar.getInstance();

		// Obtém a idade baseado no ano

		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

		dateOfBirth.add(Calendar.YEAR, age);

		// se a data de hoje é antes da data de Nascimento, então diminui 1(um)

		if (today.before(dateOfBirth)) {

			age--;

		}

		return age;

	}
}
