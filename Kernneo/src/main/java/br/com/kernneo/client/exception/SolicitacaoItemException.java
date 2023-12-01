package br.com.kernneo.client.exception;

import java.io.Serializable;

public class SolicitacaoItemException extends Exception implements Serializable {

    public SolicitacaoItemException() {
	super();
    }

    public SolicitacaoItemException(String msg) {
	super(msg);
    }

}
