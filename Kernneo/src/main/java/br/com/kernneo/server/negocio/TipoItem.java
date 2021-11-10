package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.TipoItemException;
import br.com.kernneo.client.model.TipoItemModel;
import br.com.kernneo.server.dao.TipoItemDAO;

public class TipoItem extends Negocio<TipoItemModel, TipoItemDAO, TipoItemException> {

    public TipoItem() {
	super();
	dao = new TipoItemDAO();
    }

    @Override
    public TipoItemException validar(TipoItemModel vo) {
	StringBuffer msg = new StringBuffer("");

	if (msg.length() > 0)
	    return new TipoItemException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(TipoItemModel vo) {
	String filtro = super.getSqlFiltro(vo);

	filtro += " order by id asc";

	return filtro;
    }

}
