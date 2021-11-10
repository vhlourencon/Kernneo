package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.BairroException;
import br.com.kernneo.client.exception.LSTException;
import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.LSTModel;
import br.com.kernneo.server.dao.BairroDAO;
import br.com.kernneo.server.dao.LStDAO;

public class LST extends Negocio<LSTModel, LStDAO, LSTException> {

    public LST() {
	super();
	dao = new LStDAO();
    }

    @Override
    public LSTException validar(LSTModel vo) {
	StringBuffer msg = new StringBuffer("");

	if (msg.length() > 0)
	    return new LSTException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(LSTModel vo) {
	String filtro = super.getSqlFiltro(vo);

	filtro += " order by id asc";

	return filtro;
    }

}
