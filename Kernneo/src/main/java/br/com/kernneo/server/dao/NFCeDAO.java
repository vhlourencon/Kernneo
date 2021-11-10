package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.NFCeModel;
import br.com.kernneo.server.ConnectFactory;

public class NFCeDAO extends GenericDAO<NFCeModel> {

	public ArrayList<NFCeModel> obterNFCePorSerieEnumero(Long serie, Long numero) throws SQLException {
		String sql = "SELECT m FROM br.com.kernneo.client.model.NFCeModel m where deletado=:deletado and  m.numero = :numero  and m.serie=:serie";

		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setBoolean("deletado", false);
		select.setLong("serie", serie);
		select.setLong("numero", numero);
		ArrayList<NFCeModel> lista = (ArrayList<NFCeModel>) select.list();

		return lista;
	}

	public ArrayList<NFCeModel> obterTodasPorMesa(MesaModel mesaModel) throws SQLException {
		String sql = "SELECT m FROM br.com.kernneo.client.model.NFCeModel m where deletado=:deletado and  m.mesa = :mesa  order by id desc";

		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setBoolean("deletado", false);
		select.setEntity("mesa", mesaModel);
		ArrayList<NFCeModel> lista = (ArrayList<NFCeModel>) select.list();

		return lista;
	}

}
