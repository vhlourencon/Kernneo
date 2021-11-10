package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.OpcaoItemAlternativaException;
import br.com.kernneo.client.exception.OpcaoItemException;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.OpcaoItemAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.server.dao.OpcaoItemDAO;

public class OpcaoItem extends Negocio<OpcaoItemModel, OpcaoItemDAO, OpcaoItemException> {

	public OpcaoItem() {
		super();
		dao = new OpcaoItemDAO();
	}

	public ArrayList<OpcaoItemModel> obterTodosCompletosPorItem(ItemModel itemModel) throws OpcaoItemException {
		try {
			ArrayList<OpcaoItemModel> lista = dao.obterTodosPorItem(itemModel);
			if (lista != null) {
				for (OpcaoItemModel opcaoItemModel : lista) {
					opcaoItemModel = obterModelAtualizadoComDetalhes(opcaoItemModel);
					opcaoItemModel.setItem(itemModel);
				}
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OpcaoItemException(e.getLocalizedMessage());
		}

	}

	public ArrayList<OpcaoItemModel> obterTodosCompletos() throws OpcaoItemException {
		ArrayList<OpcaoItemModel> lista = obterTodos(OpcaoItemModel.class);

		if (lista != null) {
			for (OpcaoItemModel opcaoItemModel : lista) {
				opcaoItemModel = obterModelAtualizadoComDetalhes(opcaoItemModel);
			}
		}
		return lista;
	}

	public OpcaoItemModel obterModelAtualizadoComDetalhes(OpcaoItemModel model) throws OpcaoItemException {

		try {
			model.setListaDeAlternativaDoItem(new OpcaoItemAlternativa().obterTodosPorOpcaoItem(model));
			return model;
		} catch (OpcaoItemAlternativaException e) {

			e.printStackTrace();
			throw new OpcaoItemException(e.getLocalizedMessage());
		}

	}

	public OpcaoItemModel salvarCompleto(OpcaoItemModel vo) throws OpcaoItemException {
		OpcaoItemException exc = validar(vo);

		if (exc == null) {

			try {

				ArrayList<OpcaoItemAlternativaModel> listaAux = new ArrayList<OpcaoItemAlternativaModel>();
				for (OpcaoItemAlternativaModel opAlternativaModel : vo.getListaDeAlternativaDoItem()) {
					listaAux.add(opAlternativaModel);
				}

				vo = dao.salvar(vo);

				for (OpcaoItemAlternativaModel opcaoAlternativaModel : listaAux) {
					opcaoAlternativaModel.setOpcaoItem(vo);
					opcaoAlternativaModel = new OpcaoItemAlternativa().salvar(opcaoAlternativaModel);
				}

				vo.setListaDeAlternativaDoItem(listaAux);

				return vo;
			} catch (Exception e) {
				throw new OpcaoItemException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;

		}
	}

	@Override
	public OpcaoItemException validar(OpcaoItemModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new OpcaoItemException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(OpcaoItemModel vo) {
		String filtro = getGenericFiltro(vo);
		// filtro += " and 1=1 ";

		filtro += " order by id asc";

		return filtro;
	}

}
