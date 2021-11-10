package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;

import com.google.gwt.user.client.Window;

import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.EnderecoFornecedorModel;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.server.ConnectFactory;

public class EnderecoFornecedorDAO extends GenericDAO<EnderecoFornecedorModel> {

    
    public ArrayList<EnderecoFornecedorModel> obterListaPorId(Class classe, Long id) throws SQLException {

	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery("select g FROM " + classe.getCanonicalName() + " g WHERE g.id = :id ");
	select.setLong("id", id);
	ArrayList<EnderecoFornecedorModel> lista = (ArrayList<EnderecoFornecedorModel>) select.list();

	return lista;
    }
    
    public ArrayList<GenericModel> obterTodosComEndereco(Class c) throws SQLException {

	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery(" FROM " + c.getCanonicalName() + " e inner join e.cliente ");
	ArrayList<GenericModel> lista = (ArrayList<GenericModel>) select.list();
	return lista;
    }

    public ArrayList<EnderecoFornecedorModel> obterEnderecosPorFornecedor(FornecedorModel clienteModel) {
	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery("select g FROM " + EnderecoFornecedorModel.class.getCanonicalName() + " g WHERE id_fornecedor= :id_fornecedor");
	select.setLong("id_fornecedor", clienteModel.getId());
	ArrayList<EnderecoFornecedorModel> lista = (ArrayList<EnderecoFornecedorModel>) select.list();

	return lista;
    }
}
