package br.com.kernneo.client.band.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.kernneo.client.model.GenericModel;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "log_importacao")
public class BandLogImportacaoModel extends GenericModel {

	private Date dataImportacao;

	public Date getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(Date dataImportacao) {
		this.dataImportacao = dataImportacao;
	}

	@Override
	public Record toRecord() {
		// TODO Auto-generated method stub
		return null;
	}

}
