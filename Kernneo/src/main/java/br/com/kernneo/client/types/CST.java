package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum CST {
    _00("00", "Tributada integralmente"),
    _20("20", "Com redução de base de cálculo"),
    _40("40", "Isenta"),
    _41("41", "Não tributada"),
    _60("60", "ICMS cobrado anteriormente por substituição tributária; "),
    _90("90", "Tributada pelo Simples Nacional sem permissão de crédito");

    private String descricao;
    private String valor;

    CST(String valor, String texto) {
	this.valor = valor;
	this.descricao = texto;

    }

    public String getText() {
	return this.descricao;
    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

    public static LinkedHashMap<String, String> getLinkedHashMap() {
	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

	for (CST cst : CST.values()) {
	    listaHashMap.put(cst.toString(), cst.getValor() + " - " + cst.getText());
	}

	return listaHashMap;
    }

    public static LinkedHashMap<String, String> getNFCeLinkedHashMap() {
	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

	listaHashMap.put(_00.toString(), _00.getValor() + " - " + _00.getText());
	listaHashMap.put(_20.toString(), _20.getValor() + " - " + _20.getText());
	listaHashMap.put(_40.toString(), _40.getValor() + " - " + _40.getText());
	listaHashMap.put(_41.toString(), _41.getValor() + " - " + _41.getText());
	listaHashMap.put(_60.toString(), _60.getValor() + " - " + _60.getText());
	listaHashMap.put(_90.toString(), _90.getValor() + " - " + _90.getText());

	return listaHashMap;
    }
}
