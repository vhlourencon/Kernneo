package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.BancoException;
import br.com.kernneo.client.model.BancoModel;
import br.com.kernneo.server.dao.BancoDAO;

public class Banco extends Negocio<BancoModel, BancoDAO, BancoException> {

    public Banco() {
	super();
	dao = new BancoDAO();
    }

    @Override
    public BancoException validar(BancoModel vo) {
	StringBuffer msg = new StringBuffer("");
//	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
//	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
//	}

	if (msg.length() > 0)
	    return new BancoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(BancoModel vo) {
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
