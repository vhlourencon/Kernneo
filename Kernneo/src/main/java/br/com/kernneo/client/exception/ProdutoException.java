package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ProdutoException extends Exception   implements IsSerializable{

	public ProdutoException() {
		super();
	}
	
	public ProdutoException(String msg) {
		super(msg);
	}
	
}
