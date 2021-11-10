package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.exception.OpcaoProdutoException;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.EstoqueLancamentoModel;
import br.com.kernneo.client.model.OpcaoProdutoModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.dao.ProdutoDAO;

public class Produto extends Negocio<ProdutoModel, ProdutoDAO, ProdutoException> {

	public Produto() {
		super();
		dao = new ProdutoDAO();
	}

	public ProdutoModel obterModelCompletoParaPDV(ProdutoModel produtoModel) throws ProdutoException {

		try {
			// ProdutoModel model = obterPorId(produtoModel);
			
			if (produtoModel.isOpcaoUsaConfigCategoria() == false) {
				produtoModel.setListaDeOpcaoDoProduto(new OpcaoProduto().obterTodosPorProduto(produtoModel));
			}
			return produtoModel;
		} catch (OpcaoProdutoException e) {
			e.printStackTrace();
			throw new ProdutoException(e.getLocalizedMessage());
		}

	}

	public ArrayList<ProdutoModel> obterProdutosPorCatetoriaParaPDV(CategoriaModel categoriaModel) throws ProdutoException {

		try {
			ArrayList<ProdutoModel> lista = dao.obterTodosAtivosPorCategoria(categoriaModel);

			if (lista != null) {
				for (ProdutoModel produtoModel : lista) {
					produtoModel = obterModelCompletoParaPDV(produtoModel);
					produtoModel.setCategoria(categoriaModel);
					;

				}
			}

			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProdutoException(e.getLocalizedMessage());
		}

	}

	public ProdutoModel obterProdutoCompletoPorId(Long id) throws ProdutoException {
		ProdutoModel model = obterPorId(ProdutoModel.class, id);

		try {
			model.getEstoque().setListaDeLancamentosDeEstoque(new EstoqueLancamento().obterLancamentosPorEstoque(model.getEstoque()));
			model.getComposicao().setListaDeComposicaoDoProduto(new ComposicaoProduto().obterProdutosDaComposicao(model.getComposicao()));
			model.setListaDeOpcaoDoProduto(new OpcaoProduto().obterTodosPorProduto(model));
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProdutoException(e.getLocalizedMessage());
		}

	}

	public ProdutoModel obterProdutoCompletoComEstoqueResumido(Long id) throws ProdutoException {
		ProdutoModel model = obterPorId(ProdutoModel.class, id);

		try {
			model.getEstoque().setListaDeLancamentosDeEstoque(new EstoqueLancamento().obter10UltimosLancamentosPorEstoque(model.getEstoque()));
			model.getComposicao().setListaDeComposicaoDoProduto(new ComposicaoProduto().obterProdutosDaComposicao(model.getComposicao()));
			model.setListaDeOpcaoDoProduto(new OpcaoProduto().obterTodosPorProduto(model));
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProdutoException(e.getLocalizedMessage());
		}

	}

	@Override
	public ProdutoException validar(ProdutoModel vo) {
		StringBuffer msg = new StringBuffer("");
		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome  é de preenchimento obrigatório! \n");
		}

		if (vo.getCategoria() == null) {
			msg.append("O campo Categoria  é de preenchimento obrigatório! \n");
		}

		if (vo.getCodigo() != null) {
			try {

				ProdutoModel modelAux = obterPorCodigo(vo.getCodigo());
				if (modelAux != null) {
					if (vo.getId() == null) {
						msg.append("Código já cadastrado! \n");
					} else if (vo.getId().compareTo(modelAux.getId()) != 0) {
						msg.append("Código já cadastrado! \n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("Erro ao verificar código do produto! \n");
			}
		} else {
			try {
				Long ulitmoCodigo = obterUltimoCodigo();

				ulitmoCodigo = ulitmoCodigo + 1L;

				vo.setCodigo(ulitmoCodigo);
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("Erro ao obter o código do produto!");
			}
		}

		if (msg.length() > 0)
			return new ProdutoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(ProdutoModel vo) {
		String filtro = super.getSqlFiltro(vo);

		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('%" + vo.getNome() + "%')";
		}

		if (vo.getCategoria() != null) {
			filtro += " and id_categoria = " + vo.getCategoria().getId();
		}

		filtro += " order by id asc";

		return filtro;
	}

	public ProdutoModel obterPorCodigo(Long codigo) throws Exception {

		try {

			return dao.obterPorCodigo(codigo);
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
		}

	}

	public Long obterUltimoCodigo() throws Exception {

		try {

			return dao.obterUltimoCodigo();
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
		}

	}

}
