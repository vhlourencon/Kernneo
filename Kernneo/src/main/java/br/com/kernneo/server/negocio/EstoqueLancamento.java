package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.exception.EstoqueException;
import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.model.ComposicaoProdutoModel;
import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.client.model.EstoqueLancamentoModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoEstoqueTypes;
import br.com.kernneo.server.SendMailUsingAuthentication;
import br.com.kernneo.server.dao.EstoqueLancamentoDAO;

public class EstoqueLancamento extends Negocio<EstoqueLancamentoModel, EstoqueLancamentoDAO, EstoqueLancamentoException> {

	public ArrayList<EstoqueLancamentoModel> obterLancamentosPorEstoque(EstoqueModel estoque) throws EstoqueLancamentoException {

		try {
			ArrayList<EstoqueLancamentoModel> listaDeLancamentos = null;
			if (estoque.getId() != null) {
				listaDeLancamentos = dao.obterTodosPorEstoque(estoque);
			}

			if (listaDeLancamentos == null) {
				listaDeLancamentos = new ArrayList<EstoqueLancamentoModel>();
			}
			return listaDeLancamentos;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EstoqueLancamentoException("Erro ao obter lista de lançamentos de estoque!");
		}

	}

	public ArrayList<EstoqueLancamentoModel> obter10UltimosLancamentosPorEstoque(EstoqueModel estoque) throws EstoqueLancamentoException {

		try {
			ArrayList<EstoqueLancamentoModel> listaDeLancamentos = null;
			if (estoque.getId() != null) {
				listaDeLancamentos = dao.obterUltimos10(estoque);
			}

			if (listaDeLancamentos == null) {
				listaDeLancamentos = new ArrayList<EstoqueLancamentoModel>();
			}
			return listaDeLancamentos;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EstoqueLancamentoException("Erro ao obter lista de lançamentos de estoque!");
		}

	}

	public void inserirItem(UsuarioModel usuario, ItemModel item) throws EstoqueLancamentoException {

		inserirLancamentoDeItemEComposicao(usuario, item, item.getProduto(), item.getQuantidade());

		if (item.getListaDeOpcaoDoItem().size() > 0) {
			for (OpcaoItemModel opcaoItemModel : item.getListaDeOpcaoDoItem()) {
				for (OpcaoItemAlternativaModel opcaoItemAlternativaModel : opcaoItemModel.getListaDeAlternativaDoItem()) {

					if (opcaoItemAlternativaModel.isSelecionada() && opcaoItemAlternativaModel.getAlternativa().getProduto() != null) {
						Double quantidadeDoItem = item.getCalcQuantidade() * opcaoItemAlternativaModel.getQuantidade();
						inserirLancamentoDeItemEComposicao(usuario,item, opcaoItemAlternativaModel.getAlternativa().getProduto(), quantidadeDoItem);
					}
				}
			}
		}

		if (item.getListaDeItensConjugados().size() > 0) {
			for (ItemModel itemConjugadoModel : item.getListaDeItensConjugados()) {

				inserirLancamentoDeItemEComposicao(usuario,itemConjugadoModel, itemConjugadoModel.getProduto(), itemConjugadoModel.getQuantidade());

			}
		}

	}

	public void inserirLancamentoDeItemEComposicao(UsuarioModel usuario, ItemModel itemModel, ProdutoModel produtoModel, Double quantidade) throws EstoqueLancamentoException {

		EstoqueLancamentoModel estoqueLancamentoItemModel = new EstoqueLancamentoModel();
		estoqueLancamentoItemModel.setUsuario(usuario);
		estoqueLancamentoItemModel.setQuantidade(quantidade);
		estoqueLancamentoItemModel.setMovimentacaoEstoqueTypes(MovimentacaoEstoqueTypes.saida);
		estoqueLancamentoItemModel.setObservacao(getObservacaoDeLancamentoDoITem(itemModel));
		estoqueLancamentoItemModel.setItem(itemModel);

		inserirLancamento(usuario,produtoModel, estoqueLancamentoItemModel);

		if (produtoModel.getComposicao().isProdutoComposto()) {

			ArrayList<ComposicaoProdutoModel> listaDeComposicaoDoConjugado = new ComposicaoProduto().obterProdutosDaComposicao(produtoModel.getComposicao());
			if (listaDeComposicaoDoConjugado != null) {
				for (ComposicaoProdutoModel composicaoProdutoModel : listaDeComposicaoDoConjugado) {

					EstoqueLancamentoModel estoqueLancamentoComposicaoModel = new EstoqueLancamentoModel();
					estoqueLancamentoComposicaoModel.setUsuario(usuario);
					estoqueLancamentoComposicaoModel.setQuantidade(quantidade * composicaoProdutoModel.getQuantidade());
					estoqueLancamentoComposicaoModel.setMovimentacaoEstoqueTypes(MovimentacaoEstoqueTypes.saida);
					estoqueLancamentoComposicaoModel.setObservacao(getObservacaoDeLancamentoDoITem(itemModel));
					estoqueLancamentoComposicaoModel.setItem(itemModel);

					inserirLancamento(usuario, composicaoProdutoModel.getProduto(), estoqueLancamentoComposicaoModel);
				}
			}

		}
	}

