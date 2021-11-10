package br.com.kernneo.server.negocio;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.kernneo.client.exception.EnderecoClienteException;
import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.EnderecoClienteModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.dao.EnderecoClienteDAO;

public class EnderecoCliente extends Negocio<EnderecoClienteModel, EnderecoClienteDAO, EnderecoClienteException> {

    public EnderecoCliente() {
	super();
	dao = new EnderecoClienteDAO();
    }

    @Override
    public EnderecoClienteException validar(EnderecoClienteModel vo) {
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
    public String getSqlFiltro(EnderecoClienteModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	filtro += " where 1=1 "; 
	
//	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) { 
//	    filtro += " and descricao like('%" +vo.getDescricao() +  "%')"; 
//	}

	filtro += " order by id asc";

	return filtro;
    }
    
    
    public ArrayList<EnderecoClienteModel> obterEnderecosPorCliente(ClienteModel clienteModel) throws EnderecoClienteException {
   	try {

   	    ArrayList<EnderecoClienteModel> lista =  dao.obterEnderecosPorCliente(clienteModel);
   	    
   	    if ( lista != null) { 
   	    	for (EnderecoClienteModel enderecoClienteModel : lista) {
				enderecoClienteModel.setCliente(clienteModel);
			}
   	    }
   	    
   	    return lista;
   	} catch (Exception e) {
   	    throw (EnderecoClienteException) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
   	}
       }
    
   
    public EnderecoClienteModel obterEnderecoPorId(Class classe, Long id) throws EnderecoClienteException {
	EnderecoClienteModel model = obterPorId(EnderecoClienteModel.class, id);

	try {
	    return dao.obterPorId(classe, id);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new EnderecoClienteException(e.getLocalizedMessage());
	}

    }
}
