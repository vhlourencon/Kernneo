package br.com.kernneo.server.negocio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.kernneo.client.exception.CaixaException;
import br.com.kernneo.client.exception.EstoqueException;
import br.com.kernneo.client.exception.EstoqueLancamentoException;
import br.com.kernneo.client.exception.ImpressoraException;
import br.com.kernneo.client.exception.ItemException;
import br.com.kernneo.client.exception.OpcaoItemException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.EstoqueModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.OpcaoItemAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.server.dao.ItemDAO;
import br.com.kernneo.server.report.ComissaoReport;

public class Item extends Negocio<ItemModel, ItemDAO, ItemException> {

	public Item() {
		super();
		dao = new ItemDAO();
	}

	public ArrayList<String> verificarEstoqueDoItemCompleto(ItemModel itemModel) throws ItemException {
		ArrayList<String> lista =new ArrayList<String>();
		try {

			EstoqueModel estoqueItemModel = new Estoque().obterEstoquePorProduto(itemModel.getProduto());

			if (estoqueItemModel != null && estoqueItemModel.isAlertaPDV()) {
				Double quantidadeDoItem = itemModel.getCalcQuantidade();
				Double saldoItemAtual = estoqueItemModel.getSaldo() - quantidadeDoItem;

				if (saldoItemAtual.compareTo(estoqueItemModel.getSaldoMinimo()) < 0) {
					lista.add("O Produto: " + itemModel.getProduto().getNome() + " atingiu o estoque " + saldoItemAtual  + " de: " + estoqueItemModel.getSaldoMinimo() +"<br>");
				}

			}
			
			
			for (OpcaoItemModel opcaoItemModel : itemModel.getListaDeOpcaoDoItem()) {
				for (OpcaoItemAlternativaModel opcaoItemAlternativaModel : opcaoItemModel.getListaDeAlternativaDoItem()) {
					if (opcaoItemAlternativaModel.isSelecionada()) {
						EstoqueModel estoqueAlternativa = new Estoque().obterEstoquePorProduto(opcaoItemAlternativaModel.getAlternativa().getProduto());

						if (estoqueAlternativa != null && estoqueAlternativa.isAlertaPDV()) {
							Double quantidadeDaAlternativa = itemModel.getCalcQuantidade() * opcaoItemAlternativaModel.getQuantidade();
							Double saldoAlternativaAtual = estoqueAlternativa.getSaldo() - quantidadeDaAlternativa;
							if (saldoAlternativaAtual.compareTo(estoqueAlternativa.getSaldoMinimo()) < 0) {
								lista.add("O Produto: " + opcaoItemAlternativaModel.getAlternativa().getProduto().getNome() + " atingiu o estoque "+ saldoAlternativaAtual  + " de: " + estoqueAlternativa.getSaldoMinimo() +"<br>");
							}

						}
					}

				}

			}

		} catch (EstoqueException e) {
			e.printStackTrace();
			throw new ItemException("Erro ao verificar estoque do Item");
		}
		return lista;

	}

	public void imirimirRelatorioDeComissao(UsuarioModel usuarioModel, Date dataInicial, Date dataFinal, boolean relatorioCompleto) throws ItemException {
		try {
			ArrayList<ItemModel> listaDeItens = obterTodosPorUsuarioEdata(usuarioModel, dataInicial, dataFinal);
			ComissaoReport comissaoReport = new ComissaoReport(usuarioModel, listaDeItens, dataInicial, dataFinal, relatorioCompleto);
			comissaoReport.imprimir(LocalDeImpressao.caixa);
		} catch (ImpressoraException e) {
			e.printStackTrace();
			throw new ItemException("Erro ao imprimir relatorio");
		}

	}

	public ArrayList<ItemModel> obterTodosPorUsuarioEdata(UsuarioModel usuarioModel, Date dataInicial, Date dataFinal) throws ItemException {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			return dao.obterTodosPorUsuarioEdata(usuarioModel, dataInicial, dataFinal);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ItemException(e.getLocalizedMessage());
		}

	}

	public ItemModel inserirItem(UsuarioModel usuarioModel, MesaModel mesa, ItemModel item) throws ItemException {

		try {

			item.setMesa(mesa);

			ArrayList<ItemModel> listaDeItemConjugadoAux = new ArrayList<ItemModel>();
			for (ItemModel itemConjugadoModel : item.getListaDeItensConjugados()) {
				listaDeItemConjugadoAux.add(itemConjugadoModel);
			}

			ArrayList<OpcaoItemModel> listaDeOPcaoItem = new ArrayList<OpcaoItemModel>();
			for (OpcaoItemModel opcaoItemModel : item.getListaDeOpcaoDoItem()) {
				listaDeOPcaoItem.add(opcaoItemModel);
			}

			if (item.getId() == null) {
				item.setUsuario(usuarioModel);
				item.setDataHora(new Date());
				salvar(item);
				if (item.getListaDeItensConjugados().size() > 0) {
					for (ItemModel itemConjugado : listaDeItemConjugadoAux) {
						itemConjugado.setItemPai(item);
						itemConjugado.setMesa(item.getMesa());
						salvar(itemConjugado);

					}
				}
				new EstoqueLancamento().inserirItem(usuarioModel, item);

				for (OpcaoItemModel opcaoItemModel : listaDeOPcaoItem) {
					opcaoItemModel.setItem(item);
					opcaoItemModel = new OpcaoItem().salvarCompleto(opcaoItemModel);
				}
			} else {
				alterar(item);
			}

			return item;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ItemException(e.getLocalizedMessage());
		}

	}

	public ArrayList<ItemModel> obterTodosPorMesa(MesaModel pedido) throws ItemException {

		try {

			ArrayList<ItemModel> listaDeItem = dao.obterTodosPorMesa(pedido);
			if (listaDeItem != null) {
				for (ItemModel itemModel : listaDeItem) {
					itemModel.setMesa(pedido);
					itemModel.setListaDeItensConjugados(obterTodosPorItemPai(itemModel));
					itemModel.setListaDeOpcaoDoItem(new OpcaoItem().obterTodosCompletosPorItem(itemModel));
				}
			}

			return listaDeItem;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ItemException("Erro ao obter itens da mesa!");
		}

	}

	public void excluirItem(ItemModel item) throws ItemException {

		try {
			if (item.getListaDeItensConjugados().size() > 0) {
				excluirTodosDoMesmoItemPai(item);
			}
			excluir(item);
			new EstoqueLancamento().removerLancamentoDeitem(item);
		} catch (EstoqueLancamentoException e) {
			e.printStackTrace();
			throw new ItemException("Erro ao excluir lançamentos do estoque");
		}
	}

	public void excluirTodosDoMesmoItemPai(ItemModel item) throws ItemException {
		try {
			dao.excluirTodosDoMesmoItemPai(item);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ItemException("Erro ao excluir itens conjugados");
		}
	}

	public ArrayList<ItemModel> obterTodosPorItemPai(ItemModel itemPai) throws ItemException {

		try {

			ArrayList<ItemModel> listaDeItemConjugado = dao.obterTodosPorItemPai(itemPai);
			if (listaDeItemConjugado != null) {
				for (ItemModel itemModelConjugado : listaDeItemConjugado) {
					itemModelConjugado.setItemPai(itemPai);
				}
			}

			return listaDeItemConjugado;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ItemException("Erro ao obter itens!");
		}

	}

	@Override
	public ItemException validar(ItemModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new ItemException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(ItemModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

		filtro += " where 1=1 ";

		filtro += " order by id asc";

		return filtro;
	}

}
