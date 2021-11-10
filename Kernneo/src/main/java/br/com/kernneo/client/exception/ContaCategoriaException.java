package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ContaCategoriaException extends Exception implements IsSerializable {

	public ContaCategoriaException() {
		super();
	}
	
	public ContaCategoriaException(String msg) {
		super(msg);
	}
	
	
	
}
