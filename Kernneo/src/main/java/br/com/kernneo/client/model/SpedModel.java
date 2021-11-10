package br.com.kernneo.client.model;

import java.util.ArrayList;

import com.smartgwt.client.data.Record;

public class SpedModel extends GenericModel {
    
    

    private String cod_ver;
    private String tipo_escrit;
    private String ind_sit_esp;
    private String num_rec_anterior;
    private String dt_ini;
    private String dt_fim;
    private String nome;
    private String cnpj;
    private String suframa;
    private String cod_mun;
    private String ind_nat_pj;
    private String ind_ativ;

    private ArrayList<SpedEmpresaModel> listaDeEmpresas;
    private ArrayList<SpedC010Model> listaDeC010;
    private ArrayList<SpedProdutoModel> listaDeProtudos;
    public SpedModel() { 
	
    }

    public SpedModel(String[] linha) {

	setCod_ver(linha[2]);
	setTipo_escrit(linha[3]);
	setInd_sit_esp(linha[4]);
	setNum_rec_anterior(linha[5]);
	setDt_ini(linha[6]);
	setDt_fim(linha[7]);
	setNome(linha[8]);
	setCnpj(linha[9]);
	setSuframa(linha[10]);
	setInd_nat_pj(linha[11]);
	setInd_ativ(linha[12]);

    }

    public String getCod_ver() {
	return cod_ver;
    }

    public void setCod_ver(String cod_ver) {
	this.cod_ver = cod_ver;
    }

    public String getNum_rec_anterior() {
	return num_rec_anterior;
    }

    public void setNum_rec_anterior(String num_rec_anterior) {
	this.num_rec_anterior = num_rec_anterior;
    }
    
    public void addC010(final SpedC010Model spedC010Model) {
	spedC010Model.setSped(this);
   	getListaDeC010().add(spedC010Model);
       }
    
    public SpedC010Model getUltimoC010Adicionado() {
   	SpedC010Model spedC010model = getListaDeC010().get(getListaDeC010().size() -1);
   	return spedC010model;
       }



    public void addEmpresa(final SpedEmpresaModel spedEmpresaModel) {
	spedEmpresaModel.setSped(this);
	getListaDeEmpresas().add(spedEmpresaModel);
    }

    public SpedEmpresaModel getUltimaEmpresaAdicionada() {
	SpedEmpresaModel spedEmpresaModel = getListaDeEmpresas().get(getListaDeEmpresas().size() - 1);
	return spedEmpresaModel;
    }

    @Override
    public Record toRecord() {
	Record record = new Record(); 
	record.setAttribute("id", getId());
	record.setAttribute("dataIni",getDt_ini());
	record.setAttribute("dataFim", getDt_fim());
	record.setAttribute("nome", getNome());
	record.setAttribute("cnpj", getCnpj());
	return record;
    }

    public String getTipo_escrit() {
	return tipo_escrit;
    }

    public void setTipo_escrit(String tipo_escrit) {
	this.tipo_escrit = tipo_escrit;
    }

    public String getInd_sit_esp() {
	return ind_sit_esp;
    }

    public void setInd_sit_esp(String ind_sit_esp) {
	this.ind_sit_esp = ind_sit_esp;
    }

    public String getDt_ini() {
	return dt_ini;
    }

    public void setDt_ini(String dt_ini) {
	this.dt_ini = dt_ini;
    }

    public String getDt_fim() {
	return dt_fim;
    }

    public void setDt_fim(String dt_fim) {
	this.dt_fim = dt_fim;
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

    public String getSuframa() {
	return suframa;
    }

    public void setSuframa(String suframa) {
	this.suframa = suframa;
    }

    public String getCod_mun() {
	return cod_mun;
    }

    public void setCod_mun(String cod_mun) {
	this.cod_mun = cod_mun;
    }

    public String getInd_nat_pj() {
	return ind_nat_pj;
    }

    public void setInd_nat_pj(String ind_nat_pj) {
	this.ind_nat_pj = ind_nat_pj;
    }

    public String getInd_ativ() {
	return ind_ativ;
    }

    public void setInd_ativ(String ind_ativ) {
	this.ind_ativ = ind_ativ;
    }

    public ArrayList<SpedEmpresaModel> getListaDeEmpresas() {
	if (listaDeEmpresas == null) {
	    listaDeEmpresas = new ArrayList<SpedEmpresaModel>();
	}
	return listaDeEmpresas;
    }

    public void setListaDeEmpresas(ArrayList<SpedEmpresaModel> listaDeEmpresas) {
	this.listaDeEmpresas = listaDeEmpresas;
    }

    public ArrayList<SpedC010Model> getListaDeC010() {
	if ( listaDeC010 == null) { 
	    listaDeC010 = new ArrayList<SpedC010Model>();
	}
	return listaDeC010;
    }

    public void setListaDeC010(ArrayList<SpedC010Model> listaDeC010) {
	this.listaDeC010 = listaDeC010;
    }

    public ArrayList<SpedProdutoModel> getListaDeProtudos() {
	if ( listaDeProtudos == null) { 
	    listaDeProtudos = new ArrayList<SpedProdutoModel>();
	}
	return listaDeProtudos;
    }

    public void setListaDeProtudos(ArrayList<SpedProdutoModel> listaDeProtudos) {
	this.listaDeProtudos = listaDeProtudos;
    }

}
