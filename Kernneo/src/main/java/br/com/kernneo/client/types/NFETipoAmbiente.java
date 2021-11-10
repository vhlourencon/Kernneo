package br.com.kernneo.client.types;

public enum NFETipoAmbiente {

    _1_PRODUCAO("1"), _2_HOMOLOGACAO("2");

    private String valor;

    NFETipoAmbiente(String valor) {
	this.valor = valor;

    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

}
