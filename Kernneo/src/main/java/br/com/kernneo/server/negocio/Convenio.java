package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.ConvenioException;
import br.com.kernneo.client.model.ConvenioModel;
import br.com.kernneo.server.dao.ConvenioDAO;

public class Convenio extends Negocio<ConvenioModel, ConvenioDAO, ConvenioException> {

    public Convenio() {
	super();
	dao = new ConvenioDAO();
    }

    @Override
    public ConvenioException validar(ConvenioModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
	    msg.append("O campo Nome  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new ConvenioException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(ConvenioModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	String deletado = "and deletado = false";
	filtro += " where 1=1 " + deletado; 

	if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
	    filtro += " and nome like('%" + vo.getNome() + "%')";
	}

	filtro += " order by id asc";

	return filtro;
    }

}
