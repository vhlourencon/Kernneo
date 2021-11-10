package br.com.kernneo.client.exception;

import java.io.Serializable;

public class MesaRecebimentoException extends Exception implements Serializable {

    public MesaRecebimentoException() {
	super();
    }

    public MesaRecebimentoException(String msg) {
	super(msg);
    }

}
