package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.exception.CaixaException;
import br.com.kernneo.client.exception.CargoException;
import br.com.kernneo.client.exception.ComposicaoException;
import br.com.kernneo.client.exception.EstoqueException;
import br.com.kernneo.client.exception.MesaRecebimentoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.ComposicaoModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.dao.CaixaDAO;
import br.com.kernneo.server.dao.ComposicaoDAO;
import br.com.kernneo.server.dao.EstoqueDAO;

public class Composicao extends Negocio<ComposicaoModel, ComposicaoDAO, ComposicaoException> {

    public ComposicaoModel obterComposicaoPorProduto(ProdutoModel produtoModel) throws ComposicaoException {

	try {
	    return dao.obterComposicaoPorProduto(produtoModel);
	} catch (SQLException e) {

	    e.printStackTrace();
	    throw new ComposicaoException("Erro ao obter informações: " + e.getMessage());
	}

    }

    public Composicao() {
	super();
	dao = new ComposicaoDAO();
    }

    @Override
    public ComposicaoException validar(ComposicaoModel vo) {
	StringBuffer msg = new StringBuffer("");
	// if (vo.getUsuarioAbertura() == null) {
	// msg.append("O campo data Hora de Abertura  é de preenchimento obrigatório!");
	// }

	if (msg.length() > 0)
	    return new ComposicaoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(ComposicaoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " order by id asc";

	return filtro;
    }

}
