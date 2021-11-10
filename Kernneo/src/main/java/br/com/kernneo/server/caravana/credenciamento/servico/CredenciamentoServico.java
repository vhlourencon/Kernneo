package br.com.kernneo.server.caravana.credenciamento.servico;

import java.util.ArrayList;

import br.com.kernneo.client.caravana.model.CaravanaCredenciamentoModel;
import br.com.kernneo.client.caravana.model.CredenciamentoServicoModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CredenciamentoServico extends BandNegocio<CredenciamentoServicoModel, CredenciamentoServicoDAO> {

	public ArrayList<CredenciamentoServicoModel> obterTodosPorCredenciamento(CaravanaCredenciamentoModel credenciamentoModel) throws Exception {
		ArrayList<CredenciamentoServicoModel> lista = dao.obterTodosServicoPorCredenciamento(credenciamentoModel);

		return lista;

	}

	public CredenciamentoServico() {
		super();
		dao = new CredenciamentoServicoDAO();
	}

	@Override
	public Exception validar(CredenciamentoServicoModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

}
