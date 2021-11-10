package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.PlanoContasException;
import br.com.kernneo.client.model.PlanoContasModel;
import br.com.kernneo.server.dao.PlanoContasDAO;

public class PlanoContas extends Negocio<PlanoContasModel, PlanoContasDAO, PlanoContasException> {

    public PlanoContas() {
	super();
	dao = new PlanoContasDAO();
    }

    @Override
    public PlanoContasException validar(PlanoContasModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new PlanoContasException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(PlanoContasModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	filtro += " where 1=1 "; 
	
	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
	}

	filtro += " order by id asc";

	return filtro;
    }

}
