package br.com.kernneo.server.caravana.evento.catParticipantes;

import java.util.ArrayList;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.client.caravana.model.EventoCategoriaPartModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaEventoCatParticipantes extends BandNegocio<EventoCategoriaPartModel, CaravanaEventoCatParticipantesDAO> {

	public void salvarPorEvento(CaravanaEventoModel caravanaEventoModel) throws Exception {
		for (EventoCategoriaPartModel eventoServicoLocalModel : caravanaEventoModel.getListaDeCategoriaDeParticipantes()) {
			eventoServicoLocalModel.setEvento(caravanaEventoModel);
			eventoServicoLocalModel = merge(eventoServicoLocalModel);

		}
	}

	public ArrayList<EventoCategoriaPartModel> obterTodosPorEvento(CaravanaEventoModel caravanaEventoModel) throws Exception {
		ArrayList<EventoCategoriaPartModel> lista = dao.obterTodosPorEvento(caravanaEventoModel);
		return lista;
	}

	public CaravanaEventoCatParticipantes() {
		super();
		dao = new CaravanaEventoCatParticipantesDAO();
	}

	@Override
	public Exception validar(EventoCategoriaPartModel vo) {
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
