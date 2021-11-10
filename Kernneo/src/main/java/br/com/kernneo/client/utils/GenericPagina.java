package br.com.kernneo.client.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.kernneo.client.model.GenericModel;

public class GenericPagina<GENERICMODEL extends GenericModel> implements Serializable {
    /**
     * 
     */

    private GENERICMODEL model;

    private static final long serialVersionUID = 1L;
    private int primeiraPagina = 0;
    private int ultimaPagina = 0;
    private int paginaAtual = 0;
    private int qtdeRegistro = 20;
    private int qtdePaginas = 0;
    private int paginaAnterior =0; 
    private int resto = 0;
    private List<GENERICMODEL> listaRegistros;

    public GenericPagina() {
	listaRegistros = new ArrayList<GENERICMODEL>();
    }

    public int getPrimeiraPagina() {
	return primeiraPagina;
    }

    public void setPrimeiraPagina(int primeiraPagina) {
	this.primeiraPagina = primeiraPagina;
    }

    public int getUltimaPagina() {
	return ultimaPagina;
    }

    public void setUltimaPagina(int ultimaPagina) {
	this.ultimaPagina = ultimaPagina;
    }

    public int getPaginaAtual() {
	return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
	setPaginaAnterior(getPaginaAtual());
	this.paginaAtual = paginaAtual;
    }

    public int getQtdeRegistro() {
	return qtdeRegistro;
    }

    public void setQtdeRegistro(int qtdeRegistro) {
	this.qtdeRegistro = qtdeRegistro;
    }

    public List<GENERICMODEL> getListaRegistros() {
	return listaRegistros;
    }

    public void setListaRegistros(List<GENERICMODEL> listaRegistros) {
	this.listaRegistros = listaRegistros;
    }

    public int getQtdePaginas() {
	return qtdePaginas;
    }

    public void setQtdePaginas(int qtdePaginas) {
	this.qtdePaginas = qtdePaginas;
    }

    public int getResto() {
	return resto;
    }

    public void setResto(int resto) {
	this.resto = resto;
    }

    public GENERICMODEL getModel() {
	return model;
    }

    public void setModel(GENERICMODEL model) {
	this.model = model;
    }

    public int getPaginaAnterior() {
	return paginaAnterior;
    }

    public void setPaginaAnterior(int paginaAnterior) {
	
	this.paginaAnterior = paginaAnterior;
    }

}