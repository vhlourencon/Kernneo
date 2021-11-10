package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.OpcaoAlternativaException;
import br.com.kernneo.client.model.OpcaoAlternativaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.server.dao.OpcaoAlternativaDAO;

public class OpcaoAlternativa extends Negocio<OpcaoAlternativaModel, OpcaoAlternativaDAO, OpcaoAlternativaException> {

	public OpcaoAlternativa() {
		super();
		dao = new OpcaoAlternativaDAO();
	}

	public ArrayList<OpcaoAlternativaModel> obterTodosPorOpcao(OpcaoModel opcaoModel) throws OpcaoAlternativaException {

		try {
			ArrayList<OpcaoAlternativaModel> lista = dao.obterTodosPorOpcao(opcaoModel);
			if (lista != null) {
				for (OpcaoAlternativaModel opcaoAlternativaModel : lista) {
					opcaoAlternativaModel.setOpcao(opcaoModel);
				}
			}

			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OpcaoAlternativaException(e.getMessage());
		}

	}

	@Override
	public OpcaoAlternativaException validar(OpcaoAlternativaModel vo) {
		StringBuffer msg = new StringBuffer("");
//		if (vo.getProduto() == null) {
//			msg.append("O campo Produto  é de preenchimento obrigatório! \n");
//		}

		if (msg.length() > 0)
			return new OpcaoAlternativaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(OpcaoAlternativaModel vo) {
		String filtro = getGenericFiltro(vo);
		// filtro += " where 1=1 ";
		//
		// if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
		// filtro += " and nome like('%" + vo.getNome() + "%')";
		// }

		filtro += " order by id asc";

		return filtro;
	}

}
