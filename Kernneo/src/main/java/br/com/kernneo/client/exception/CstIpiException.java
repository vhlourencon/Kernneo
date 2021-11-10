package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CstIpiException extends Exception implements IsSerializable {

    public CstIpiException() {
	super();
    }

    public CstIpiException(String msg) {
	super(msg);
    }

}
