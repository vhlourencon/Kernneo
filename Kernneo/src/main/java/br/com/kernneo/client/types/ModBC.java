package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum ModBC {

    margem_0("0", "Margem Valor Agregado (%)"), pauta_1("1", "Pauta (valor)"), preco_2("2", "Preço Tabelado Máximo (valor)"), valor_operacao_3("3", "Valor da Operação");

    private String descricao;
    private String valor;

    ModBC(String valor, String texto) {
	this.descricao = texto;
	this.valor = valor;
    }

    public String getText() {
	return this.descricao;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

    public static LinkedHashMap<String, String> getLinkedHashMap() {
	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

	for (ModBC modBC : ModBC.values()) {
	    listaHashMap.put(modBC.toString(), modBC.getText());
	}

	return listaHashMap;
    }

}
