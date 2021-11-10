package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CartaoException extends Exception  implements IsSerializable {

	public CartaoException() {
		super();
	}
	
	public CartaoException(String msg) {
		super(msg);
	}
	
}
