package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.ConnectFactory;

public class UsuarioDAO extends GenericDAO<UsuarioModel> {

    public Long obterUltimoCodigo() throws SQLException {

	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery("SELECT max(codigo) from br.com.kernneo.client.model.UsuarioModel where deletado=:deletado");
	select.setBoolean("deletado", false);

	Long maxId = (Long) select.uniqueResult();
	if (maxId == null) {
	    maxId = 0L;
	}

	return maxId;

    }

    public UsuarioModel obterPorLogin(String login) throws SQLException {
	Session session = ConnectFactory.getSession();

	String sql = "from br.com.kernneo.client.model.UsuarioModel where login=:login  and deletado=:deletado";

	Query select = session.createQuery(sql);
	select.setParameter("login", login);
	select.setBoolean("deletado", false);

	UsuarioModel usuarioModel = (UsuarioModel) select.uniqueResult();

	return usuarioModel;

    }

    public UsuarioModel obterPorLoginEsenha(String login, String senha) throws SQLException {
	Session session = ConnectFactory.getSession();

	String sql = "from br.com.kernneo.client.model.UsuarioModel where login=:login and senha=:senha and  deletado=:deletado";

	Query select = session.createQuery(sql);
	select.setParameter("login", login);
	select.setParameter("senha", senha);
	select.setBoolean("deletado", false);

	UsuarioModel usuarioModel = (UsuarioModel) select.uniqueResult();

	return usuarioModel;

    }

    public UsuarioModel obterPorCodigo(Long codigo) throws SQLException {
	Session session = ConnectFactory.getSession();

	String sql = "from br.com.kernneo.client.model.UsuarioModel where codigo=:codigo and deletado=:deletado";

	Query select = session.createQuery(sql);
	select.setParameter("codigo", codigo);
	select.setBoolean("deletado", false);

	
	UsuarioModel usuarioModel = (UsuarioModel) select.uniqueResult();

	return usuarioModel;

    }

  


}
