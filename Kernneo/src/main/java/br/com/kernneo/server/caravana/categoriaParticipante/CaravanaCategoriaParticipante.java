package br.com.kernneo.server.caravana.categoriaParticipante;

import br.com.kernneo.client.caravana.model.CaravanaCatParticipanteModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaCategoriaParticipante extends BandNegocio<CaravanaCatParticipanteModel, CaravanaCategoriaParticipanteDAO> {

	public CaravanaCategoriaParticipante() {
		super();
		dao = new CaravanaCategoriaParticipanteDAO();
	}

	@Override
	public Exception validar(CaravanaCatParticipanteModel vo) {
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
