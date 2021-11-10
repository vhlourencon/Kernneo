package br.com.kernneo.server.caravana.usuario;

import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaUsuarioModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;



public class CaravanaUsuarioDAO extends GenericDAO<CaravanaUsuarioModel> {
	
	

	public CaravanaUsuarioModel obterPorLoginEsenha(String login, String senha) throws SQLException {
	Session session = ConnectFactory.getSession();

	String sql = "from br.com.kernneo.client.caravana.model.CaravanaUsuarioModel where login=:login and senha=:senha and ativo=:ativo and  deletado=:deletado";

	Query select = session.createQuery(sql);
	select.setString("login", login);
	select.setString("senha", senha);
	select.setBoolean("deletado", false);
	select.setBoolean("ativo", true);
	
	System.out.println(login + " " + senha);
	
	
	System.out.println(select.getQueryString());

	CaravanaUsuarioModel usuarioModel = (CaravanaUsuarioModel) select.uniqueResult();


	return usuarioModel;

    }

}
