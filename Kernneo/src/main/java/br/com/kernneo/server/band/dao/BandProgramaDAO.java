package br.com.kernneo.server.band.dao;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;

import br.com.kernneo.client.band.model.BandLogImportacaoModel;
import br.com.kernneo.client.band.model.BandProgramaModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;



public class BandProgramaDAO extends GenericDAO<BandProgramaModel> {

//	public ArrayList<BandProgramaModel> obterProgramasPorLog(BandLogImportacaoModel BandLogImportacaoModel ) throws Exception {
//		Session session = ConnectFactory.getSession();
//
//		org.hibernate.Query select = session.createQuery("select p FROM " + BandProgramaModel.class.getCanonicalName() + " p WHERE p.logImportacao=:logImportacao or date(l.dataHoraInicio)=date(:dataHoraInicio)");
//		select.setEntity("logImportacao", BandLogImportacaoModel);
//		ArrayList<BandProgramaModel> model = (ArrayList<BandProgramaModel>) select.list();
//
//		return model;
//
//	}
	
	public ArrayList<BandProgramaModel> obterProgramasPorData(Date dataInicial, Date dataFinal ) throws Exception {
		Session session = ConnectFactory.getSession();

		org.hibernate.Query select = session.createQuery("select p FROM " + BandProgramaModel.class.getCanonicalName() + " p WHERE deletado=:deletado and date(p.dataHoraInicio)   between date(:dataInicial) AND date(:dataFinal)");
		select.setDate("dataInicial", dataInicial);
		select.setDate("dataFinal", dataFinal);
		select.setBoolean("deletado", false);
		
		ArrayList<BandProgramaModel> model = (ArrayList<BandProgramaModel>) select.list();

		return model;

	}
	
	public ArrayList<BandProgramaModel> obterProgramasPorDataEHora(Date dataHora) throws Exception {
		Session session = ConnectFactory.getSession();

		org.hibernate.Query select = session.createQuery("select p FROM " + BandProgramaModel.class.getCanonicalName() + " p WHERE deletado=:deletado and :dataHora   between dataHoraInicio AND dataHoraFim");
		select.setTimestamp("dataHora", dataHora);
		select.setBoolean("deletado", false);
		
		
		ArrayList<BandProgramaModel> model = (ArrayList<BandProgramaModel>) select.list();

		return model;

	}

}
