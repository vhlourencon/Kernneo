package br.com.kernneo.client.types;

import java.util.LinkedHashMap;

public enum OrigemDaMercadoria {
    nacional_0("0","Nacional, exceto as indicadas nos códigos 3, 4, 5 e 8"),
    estrangeira_imp_direta_1("1"," Estrangeira - Importação direta, exceto a indicada no código 6"),
    estrangeira_adq_mercado_interno_2("2"," Estrangeira - Adquirida no mercado interno, exceto a indicada no código 7"),
    nacional_3("3","Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40% e inferior ou igual a 70%"),
    nacional_4("4"," Nacional, cuja produção tenha sido feita em conformidade com os processos produtivos básicos de que tratam as legislações citadas nos Ajustes"),
    nacional_5("5","Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40%;"), 
    estrangeira_6("6","Estrangeira - Importação direta, sem similar nacional, constante em lista da CAMEX e gás natural;"),
    estrangeira_7("7","Estrangeira - Adquirida no mercado interno, sem similar nacional, constante em lista da CAMEX e gás natura"),
    nacional_8("8","Nacional, mercadoria ou bem com Conteúdo de Importação superior a 70%;");

    private String descricao;
    private String valor; 

    OrigemDaMercadoria(String valor, String texto) {
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
    
    public static LinkedHashMap<String, String> getListaDeOrigemDaMercaoria() {
  	LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

  	for (OrigemDaMercadoria origemDaMercadoria : OrigemDaMercadoria.values()) {
  	    listaHashMap.put(origemDaMercadoria.toString(), origemDaMercadoria.getValor() + " - " + origemDaMercadoria.getDescricao());
  	}

  	return listaHashMap;
      }

    
    

}
