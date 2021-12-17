package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;

public class PosicaoFinanceiraModel {

	private Date dataSelecionada;
	private ArrayList<PosicaoBancariaModel> listaDePosicoesBancarias;

	public BigDecimal getCalcSaldoInicial() {
		BigDecimal bigDecimalSaldoInicial = new BigDecimal(0.0);
		for (PosicaoBancariaModel posicaoBancariaModel : getListaDePosicoesBancarias()) {
              bigDecimalSaldoInicial = bigDecimalSaldoInicial.add(posicaoBancariaModel.getCalcSaldoInicial());
		}
		
		return bigDecimalSaldoInicial; 

	}
	
	public BigDecimal getCalcSaldoFinal() {
		BigDecimal bigDecimalSaldoFinal = new BigDecimal(0.0);
		for (PosicaoBancariaModel posicaoBancariaModel : getListaDePosicoesBancarias()) {
			bigDecimalSaldoFinal = bigDecimalSaldoFinal.add(posicaoBancariaModel.getCalcSaldoFinal());
		}
		
		return bigDecimalSaldoFinal; 

	}

	public Date getDataSelecionada() {
		return dataSelecionada;
	}

	public void setDataSelecionada(Date dataSelecionada) {
		this.dataSelecionada = dataSelecionada;
	}

	public ArrayList<PosicaoBancariaModel> getListaDePosicoesBancarias() {
		if(listaDePosicoesBancarias == null) { 
			listaDePosicoesBancarias = new ArrayList<PosicaoBancariaModel>();
		}
		return listaDePosicoesBancarias;
	}

	public void setListaDePosicoesBancarias(ArrayList<PosicaoBancariaModel> listaDePosicoesBancarias) {
		this.listaDePosicoesBancarias = listaDePosicoesBancarias;
	}

}
