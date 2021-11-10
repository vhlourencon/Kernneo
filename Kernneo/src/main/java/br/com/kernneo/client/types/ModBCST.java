package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum ModBCST {
    preco_tabelado_0("Preço tabelado ou máximo sugerido"),
    lista_negativa_1("Lista Negativa (valor)"),
    lista_positiva_2("Lista Positiva (valor)"),
    lista_neutra_3("Lista Neutra (valor)") ,
    margem_valaor_agregado_4("Margem Valor Agregado (%)") ,
    pauta_5("Pauta (valor)");
    
    
    
    private String descricao;

    ModBCST(String texto) {
	this.descricao = texto;
    }
    
    
    public String getText() {
	  return this.descricao;
     }
    
    
    public static LinkedHashMap<String, String> getLinkedHashMap() {
  	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

  	for (ModBCST modBCST : ModBCST.values()) {
  	    listaHashMap.put(modBCST.toString(), modBCST.getText());
  	}

  	return listaHashMap;
      }
}