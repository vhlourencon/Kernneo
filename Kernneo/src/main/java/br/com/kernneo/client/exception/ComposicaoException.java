package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ComposicaoException extends Exception  implements IsSerializable {

	public ComposicaoException() {
		super();
	}
	
	public ComposicaoException(String msg) {
		super(msg);
	}
	
}
