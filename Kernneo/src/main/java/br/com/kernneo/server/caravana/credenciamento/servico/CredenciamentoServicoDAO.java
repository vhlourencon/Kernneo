package br.com.kernneo.server.caravana.credenciamento.servico;

import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaCredenciamentoModel;
import br.com.kernneo.client.caravana.model.CredenciamentoServicoModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class CredenciamentoServicoDAO extends GenericDAO<CredenciamentoServicoModel> {

	public ArrayList<CredenciamentoServicoModel> obterTodosServicoPorCredenciamento(CaravanaCredenciamentoModel credenciamentoModel) throws SQLException {
		String sql = "SELECT c FROM br.com.kernneo.client.caravana.model.CredenciamentoServicoModel c  where deletado=:deletado and  c.credenciamento=:credenciamento ";

		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setEntity("credenciamento", credenciamentoModel);

		select.setBoolean("deletado", false);
		ArrayList<CredenciamentoServicoModel> lista = (ArrayList<CredenciamentoServicoModel>) select.list();

		return lista;
	}

}
