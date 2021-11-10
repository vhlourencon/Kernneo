package br.com.kernneo.server.caravana.credenciamento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaCatParticipanteModel;
import br.com.kernneo.client.caravana.model.CaravanaCredenciamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.CaravanaParticipanteModel;
import br.com.kernneo.client.caravana.model.RelatorioCredenciamentoCategoriaModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class CaravanaCredenciamentoDAO extends GenericDAO<CaravanaCredenciamentoModel> {
	
	public RelatorioCredenciamentoCategoriaModel obterQuantidadeCredenciamentoPorCategoriaEData(CaravanaEventoModel eventoModel, CaravanaCatParticipanteModel catParticipanteModel, Date dataDeCredenciamento) throws SQLException {
		Session session = ConnectFactory.getSession();

		String sql = "select new br.com.kernneo.client.caravana.model.RelatorioCredenciamentoCategoriaModel(categoriaParticipante,count(id_participante) as quantidadeDePessoas)";
		sql += " 	  FROM " + CaravanaCredenciamentoModel.class.getCanonicalName() + " g ";
		sql += "  	  WHERE date(g.dataHoraCredenciamento)=date(:dataHoraCredenciamento) and g.evento=:evento and g.categoriaParticipante=:categoriaParticipante and g.deletado=:deletado ";
		
		org.hibernate.Query select = session.createQuery(sql);
		select.setDate("dataHoraCredenciamento", dataDeCredenciamento);
		select.setEntity("evento", eventoModel);
		select.setEntity("categoriaParticipante", catParticipanteModel);
		select.setBoolean("deletado", false);

		RelatorioCredenciamentoCategoriaModel relatorio =  (RelatorioCredenciamentoCategoriaModel) select.uniqueResult();

		return relatorio;

	}

	public ArrayList<RelatorioCredenciamentoCategoriaModel> obterRelatorioDeCredenciamentoCategoriaPorData(CaravanaEventoModel eventoModel, Date dataDeCredenciamento) throws SQLException {
		Session session = ConnectFactory.getSession();

		String sql = "select new br.com.kernneo.client.caravana.model.RelatorioCredenciamentoCategoriaModel(categoriaParticipante,count(id_participante) as quantidadeDePessoas)";
		sql += " 	  FROM " + CaravanaCredenciamentoModel.class.getCanonicalName() + " g ";
		sql += "  	  WHERE date(g.dataHoraCredenciamento)=date(:dataHoraCredenciamento) and g.evento=:evento and  g.deletado=:deletado ";
		sql += "	  group by (id_categoria_participante) ";

		org.hibernate.Query select = session.createQuery(sql);
		select.setDate("dataHoraCredenciamento", dataDeCredenciamento);
		select.setEntity("evento", eventoModel);
		select.setBoolean("deletado", false);

		ArrayList<RelatorioCredenciamentoCategoriaModel> lista = (ArrayList<RelatorioCredenciamentoCategoriaModel>) select.list();

		return lista;

	}

	public CaravanaCredenciamentoModel obterCredenciamentoDePatcipantePorDia(CaravanaEventoModel eventoModel, CaravanaParticipanteModel participanteModel, Date dataCredenciemtno) throws SQLException {
		String sql = "SELECT c FROM br.com.kernneo.client.caravana.model.CaravanaCredenciamentoModel c  where deletado=:deletado and  (c.evento=:evento and c.participante=:participante and date(c.dataHoraCredenciamento)=date(:dataHoraCredenciamento) )";

		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setEntity("participante", participanteModel);
		select.setDate("dataHoraCredenciamento", dataCredenciemtno);
		select.setBoolean("deletado", false);
		CaravanaCredenciamentoModel model = (CaravanaCredenciamentoModel) select.uniqueResult();

		return model;
	}

	public ArrayList<CaravanaCredenciamentoModel> obterTodosCredenciamentosDoPArticipanteNoEvento(CaravanaEventoModel eventoModel, CaravanaParticipanteModel participanteModel) throws SQLException {
		String sql = "SELECT c FROM br.com.kernneo.client.caravana.model.CaravanaCredenciamentoModel c  where deletado=:deletado and  (c.evento=:evento and c.participante=:participante)";

		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setEntity("evento", eventoModel);
		select.setEntity("participante", participanteModel);
		select.setBoolean("deletado", false);
		ArrayList<CaravanaCredenciamentoModel> lista = (ArrayList<CaravanaCredenciamentoModel>) select.list();

		return lista;
	}

}
