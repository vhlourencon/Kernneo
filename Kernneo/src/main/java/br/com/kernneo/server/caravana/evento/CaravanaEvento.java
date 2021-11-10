package br.com.kernneo.server.caravana.evento;

import br.com.kernneo.client.caravana.model.CaravanaEventoModel;
import br.com.kernneo.server.band.negocio.BandNegocio;
import br.com.kernneo.server.caravana.evento.catParticipantes.CaravanaEventoCatParticipantes;
import br.com.kernneo.server.caravana.evento.permissaoAgendamento.EventoPermissaoAgendamento;
import br.com.kernneo.server.caravana.evento.servicoLocal.CaravanaEventoServicoLocal;

public class CaravanaEvento extends BandNegocio<CaravanaEventoModel, CaravanaEventoDAO> {

	public CaravanaEventoModel obterModelAtualizadoComDetalhes(CaravanaEventoModel model) throws Exception {
		model = new CaravanaEvento().obterPorId(model);
		model.setListaDeServicoLocal(new CaravanaEventoServicoLocal().obterTodosPorEvento(model));
		model.setListaDeCategoriaDeParticipantes(new CaravanaEventoCatParticipantes().obterTodosPorEvento(model));
		model.setListaDePermissaoAgendamento(new EventoPermissaoAgendamento().obterTodosPorEvento(model));
		return model;

	}

	@Override
	public CaravanaEventoModel salvar(CaravanaEventoModel vo) throws Exception {
		CaravanaEventoModel caravanaEventoModel = super.salvar(vo);

		new CaravanaEventoServicoLocal().salvarPorEvento(caravanaEventoModel);
		new CaravanaEventoCatParticipantes().salvarPorEvento(caravanaEventoModel);
		new EventoPermissaoAgendamento().salvarPorEvento(caravanaEventoModel);

		return caravanaEventoModel;

	}

	@Override
	public void alterar(CaravanaEventoModel model) throws Exception {

		super.alterar(model);
		new CaravanaEventoServicoLocal().salvarPorEvento(model);
		new CaravanaEventoCatParticipantes().salvarPorEvento(model);
		new EventoPermissaoAgendamento().salvarPorEvento(model);

	}

	public CaravanaEvento() {
		super();
		dao = new CaravanaEventoDAO();
	}

	@Override
	public Exception validar(CaravanaEventoModel vo) {
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
