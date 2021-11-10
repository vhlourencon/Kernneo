package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.ComposicaoException;
import br.com.kernneo.client.exception.ComposicaoProdutoException;
import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.model.ComposicaoModel;
import br.com.kernneo.client.model.ComposicaoProdutoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.dao.ComposicaoProdutoDAO;

public class ComposicaoProduto extends Negocio<ComposicaoProdutoModel, ComposicaoProdutoDAO, ComposicaoProdutoException> {

    public ArrayList<ComposicaoProdutoModel> obterProdutosDaComposicao(ComposicaoModel composicaoModel) throws EstoqueLancamentoException {

	try {
	    ArrayList<ComposicaoProdutoModel> listaDeComposicao = null;
	    if (composicaoModel.getId() != null) {
		listaDeComposicao = dao.obterProdutosDaComposicao(composicaoModel);
	    }

	    if (listaDeComposicao == null) {
		listaDeComposicao = new ArrayList<ComposicaoProdutoModel>();
	    }
	    return listaDeComposicao;
	} catch (SQLException e) {
	    e.printStackTrace();
	    throw new EstoqueLancamentoException("Erro ao obter lista de produtos ca Composicao!");
	}

    }

    public void inserirProdutosDaComposicao(  ProdutoModel produtoModel, ArrayList<ComposicaoProdutoModel> listaDeProdutoDaComposicao) throws ComposicaoProdutoException {

	try {
	    ComposicaoModel composicaoAux = new Composicao().obterComposicaoPorProduto(produtoModel);
	    if (composicaoAux == null) {
		composicaoAux = produtoModel.getComposicao();
		composicaoAux.setProduto(produtoModel);
		produtoModel.setComposicao(composicaoAux);
		new Composicao().salvar(composicaoAux);
		composicaoAux = new Composicao().obterComposicaoPorProduto(produtoModel);

	    } else  {
		new Composicao().alterar(produtoModel.getComposicao());
	    }

	    for (ComposicaoProdutoModel composicaoProdutoModel : listaDeProdutoDaComposicao) {
		if (composicaoProdutoModel.getId() == null) {
		    composicaoProdutoModel.setComposicao(composicaoAux);

		    salvar(composicaoProdutoModel);
		}

	    }

	} catch (ComposicaoException e) {
	    e.printStackTrace();
	    throw new ComposicaoProdutoException("Erro ao obter informações do estoque");
	}

    }

    public ComposicaoProduto() {
	super();
	dao = new ComposicaoProdutoDAO();
    }

    @Override
    public ComposicaoProdutoException validar(ComposicaoProdutoModel vo) {
	StringBuffer msg = new StringBuffer("");

	if (msg.length() > 0)
	    return new ComposicaoProdutoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(ComposicaoProdutoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " where 1=1 ";

	filtro += " order by id asc";

	return filtro;
    }

}
