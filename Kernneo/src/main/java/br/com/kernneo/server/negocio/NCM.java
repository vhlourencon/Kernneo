package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.NCMException;
import br.com.kernneo.client.model.NCMModel;
import br.com.kernneo.server.dao.NcmDAO;

public class NCM extends Negocio<NCMModel, NcmDAO, NCMException> {

    public NCM() {
	super();
	dao = new NcmDAO();
    }

    @Override
    public NCMException validar(NCMModel vo) {
	StringBuffer msg = new StringBuffer("");
//	if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
//	    msg.append("O campo Nome  é de preenchimento obrigatório!");
//	}
//	
//	if (vo.getNcm() == null || vo.getNcm().trim().length() == 0) {
//	    msg.append("O campo NCM  é de preenchimento obrigatório!");
//	}

	if (msg.length() > 0)
	    return new NCMException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(NCMModel vo) {
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
