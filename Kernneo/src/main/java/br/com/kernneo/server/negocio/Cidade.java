package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.server.dao.CidadeDAO;

public class Cidade extends Negocio<CidadeModel, CidadeDAO, CidadeException> {

    public Cidade() {
	super();
	dao = new CidadeDAO();
    }

    public CidadeModel obterPorCodigoIbge(Integer codigo) throws CidadeException {
	try {
	    return dao.obterPorCodigoIbge(codigo);
	} catch (SQLException e) {
	    e.printStackTrace();
	    throw new CidadeException(e.getLocalizedMessage());
	}
    }

    public ArrayList<CidadeModel> obterTodasPorEstado(EstadoModel estadoModel) throws CidadeException {
	try {
	    return dao.obterTodasPorEstado(estadoModel);
	} catch (SQLException e) {

	    e.printStackTrace();
	    throw new CidadeException(e.getLocalizedMessage());
	}
    }

    @Override
    public CidadeException validar(CidadeModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
	    msg.append("O campo Nome  é de preenchimento obrigatório! \n");
	}
	
	if ( vo.getEstado() == null) { 
	    msg.append("O estado nao pode ser nulo \n");
	}

	if (vo.getCodigoIbge() == null) {
	    msg.append("O campo código Ibge é obrigatório \n");
	} else {
	 
	    try {
		 CidadeModel cidadeAux = obterPorCodigoIbge(vo.getCodigoIbge());
		if ( cidadeAux != null && vo.getId() != cidadeAux.getId()) { 
		    msg.append("Código ibge já cadastrao \n");
		}
	    } catch (CidadeException e) {
		e.printStackTrace();
		msg.append("erro ao comparar códigos das cidades \n");
	    }

	}

	if (msg.length() > 0)
	    return new CidadeException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CidadeModel vo) {
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
