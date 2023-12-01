package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class VeiculoException extends Exception implements IsSerializable {

    public VeiculoException() {
	super();
    }

    public VeiculoException(String msg) {
	super(msg);
    }

}
