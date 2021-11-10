package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class NFCeException extends Exception implements IsSerializable {

    public NFCeException() {
	super();
    }

    public NFCeException(String msg) {
	super(msg);
    }

}
