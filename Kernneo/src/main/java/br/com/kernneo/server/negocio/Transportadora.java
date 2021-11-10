package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.TransportadoraException;
import br.com.kernneo.client.model.TransportadoraModel;
import br.com.kernneo.server.dao.TransportadoraDAO;

public class Transportadora extends Negocio<TransportadoraModel, TransportadoraDAO, TransportadoraException> {

    public Transportadora() {
	super();
	dao = new TransportadoraDAO();
    }

    @Override
    public TransportadoraException validar(TransportadoraModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getNomeFantasia() == null || vo.getNomeFantasia().trim().length() == 0) {
	    msg.append("O campo NOME  é de preenchimento obrigatório! \n");
	}

	if (vo.getCnpj() == null || vo.getCnpj().trim().length() == 0) {
	    msg.append("O campo CNPJ  é de preenchimento obrigatório! \n");
	}
	
	if (vo.getCnpj() == null || vo.getCnpj().contains("_") ) {
	    msg.append("O campo CNPJ  esta com formato inválido! \n");
	}
	

	if (msg.length() > 0)
	    return new TransportadoraException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(TransportadoraModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	String deletado = "and deletado = false";
	filtro += " where 1=1 " + deletado;

	if (vo.getNomeFantasia() != null && vo.getNomeFantasia().trim().length() > 0) {
	    filtro += " and nomeFantasia like('%" + vo.getNomeFantasia() + "%')";
	}
	
	if (vo.getCnpj() != null && vo.getCnpj().trim().length() > 0) {
	    filtro += " and cnpj like('%" + vo.getCnpj() + "%')";
	}

	filtro += " order by id asc";

	return filtro;
    }

}
