package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class UnidadeException extends Exception  implements IsSerializable {

	public UnidadeException() {
		super();
	}
	
	public UnidadeException(String msg) {
		super(msg);
	}
	
}
