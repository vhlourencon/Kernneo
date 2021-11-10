package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EstadoException extends Exception  implements IsSerializable {

	public EstadoException() {
		super();
	}
	
	public EstadoException(String msg) {
		super(msg);
	}
	
}
