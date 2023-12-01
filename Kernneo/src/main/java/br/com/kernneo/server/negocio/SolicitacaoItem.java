package br.com.kernneo.server.negocio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.kernneo.client.exception.SolicitacaoItemException;
import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.exception.OcorrenciaException;
import br.com.kernneo.client.exception.RegulacaoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.SolicitacaoItemModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.OcorrenciaModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.dao.SolicitacaoItemDAO;
import br.com.kernneo.server.dao.MovimentacaoDAO;
import br.com.kernneo.server.dao.OcorrenciaDAO;
import br.com.kernneo.server.dao.RegulacaoDAO;

public class SolicitacaoItem extends Negocio<SolicitacaoItemModel, SolicitacaoItemDAO, SolicitacaoItemException>
	{

		public SolicitacaoItem() {
			super();
			dao = new SolicitacaoItemDAO();
		}

		@Override
		public SolicitacaoItemException validar(SolicitacaoItemModel vo) {
			StringBuffer msg = new StringBuffer("");

			if (vo.getEquipamento() == null) {
				msg.append("O campo Equipamento é de preenchimento obrigatório! \n");
			}

			if (vo.getDocumento() == null) {
				msg.append("O campo Documento é de preenchimento obrigatório! \n");
			}

			if (vo.getSolicitacao() == null) {
				msg.append("O campo Compilado é de preenchimento obrigatório! \n");
			}

			if (vo.getUnidade() == null) {
				msg.append("O campo Unidade é de preenchimento obrigatório! \n");
			}

			if (vo.getQuantidade() == null || vo.getQuantidade() == 0) {
				msg.append("O campo Quantidade é de preenchimento obrigatório! \n");
			}
			
			

			if (msg.length() > 0)
				return new SolicitacaoItemException(msg.toString());
			else
				return null;
		}

		@Override
		public String getSqlFiltro(SolicitacaoItemModel vo) {
			String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
			String deletado = "and deletado = false";
			filtro += " where 1=1 " + deletado;

			if (vo.getDocumento() != null) {
				filtro += " and id_documento = " + vo.getDocumento().getId();
			}

			if (vo.getUnidade() != null) {
				filtro += " and id_unidade = " + vo.getUnidade().getId();
			}

			if (vo.getEquipamento() != null) {
				filtro += " and id_equipamento = " + vo.getEquipamento().getId();
			}

			if (vo.getSolicitacao() != null) {
				filtro += " and id_solicitacao = " + vo.getSolicitacao().getId();
			}

			filtro += " order by id asc";

			return filtro;
		}

	}
