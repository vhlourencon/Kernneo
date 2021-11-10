package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.EstoqueLancamentoModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.server.ConnectFactory;

public class EstoqueLancamentoDAO extends GenericDAO<EstoqueLancamentoModel> {

    public ArrayList<EstoqueLancamentoModel> obterTodosPorEstoque(EstoqueModel estoqueModel) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.EstoqueLancamentoModel p where p.estoque=:estoque and deletado=:deletado";
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setEntity("estoque", estoqueModel);
	select.setBoolean("deletado", false);
	ArrayList<EstoqueLancamentoModel> lista = (ArrayList<EstoqueLancamentoModel>) select.list();

	return lista;
    }
    
    public ArrayList<EstoqueLancamentoModel> obterUltimos10(EstoqueModel estoqueModel) throws SQLException {
   	String sql = "select p FROM br.com.kernneo.client.model.EstoqueLancamentoModel p where p.estoque=:estoque and deletado=:deletado order by id desc";
   	Session session = ConnectFactory.getSession();
   	Query select = session.createQuery(sql);
   	select.setMaxResults(10);
   	select.setEntity("estoque", estoqueModel);
   	select.setBoolean("deletado", false);
   	ArrayList<EstoqueLancamentoModel> lista = (ArrayList<EstoqueLancamentoModel>) select.list();

   	return lista;
       }
    
    
    public ArrayList<EstoqueLancamentoModel> obterTodosPorItem(ItemModel item) throws SQLException {
	String sql = "select p FROM br.com.kernneo.client.model.EstoqueLancamentoModel p where p.item=:item and deletado=:deletado";
	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setEntity("item", item);
	select.setBoolean("deletado", false);
	ArrayList<EstoqueLancamentoModel> lista = (ArrayList<EstoqueLancamentoModel>) select.list();

	return lista;
    }

}
