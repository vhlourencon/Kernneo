package br.com.kernneo.server.band.negocio;

import br.com.kernneo.client.band.model.BandEmpresaModel;
import br.com.kernneo.server.band.dao.BandEmpresaDAO;

public class BandEmpresa extends BandNegocio<BandEmpresaModel, BandEmpresaDAO> {

	public BandEmpresa() {
		super();
		dao = new BandEmpresaDAO();
	}

	@Override
	public Exception validar(BandEmpresaModel vo) {
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
