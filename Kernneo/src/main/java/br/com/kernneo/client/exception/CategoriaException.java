package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CategoriaException extends Exception  implements IsSerializable {

	public CategoriaException() {
		super();
	}
	
	public CategoriaException(String msg) {
		super(msg);
	}
	
}
