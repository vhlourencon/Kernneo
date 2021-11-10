package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CaixaException extends Exception  implements IsSerializable {

	public CaixaException() {
		super();
	}
	
	public CaixaException(String msg) {
		super(msg);
	}
	
}
