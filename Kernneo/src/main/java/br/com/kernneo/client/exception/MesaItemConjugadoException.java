package br.com.kernneo.client.exception;

import java.io.Serializable;

public class MesaItemConjugadoException extends Exception implements Serializable {

    public MesaItemConjugadoException() {
	super();
    }

    public MesaItemConjugadoException(String msg) {
	super(msg);
    }

}
