package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum CFOP {
    _5101("5101","Venda de produção do estabelecimento"),
    _5102("5102","Venda de mercadoria de terceiros"),
    _5103("5103","Venda de produção do estabelecimento, efetuada fora do estabelecimento;"),
    _5104("5104","Venda de mercadoria adquirida ou recebida de terceiros, efetuada fora do estabelecimento;"),
    _5115("5115","Venda de mercadoria adquirida ou recebida de terceiros, recebida anteriormente em consignação mercantil;"),
    _5401("5401","Venda de produção do estabelecimento em operação com produto sujeito a ST, como contribuinte substituto"), 
    _5403("5403","Venda de mercadoria de terceiros em operação com mercadoria sujeita a ST, como contribuinte substituto"),
    _5405("5405","Venda de mercadoria de terceiros, sujeita a ST, como contribuinte substituído"),
    _5656("5656","Venda de combustível ou lubrificante de terceiros, para consumidor final"),
    _5933("5933","Prestação de serviço tributado pelo ISSQN");

    private String descricao;
    private String valor; 

    CFOP(String valor, String texto) {
	this.valor = valor; 
	this.descricao = texto; 
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

	for (CFOP cfop : CFOP.values()) {
	    listaHashMap.put(cfop.toString(), cfop.getValor() + " - " + cfop.getText());
	}

	return listaHashMap;
    }
    

}
