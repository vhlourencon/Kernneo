package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.OpcaoCategoriaException;
import br.com.kernneo.client.exception.OpcaoProdutoException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.OpcaoCategoriaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.client.model.OpcaoProdutoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.dao.OpcaoCategoriaDAO;
import br.com.kernneo.server.dao.OpcaoProdutoDAO;

public class OpcaoProduto extends Negocio<OpcaoProdutoModel, OpcaoProdutoDAO, OpcaoProdutoException> {

	public OpcaoProduto() {
		super();
		dao = new OpcaoProdutoDAO();
	}

	public ArrayList<OpcaoProdutoModel> obterTodosPorProduto(ProdutoModel produtoModel) throws OpcaoProdutoException {

		try {

			ArrayList<OpcaoProdutoModel> listaDeOpcaoProduto = dao.obterTodosPorProduto(produtoModel);
			if (listaDeOpcaoProduto != null) {
				for (OpcaoProdutoModel opcaoProdutoModel : listaDeOpcaoProduto) {
					opcaoProdutoModel.setProduto(produtoModel);
					OpcaoModel opcaoModel = opcaoProdutoModel.getOpcao();
					opcaoModel = new Opcao().obterModelAtualizadoComDetalhes(opcaoModel);
				}
			}
			return listaDeOpcaoProduto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new OpcaoProdutoException(e.getMessage());
		}

	}

	@Override
	public OpcaoProdutoException validar(OpcaoProdutoModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getProduto() == null) {
			msg.append("O campo Produto  é de preenchimento obrigatório! \n");
		}

		if (vo.getOpcao() == null) {
			msg.append("O campo Opção  é de preenchimento obrigatório! \n");
		}

		if (msg.length() > 0)
			return new OpcaoProdutoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(OpcaoProdutoModel vo) {
		String filtro = getGenericFiltro(vo);

		filtro += " order by id asc";

		return filtro;
	}

}
