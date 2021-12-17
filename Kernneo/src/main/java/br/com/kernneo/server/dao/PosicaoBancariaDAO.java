package br.com.kernneo.server.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fasterxml.jackson.databind.BeanProperty.Bogus;

import br.com.kernneo.client.exception.ContaBancariaException;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoBancariaModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.negocio.ContaBancaria;

public class PosicaoBancariaDAO {
	
	
	public PosicaoBancariaModel obterPosicaoBancaria(Date dataSelecionada,ContaBancariaModel contaBancariaSelecionada) throws Exception { 
		
		contaBancariaSelecionada = new ContaBancaria().obterPorId(contaBancariaSelecionada);
		/*
		 * CRÉDITOS 
		 */
		Session session = ConnectFactory.getSession();
		Query select = session.createSQLQuery("select sum(valor)  FROM movimentacao " +
				" p WHERE "
				+ "p.deletado = :deletado "
				+ "AND dataHora is not null "
				+ "AND date(dataHora)<date(:dataHora)  "
				+ "AND id_conta=:id_conta"
				);
		select.setParameter("deletado", false);
		select.setParameter("dataHora",dataSelecionada);
		select.setParameter("id_conta", contaBancariaSelecionada.getId());
		
		BigDecimal bigDecimalSaldo =  (BigDecimal) select.uniqueResult();
		if(bigDecimalSaldo == null ) { 
		    bigDecimalSaldo = BigDecimal.ZERO; 
		}
		

		
		
		bigDecimalSaldo = bigDecimalSaldo.setScale(2,RoundingMode.HALF_EVEN);
		
		

		
		
		select = session.createQuery("select p FROM  "+  MovimentacaoModel.class.getCanonicalName() +
				" p WHERE "
				+ "p.deletado = :deletado "
				+ "AND dataHora is not null "
				+ "AND date(dataHora)=date(:dataHora) "
				+ "AND id_conta=:id_conta "
				+ "order by id asc"
				);
		select.setParameter("deletado", false);
		select.setParameter("dataHora",dataSelecionada);
		select.setParameter("id_conta",contaBancariaSelecionada.getId());
		
		ArrayList<MovimentacaoModel> lista = (ArrayList<MovimentacaoModel>) select.getResultList();
	
		PosicaoBancariaModel posicaoBancariaModel = new PosicaoBancariaModel(); 
		posicaoBancariaModel.setListaDeMovimentacao(lista);
		posicaoBancariaModel.setContaBancariaSelecionada(contaBancariaSelecionada);
		posicaoBancariaModel.setSaldo(bigDecimalSaldo);

		return posicaoBancariaModel; 
	}

}
