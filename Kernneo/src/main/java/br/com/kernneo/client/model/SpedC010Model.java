package br.com.kernneo.client.model;

import java.util.ArrayList;

import com.smartgwt.client.data.Record;

public class SpedC010Model extends GenericModel {

    private SpedModel sped;
    private String cnpj;
    private String ind_escri;

    private ArrayList<SpedNotaModel> listaDeNotas;

    public SpedC010Model(String[] linha) {
	setCnpj(linha[2]);
	setInd_escri(linha[3]);
    }

    public SpedC010Model() {

    }

    public SpedNotaModel getUltimaNotaAdiciona() {
	SpedNotaModel spedC010model = getListaDeNotas().get(getListaDeNotas().size() - 1);
	return spedC010model;
    }

    public void addNota(final SpedNotaModel spedNotaModel) {
	spedNotaModel.setSpdC010(this);
	getListaDeNotas().add(spedNotaModel);

    }

    public ArrayList<SpedNotaModel> getListaDeNotas() {
	if (listaDeNotas == null) {
	    listaDeNotas = new ArrayList<SpedNotaModel>();
	}
	return listaDeNotas;
    }

    public void setListaDeNotas(ArrayList<SpedNotaModel> listaDeNotas) {
	this.listaDeNotas = listaDeNotas;
    }

    @Override
    public Record toRecord() {
	return null;
    }

    public String getCnpj() {
	return cnpj;
    }

    public void setCnpj(String cnpj) {
	this.cnpj = cnpj;
    }

    public String getInd_escri() {
	return ind_escri;
    }

    public void setInd_escri(String ind_escri) {
	this.ind_escri = ind_escri;
    }

    public SpedModel getSped() {
	return sped;
    }

    public void setSped(SpedModel sped) {
	this.sped = sped;
    }

}
