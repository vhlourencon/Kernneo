package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class TipoItemException extends Exception implements IsSerializable {

    public TipoItemException() {
	super();
    }

    public TipoItemException(String msg) {
	super(msg);
    }

}
