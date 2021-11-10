package br.com.kernneo.client.band.model;

import java.util.Date;

import br.com.kernneo.client.model.GenericModel;

import com.smartgwt.client.data.Record;

public class BandExibicaoModel extends GenericModel {

	private BandProgramaModel programa;

	private String codigo;
	private String descricao;
	private Date dataHoraExibicao;
	private String situacao;
	private String dataStr;
	private String horaStr;
	private String miliSegundosHoraExibicao;
	private String duracao;
	private String categoria;

	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public String getHoraStr() {
		return horaStr;
	}

	public void setHoraStr(String horaStr) {
		this.horaStr = horaStr;
	}

	public String getMiliSegundosHoraExibicao() {
		return miliSegundosHoraExibicao;
	}

	public void setMiliSegundosHoraExibicao(String miliSegundosHoraExibicao) {
		this.miliSegundosHoraExibicao = miliSegundosHoraExibicao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getDataHoraExibicao() {
		return dataHoraExibicao;
	}

	public void setDataHoraExibicao(Date dataHoraExibicao) {
		this.dataHoraExibicao = dataHoraExibicao;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public Record toRecord() {
		
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("codigo", getCodigo());
		record.setAttribute("descricao", getDescricao());
		record.setAttribute("dataHoraExibicao", getDataHoraExibicao());
		record.setAttribute("situacao", getSituacao());
		record.setAttribute("dataStr", getDataStr());
		record.setAttribute("horaStr", getHoraStr());
		record.setAttribute("dataHoraExibicaoStr", getDataStr() + " - " + getHoraStr() + "" + getMiliSegundosHoraExibicao());
		record.setAttribute("milisegundosHoraExibicao", getMiliSegundosHoraExibicao());
		record.setAttribute("duracao", getDuracao());
		record.setAttribute("categoria", getCategoria());

		if (getPrograma() != null) {
			record.setAttribute("programaTitulo", getPrograma().getTitulo());

		}

		return record;
	}

	public BandProgramaModel getPrograma() {
		return programa;
	}

	public void setPrograma(BandProgramaModel programa) {
		this.programa = programa;
	}

}
