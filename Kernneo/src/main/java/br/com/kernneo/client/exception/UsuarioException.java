package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class UsuarioException extends Exception   implements IsSerializable{

	public UsuarioException() {
		super();
	}
	
	public UsuarioException(String msg) {
		super(msg);
	}
	
}
