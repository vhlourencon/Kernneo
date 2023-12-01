package br.com.kernneo.client.exception;

import java.io.Serializable;

public class RegulacaoException extends Exception implements Serializable {

    public RegulacaoException() {
	super();
    }

    public RegulacaoException(String msg) {
	super(msg);
    }

}