	public void removerLancamentoDeitem(final ItemModel item) throws EstoqueLancamentoException {

		try {
			ArrayList<EstoqueLancamentoModel> listaDelancamentos = dao.obterTodosPorItem(item);
			for (EstoqueLancamentoModel estoqueLancamentoModel : listaDelancamentos) {
				removerLancamento(item.getUsuario(), estoqueLancamentoModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String getObservacaoDeLancamentoDoITem(ItemModel item) {
		String descricao = "";

		if (item.getMesa().getTipo() == MesaTipo.mesa) {

			descricao = "Pedido mesa: " + item.getMesa().getNumero();
		} else {
			descricao = "Pedido nº: " + item.getMesa().getId();

		}

		return descricao;
	}

	public void inserirLancamento(UsuarioModel usuario,ProdutoModel produtoModel, EstoqueLancamentoModel estoqueLancamentoModel) throws EstoqueLancamentoException {

		try {
			EstoqueModel estoqueAux = new Estoque().obterEstoquePorProduto(produtoModel);
			if (estoqueAux == null) {
				estoqueAux = produtoModel.getEstoque();
				estoqueAux.setProduto(produtoModel);
				produtoModel.setEstoque(estoqueAux);
				new Estoque().alterar(estoqueAux);
				estoqueAux = new Estoque().obterEstoquePorProduto(produtoModel);
			}

			if (estoqueLancamentoModel.getId() == null) {
				estoqueLancamentoModel.setUsuario(usuario);
				estoqueLancamentoModel.setEstoque(estoqueAux);
				estoqueLancamentoModel.setDataHora(new Date());

				if (estoqueLancamentoModel.getMovimentacaoEstoqueTypes() == MovimentacaoEstoqueTypes.entrada) {
					estoqueAux.setUltimoFornecedor(estoqueLancamentoModel.getFornecedor());
				}

				Double saldoAtual = estoqueAux.getSaldo();

				boolean verificar = false;
				if (saldoAtual.compareTo(estoqueAux.getSaldoMinimo()) > 0) {
					verificar = true;
				}

				if (estoqueLancamentoModel.getMovimentacaoEstoqueTypes() == MovimentacaoEstoqueTypes.entrada) {
					estoqueAux.setSaldo(estoqueAux.getSaldo() + estoqueLancamentoModel.getQuantidade());
				} else if (estoqueLancamentoModel.getMovimentacaoEstoqueTypes() == MovimentacaoEstoqueTypes.saida) {
					estoqueAux.setSaldo(estoqueAux.getSaldo() - estoqueLancamentoModel.getQuantidade());
				}

				Double saldoAtualAtualizado = estoqueAux.getSaldo();
				if (verificar && saldoAtualAtualizado.compareTo(estoqueAux.getSaldoMinimo()) <= 0) {

					try {

						EmpresaModel empresaModel = new Empresa().obterEmpresa();
						if (empresaModel.getEmailUsuarioFechamentoCaixa() != null) {
							String email = "<div style='margin-bottom: -6px; padding: 30px;background: #E2E2E2;'> Olá" + empresaModel.getEmailUsuarioFechamentoCaixa().getNome() + ", o seu produto <b> " + produtoModel.getNome() + "</b> - Código: " + produtoModel.getCodigo() + ",  atingiu o estoque mínimo de <b>" + estoqueAux.getSaldoMinimo() + " itens</b>. </div> ";

							String destinatario[] = new String[] { empresaModel.getEmailUsuarioFechamentoCaixa().getEmail() };
							SendMailUsingAuthentication sendMailUsingAuthentication = new SendMailUsingAuthentication();
							sendMailUsingAuthentication.postMail(new Empresa().obterEmpresa(), destinatario, "Alerta de Estoque Mínimo", sendMailUsingAuthentication.getEmailHTML(email));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				salvar(estoqueLancamentoModel);
			}

			new Estoque().alterar(estoqueAux);

		} catch (EstoqueException e) {
			e.printStackTrace();
			throw new EstoqueLancamentoException("Erro ao obter informações do estoque");
		}

	}

	public void removerLancamento(UsuarioModel usuarioModel, EstoqueLancamentoModel estoqueLancamentoModel) throws EstoqueLancamentoException {

		try {
			EstoqueModel estoqueAux = estoqueLancamentoModel.getEstoque();
			if (estoqueAux != null) {
				// estoqueLancamentoModel.setEstoque(null);
				estoqueLancamentoModel.setDataHora(new Date());
				if (estoqueLancamentoModel.getMovimentacaoEstoqueTypes() == MovimentacaoEstoqueTypes.saida) {
					estoqueAux.setSaldo(estoqueAux.getSaldo() + estoqueLancamentoModel.getQuantidade());
				} else if (estoqueLancamentoModel.getMovimentacaoEstoqueTypes() == MovimentacaoEstoqueTypes.entrada) {
					estoqueAux.setSaldo(estoqueAux.getSaldo() - estoqueLancamentoModel.getQuantidade());
				}
				new Estoque().alterar(estoqueAux);
				excluir(estoqueLancamentoModel);
			}

		} catch (EstoqueException e) {
			e.printStackTrace();
			throw new EstoqueLancamentoException("Erro ao obter informações do estoque");
		}

	}

	public EstoqueLancamento() {
		super();
		dao = new EstoqueLancamentoDAO();
	}

	@Override
	public EstoqueLancamentoException validar(EstoqueLancamentoModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new EstoqueLancamentoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(EstoqueLancamentoModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

		filtro += " where 1=1 ";

		filtro += " order by id asc";

		return filtro;
	}

}
