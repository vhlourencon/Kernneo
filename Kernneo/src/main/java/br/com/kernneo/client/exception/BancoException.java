package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class BancoException extends Exception  implements IsSerializable {

	public BancoException() {
		super();
	}
	
	public BancoException(String msg) {
		super(msg);
	}
	
}
