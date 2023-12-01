package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PosicaoBancariaModel
	{

		private ContaBancariaModel contaBancariaSelecionada;
		private BigDecimal saldo;
		private BigDecimal saldoAcumuladoMensal;
		private BigDecimal saldoAcumuladoTotal;

		public PosicaoBancariaModel(BigDecimal saldo, ContaBancariaModel conta) {
			this.saldo = saldo;
			this.contaBancariaSelecionada = conta;
		}

		public PosicaoBancariaModel() {
			// TODO Auto-generated constructor stub
		}

		public ContaBancariaModel getContaBancariaSelecionada() {
			return contaBancariaSelecionada;
		}

		public void setContaBancariaSelecionada(ContaBancariaModel contaBancariaSelecionada) {
			this.contaBancariaSelecionada = contaBancariaSelecionada;
		}

		public BigDecimal getSaldo() {
			if (saldo != null) {
				saldo = saldo.setScale(2, RoundingMode.HALF_EVEN);
			}
			return saldo;
		}

		public void setSaldo(BigDecimal saldo) {
			this.saldo = saldo;
		}

		public BigDecimal getSaldoAcumuladoMensal() {
			if (saldoAcumuladoMensal == null) {
				saldoAcumuladoMensal = BigDecimal.ZERO;
			}
			saldoAcumuladoMensal = saldoAcumuladoMensal.setScale(2, RoundingMode.HALF_EVEN);

			return saldoAcumuladoMensal;

		}

		public void setSaldoAcumuladoMensal(BigDecimal saldoAcumuladoMensal) {
			this.saldoAcumuladoMensal = saldoAcumuladoMensal;
		}

		

		public BigDecimal getSaldoAcumuladoTotal() {
			if (saldoAcumuladoTotal == null) {
				saldoAcumuladoTotal = BigDecimal.ZERO;
			}
			saldoAcumuladoTotal = saldoAcumuladoTotal.setScale(2, RoundingMode.HALF_EVEN);
			return saldoAcumuladoTotal;
		}

		public void setSaldoAcumuladoTotal(BigDecimal saldoAcumuladoTotal) {
			this.saldoAcumuladoTotal = saldoAcumuladoTotal;
		}

		
		

	}
