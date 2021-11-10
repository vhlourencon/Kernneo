package br.com.kernneo.server.caravana.participante;

import java.util.Date;

import br.com.kernneo.client.caravana.model.CaravanaParticipanteModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaParticipante extends BandNegocio<CaravanaParticipanteModel, CaravanaParticipanteDAO> {

	
	
	@Override
	public CaravanaParticipanteModel salvar(CaravanaParticipanteModel vo) throws Exception {
		vo.setDataHoraCadastro(new Date());
		String nome = vo.getNome(); 
		nome = nome.trim().replaceAll("\\s+", " ");
		vo.setNome(nome);
		return super.salvar(vo);
	}

	public CaravanaParticipante() {
		super();
		dao = new CaravanaParticipanteDAO();
	}

	@Override
	public Exception validar(CaravanaParticipanteModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome  é de preenchimento obrigatório! \n");
		}

		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

	@Override
	public String getSqlFiltro(CaravanaParticipanteModel vo) {
		String filtro = super.getSqlFiltro(vo);
		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('" + vo.getNome() + "%')";
		}
		
		if (vo.getCartaoSus() != null && vo.getCartaoSus().trim().length() > 0) {
			filtro += " and cartaoSus like('" + vo.getCartaoSus() + "%')";
		}
		filtro += " order by id asc";

		return filtro;
	}

}
