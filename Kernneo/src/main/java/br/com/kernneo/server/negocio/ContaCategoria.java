package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.ContaCategoriaException;
import br.com.kernneo.client.model.ContaCategoriaModel;
import br.com.kernneo.server.dao.ContaCategoriuaDAO;

public class ContaCategoria extends Negocio<ContaCategoriaModel, ContaCategoriuaDAO, Exception> {

	public ContaCategoria() {
		super();
		dao = new ContaCategoriuaDAO();

	}

	@Override
	public ContaCategoriaException validar(ContaCategoriaModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new ContaCategoriaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(ContaCategoriaModel vo) {
		String filtro = super.getSqlFiltro(vo);

		filtro += " order by id asc";

		return filtro;
	}

}
