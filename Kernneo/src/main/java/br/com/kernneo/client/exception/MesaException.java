package br.com.kernneo.client.exception;

import java.io.Serializable;

public class MesaException extends Exception implements Serializable {

    public MesaException() {
	super();
    }

    public MesaException(String msg) {
	super(msg);
    }

}
