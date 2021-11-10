package br.com.kernneo.client.band.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.model.GenericModel;

import com.smartgwt.client.data.Record;


@Table
@Entity(name = "programa")
public class BandProgramaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_log_importacao")
	private BandLogImportacaoModel logImportacao;
	private String titulo;
	private String descricao; 
	private String dataHoraIniStr;
	private Date dataHoraInicio;
	private Date dataHoraFim;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public String getDataHoraIniStr() {
		return dataHoraIniStr;
	}

	public void setDataHoraIniStr(String dataHoraIniStr) {
		this.dataHoraIniStr = dataHoraIniStr;
	}

	public BandLogImportacaoModel getLogImportacao() {
		return logImportacao;
	}

	public void setLogImportacao(BandLogImportacaoModel logImportacao) {
		this.logImportacao = logImportacao;
	}

	@Override
	public Record toRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
