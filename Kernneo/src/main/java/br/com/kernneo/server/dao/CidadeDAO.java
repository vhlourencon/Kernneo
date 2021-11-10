package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.ConnectFactory;

public class CidadeDAO extends GenericDAO<CidadeModel> {

    public CidadeModel obterPorCodigoIbge(Integer codigoIbge) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.CidadeModel p where p.codigoIbge=:codigoIbge and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setLong("codigoIbge", codigoIbge);
	select.setBoolean("deletado", false);
	CidadeModel model = (CidadeModel) select.uniqueResult();

	return model;
    }

    public ArrayList<CidadeModel> obterTodasPorEstado(EstadoModel estadoModel) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.CidadeModel p where p.estado=:estado and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setEntity("estado", estadoModel);
	select.setBoolean("deletado", false);

	ArrayList<CidadeModel> lista = (ArrayList<CidadeModel>) select.list();

	return lista;
    }

}
