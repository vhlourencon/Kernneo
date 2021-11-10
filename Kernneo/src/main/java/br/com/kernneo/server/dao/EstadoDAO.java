package br.com.kernneo.server.dao;

import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.server.ConnectFactory;

public class EstadoDAO extends GenericDAO<EstadoModel> {

    public EstadoModel obterPorSigla(String sigla) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.EstadoModel p where p.sigla=:sigla and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setString("sigla", sigla);
	select.setBoolean("deletado", false);
	EstadoModel model = (EstadoModel) select.uniqueResult();

	return model;
    }

    public EstadoModel obterPorCodigoIbge(Integer codigoIbge) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.EstadoModel p where p.codigoIbge=:codigoIbge and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setBoolean("deletado", false);
	select.setLong("codigoIbge", codigoIbge);
	EstadoModel model = (EstadoModel) select.uniqueResult();

	return model;
    }

}
