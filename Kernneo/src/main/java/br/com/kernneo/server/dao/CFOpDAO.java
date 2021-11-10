package br.com.kernneo.server.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CFOPModel;
import br.com.kernneo.server.ConnectFactory;

public class CFOpDAO extends GenericDAO<CFOPModel> {

    public CFOPModel obterPorCFOP(String cfop) {
	String sql = "select u FROM br.com.kernneo.client.model.CFOPModel u where u.cfop=:cfop and deletado=:deletado";
	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setString("cfop", cfop);
	select.setBoolean("deletado", false);
	CFOPModel cfopModel = (CFOPModel) select.uniqueResult();
	
	return cfopModel;
    }
}
