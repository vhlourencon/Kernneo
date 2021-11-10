package br.com.kernneo.server.band.negocio;

import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.band.model.BandExibicaoModel;
import br.com.kernneo.client.band.model.BandPiModel;
import br.com.kernneo.client.band.model.BandRelatorioExibicaoModel;
import br.com.kernneo.server.band.dao.BandRelatorioExibicaoDAO;

public class BandRelatorioExibicao extends BandNegocio<BandRelatorioExibicaoModel, BandRelatorioExibicaoDAO> {

	public BandRelatorioExibicao() {
		super();
		dao = new BandRelatorioExibicaoDAO();
	}

	public BandRelatorioExibicaoModel obterRelatorio(BandPiModel banPiModel, Date dataInicial, Date dataFinal, String codigoPi, boolean salvar) throws Exception {

		StringBuffer msg = new StringBuffer("");

		if (dataInicial == null || dataFinal == null) {
			if (dataInicial == null) {
				msg.append("O campo Data Inicial de preenchimento obrigatório! \n");
			}

			if (dataFinal == null) {
				msg.append("O campo Data Final é de preenchimento obrigatório! \n");
			}

		} else if (dataInicial.after(dataFinal)) {
			msg.append("O campo Data Inicial não pode ser deopis da Data Final! \n");
		}

		if (codigoPi == null || codigoPi.trim().length() == 0) {
			msg.append("O campo Código do Cartucho é de preenchimento obrigatório! \n");
		}

		if (msg.length() > 0) {
			throw new Exception(msg.toString());

		} else {

			ArrayList<BandExibicaoModel> listaDeExibidos = new BandExibicao().obterTodosPorDataECodigoPi(dataInicial, dataFinal, codigoPi);

			BandRelatorioExibicaoModel relatorioExibicaoModel = new BandRelatorioExibicaoModel();
			relatorioExibicaoModel.setBandPi(banPiModel);
			relatorioExibicaoModel.setDataInicial(dataInicial);
			relatorioExibicaoModel.setDataFinal(dataFinal);
			relatorioExibicaoModel.setCodigoPi(codigoPi);
			relatorioExibicaoModel.setListaDeExibidos(listaDeExibidos);

			if (salvar) {
				salvar(relatorioExibicaoModel);
			}

			return relatorioExibicaoModel;
		}

	}

	@Override
	public Exception validar(BandRelatorioExibicaoModel vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
