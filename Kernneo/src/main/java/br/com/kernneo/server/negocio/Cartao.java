package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.CartaoException;
import br.com.kernneo.client.model.CartaoModel;
import br.com.kernneo.server.dao.CartaoDAO;

public class Cartao extends Negocio<CartaoModel, CartaoDAO, CartaoException> {

    public Cartao() {
	super();
	dao = new CartaoDAO();
    }

    @Override
    public CartaoException validar(CartaoModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new CartaoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CartaoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	filtro += " where 1=1 "; 
	
	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
	}

	filtro += " order by id asc";

	return filtro;
    }

}
