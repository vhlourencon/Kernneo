package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;

import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.EnderecoFuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.server.ConnectFactory;


public class FuncionarioDAO extends GenericDAO<FuncionarioModel> {

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
	org.hibernate.Query select = session.createQuery(filtro);
	ArrayList<FuncionarioModel> lista = (ArrayList<FuncionarioModel>) select.list();
	

	return lista;
    }
}
