package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.exception.CaixaException;
import br.com.kernneo.client.exception.CargoException;
import br.com.kernneo.client.exception.EstoqueException;
import br.com.kernneo.client.exception.MesaRecebimentoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.dao.CaixaDAO;
import br.com.kernneo.server.dao.EstoqueDAO;

public class Estoque extends Negocio<EstoqueModel, EstoqueDAO, EstoqueException> {

	public EstoqueModel obterEstoquePorProduto(ProdutoModel produtoModel) throws EstoqueException {

		try {
			return dao.obterEstoquePorProduto(produtoModel);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new EstoqueException("Erro ao obter informações: " + e.getMessage());
		}

	}

	public Estoque() {
		super();
		dao = new EstoqueDAO();
	}

	@Override
	public EstoqueException validar(EstoqueModel vo) {
		StringBuffer msg = new StringBuffer("");
		// if (vo.getUsuarioAbertura() == null) {
		// msg.append("O campo data Hora de Abertura é de preenchimento
		// obrigatório!");
		// }

		if (msg.length() > 0)
			return new EstoqueException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(EstoqueModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

		filtro += " order by id asc";

		return filtro;
	}

}
