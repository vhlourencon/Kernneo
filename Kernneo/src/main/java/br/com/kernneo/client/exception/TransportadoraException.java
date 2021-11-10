package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class TransportadoraException extends Exception  implements IsSerializable {

	public TransportadoraException() {
		super();
	}
	
	public TransportadoraException(String msg) {
		super(msg);
	}
	
}
