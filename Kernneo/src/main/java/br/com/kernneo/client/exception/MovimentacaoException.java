package br.com.kernneo.client.exception;

import java.io.Serializable;

public class MovimentacaoException extends Exception implements Serializable {

    public MovimentacaoException() {
	super();
    }

    public MovimentacaoException(String msg) {
	super(msg);
    }

}
