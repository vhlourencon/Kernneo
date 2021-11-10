package br.com.kernneo.server.caravana.categoriaServico;

import br.com.kernneo.client.caravana.model.CaravanaCatServicoModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaCatServico extends BandNegocio<CaravanaCatServicoModel, CaravanaCatServicoDAO> {

	public CaravanaCatServico() {
		super();
		dao = new CaravanaCatServicoDAO();
	}

	@Override
	public Exception validar(CaravanaCatServicoModel vo) {
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
