package br.com.kernneo.client.band.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BandRelatorioExibicaoJasperModel implements IsSerializable {

	private String dataHora;
	private String programa;
	private String titulo;
	private String duracao;

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

}
