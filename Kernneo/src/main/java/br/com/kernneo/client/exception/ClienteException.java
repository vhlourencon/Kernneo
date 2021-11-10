package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ClienteException extends Exception  implements IsSerializable {

	public ClienteException() {
		super();
	}
	
	public ClienteException(String msg) {
		super(msg);
	}
	
}
