package br.com.kernneo.server.band.negocio;

import br.com.kernneo.client.band.model.BandAgenciaModel;
import br.com.kernneo.client.band.model.BandEmpresaModel;
import br.com.kernneo.client.band.model.BandPiModel;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.server.band.dao.BandAgenciaDAO;
import br.com.kernneo.server.band.dao.BandEmpresaDAO;

public class BandAgencia extends BandNegocio<BandAgenciaModel, BandAgenciaDAO> {

	public BandAgencia() {
		super();
		dao = new BandAgenciaDAO();
	}

	@Override
	public Exception validar(BandAgenciaModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Código  é de preenchimento obrigatório! \n");

		}

		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;
	}

}
