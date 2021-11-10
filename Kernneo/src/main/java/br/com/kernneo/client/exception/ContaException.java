package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ContaException extends Exception implements IsSerializable {

	public ContaException() {
		super();
	}
	
	public ContaException(String msg) {
		super(msg);
	}
	
	
	
}
