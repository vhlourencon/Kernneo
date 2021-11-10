package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.types.MesaStatus;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.server.ConnectFactory;

public class MesaDAO extends GenericDAO<MesaModel> {

    public MesaModel obterMesaNaoRecebidaPorNumero(Long numero) throws Exception {
	String sql = "SELECT m FROM br.com.kernneo.client.model.MesaModel m where deletado=:deletado and  (m.numero = :numero  and m.status<>:status  and m.tipo=:tipo)";

	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setString("status", MesaStatus.mesa_recebida.toString());
	select.setString("tipo", MesaTipo.mesa.toString());
	select.setBoolean("deletado", false);
	select.setLong("numero", numero);
	MesaModel model = (MesaModel) select.uniqueResult();

	return model;
    }

    public ArrayList<MesaModel> obterTodasMesasNaoRecebidas() throws SQLException {
	String sql = "SELECT m FROM br.com.kernneo.client.model.MesaModel m where deletado=:deletado and (m.status<>:status and m.tipo=:tipo) ";

	Session session = ConnectFactory.getSession();
	Query select = session.createQuery(sql);
	select.setString("tipo", MesaTipo.mesa.toString());
	select.setString("status", MesaStatus.mesa_recebida.toString());
	select.setBoolean("deletado", false);
	ArrayList<MesaModel> lista = (ArrayList<MesaModel>) select.list();

	return lista;
    }

    public MesaModel obterPedidoBalcaoPorCaixaEnumero(CaixaModel caixaModel, Long numero) throws SQLException {
	String sql = "SELECT m FROM br.com.kernneo.client.model.MesaModel m where deletado=:deletado and  (m.status<>:status and m.numero = :numero  and caixaAbertura=:caixa  and m.tipo=:tipo)";

	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setEntity("caixa", caixaModel);
	select.setString("tipo", MesaTipo.balcao.toString());
	select.setBoolean("deletado", false);
	select.setString("status", MesaStatus.balcao_cancelado.toString());
	select.setLong("numero", numero);
	MesaModel model = (MesaModel) select.uniqueResult();

	return model;
    }

    public ArrayList<MesaModel> obterTodosPedidosDeBalcaoNaoAbertosNoCaixa(CaixaModel caixa) throws SQLException {
	String sql = "SELECT m FROM br.com.kernneo.client.model.MesaModel m where deletado=:deletado and (m.status<>:status and m.status<>:status1 and m.tipo=:tipo and caixaAbertura=:caixa) ";

	Session session = ConnectFactory.getSession();

	Query select = session.createQuery(sql);
	select.setString("tipo", MesaTipo.balcao.toString());
	select.setEntity("caixa", caixa);
	select.setString("status", MesaStatus.balcao_novo.toString());
	select.setString("status1", MesaStatus.balcao_cancelado.toString());
	select.setBoolean("deletado", false);
	ArrayList<MesaModel> lista = (ArrayList<MesaModel>) select.list();

	return lista;
    }

}
