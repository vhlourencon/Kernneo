package br.com.kernneo.server.negocio;

import java.util.ArrayList;

import br.com.kernneo.client.exception.EnderecoClienteException;
import br.com.kernneo.client.model.EnderecoModel;
import br.com.kernneo.server.dao.EnderecoDAO;

public class Endereco extends Negocio<EnderecoModel, EnderecoDAO, EnderecoClienteException> {

    public Endereco() {
	super();
	dao = new EnderecoDAO();
    }

    @Override
    public EnderecoClienteException validar(EnderecoModel vo) {
	StringBuffer msg = new StringBuffer("");
//	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
//	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
//	}

	if (msg.length() > 0)
	    return new EnderecoClienteException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(EnderecoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	filtro += " where 1=1 "; 
	
//	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
//	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
//	}

	filtro += " order by id asc";

	return filtro;
    }
    
    public ArrayList<EnderecoModel> obterListaPorId(Class classe, Long id) throws EnderecoClienteException {
	try {

	    return dao.obterListaPorId(classe, id);
	} catch (Exception e) {
	    throw (EnderecoClienteException) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
	}
    }
}
