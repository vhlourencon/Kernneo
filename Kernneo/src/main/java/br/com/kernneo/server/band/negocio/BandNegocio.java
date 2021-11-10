package br.com.kernneo.server.band.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.GenericDAO;

public abstract class BandNegocio<GENERICMODEL extends GenericModel, DAOG extends GenericDAO<GENERICMODEL>> {

	protected DAOG dao;

	public GENERICMODEL salvar(GENERICMODEL vo) throws Exception {
		Exception exc = validar(vo);

		if (exc == null) {

			try {
				dao.salvar(vo);
				return vo;
			} catch (Exception e) {
				throw (Exception) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	public GENERICMODEL merge(GENERICMODEL vo) throws Exception {
		Exception exc = validar(vo);
		if (exc == null) {
			try {
				dao.merge(vo);
				return vo;
			} catch (Exception e) {
				throw (Exception) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	public void alterar(GENERICMODEL model) throws Exception {
		Exception exc = validar(model);
		if (exc == null) {
			try {
				dao.alterar(model);
			} catch (SQLException e) {
				throw (Exception) new Exception("Ocorreu um erro ao tentar alterar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}
	
	public GENERICMODEL saveOrUpdate(GENERICMODEL model) throws Exception {
		Exception exc = validar(model);
		if (exc == null) {
			try {
				model = dao.saveOrUpdate(model);
				return model;
			} catch (SQLException e) {
				throw (Exception) new Exception("Ocorreu um erro ao tentar alterar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}


	public void excluir(GENERICMODEL model) throws Exception {
		try {
			dao.excluir(model);
		} catch (SQLException e) {
			throw (Exception) new Exception("Ocorreu um erro ao tentar excluir:\n" + e.getMessage());
		}
	}

	public void setarDeletado(GENERICMODEL model) throws Exception {
		try {
			model.setDeletado(true);
			dao.setarDeletado(model);
		} catch (SQLException e) {
			throw (Exception) new Exception("Ocorreu um erro ao tentar excluir:\n" + e.getMessage());
		}
	}

	public GENERICMODEL obterPorId(GENERICMODEL model) throws Exception {

		if (model.getId() != null) {
			try {
				model = dao.obterPorId(model);
				return model;
			} catch (Exception e) {
				throw (Exception) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
			}

		} else {
			throw ((Exception) new Exception("O id não pode ser nulo"));
		}

	}

	public GENERICMODEL obterPorId(Class classe, Long id) throws Exception {

		try {

			return dao.obterPorId(classe, id);
		} catch (Exception e) {
			throw (Exception) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
		}

	}

	public Long obterUltimoId(Class classe) throws Exception {

		try {

			return dao.obterUltimoID(classe);
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
		}

	}

	public ArrayList<GENERICMODEL> obterTodos(Class classe) throws Exception {

		try {
			ArrayList<GENERICMODEL> lista = (ArrayList<GENERICMODEL>) dao.obterTodos(classe);
			return lista;
		} catch (SQLException e) {
			throw (Exception) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());

		}

	}

	public ArrayList<GENERICMODEL> obterTodosComFiltro(GENERICMODEL filtroModel) throws Exception {

		try {
			ArrayList<GENERICMODEL> lista = (ArrayList<GENERICMODEL>) dao.obterTodosComFiltro(getSqlFiltro(filtroModel));
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public GenericPagina<GENERICMODEL> obterPaginaPosterior(GenericPagina<GENERICMODEL> pagina, GENERICMODEL filtroModel) throws Exception {

		try {
			return dao.obterPaginaPosterior(pagina, getSqlFiltro(filtroModel));
		} catch (Exception e) {
			e.printStackTrace();
			throw (Exception) new Exception("Ocorreu um erro ao tentar obter os dados da pagina :\n" + e.getMessage());
		}

	}

	public GenericPagina<GENERICMODEL> obterPaginaAnterior(GenericPagina<GENERICMODEL> pagina, GENERICMODEL filtroModel) throws Exception {

		try {
			return dao.obterPaginaAnterior(pagina, getSqlFiltro(filtroModel));
		} catch (Exception e) {

			e.printStackTrace();
			throw (Exception) new Exception("Ocorreu um erro ao tentar obter os dados da pagina :\n" + e.getMessage());
		}

	}

	public abstract Exception validar(GENERICMODEL vo);

	public String getGenericFiltro(GENERICMODEL vo) {

		String filtroGenerico = "FROM " + vo.getClass().getCanonicalName() + " g ";
		filtroGenerico += " where deletado=0 ";

		return filtroGenerico;

	}

	public String getSqlFiltro(GENERICMODEL vo) {
		String filtro = getGenericFiltro(vo);

		return filtro;
	}
}