package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class BairroException extends Exception implements IsSerializable {

	public BairroException() {
		super();
	}
	
	public BairroException(String msg) {
		super(msg);
	}
	
	
	
}
