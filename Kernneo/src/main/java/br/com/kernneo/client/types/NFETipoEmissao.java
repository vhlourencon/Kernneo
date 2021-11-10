package br.com.kernneo.client.types;

public enum NFETipoEmissao {
    _1_EMISSAO_NORMAL("1"), _9_CONTINGENCIA_OFF_LINE("9");

    private String valor;

    NFETipoEmissao(String valor) {
	this.valor = valor;

    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }
    
    
    
    
    

}
