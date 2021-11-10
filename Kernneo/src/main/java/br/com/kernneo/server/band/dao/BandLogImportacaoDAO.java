package br.com.kernneo.server.band.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;

import br.com.kernneo.client.band.model.BandLogImportacaoModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class BandLogImportacaoDAO extends GenericDAO<BandLogImportacaoModel> {

	public ArrayList<BandLogImportacaoModel> obterLogImportacaoNaData(Date data) throws SQLException {
		Session session = ConnectFactory.getSession();
		org.hibernate.Query select = session.createQuery("select l FROM " + BandLogImportacaoModel.class.getCanonicalName() + " l WHERE deletado=:deletado and date(l.dataImportacao)=date(:dataImportacao) ");
		select.setDate("dataImportacao", data);
		select.setBoolean("deletado", false);
		ArrayList<BandLogImportacaoModel> lista = (ArrayList<BandLogImportacaoModel>) select.list();

		return lista;

	}

}
