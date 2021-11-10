package br.com.kernneo.client.model;

import java.util.ArrayList;

import com.smartgwt.client.data.Record;

public class SpedEmpresaModel extends GenericModel {

    private SpedModel sped;
    private String cod_est;
    private String nome;
    private String cnpj;
    private String uf;
    private Long cod_mun;
    private String im;
    private String suframa;
    
    public SpedEmpresaModel() { 
	
    }
    
  
    public SpedEmpresaModel(String[] linha) {
	setCod_est(linha[2]);
	setNome(linha[3]);
	setCnpj(linha[4]);
	setUf(linha[5]);

	String codigoDoMunicipioStr = linha[6];
	if (codigoDoMunicipioStr != null) {
	    setCod_mun(Long.valueOf(codigoDoMunicipioStr));
	}

	setIm(linha[7]);
	if (linha.length > 8) {
	    setSuframa(linha[8]);
	}

    }
   

    @Override
    public Record toRecord() {
	Record record = new Record(); 
	record.setAttribute("cod_Est", getCod_est());
	record.setAttribute("nome", getNome());
	record.setAttribute("cnpj", getCnpj());
	record.setAttribute("uf", getUf());
	record.setAttribute("cod_mund", getCod_mun());
	return record;
    }

    public String getCod_est() {
	return cod_est;
    }

    public void setCod_est(String cod_est) {
	this.cod_est = cod_est;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getCnpj() {
	return cnpj;
    }

    public void setCnpj(String cnpj) {
	this.cnpj = cnpj;
    }

    public String getUf() {
	return uf;
    }

    public void setUf(String uf) {
	this.uf = uf;
    }

    public Long getCod_mun() {
	return cod_mun;
    }

    public void setCod_mun(Long cod_mun) {
	this.cod_mun = cod_mun;
    }

    public String getIm() {
	return im;
    }

    public void setIm(String im) {
	this.im = im;
    }

    public String getSuframa() {
	return suframa;
    }

    public void setSuframa(String suframa) {
	this.suframa = suframa;
    }

    public SpedModel getSped() {
	return sped;
    }

    public void setSped(SpedModel sped) {
	this.sped = sped;
    }

}
