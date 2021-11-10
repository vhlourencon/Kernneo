package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.BairroException;
import br.com.kernneo.client.exception.CstIpiException;
import br.com.kernneo.client.exception.LSTException;
import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.CstIpiModel;
import br.com.kernneo.client.model.LSTModel;
import br.com.kernneo.server.dao.BairroDAO;
import br.com.kernneo.server.dao.CstIpiDAO;
import br.com.kernneo.server.dao.LStDAO;

public class CstIpi extends Negocio<CstIpiModel, CstIpiDAO, CstIpiException> {

    public CstIpi() {
	super();
	dao = new CstIpiDAO();
    }

    @Override
    public CstIpiException validar(CstIpiModel vo) {
	StringBuffer msg = new StringBuffer("");

	if (msg.length() > 0)
	    return new CstIpiException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CstIpiModel vo) {
	String filtro = super.getSqlFiltro(vo);

	filtro += " order by id asc";

	return filtro;
    }

}
