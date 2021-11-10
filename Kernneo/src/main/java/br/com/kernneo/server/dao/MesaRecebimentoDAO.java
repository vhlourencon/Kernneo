package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.server.ConnectFactory;

public class MesaRecebimentoDAO extends GenericDAO<MesaRecebimentoModel> {

    public ArrayList<MesaRecebimentoModel> obterTodosPorMesa(MesaModel mesa) throws SQLException {
	ArrayList<MesaRecebimentoModel> lista = new ArrayList<MesaRecebimentoModel>();

	String sql = "select m FROM br.com.kernneo.client.model.MesaRecebimentoModel m where id_mesa = :id_mesa and deletado=:deletado";
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setLong("id_mesa", mesa.getId());
	select.setBoolean("deletado", false);
	lista = (ArrayList<MesaRecebimentoModel>) select.list();

	return lista;
    }

    public ArrayList<MesaRecebimentoModel> obterTodosPorClienteSemReceber(ClienteModel cliente) throws SQLException {
	ArrayList<MesaRecebimentoModel> lista = new ArrayList<MesaRecebimentoModel>();

	String sql = "select m FROM br.com.kernneo.client.model.MesaRecebimentoModel m where id_cliente = :id_cliente  and deletado=:deletado and id_caixa is null";
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setLong("id_cliente", cliente.getId());
	select.setBoolean("deletado", false);
	lista = (ArrayList<MesaRecebimentoModel>) select.list();

	return lista;
    }

}
