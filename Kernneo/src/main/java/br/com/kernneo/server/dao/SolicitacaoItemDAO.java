package br.com.kernneo.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.kernneo.client.model.SolicitacaoItemModel;
import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.server.ConnectFactory;

public class SolicitacaoItemDAO extends GenericDAO<SolicitacaoItemModel>
	{

		public List<SolicitacaoItemModel> obterPorSolicitacao(SolicitacaoModel compiladoModel) {

			String sqlMovimentacoes = "select p FROM  " + SolicitacaoItemModel.class.getCanonicalName()
					+ " p WHERE " + "p.deletado = :deletado ";

			sqlMovimentacoes += compiladoModel != null ? "AND p.solicitacao=:solicitacao  " : "";
			sqlMovimentacoes += "	order by id asc";

			Session session = ConnectFactory.getSession();

			Query select = session.createQuery(sqlMovimentacoes);
			select.setParameter("deletado", false);
			if (compiladoModel != null) {
				select.setParameter("solicitacao", compiladoModel);
			}

			return (ArrayList<SolicitacaoItemModel>) select.getResultList();

		}
		
		public List<SolicitacaoItemModel> obterPor(SolicitacaoItemModel solicitacaoItemModel) {

			String sqlMovimentacoes = "select p FROM  " + SolicitacaoItemModel.class.getCanonicalName()
					+ " p WHERE " + "p.deletado = :deletado ";

			sqlMovimentacoes += "AND p.solicitacao=:solicitacao  ";
			sqlMovimentacoes += "AND p.documento=:documento  ";
			//sqlMovimentacoes += "AND p.equipamento=:equipamento  " : "";
			sqlMovimentacoes += solicitacaoItemModel.getUnidade() != null ? "AND p.unidade=:unidade  " : "";
			
			sqlMovimentacoes += "	order by id asc";

			Session session = ConnectFactory.getSession();

			Query select = session.createQuery(sqlMovimentacoes);
			select.setParameter("deletado", false);
		//	if (compiladoModel != null) {
		//		select.setParameter("solicitacao", compiladoModel);
		//	}

			return (ArrayList<SolicitacaoItemModel>) select.getResultList();

		}


	}
