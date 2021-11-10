package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;

import com.google.gwt.user.client.Window;

import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.EnderecoFuncionarioModel;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.server.ConnectFactory;

public class EnderecoFuncionarioDAO extends GenericDAO<EnderecoFuncionarioModel> {

    public ArrayList<EnderecoFuncionarioModel> obterListaPorId(Class classe, Long id) throws SQLException {

	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery("select g FROM " + classe.getCanonicalName() + " g WHERE g.id = :id ");
	select.setLong("id", id);
	ArrayList<EnderecoFuncionarioModel> lista = (ArrayList<EnderecoFuncionarioModel>) select.list();

	return lista;
    }

    public ArrayList<GenericModel> obterTodosComEndereco(Class c) throws SQLException {

	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery(" FROM " + c.getCanonicalName() + " e inner join e.funcionario ");
	ArrayList<GenericModel> lista = (ArrayList<GenericModel>) select.list();

	return lista;
    }

    public EnderecoFuncionarioModel obterEnderecosPorFuncionario(FuncionarioModel funcionarioModel) {
	Session session = ConnectFactory.getSession();
	org.hibernate.Query select = session.createQuery("select g FROM " + EnderecoFuncionarioModel.class.getCanonicalName() + " g WHERE id_funcionario= :id_funcionario");
	select.setLong("id_funcionario", funcionarioModel.getId());
	EnderecoFuncionarioModel model = (EnderecoFuncionarioModel) select.uniqueResult();

	return model;
    }
}
