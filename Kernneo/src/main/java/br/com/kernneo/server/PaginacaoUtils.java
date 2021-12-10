package br.com.kernneo.server;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.utils.GenericPagina;

public class PaginacaoUtils<T extends GenericModel> {

    public GenericPagina<T> getPaginaPosterior(String sql, GenericPagina<T> pagina) throws Exception {
	Session session = ConnectFactory.getSession(); 

	/**
	 * sql que faz a contagem de registros para fazer o calculo de paginas.
	 */
	String sqlCount = "select count(id) " + sql;
	
	org.hibernate.query.Query selectCount = session.createQuery(sqlCount);
	Long quantidadeRegistros = (Long) selectCount.uniqueResult();
	if (quantidadeRegistros == null) {
	    quantidadeRegistros = 0L;
	}
	
	
	/**
	 * sql que tras a lista de registros
	 */
	org.hibernate.query.Query select = session.createQuery(sql);

	int iniPagina = pagina.getPaginaAtual();
	iniPagina = iniPagina * pagina.getQtdeRegistro();
	
	select.setFirstResult(iniPagina);
	select.setMaxResults(pagina.getQtdeRegistro());

	List lista = select.list();

	int nLinhasPorPagina = pagina.getQtdeRegistro();
	int nPaginas = (int) ((quantidadeRegistros + (nLinhasPorPagina - 1)) / nLinhasPorPagina);
	int resto = (int) (quantidadeRegistros % nLinhasPorPagina);
	
	if (pagina.getPaginaAtual() > nPaginas ) {
	    pagina.setPaginaAtual(nPaginas - 1);

	    iniPagina = pagina.getPaginaAtual();
	    iniPagina = iniPagina * pagina.getQtdeRegistro();
	    
	  
	    select.setFirstResult(iniPagina);
	    select.setMaxResults(pagina.getQtdeRegistro());

	    lista = select.list();

	    nLinhasPorPagina = pagina.getQtdeRegistro();
	    nPaginas = (int) ((quantidadeRegistros + (nLinhasPorPagina - 1)) / nLinhasPorPagina);
	    resto = (int) (quantidadeRegistros % nLinhasPorPagina);
	}
	// atualiza a pagina
	pagina.setListaRegistros(lista);
	pagina.setPaginaAtual(pagina.getPaginaAtual() + 1);
	pagina.setPrimeiraPagina(1);
	pagina.setUltimaPagina(nPaginas);
	pagina.setQtdePaginas(nPaginas);
	pagina.setResto(resto);

	
	return pagina;

    }

    public GenericPagina<T> getPaginaAnterior(String sql, GenericPagina<T> pagina) throws Exception {
	Session session = ConnectFactory.getSession(); 


	/**
	 * sql que faz a contagem de registros para fazer o calculo de paginas.
	 */
	String sqlCount = "select count(id) " + sql;

	
	Query selectCount = session.createQuery(sqlCount);
	Long quantidadeRegistros = (Long) selectCount.uniqueResult();
	if (quantidadeRegistros == null) {
	    quantidadeRegistros = 0L;
	}

	/**
	 * sql que tras a lista de registros
	 */
	/**
	 * sql que tras a lista de registros
	 */
	Query select = session.createQuery(sql);

	int iniPagina = pagina.getPaginaAtual() - 2;
	iniPagina = iniPagina * pagina.getQtdeRegistro();

	select.setFirstResult(iniPagina);
	select.setMaxResults(pagina.getQtdeRegistro());

	List lista = select.list();
	int nLinhasPorPagina = pagina.getQtdeRegistro();
	int nPaginas = (int) ((quantidadeRegistros + (nLinhasPorPagina - 1)) / nLinhasPorPagina);
	int resto = (int) (quantidadeRegistros % nLinhasPorPagina);

	// atualiza a pagina
	pagina.setPrimeiraPagina(1);
	pagina.setUltimaPagina(nPaginas);
	pagina.setQtdePaginas(nPaginas);
	pagina.setListaRegistros(lista);
	pagina.setPrimeiraPagina(1);
	pagina.setPaginaAtual(pagina.getPaginaAtual() - 1);
	pagina.setResto(resto);

	
	return pagina;

    }

}
