package br.com.kernneo.client.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "lst")
public class LSTModel extends GenericModel {

    private String codigo;
    private String descricao;
    private Date dataInicial;
    private Date dataFinal;

    @Transient
    private String dataInialString;

    @Transient
    private String dataFinalString;

    public LSTModel() {

    }

    public LSTModel(String[] stringLST) {
	setCodigo(stringLST[0]);
	setDescricao(stringLST[1]);
	if ( getDescricao().length() > 100) {
	    setDescricao(getDescricao().substring(0,100));
	}
	
	setDataInialString(stringLST[2]);
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("codigo", getCodigo());
	record.setAttribute("dataInicial", getDataInicial());
	record.setAttribute("dataFinal", getDataFinal());
	
	record.setAttribute("descricao", getDescricao());
	return record;
    }

    public String getCodigo() {
	return codigo;
    }

    public void setCodigo(String codigo) {
	this.codigo = codigo;
    }

    public Date getDataInicial() {
	return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
	this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
	return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
	this.dataFinal = dataFinal;
    }

    public String getDataInialString() {
	return dataInialString;
    }

    public void setDataInialString(String dataInialString) {
	this.dataInialString = dataInialString;
    }

    public String getDataFinalString() {
	return dataFinalString;
    }

    public void setDataFinalString(String dataFinalString) {
	this.dataFinalString = dataFinalString;
    }

}
