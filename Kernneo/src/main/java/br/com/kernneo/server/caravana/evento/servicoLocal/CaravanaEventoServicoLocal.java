package br.com.kernneo.server.caravana.evento.servicoLocal;

import java.util.ArrayList;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.EventoServicoLocalModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaEventoServicoLocal extends BandNegocio<EventoServicoLocalModel, CaravanaEventoServicoLocalDAO> {

	public void salvarPorEvento(CaravanaEventoModel caravanaEventoModel) throws Exception {
		for (EventoServicoLocalModel eventoServicoLocalModel : caravanaEventoModel.getListaDeServicoLocal()) {
			eventoServicoLocalModel.setEvento(caravanaEventoModel);
			eventoServicoLocalModel = merge(eventoServicoLocalModel);

		}
	}
	
	public ArrayList<EventoServicoLocalModel> obterTodosPorEvento(CaravanaEventoModel caravanaEventoModel) throws Exception {
		ArrayList<EventoServicoLocalModel> lista = dao.obterTodosPorEvento(caravanaEventoModel);
		return lista;
	}

	public CaravanaEventoServicoLocal() {
		super();
		dao = new CaravanaEventoServicoLocalDAO();
	}

	@Override
	public Exception validar(EventoServicoLocalModel vo) {
		StringBuffer msg = new StringBuffer("");

		// if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
		// msg.append("O campo Nome é de preenchimento obrigatório! \n");
		// }
		//
		//
		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

}
