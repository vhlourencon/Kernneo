package br.com.kernneo.server.negocio;

import java.sql.SQLException;

import br.com.kernneo.client.exception.CFOPException;
import br.com.kernneo.client.exception.SpedException;
import br.com.kernneo.client.model.CFOPModel;
import br.com.kernneo.client.model.SpedModel;
import br.com.kernneo.server.dao.CFOpDAO;
import br.com.kernneo.server.dao.SpedDAO;

public class Sped extends Negocio<SpedModel, SpedDAO, SpedException> {

    public Sped() {
	super();
	dao = new SpedDAO();
    }

    @Override
    public SpedException validar(SpedModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getId() == null) {
	    msg.append("O campo CFOP  é de preenchimento obrigatório! \n");
	}

	if (vo.getId() == null) {

	}

	if (msg.length() > 0)
	    return new SpedException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(SpedModel vo) {
	String filtro = super.getSqlFiltro(vo);

	filtro += " order by id asc";
	return filtro;
    }

}
