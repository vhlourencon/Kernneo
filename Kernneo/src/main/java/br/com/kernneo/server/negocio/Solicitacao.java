package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.SolicitacaoException;
import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.server.dao.SolicitacaoDAO;

public class Solicitacao extends Negocio<SolicitacaoModel, SolicitacaoDAO, SolicitacaoException> {

	public Solicitacao() {
		super();
		dao = new SolicitacaoDAO();
	}


	@Override
	public SolicitacaoModel salvar(SolicitacaoModel vo) throws SolicitacaoException {

		SolicitacaoException exc = validar(vo);
		if (exc == null) {
			try {

				vo = dao.salvar(vo);
				return vo;
			} catch (Exception e) {
				throw  new SolicitacaoException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}

	}

	

	@Override
	public SolicitacaoException validar(SolicitacaoModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome  é de preenchimento obrigatório!");
		}

		if (msg.length() > 0)
			return new SolicitacaoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(SolicitacaoModel vo) {
		String filtro = super.getSqlFiltro(vo);
		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('%" + vo.getNome() + "%')";
		}
		filtro += " order by id asc";

		return filtro;
	}

}
