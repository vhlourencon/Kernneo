package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.CstPisCofinsException;
import br.com.kernneo.client.model.CstPisCofinsModel;
import br.com.kernneo.server.dao.CstPisCofinsDAO;

public class CstPisCofins extends Negocio<CstPisCofinsModel, CstPisCofinsDAO, CstPisCofinsException> {

    public CstPisCofins() {
	super();
	dao = new CstPisCofinsDAO();
    }

    @Override
    public CstPisCofinsException validar(CstPisCofinsModel vo) {
	StringBuffer msg = new StringBuffer("");

	if (msg.length() > 0)
	    return new CstPisCofinsException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CstPisCofinsModel vo) {
	String filtro = super.getSqlFiltro(vo);
	filtro += " order by id asc";

	return filtro;
    }

}
