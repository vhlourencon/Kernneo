package br.com.kernneo.server.caravana.local;

import br.com.kernneo.client.caravana.model.CaravanaLocalModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaLocal extends BandNegocio<CaravanaLocalModel, CaravanaLocalDAO> {

	public CaravanaLocal() {
		super();
		dao = new CaravanaLocalDAO();
	}

	@Override
	public Exception validar(CaravanaLocalModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome  é de preenchimento obrigatório! \n");
		}

	
		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

}
