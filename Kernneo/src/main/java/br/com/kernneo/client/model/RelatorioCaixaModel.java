package br.com.kernneo.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RelatorioCaixaModel implements IsSerializable {

	private FluxoDeCaixaModel fluxo;
	private double totalPendente;

	public FluxoDeCaixaModel getFluxo() {
		return fluxo;
	}

	public void setFluxo(FluxoDeCaixaModel fluxo) {
		this.fluxo = fluxo;
	}

	public double getTotalPendente() {
		return totalPendente;
	}

	public void setTotalPendente(double totalPendente) {
		this.totalPendente = totalPendente;
	}

}
