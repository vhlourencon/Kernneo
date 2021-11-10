package br.com.kernneo.client.types;

public enum CRT {
    simples_nacional("1"), simples_nacional_excesso("2"),  regime_normal("3");
    
    
    private String descricao;

    CRT(String texto) {
	this.descricao = texto;
    }
    
    
    public String getText() {
	  return this.descricao;
     }
}
