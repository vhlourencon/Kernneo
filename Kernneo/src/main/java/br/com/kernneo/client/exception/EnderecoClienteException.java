package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EnderecoClienteException extends Exception  implements IsSerializable {

	public EnderecoClienteException() {
		super();
	}
	
	public EnderecoClienteException(String msg) {
		super(msg);
	}
	
}
