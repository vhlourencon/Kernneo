package br.com.kernneo.client.model;

import java.math.BigDecimal;

import com.smartgwt.client.data.Record;

public class SpedProdutoModel extends GenericModel {
    
    private SpedEmpresaModel spedEmpresa; 

    private String cod_item;
    private String descr_item;
    private String cod_barra;
    private String cod_ant_item;
    private String unid_inv;
    private String tipo_item;
    private String cod_ncm;
    private String ex_ipi;
    private Long cod_gen;
    private Long cod_lst;
    private BigDecimal alig_icms;
    
    public SpedProdutoModel() { 
	
    }

    public SpedProdutoModel(String[] linha) {
	setCod_item(linha[2]);
	setDescr_item(linha[3]);
	setCod_barra(linha[4]);
	setCod_ant_item(linha[5]);
	setUnid_inv(linha[6]);
	setTipo_item(linha[7]);
	setCod_ncm(linha[8]);
	setEx_ipi(linha[9]);
	
	

	String codigGenStr = linha[10];
	if (codigGenStr != null && codigGenStr.trim().equals("") == false) {
	    setCod_gen(Long.valueOf(codigGenStr));
	}
	
	String codLstStr = linha[11];
	if (codLstStr != null  && codLstStr.trim().equals("") == false){
	    
		setCod_lst(Long.valueOf(codLstStr));
	}

	String aliq_icmsStr = linha[12]; 
	if (aliq_icmsStr != null && aliq_icmsStr.trim().equals("")  == false) { 
	    setAlig_icms(java.math.BigDecimal.valueOf(Double.valueOf(aliq_icmsStr)));
	}
	
	
	

    }

    @Override
    public Record toRecord() {
	Record record = new Record(); 
	record.setAttribute("codigo", getCod_item());
	record.setAttribute("ncm", getCod_ncm());
	record.setAttribute("descricao", getDescr_item());

	return record;
    }

    public String getCod_item() {
	return cod_item;
    }

    public void setCod_item(String cod_item) {
	this.cod_item = cod_item;
    }

    public String getDescr_item() {
	return descr_item;
    }

    public void setDescr_item(String descr_item) {
	this.descr_item = descr_item;
    }

    public String getCod_barra() {
	return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
	this.cod_barra = cod_barra;
    }

    public String getCod_ant_item() {
	return cod_ant_item;
    }

    public void setCod_ant_item(String cod_ant_item) {
	this.cod_ant_item = cod_ant_item;
    }

    public String getUnid_inv() {
	return unid_inv;
    }

    public void setUnid_inv(String unid_inv) {
	this.unid_inv = unid_inv;
    }

    public String getTipo_item() {
	return tipo_item;
    }

    public void setTipo_item(String tipo_item) {
	this.tipo_item = tipo_item;
    }

    public String getCod_ncm() {
	return cod_ncm;
    }

    public void setCod_ncm(String cod_ncm) {
	this.cod_ncm = cod_ncm;
    }

    public String getEx_ipi() {
	return ex_ipi;
    }

    public void setEx_ipi(String ex_ipi) {
	this.ex_ipi = ex_ipi;
    }

    public Long getCod_gen() {
	return cod_gen;
    }

    public void setCod_gen(Long cod_gen) {
	this.cod_gen = cod_gen;
    }

    public Long getCod_lst() {
	return cod_lst;
    }

    public void setCod_lst(Long cod_lst) {
	this.cod_lst = cod_lst;
    }

    public BigDecimal getAlig_icms() {
	return alig_icms;
    }

    public void setAlig_icms(BigDecimal alig_icms) {
	this.alig_icms = alig_icms;
    }

    public SpedEmpresaModel getSpedEmpresa() {
	return spedEmpresa;
    }

    public void setSpedEmpresa(SpedEmpresaModel spedEmpresa) {
	this.spedEmpresa = spedEmpresa;
    }

}
