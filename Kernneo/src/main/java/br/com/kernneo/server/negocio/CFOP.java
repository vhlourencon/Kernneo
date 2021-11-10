package br.com.kernneo.server.negocio;

import java.sql.SQLException;

import br.com.kernneo.client.exception.CFOPException;
import br.com.kernneo.client.model.CFOPModel;
import br.com.kernneo.server.dao.CFOpDAO;

public class CFOP extends Negocio<CFOPModel, CFOpDAO, CFOPException> {

    public CFOP() {
	super();
	dao = new CFOpDAO();
    }

    @Override
    public CFOPModel salvar(CFOPModel vo) throws CFOPException {
	CFOPException exc = validar(vo);
	if (exc == null) {
	    if (new CFOpDAO().obterPorCFOP(vo.getCfop()) == null) {
		try {
		    dao.salvar(vo);
		    return vo;
		} catch (SQLException e) {
		    throw new CFOPException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
		}
	    } else {
		throw new CFOPException("CFOP ja cadastrado!");
	    }
	} else {
	    throw exc;
	}
    }

    @Override
    public CFOPException validar(CFOPModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getId() == null) {
	    msg.append("O campo CFOP  é de preenchimento obrigatório! \n");
	}
	
	if ( vo.getId() == null ) { 
	    
	}

	if (msg.length() > 0)
	    return new CFOPException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CFOPModel vo) {
	String filtro = super.getSqlFiltro(vo);

	
	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) {
	    filtro += " and descricao like('%" + vo.getDescricao() + "%')";
	}

	filtro += " order by id asc";
	return filtro;
    }

    public CFOPModel obterPorCFOP(String CFOP) throws Exception {
	CFOPModel model;
	try {
	    model = dao.obterPorCFOP(CFOP);
	    return model;
	} catch (Exception e) {
	    throw new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
	}

    }

}
