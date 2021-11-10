package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.ConnectFactory;

public class ItemDAO extends GenericDAO<ItemModel> {

    public ArrayList<ItemModel> obterTodosPorUsuarioEdata(UsuarioModel usuarioModel, Date dataInicial, Date dataFinal) throws SQLException{
	Session session = ConnectFactory.getSession();

	String sql = "select g FROM " + ItemModel.class.getCanonicalName() + " g WHERE g.mesa.deletado=:deletado and deletado=:deletado and g.dataHora between :dataIni and :dataFim ";
	if (usuarioModel != null) {
	    sql += " and g.usuario=:usuario";
	}

	org.hibernate.Query select = session.createQuery(sql);
	select.setBoolean("deletado", false);
	select.setTimestamp("dataIni", dataInicial);
	select.setTimestamp("dataFim", dataFinal);
	if (usuarioModel != null) {
	    select.setEntity("usuario", usuarioModel);
	}
	ArrayList<ItemModel> lista = (ArrayList<ItemModel>) select.list();

	return lista;
    }
    
    
  
    public ArrayList<ItemModel> obterTodosPorMesa(MesaModel mesa) throws SQLException {

	String sql = "select m FROM br.com.kernneo.client.model.ItemModel m where m.mesa = :mesa and m.itemPai is null and deletado=:deletado";
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setEntity("mesa", mesa);
	select.setBoolean("deletado", false);
	ArrayList<ItemModel> lista = (ArrayList<ItemModel>) select.list();

	return lista;
    }

    public ArrayList<ItemModel> obterTodosPorItemPai(ItemModel item) throws SQLException {

	String sql = "select m FROM br.com.kernneo.client.model.ItemModel m where m.itemPai = :itemPai  and deletado=:deletado";
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setEntity("itemPai", item);
	select.setBoolean("deletado", false);


	ArrayList<ItemModel> lista = (ArrayList<ItemModel>) select.list();

	return lista;
    }
    
    
 
    public void excluirTodosDoMesmoItemPai(ItemModel item) throws SQLException {
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery("delete br.com.kernneo.client.model.ItemModel  where itemPai = :itemPai ");
	select.setEntity("itemPai", item);
	select.executeUpdate();

    }

}
