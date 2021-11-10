package br.com.kernneo.server.negocio;

import java.util.ArrayList;

import br.com.kernneo.client.exception.EnderecoFornecedorException;
import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.EnderecoFornecedorModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.dao.EnderecoFornecedorDAO;

public class EnderecoFornecedor extends Negocio<EnderecoFornecedorModel, EnderecoFornecedorDAO, EnderecoFornecedorException> {

    public EnderecoFornecedor() {
	super();
	dao = new EnderecoFornecedorDAO();
    }

    @Override
    public EnderecoFornecedorException validar(EnderecoFornecedorModel vo) {
	StringBuffer msg = new StringBuffer("");
	// if (vo.getDescricao() == null || vo.getDescricao().trim().length() ==
	// 0) {
	// msg.append("O campo Descricao  é de preenchimento obrigatório!");
	// }

	if (msg.length() > 0)
	    return new EnderecoFornecedorException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(EnderecoFornecedorModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " where 1=1 ";

	// if (vo.getDescricao() != null && vo.getDescricao().trim().length() >
	// 0) {
	// filtro += " and descricao like('%" +vo.getDescricao() + "%')";
	// }

	filtro += " order by id asc";

	return filtro;
    }

    public ArrayList<EnderecoFornecedorModel> obterEnderecosPorFornecedor(FornecedorModel clienteModel) throws EnderecoFornecedorException {
	try {
	    return dao.obterEnderecosPorFornecedor(clienteModel);
	} catch (Exception e) {
	    throw (EnderecoFornecedorException) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
	}
    }

    public ArrayList<EnderecoFornecedorModel> obterListaPorId(Class classe, Long id) throws EnderecoFornecedorException {
	try {

	    return dao.obterListaPorId(classe, id);
	} catch (Exception e) {
	    throw (EnderecoFornecedorException) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
	}
    }

    public EnderecoFornecedorModel obterEnderecoPorId(Class classe, Long id) throws EnderecoFornecedorException {
	EnderecoFornecedorModel model = obterPorId(EnderecoFornecedorModel.class, id);

	try {
	    return dao.obterPorId(classe, id);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new EnderecoFornecedorException(e.getLocalizedMessage());
	}

    }
}
