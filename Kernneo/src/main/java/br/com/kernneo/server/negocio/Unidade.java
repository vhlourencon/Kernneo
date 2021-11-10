package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.UnidadeException;
import br.com.kernneo.client.model.UnidadeModel;
import br.com.kernneo.server.dao.UnidadeDAO;

public class Unidade extends Negocio<UnidadeModel, UnidadeDAO, UnidadeException> {

    public Unidade() {
	super();
	dao = new UnidadeDAO();
    }

    @Override
    public UnidadeException validar(UnidadeModel model) {
	StringBuffer msg = new StringBuffer("");
	if (model.getDescricao() == null || model.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descriçãoo  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new UnidadeException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(UnidadeModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " where 1=1 ";

	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) {
	    filtro += " and descricao like('%" + vo.getDescricao() + "%')";
	}

	filtro += " order by id asc";

	return filtro;
    }

}
