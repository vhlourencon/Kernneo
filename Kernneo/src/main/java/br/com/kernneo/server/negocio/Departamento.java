package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.server.dao.DepartamentoDAO;

public class Departamento extends Negocio<DepartamentoModel, DepartamentoDAO, FornecedorException>
	{

		public Departamento() {
			super();
			dao = new DepartamentoDAO();
		}

		@Override
		public FornecedorException validar(DepartamentoModel vo) {
			StringBuffer msg = new StringBuffer("");
			if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
				msg.append("O campo Nome  é de preenchimento obrigatório!");
			}

			if (msg.length() > 0)
				return new FornecedorException(msg.toString());
			else
				return null;
		}

		@Override
		public String getSqlFiltro(DepartamentoModel vo) {
			String filtro = super.getSqlFiltro(vo);

			if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
				filtro += " and nome like('%" + vo.getNome() + "%')";
			}

			filtro += " order by id asc";

			return filtro;
		}

	}
