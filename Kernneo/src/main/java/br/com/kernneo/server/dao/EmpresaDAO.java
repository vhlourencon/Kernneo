package br.com.kernneo.server.dao;

import org.hibernate.Session;

import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.server.ConnectFactory;

public class EmpresaDAO extends GenericDAO<EmpresaModel> {

    public EmpresaModel obterPorId(String id) {
	Session session = ConnectFactory.getSession();

	org.hibernate.Query select = session.createQuery("FROM br.com.kernneo.client.model.EmpresaModel WHERE id=:id and deletado=:deletado");
	select.setString("id", id);
	select.setBoolean("deletado", false);

	EmpresaModel model = (EmpresaModel) select.uniqueResult();
	return model;

    }
}
