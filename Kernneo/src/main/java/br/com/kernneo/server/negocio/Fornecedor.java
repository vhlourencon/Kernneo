package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.ClienteException;
import br.com.kernneo.client.exception.EnderecoClienteException;
import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.exception.EnderecoFornecedorException;
import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.exception.EnderecoFornecedorException;
import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.ClienteDAO;
import br.com.kernneo.server.dao.FornecedorDAO;

public class Fornecedor extends Negocio<FornecedorModel, FornecedorDAO, FornecedorException> {

    public Fornecedor() {
	super();
	dao = new FornecedorDAO();
    }
    
  
    
    
    @Override
    public FornecedorException validar(FornecedorModel fornecedorModel) {
	StringBuffer msg = new StringBuffer("");
	

	if (msg.length() > 0)
	    return new FornecedorException(msg.toString());
	else
	    return null;
    }
    
    
    
   

    @Override
    public String getSqlFiltro(FornecedorModel vo) {
	String filtro = getGenericFiltro(vo);

	filtro += " and 1=1 " ; 
	
	if (vo.getFantasia() != null && vo.getFantasia().trim().length() > 0) {
	    filtro += " and fantasia like('%" + vo.getFantasia() + "%')";
	}

	filtro += " order by id asc";

	return filtro;

    }
    
  

}
