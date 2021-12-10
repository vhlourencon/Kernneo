package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.PaginacaoUtils;

public abstract class GenericDAO<GENERICMODEL extends GenericModel> {

	public GENERICMODEL salvar(GENERICMODEL vo) throws SQLException {
		Session session = ConnectFactory.getSession();
		session.save(vo);
		return vo;
	}

	public void alterar(GENERICMODEL model) throws SQLException {
		Session session = ConnectFactory.getSession();
		session.merge(model);

	}

	public void excluir(GENERICMODEL model) throws SQLException {
		model.setDeletado(true);
		alterar(model);
	}

	public void setarDeletado(GENERICMODEL model) throws SQLException {
		Session session = ConnectFactory.getSession();
		session.update(model);
	}

	public GENERICMODEL merge(GENERICMODEL vo) throws SQLException {
		Session session = ConnectFactory.getSession();
		session.merge(vo);

		return vo;
	}

	public GENERICMODEL saveOrUpdate(GENERICMODEL vo) throws SQLException {
		Session session = ConnectFactory.getSession();
		session.saveOrUpdate(vo);

		return vo;
	}

	public GENERICMODEL obterPorId(GENERICMODEL model) throws SQLException {

		Session session = ConnectFactory.getSession();
		org.hibernate.Query select = session
				.createQuery("select g FROM " + model.getClass().getCanonicalName() + " g WHERE g.id = :id ");
		select.setLong("id", model.getId());

		model = (GENERICMODEL) select.uniqueResult();

		return (GENERICMODEL) model;
	}

	public GENERICMODEL obterPorId(Class classe, Long id) throws SQLException {

		Session session = ConnectFactory.getSession();
		org.hibernate.Query select = session
				.createQuery("select g FROM " + classe.getCanonicalName() + " g WHERE g.id = :id ");
		select.setLong("id", id);

		GENERICMODEL model = (GENERICMODEL) select.uniqueResult();

		return model;
	}

	public Long obterUltimoID(Class classe) throws SQLException {

		Session session = ConnectFactory.getSession();

		org.hibernate.Query select = session.createQuery("SELECT max(id) from " + classe.getCanonicalName());
		Long maxId = (Long) select.uniqueResult();
		if (maxId == null) {
			maxId = 0L;
		}
		return maxId;

	}

	public ArrayList<GenericModel> obterTodos(Class c) throws SQLException {

		Session session = ConnectFactory.getSession();
		Query select = session.createQuery(" FROM " + c.getCanonicalName() + " g WHERE g.deletado = :deletado");
		select.setParameter("deletado", false);
		ArrayList<GenericModel> lista = (ArrayList<GenericModel>) select.getResultList();

		return lista;
	}

	public ArrayList<GenericModel> obterTodosComFiltro(String filtro) throws SQLException {

		Session session = ConnectFactory.getSession();
		org.hibernate.Query select = session.createQuery(filtro);
		ArrayList<GenericModel> lista = (ArrayList<GenericModel>) select.list();

		return lista;
	}

	public GenericPagina<GENERICMODEL> obterPaginaPosterior(GenericPagina<GENERICMODEL> pagina, String filtroSql)
			throws Exception {

		return new PaginacaoUtils().getPaginaPosterior(filtroSql, pagina);
	}

	public GenericPagina<GENERICMODEL> obterPaginaAnterior(GenericPagina<GENERICMODEL> pagina, String filtroSql)
			throws Exception {
		return new PaginacaoUtils().getPaginaAnterior(filtroSql, pagina);
	}

}
