package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class LSTException extends Exception implements IsSerializable {

    public LSTException() {
	super();
    }

    public LSTException(String msg) {
	super(msg);
    }

}
