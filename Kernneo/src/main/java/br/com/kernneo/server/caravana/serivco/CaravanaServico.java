package br.com.kernneo.server.caravana.serivco;

import br.com.kernneo.client.caravana.model.CaravanaServicoModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaServico extends BandNegocio<CaravanaServicoModel, CaravanaSerivcoDAO> {

	public CaravanaServico() {
		super();
		dao = new CaravanaSerivcoDAO();
	}

	@Override
	public Exception validar(CaravanaServicoModel vo) {
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
