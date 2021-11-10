package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class PedidoException extends Exception implements IsSerializable {

	public PedidoException() {
		super();
	}
	
	public PedidoException(String msg) {
		super(msg);
	}
	
	
	
}
