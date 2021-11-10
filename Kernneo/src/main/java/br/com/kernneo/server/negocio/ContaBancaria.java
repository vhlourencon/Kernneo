package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.ContaBancariaException;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.server.dao.ContaBancariaDAO;

public class ContaBancaria extends Negocio<ContaBancariaModel, ContaBancariaDAO, ContaBancariaException> {

    public ContaBancaria() {
	super();
	dao = new ContaBancariaDAO();
    }

    @Override
    public ContaBancariaException validar(ContaBancariaModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new ContaBancariaException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(ContaBancariaModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	filtro += " where 1=1 "; 
	
	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
	}

	filtro += " order by id asc";

	return filtro;
    }

}
