package br.com.kernneo.server.negocio;

import java.util.ArrayList;

import br.com.kernneo.client.exception.MesaException;
import br.com.kernneo.client.exception.OpcaoAlternativaException;
import br.com.kernneo.client.exception.OpcaoException;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.OpcaoDAO;

public class Opcao extends Negocio<OpcaoModel, OpcaoDAO, OpcaoException> {

	public Opcao() {
		super();
		dao = new OpcaoDAO();
	}

	public ArrayList<OpcaoModel> obterTodosCompletos() throws OpcaoException {
		ArrayList<OpcaoModel> lista = obterTodos(OpcaoModel.class);

		if (lista != null) {
			for (OpcaoModel opcaoModel : lista) {
				opcaoModel = obterModelAtualizadoComDetalhes(opcaoModel);
			}
		}
		return lista;
	}

	public GenericPagina<OpcaoModel> buscarPaginaCompletaPosterior(GenericPagina<OpcaoModel> paginaAtual, OpcaoModel filtroModel) throws OpcaoException {
		GenericPagina<OpcaoModel> pagina = obterPaginaPosterior(paginaAtual, filtroModel);
		for (OpcaoModel model : pagina.getListaRegistros()) {
			model = obterModelAtualizadoComDetalhes(model);
		}
		return pagina;
	}

	public OpcaoModel obterModelAtualizadoComDetalhes(OpcaoModel model) throws OpcaoException {

		try {
			model = obterPorId(model);
			model.setListaDeAlternativa(new OpcaoAlternativa().obterTodosPorOpcao(model));
			return model;
		} catch (OpcaoAlternativaException e) {

			e.printStackTrace();
			throw new OpcaoException(e.getLocalizedMessage());
		}

	}

	public GenericPagina<OpcaoModel> buscarPaginaCompletaAnterior(GenericPagina<OpcaoModel> paginaAtual, OpcaoModel filtroModel) throws OpcaoException {
		GenericPagina<OpcaoModel> pagina = obterPaginaAnterior(paginaAtual, filtroModel);
		for (OpcaoModel model : pagina.getListaRegistros()) {
			model = obterModelAtualizadoComDetalhes(model);
		}
		return pagina;
	}

	public OpcaoModel salvarCompleto(OpcaoModel vo) throws OpcaoException {
		OpcaoException exc = validar(vo);

		if (exc == null) {

			try {

				ArrayList<OpcaoAlternativaModel> listaAux = new ArrayList<OpcaoAlternativaModel>();
				for (OpcaoAlternativaModel opAlternativaModel : vo.getListaDeAlternativa()) {
					listaAux.add(opAlternativaModel);
				}

				vo = dao.salvar(vo);

				for (OpcaoAlternativaModel opcaoAlternativaModel : listaAux) {
					opcaoAlternativaModel.setOpcao(vo);
					opcaoAlternativaModel = new OpcaoAlternativa().salvar(opcaoAlternativaModel);
				}

				vo.setListaDeAlternativa(listaAux);
				return vo;
			} catch (Exception e) {
				throw (OpcaoException) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;

		}
	}

	public void alterarCompleto(OpcaoModel vo) throws OpcaoException {
		OpcaoException exc = validar(vo);

		if (exc == null) {

			try {

				ArrayList<OpcaoAlternativaModel> listaAux = new ArrayList<OpcaoAlternativaModel>();
				for (OpcaoAlternativaModel opAlternativaModel : vo.getListaDeAlternativa()) {
					listaAux.add(opAlternativaModel);
				}

				dao.alterar(vo);
				
				vo  = obterPorId(vo);

				for (OpcaoAlternativaModel opcaoAlternativaModel : listaAux) {
					opcaoAlternativaModel.setOpcao(vo);
//					if ( opcaoAlternativaModel.getId() == null) { 
						opcaoAlternativaModel = new OpcaoAlternativa().merge(opcaoAlternativaModel);
//					} else { 
//						new OpcaoAlternativa().alterar(opcaoAlternativaModel);
//					}
					
				}

				vo.setListaDeAlternativa(listaAux);

			} catch (Exception e) {
				throw new OpcaoException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	@Override
	public OpcaoException validar(OpcaoModel vo) {
		StringBuffer msg = new StringBuffer("");
		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome  é de preenchimento obrigatório! \n");
		}

		if (vo.getListaDeAlternativa().size() == 0) {
			msg.append("A lista de alternativas deve conter pelo menos uma opcao\n");
		}

		if (msg.length() > 0)
			return new OpcaoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(OpcaoModel vo) {
		String filtro = getGenericFiltro(vo);
		// filtro += " and 1=1 ";

		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('%" + vo.getNome() + "%')";
		}

		filtro += " order by id asc";

		return filtro;
	}

}
