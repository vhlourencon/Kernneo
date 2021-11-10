package br.com.kernneo.server.band.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;



import br.com.kernneo.client.band.model.BandLogImportacaoModel;
import br.com.kernneo.server.band.dao.BandLogImportacaoDAO;

public class BandLogImportacao extends BandNegocio<BandLogImportacaoModel, BandLogImportacaoDAO> {

	public BandLogImportacao() {
		super();
		dao = new BandLogImportacaoDAO();
	}

	public ArrayList<BandLogImportacaoModel> obterLogImportacaoNaData(Date data) throws Exception {
		
		return dao.obterLogImportacaoNaData(data);

	}

	@Override
	public Exception validar(BandLogImportacaoModel vo) {

		// TODO Auto-generated method stub
		return null;
	}

}
