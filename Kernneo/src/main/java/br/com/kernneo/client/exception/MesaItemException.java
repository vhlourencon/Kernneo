package br.com.kernneo.client.exception;

import java.io.Serializable;

public class MesaItemException extends Exception implements Serializable {

    public MesaItemException() {
	super();
    }

    public MesaItemException(String msg) {
	super(msg);
    }

}
