package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;

import com.google.gwt.user.client.Window;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.EnderecoClienteModel;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.server.ConnectFactory;

public class EnderecoClienteDAO extends GenericDAO<EnderecoClienteModel> {

   

 

    public ArrayList<EnderecoClienteModel> obterEnderecosPorCliente(ClienteModel clienteModel) {
	Session session = ConnectFactory.getSession(); 
	
	org.hibernate.Query select = session.createQuery("select g FROM " + EnderecoClienteModel.class.getCanonicalName() + " g WHERE cliente= :cliente and deletado=:deletado");
	select.setEntity("cliente", clienteModel);
	select.setBoolean("deletado",false);
	ArrayList<EnderecoClienteModel> lista = (ArrayList<EnderecoClienteModel>) select.list();

	
	return lista;
    }
    
}
