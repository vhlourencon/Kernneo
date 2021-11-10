package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class GeneroException extends Exception implements IsSerializable {

    public GeneroException() {
	super();
    }

    public GeneroException(String msg) {
	super(msg);
    }

}
