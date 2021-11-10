package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.exception.OpcaoAlternativaException;
import br.com.kernneo.client.exception.OpcaoCategoriaException;
import br.com.kernneo.client.exception.OpcaoException;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.OpcaoCategoriaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.CategoriaDAO;

public class Categoria extends Negocio<CategoriaModel, CategoriaDAO, CategoriaException> {

	public Categoria() {
		super();
		dao = new CategoriaDAO();
	}

	// public GenericPagina<CategoriaModel>
	// buscarPaginaCompletaPosterior(GenericPagina<CategoriaModel> paginaAtual,
	// CategoriaModel filtroModel) throws CategoriaException {
	// GenericPagina<CategoriaModel> pagina = obterPaginaPosterior(paginaAtual,
	// filtroModel);
	// for (CategoriaModel model : pagina.getListaRegistros()) {
	// model = obterModelAtualizadoComDetalhes(model);
	// }
	// return pagina;
	// }
	//
	// public GenericPagina<CategoriaModel>
	// buscarPaginaCompletaAnterior(GenericPagina<CategoriaModel> paginaAtual,
	// CategoriaModel filtroModel) throws CategoriaException {
	// GenericPagina<CategoriaModel> pagina = obterPaginaAnterior(paginaAtual,
	// filtroModel);
	// for (CategoriaModel model : pagina.getListaRegistros()) {
	// model = obterModelAtualizadoComDetalhes(model);
	// }
	// return pagina;
	// }
	public ArrayList<CategoriaModel> obterTodosCompletosParaPDV() throws CategoriaException {
		try {
			ArrayList<CategoriaModel> listaDeCategoria = dao.obterTodasAtivas();
			if (listaDeCategoria != null) {
				for (CategoriaModel categoriaModel : listaDeCategoria) {
					categoriaModel = obterModelCompletoParaPDV(categoriaModel);
				}
			}

			return listaDeCategoria;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CategoriaException(e.getLocalizedMessage());
		}
	}

	public CategoriaModel obterModelCompletoParaPDV(CategoriaModel categoriaModel) throws CategoriaException {

		try {
			categoriaModel.setListaDeOpcaoDaCategoria(new OpcaoCategoria().obterTodosPorCategoria(categoriaModel));
			categoriaModel.setListaDeProdutos(new Produto().obterProdutosPorCatetoriaParaPDV(categoriaModel));
			
		
			return categoriaModel;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CategoriaException(e.getLocalizedMessage());
		}
	}

	public CategoriaModel obterModelAtualizadoComDetalhes(CategoriaModel model) throws CategoriaException {

		try {
			model = obterPorId(model);
			model.setListaDeOpcaoDaCategoria(new OpcaoCategoria().obterTodosPorCategoria(model));

			return model;
		} catch (OpcaoCategoriaException e) {

			e.printStackTrace();
			throw new CategoriaException(e.getLocalizedMessage());
		}

	}

	@Override
	public CategoriaModel salvar(CategoriaModel vo) throws CategoriaException {

		CategoriaException exc = validar(vo);
		if (exc == null) {
			try {

				ArrayList<OpcaoCategoriaModel> listaDeOpcoesDaCategoria = new ArrayList<OpcaoCategoriaModel>();

				for (OpcaoCategoriaModel opcaoCategoriaModel : vo.getListaDeOpcaoDaCategoria()) {
					listaDeOpcoesDaCategoria.add(opcaoCategoriaModel);
				}

				vo = dao.salvar(vo);

				for (OpcaoCategoriaModel opcaoCategoriaModel : listaDeOpcoesDaCategoria) {
					opcaoCategoriaModel.setCategoria(vo);
					opcaoCategoriaModel = new OpcaoCategoria().salvar(opcaoCategoriaModel);

				}

				vo = obterModelAtualizadoComDetalhes(vo);
				return vo;
			} catch (Exception e) {
				throw (CategoriaException) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}

	}

	@Override
	public void alterar(CategoriaModel vo) throws CategoriaException {

		CategoriaException exc = validar(vo);
		if (exc == null) {
			try {

				ArrayList<OpcaoCategoriaModel> listaDeOpcoesDaCategoria = new ArrayList<OpcaoCategoriaModel>();

				for (OpcaoCategoriaModel opcaoCategoriaModel : vo.getListaDeOpcaoDaCategoria()) {
					listaDeOpcoesDaCategoria.add(opcaoCategoriaModel);
				}

				dao.alterar(vo);

				for (OpcaoCategoriaModel opcaoCategoriaModel : listaDeOpcoesDaCategoria) {
					opcaoCategoriaModel.setCategoria(vo);
					opcaoCategoriaModel = new OpcaoCategoria().merge(opcaoCategoriaModel);

				}

				vo = obterModelAtualizadoComDetalhes(vo);

			} catch (Exception e) {
				throw (CategoriaException) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}

	}

	@Override
	public CategoriaException validar(CategoriaModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getCategoria_nome_portugues() == null || vo.getCategoria_nome_portugues().trim().length() == 0) {
			msg.append("O campo Nome Português  é de preenchimento obrigatório!");
		}

		if (msg.length() > 0)
			return new CategoriaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(CategoriaModel vo) {
		String filtro = super.getSqlFiltro(vo);
		if (vo.getCategoria_nome_portugues() != null && vo.getCategoria_nome_portugues().trim().length() > 0) {
			filtro += " and categoria_nome_portugues like('%" + vo.getCategoria_nome_portugues() + "%')";
		}
		filtro += " order by id asc";

		return filtro;
	}

}
