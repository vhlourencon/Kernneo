package br.com.kernneo.client.exception;

import java.io.Serializable;

public class OcorrenciaException extends Exception implements Serializable {

    public OcorrenciaException() {
	super();
    }

    public OcorrenciaException(String msg) {
	super(msg);
    }

}
