package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum CSOSN {
    _101("101","Tributada pelo Simples Nacional com permissão de crédito") ,
    _102("102", "Tributada pelo Simples Nacional sem permissão de crédito"), _103("103", "Isenção do ICMS no Simples Nacional para faixa de receita bruta"),
    _201("201","Tributada pelo Simples Nacional com permissão de crédito e com cobrança do ICMS por substituição tributária"),
    _202("202","Tributada pelo Simples Nacional sem permissão de crédito e com cobrança do ICMS por substituição tributária"),
    _300("300", "Imune"),
    _400("400", "Não tributada pelo Simples Nacional"),
    _500("500", "ICMS cobrado anteriormente por substituição tributária (substituído) ou por antecipação"),
    _900("900","Outros");

    private String descricao;
    private String valor;

    CSOSN(String valor, String texto) {
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

    public static LinkedHashMap<String, String> getNFCeLinkedHashMap() {
	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

	listaHashMap.put(_102.toString(), _102.getValor() + " - " + _102.getText());
	listaHashMap.put(_103.toString(), _103.getValor() + " - " + _103.getText());
	listaHashMap.put(_300.toString(), _300.getValor() + " - " + _300.getText());
	listaHashMap.put(_400.toString(), _400.getValor() + " - " + _400.getText());
	listaHashMap.put(_500.toString(), _500.getValor() + " - " + _500.getText());
	listaHashMap.put(_900.toString(), _900.getValor() + " - " + _900.getText());

	return listaHashMap;
    }
}
