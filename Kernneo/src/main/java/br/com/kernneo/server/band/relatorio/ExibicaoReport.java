package br.com.kernneo.server.band.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.kernneo.client.band.model.BandExibicaoModel;
import br.com.kernneo.client.band.model.BandPiModel;
import br.com.kernneo.client.band.model.BandRelatorioExibicaoJasperModel;
import br.com.kernneo.client.band.model.BandRelatorioExibicaoModel;
import br.com.kernneo.server.band.negocio.BandPi;
import br.com.kernneo.server.band.negocio.BandRelatorioExibicao;

public class ExibicaoReport extends GenericReport {

	@Override
	protected List getLista() {

		ArrayList<BandRelatorioExibicaoJasperModel> listaDeBandRelatorioExibicaoJasperModels = new ArrayList<BandRelatorioExibicaoJasperModel>();

		try {
			String idRelatorioExibicao = null;
			if (!getRequest().getParameter("idRelatorioExibicao").equals("")) {
				idRelatorioExibicao = getRequest().getParameter("idRelatorioExibicao");
			}
			BandRelatorioExibicaoModel relatorioExibicaoModel = new BandRelatorioExibicao().obterPorId(BandRelatorioExibicaoModel.class, Long.valueOf(idRelatorioExibicao));
			relatorioExibicaoModel = new BandRelatorioExibicao().obterRelatorio(relatorioExibicaoModel.getBandPi(), relatorioExibicaoModel.getDataInicial(), relatorioExibicaoModel.getDataFinal(), relatorioExibicaoModel.getCodigoPi(), false);

			if (relatorioExibicaoModel.getListaDeExibidos() != null) {
				for (BandExibicaoModel exibicaoModel : relatorioExibicaoModel.getListaDeExibidos()) {

					BandRelatorioExibicaoJasperModel relatorioExibicaoJasperModel = new BandRelatorioExibicaoJasperModel();
					relatorioExibicaoJasperModel.setDataHora(exibicaoModel.getDataStr() + " -  " + exibicaoModel.getHoraStr() + "" + exibicaoModel.getMiliSegundosHoraExibicao());
					relatorioExibicaoJasperModel.setDuracao(exibicaoModel.getDuracao());
					relatorioExibicaoJasperModel.setTitulo(exibicaoModel.getDescricao());
					if (exibicaoModel.getPrograma() != null) {

						relatorioExibicaoJasperModel.setPrograma(exibicaoModel.getPrograma().getTitulo());
					} else {
						relatorioExibicaoJasperModel.setPrograma("# PROGRAMA NÃO CADASTRADO #");
					}

					listaDeBandRelatorioExibicaoJasperModels.add(relatorioExibicaoJasperModel);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaDeBandRelatorioExibicaoJasperModels;

	}

	@Override
	protected Map getParametros() {

		Map map = new HashMap();

		try {
			String idRelatorioExibicao = null;
			if (!getRequest().getParameter("idRelatorioExibicao").equals("")) {
				idRelatorioExibicao = getRequest().getParameter("idRelatorioExibicao");
			}
			BandRelatorioExibicaoModel relatorioExibicaoModel = new BandRelatorioExibicao().obterPorId(BandRelatorioExibicaoModel.class, Long.valueOf(idRelatorioExibicao));
			relatorioExibicaoModel = new BandRelatorioExibicao().obterRelatorio(relatorioExibicaoModel.getBandPi(), relatorioExibicaoModel.getDataInicial(), relatorioExibicaoModel.getDataFinal(), relatorioExibicaoModel.getCodigoPi(), false);

			if (relatorioExibicaoModel.getListaDeExibidos() != null) {
				map.put("totalVeiculacoes", String.valueOf(relatorioExibicaoModel.getListaDeExibidos().size()));
			} else {
				map.put("totalVeiculacoes", "0");

			}

			BandPiModel bandPiModel = relatorioExibicaoModel.getBandPi();
			
			if (bandPiModel != null) {
				map.put("rp", bandPiModel.getRp());
				if (bandPiModel.getAgencia() != null) {
					map.put("agenciaNome", bandPiModel.getAgencia().getNome());
					map.put("agenciaCidade", bandPiModel.getAgencia().getBairro());
					map.put("agenciaEndereco", bandPiModel.getAgencia().getEndereco());
					map.put("agenciaCep", bandPiModel.getAgencia().getCep());
					map.put("agenciaEstado", bandPiModel.getAgencia().getUf());
					map.put("agenciaBairro", bandPiModel.getAgencia().getBairro());
				}

				if (bandPiModel.getEmpresa() != null) {
					map.put("empresaNome", bandPiModel.getEmpresa().getNome());
					map.put("empresaCidade", bandPiModel.getEmpresa().getBairro());
					map.put("empresaEndereco", bandPiModel.getEmpresa().getEndereco());
					map.put("empresaCep", bandPiModel.getEmpresa().getCep());
					map.put("empresaEstado", bandPiModel.getEmpresa().getUf());
					map.put("empresaBairro", bandPiModel.getEmpresa().getBairro());
				}
			}

			map.put("dataInicial", relatorioExibicaoModel.getDataInicial());
			map.put("dataFinal", relatorioExibicaoModel.getDataFinal());
			map.put("codPi", relatorioExibicaoModel.getCodigoPi());
			map.put("dataImpressao", new Date());
			map.put("horaImpressao", new Date());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	protected void parametrosEmpresa() {

	}

}
