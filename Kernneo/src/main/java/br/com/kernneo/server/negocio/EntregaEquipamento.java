package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.EntregaEquipamentException;
import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.EntregaEquipamentoModel;
import br.com.kernneo.server.dao.DepartamentoDAO;
import br.com.kernneo.server.dao.EntregaEquipamentoDAO;

public class EntregaEquipamento extends Negocio<EntregaEquipamentoModel,EntregaEquipamentoDAO , EntregaEquipamentException>
	{

		public EntregaEquipamento() {
			super();
			dao = new EntregaEquipamentoDAO();
		}

		@Override
		public EntregaEquipamentException validar(EntregaEquipamentoModel vo) {
			StringBuffer msg = new StringBuffer("");
			//if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			//	msg.append("O campo Nome  é de preenchimento obrigatório!");
		//	}

			if (msg.length() > 0)
				return new EntregaEquipamentException(msg.toString());
			else
				return null;
		}

		@Override
		public String getSqlFiltro(EntregaEquipamentoModel vo) {
			String filtro = super.getSqlFiltro(vo);

			//if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			//	filtro += " and nome like('%" + vo.getNome() + "%')";
//			}

			filtro += " order by id asc";

			return filtro;
		}

	}
