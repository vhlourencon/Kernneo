package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;

import br.com.kernneo.client.model.CaixaRelatorioModel;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.server.ConnectFactory;

public class CaixaDAO extends GenericDAO<CaixaModel> {

    public CaixaModel obterCaixaAberto() throws SQLException {

	Session session = ConnectFactory.getSession();

	org.hibernate.Query select = session.createQuery("select g FROM " + CaixaModel.class.getCanonicalName() + " g WHERE g.aberto = :aberto  and deletado=:deletado");
	select.setBoolean("aberto", true);
	select.setBoolean("deletado", false);

	CaixaModel model = (CaixaModel) select.uniqueResult();

	return (CaixaModel) model;
    }

    public ArrayList<CaixaRelatorioModel> obterCaixasFechadosNoPeridoAgrupado(Date dataIni, Date dataFim, String agrupamento) throws SQLException {
	Session session = ConnectFactory.getSession();

	String sql = "select new br.com.kernneo.client.caixa.CaixaRelatorioModel(sum(g.saldoDinheiro),sum(g.saldoCartaoCredito), sum(g.saldoCartaoDebito), sum(g.saldoCheque) ,";
	sql = sql + " sum(g.saldoEntrada), sum(g.saldoSaida), sum(g.saldoMesas),sum(g.saldo),g.dataHoraAbertura)";
	// sql = sql +
	// " as saldo,sum(saldoDinheiro) as saldoDinheiro,sum(saldoCartaoCredito) as saldoCartaoCredito,sum(saldoCartaoDebito) as saldoCartaoDebito,  ";
	// sql = sql +
	// "sum(saldoCheque) as saldoCheque, sum(entrada) as entrada, sum(saida) as saida ";
	sql = sql + " FROM " + CaixaModel.class.getCanonicalName() + " g WHERE g.aberto = :aberto and g.deletado=:deletado and g.dataHoraAbertura between :dataIni and :dataFim ";
	sql = sql + " group by " + agrupamento;

	if (agrupamento.equals("id") == false) {
	    sql = sql + " (g.dataHoraAbertura)";
	}


	org.hibernate.Query select = session.createQuery(sql);
	select.setBoolean("aberto", false);
	select.setBoolean("deletado", false);
	select.setTimestamp("dataIni", dataIni);
	select.setTimestamp("dataFim", dataFim);

	ArrayList<CaixaRelatorioModel> lista = (ArrayList<CaixaRelatorioModel>) select.list();

	return lista;

    }

    public ArrayList<CaixaModel> obterCaixasFEchadosNoPerido(Date dataIni, Date dataFim) throws SQLException {
	Session session = ConnectFactory.getSession();

	org.hibernate.Query select = session.createQuery("select g FROM " + CaixaModel.class.getCanonicalName() + " g WHERE g.aberto = :aberto and deletado=:deletado and g.dataHoraAbertura between :dataIni and :dataFim");
	select.setBoolean("aberto", false);
	select.setBoolean("deletado", false);
	select.setTimestamp("dataIni", dataIni);
	select.setTimestamp("dataFim", dataFim);
	ArrayList<CaixaModel> lista = (ArrayList<CaixaModel>) select.list();

	return lista;

    }

}
