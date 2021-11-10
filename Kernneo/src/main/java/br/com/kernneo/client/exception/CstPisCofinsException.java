package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CstPisCofinsException extends Exception implements IsSerializable {

    public CstPisCofinsException() {
	super();
    }

    public CstPisCofinsException(String msg) {
	super(msg);
    }

}
