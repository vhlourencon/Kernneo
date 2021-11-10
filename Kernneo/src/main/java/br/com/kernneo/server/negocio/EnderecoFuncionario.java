package br.com.kernneo.server.negocio;

import java.util.ArrayList;

import br.com.kernneo.client.exception.EnderecoFuncionarioException;
import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.EnderecoFuncionarioModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.dao.EnderecoFuncionarioDAO;

public class EnderecoFuncionario extends Negocio<EnderecoFuncionarioModel, EnderecoFuncionarioDAO, EnderecoFuncionarioException> {

    public EnderecoFuncionario() {
	super();
	dao = new EnderecoFuncionarioDAO();
    }

    @Override
    public EnderecoFuncionarioException validar(EnderecoFuncionarioModel vo) {
	StringBuffer msg = new StringBuffer("");
	// if (vo.getDescricao() == null || vo.getDescricao().trim().length() ==
	// 0) {
	// msg.append("O campo Descricao  é de preenchimento obrigatório!");
	// }

	if (msg.length() > 0)
	    return new EnderecoFuncionarioException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(EnderecoFuncionarioModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " where 1=1 ";

	// if (vo.getDescricao() != null && vo.getDescricao().trim().length() >
	// 0) {
	// filtro += " and descricao like('%" +vo.getDescricao() + "%')";
	// }

	filtro += " order by id asc";

	return filtro;
    }

    public EnderecoFuncionarioModel obterEnderecosPorFuncionario(FuncionarioModel funcionarioModel) throws EnderecoFuncionarioException {
	try {

	    return dao.obterEnderecosPorFuncionario(funcionarioModel);
	} catch (Exception e) {
	    throw (EnderecoFuncionarioException) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
	}
    }

    public ArrayList<EnderecoFuncionarioModel> obterListaPorId(Class classe, Long id) throws EnderecoFuncionarioException {
	try {

	    return dao.obterListaPorId(classe, id);
	} catch (Exception e) {
	    throw (EnderecoFuncionarioException) new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
	}
    }

    public EnderecoFuncionarioModel obterEnderecoPorId(Class classe, Long id) throws EnderecoFuncionarioException {
	EnderecoFuncionarioModel model = obterPorId(EnderecoFuncionarioModel.class, id);

	try {
	    return dao.obterPorId(classe, id);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new EnderecoFuncionarioException(e.getLocalizedMessage());
	}

    }
}
