package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.BairroException;
import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.server.dao.BairroDAO;

public class Bairro extends Negocio<BairroModel, BairroDAO, BairroException> {

    public Bairro() {
	super();
	dao = new BairroDAO();
    }

    @Override
    public BairroException validar(BairroModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
	    msg.append("O campo Nome  é de preenchimento obrigatório! \n");
	}
	
	if (vo.getCidade() == null) { 
	    msg.append("O campo cidade  é de preenchimento obrigatorio! \n");
	}

	if (msg.length() > 0)
	    return new BairroException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(BairroModel vo) {
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
