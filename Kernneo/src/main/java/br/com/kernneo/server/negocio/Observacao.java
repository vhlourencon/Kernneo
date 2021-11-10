package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.ObservacaoException;
import br.com.kernneo.client.model.ObservacaoModel;
import br.com.kernneo.server.dao.ObservacaoDAO;

public class Observacao extends Negocio<ObservacaoModel, ObservacaoDAO, ObservacaoException> {

    public Observacao() {
	super();
	dao = new ObservacaoDAO();
    }

    @Override
    public ObservacaoException validar(ObservacaoModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new ObservacaoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(ObservacaoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	String deletado = "and deletado = false";
	filtro += " where 1=1 " + deletado; 
	
	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
	}

	filtro += " order by id asc";

	return filtro;
    }

}
