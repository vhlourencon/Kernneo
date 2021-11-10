package br.com.kernneo.server.negocio;

import java.sql.SQLException;

import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.server.dao.EstadoDAO;

public class Estado extends Negocio<EstadoModel, EstadoDAO, EstadoException> {

    public Estado() {
	super();
	dao = new EstadoDAO();
    }
    
    public EstadoModel obterPorCodigoIbge(Integer codigo) throws EstadoException {
  	try {
  	    return dao.obterPorCodigoIbge(codigo);
  	} catch (SQLException e) {
  	    e.printStackTrace();
  	    throw new EstadoException(e.getLocalizedMessage());
  	}
      }
    
    

    @Override
    public EstadoException validar(EstadoModel vo) {
	StringBuffer msg = new StringBuffer("");

	try {
	    EstadoModel estadoAux = obterPorSigla(vo.getSigla());
	    if (estadoAux != null && estadoAux.getId() != vo.getId()) {
		msg.append("Sigla ja cadastrada \n");
	    }
	} catch (EstadoException e) {
	    e.printStackTrace();
	    msg.append(e.getLocalizedMessage() + "\n");
	}
	
	if (vo.getCodigoIbge() == null) {
	    msg.append("O campo código Ibge é obrigatório \n");
	} else {
	 
	    try {
		 EstadoModel estadoAux = obterPorCodigoIbge(vo.getCodigoIbge());
		if ( estadoAux != null && vo.getId() != estadoAux.getId()) { 
		    msg.append("Código ibge já cadastrao \n");
		}
	    } catch (EstadoException e) {
		e.printStackTrace();
		msg.append("erro ao comparar códigos das cidades \n");
	    }

	}

	
	if (msg.length() > 0) {
	    return new EstadoException(msg.toString());
	} else {
	    return null;
	}
    }

    public EstadoModel obterPorSigla(String sigla) throws EstadoException {
	if (sigla == null) {
	    throw new EstadoException("O cmapo sigla nao pode ser null");
	} else {
	    try {
		return dao.obterPorSigla(sigla);
	    } catch (SQLException e) {
		e.printStackTrace();
		throw new EstadoException(e.getLocalizedMessage());
	    }
	}

    }

    @Override
    public String getSqlFiltro(EstadoModel vo) {
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
