package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EstoqueException extends Exception  implements IsSerializable {

	public EstoqueException() {
		super();
	}
	
	public EstoqueException(String msg) {
		super(msg);
	}
	
}
