package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ImpressoraException extends Exception implements IsSerializable {

	public ImpressoraException() {
		super();
	}
	
	public ImpressoraException(String msg) {
		super(msg);
	}
	
	
	
}
