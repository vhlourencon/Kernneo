package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.EnderecoFuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.server.ConnectFactory;

public class FuncionarioDAO extends GenericDAO<FuncionarioModel> {

	public FuncionarioModel obterPorLogin(String login) {

		Session session = ConnectFactory.getSession();
		Query<FuncionarioModel> select = session.createQuery("select g FROM " + FuncionarioModel.class.getCanonicalName() + " g WHERE g.login = :login and deletado=0");
		select.setParameter("login", login);

		FuncionarioModel model = select.uniqueResult();

		return model;

	}

	public FuncionarioModel salvarComEndereco(FuncionarioModel vo) throws SQLException {
		EnderecoFuncionarioModel enderecoFuncionarioModel = new EnderecoFuncionarioModel();
		enderecoFuncionarioModel = vo.getEnderecoFuncionario();
		enderecoFuncionarioModel.setFuncionario(vo);

		Session session = ConnectFactory.getSession();
		session.save(vo);

		session.save(enderecoFuncionarioModel);

		return vo;
	}

	public ArrayList<FuncionarioModel> obterTodosComEnderecoComFiltro(String filtro) throws SQLException {

		Session session = ConnectFactory.getSession();
		org.hibernate.Query<FuncionarioModel> select = session.createQuery(filtro);
		ArrayList<FuncionarioModel> lista = (ArrayList<FuncionarioModel>) select.list();

		return lista;
	}
}
