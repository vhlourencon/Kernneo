package br.com.kernneo.server.band.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.kernneo.client.band.model.BandAgenciaModel;
import br.com.kernneo.client.band.model.BandEmpresaModel;
import br.com.kernneo.client.band.model.BandPiModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.GenericDAO;

public class BandPiDAO extends GenericDAO<BandPiModel> {

	public BandPiModel obterPorCodigo(String codigo) {
		String sql = "select p FROM br.com.kernneo.client.band.model.BandPiModel  p where p.codigo=:codigo  and deletado=:deletado";
		Session session = ConnectFactory.getSession();

		Query select = session.createQuery(sql);
		select.setString("codigo", codigo);
		select.setBoolean("deletado", false);

		BandPiModel model = (BandPiModel) select.uniqueResult();

		return model;
	}

}
