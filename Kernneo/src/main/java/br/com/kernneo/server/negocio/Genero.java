package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.BairroException;
import br.com.kernneo.client.exception.GeneroException;
import br.com.kernneo.client.exception.LSTException;
import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.GeneroModel;
import br.com.kernneo.client.model.LSTModel;
import br.com.kernneo.server.dao.BairroDAO;
import br.com.kernneo.server.dao.GeneroDAO;
import br.com.kernneo.server.dao.LStDAO;

public class Genero extends Negocio<GeneroModel, GeneroDAO, GeneroException> {

    public Genero() {
	super();
	dao = new GeneroDAO();
    }

    @Override
    public GeneroException validar(GeneroModel vo) {
	StringBuffer msg = new StringBuffer("");

	if (msg.length() > 0)
	    return new GeneroException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(GeneroModel vo) {
	String filtro = super.getSqlFiltro(vo);

	filtro += " order by id asc";

	return filtro;
    }

}
