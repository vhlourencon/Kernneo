package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.TicketException;
import br.com.kernneo.client.model.TicketModel;
import br.com.kernneo.server.dao.TicketDAO;

public class Ticket extends Negocio<TicketModel, TicketDAO, TicketException> {

    public Ticket() {
	super();
	dao = new TicketDAO();
    }

    @Override
    public TicketException validar(TicketModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new TicketException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(TicketModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	filtro += " where 1=1 "; 
	
	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
	}

	filtro += " order by id asc";

	return filtro;
    }

}
