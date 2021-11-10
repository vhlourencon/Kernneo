package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.OpcaoItemAlternativaException;
import br.com.kernneo.client.model.OpcaoItemAlternativaModel;
import br.com.kernneo.client.model.OpcaoItemModel;
import br.com.kernneo.server.dao.OpcaoItemAlternativaDAO;

public class OpcaoItemAlternativa extends Negocio<OpcaoItemAlternativaModel, OpcaoItemAlternativaDAO, OpcaoItemAlternativaException> {

	public OpcaoItemAlternativa() {
		super();
		dao = new OpcaoItemAlternativaDAO();
	}

	public ArrayList<OpcaoItemAlternativaModel> obterTodosPorOpcaoItem(OpcaoItemModel opcaoItemModel) throws OpcaoItemAlternativaException {

		try {
			ArrayList<OpcaoItemAlternativaModel> lista = dao.obterTodosPorOpcaoItem(opcaoItemModel);
			if (lista != null) {
				for (OpcaoItemAlternativaModel opcaoItemAlternativaModel : lista) {
					opcaoItemAlternativaModel.setOpcaoItem(opcaoItemModel);;
				}
			}

			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OpcaoItemAlternativaException(e.getMessage());
		}

	}

	@Override
	public OpcaoItemAlternativaException validar(OpcaoItemAlternativaModel vo) {
		StringBuffer msg = new StringBuffer("");
//		if (vo.getProduto() == null) {
//			msg.append("O campo Produto  é de preenchimento obrigatório! \n");
//		}

		if (msg.length() > 0)
			return new OpcaoItemAlternativaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(OpcaoItemAlternativaModel vo) {
		String filtro = getGenericFiltro(vo);
		
		filtro += " order by id asc";

		return filtro;
	}

}
