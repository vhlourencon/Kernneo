package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum MotivoDesICMS {
    _1_taxi("1","Taxi"),
    _2_deficiente_fisico("2","Deficiente Físico"),
    _3_produtor("3","Produtor Agropecuário"),
    _4_frotista("4", "Frotista/Locadora") ,
    _5_diplomatico("5","Diplomático/Consular"), 
    _6_utilitarios("6","Utilitários e Motocicletas da Amazônia Ocidental e Áreas de Livre Comércio (Resolução 714/88 e 790/94 - CONTRAN e suas alterações)"), 
    _7_suframa("7","SUFRAMA"),
    _8_venda_a_orgaos_publicos("8","Venda a Órgãos Públicos"), 
    _9_outros("9","outros");
    
    
    
    private String descricao;
    private String valor; 

    MotivoDesICMS(String valor, String texto) {
	this.descricao = texto;
	this.setValor(valor); 
    }
    
    
    public String getText() {
	  return this.descricao;
     }
    
    
    public static LinkedHashMap<String, String> getLinkedHashMap() {
   	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

   	for (MotivoDesICMS cst : MotivoDesICMS.values()) {
   	    listaHashMap.put(cst.toString(), cst.getValor() + " - " + cst.getText());
   	}

   	return listaHashMap;
       }


    public String getValor() {
	return valor;
    }


    public void setValor(String valor) {
	this.valor = valor;
    }
}
