package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.OpcaoCategoriaException;
import br.com.kernneo.client.exception.OpcaoException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.OpcaoCategoriaModel;
import br.com.kernneo.client.model.OpcaoModel;
import br.com.kernneo.server.dao.OpcaoCategoriaDAO;

public class OpcaoCategoria extends Negocio<OpcaoCategoriaModel, OpcaoCategoriaDAO, OpcaoCategoriaException> {

	public OpcaoCategoria() {
		super();
		dao = new OpcaoCategoriaDAO();
	}

	public ArrayList<OpcaoCategoriaModel> obterTodosPorCategoria(CategoriaModel categoriaModel) throws OpcaoCategoriaException {

		try {

			ArrayList<OpcaoCategoriaModel> listaDeOpcoesDaCategoria = dao.obterTodosPorCategoria(categoriaModel);
			if (listaDeOpcoesDaCategoria != null) {
				for (OpcaoCategoriaModel opcaoCategoriaModel : listaDeOpcoesDaCategoria) {
					opcaoCategoriaModel.setCategoria(categoriaModel);
					OpcaoModel opcaoModel = opcaoCategoriaModel.getOpcao();
					opcaoModel = new Opcao().obterModelAtualizadoComDetalhes(opcaoModel);
				}
			}
			return listaDeOpcoesDaCategoria;
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpcaoCategoriaException(e.getMessage());
		}

	}

	@Override
	public OpcaoCategoriaException validar(OpcaoCategoriaModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getCategoria() == null) {
			msg.append("O campo Categoria  é de preenchimento obrigatório! \n");
		}

		if (vo.getOpcao() == null) {
			msg.append("O campo Opção  é de preenchimento obrigatório! \n");
		}

		if (msg.length() > 0)
			return new OpcaoCategoriaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(OpcaoCategoriaModel vo) {
		String filtro = getGenericFiltro(vo);

		filtro += " order by id asc";

		return filtro;
	}

}
