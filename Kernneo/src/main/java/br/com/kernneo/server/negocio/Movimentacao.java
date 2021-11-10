package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.server.dao.MovimentacaoDAO;

public class Movimentacao extends Negocio<MovimentacaoModel, MovimentacaoDAO, MovimentacaoException> {

    public Movimentacao() {
	super();
	dao = new MovimentacaoDAO();
    }

    @Override
    public MovimentacaoModel salvar(MovimentacaoModel vo) throws MovimentacaoException {
	MovimentacaoException exc = validar(vo);
	if (exc == null) {
	    try {
		CaixaModel caixaModel = new Caixa().obterCaixaAberto();
		if (caixaModel == null) {
		    throw new MovimentacaoException("Não existe nenhum caixa aberto! Favor abrir um caixa");
		} else {
		    vo.setCaixa(caixaModel);
		    dao.salvar(vo);
		    return vo;
		}

	    } catch (Exception e) {
		throw (MovimentacaoException) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
	    }
	} else {
	    throw exc;
	}
    }

    @Override
    public MovimentacaoException validar(MovimentacaoModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getCaixa() == null) {
	    msg.append("O campo Caixa  é de preenchimento obrigatório! \n");
	}

	if (msg.length() > 0)
	    return new MovimentacaoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(MovimentacaoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " where 1=1 ";

	// if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
	// filtro += " and nome like('%" + vo.getNome() + "%')";
	// }

	filtro += " order by id asc";

	return filtro;
    }

}
