package br.com.kernneo.server.negocio;

import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.PosicaoBancariaModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.server.dao.PosicaoBancariaDAO;

public class PosicaoFinanceira {
	
	
	public PosicaoFinanceiraModel obterPosicoesFinanceira(Date dataSelecionada,ArrayList<ContaBancariaModel> listaDeContasSelecionadas ) throws Exception { 
		PosicaoFinanceiraModel posicaoFinanceiraModel = new PosicaoFinanceiraModel(); 
		PosicaoBancariaDAO posicaoFinanceiraDAO = new PosicaoBancariaDAO(); 
		
		ArrayList<PosicaoBancariaModel> listaDePosicoesBancarias = new ArrayList<PosicaoBancariaModel>(); 
		for (ContaBancariaModel contaBancariaSelecionada : listaDeContasSelecionadas) {
			
			PosicaoBancariaModel posicaoBancariaModel = posicaoFinanceiraDAO.obterPosicaoBancaria(dataSelecionada, contaBancariaSelecionada);
			posicaoFinanceiraModel.getListaDePosicoesBancarias().add(posicaoBancariaModel);
			
			
		}
		return posicaoFinanceiraModel; 
	}

}
