package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;

import br.com.kernneo.client.model.EnderecoModel;
import br.com.kernneo.server.ConnectFactory;

public class EnderecoDAO extends GenericDAO<EnderecoModel> {

    public ArrayList<EnderecoModel> obterListaPorId(Class classe, Long id) throws SQLException {

	Session session = ConnectFactory.getSession();

	org.hibernate.Query select = session.createQuery("select g FROM " + classe.getCanonicalName() + " g WHERE g.id = :id and deletado=:deletado ");
	select.setLong("id", id);
	select.setBoolean("deletado", false);

	ArrayList<EnderecoModel> lista = (ArrayList<EnderecoModel>) select.list();

	return lista;
    }
}
