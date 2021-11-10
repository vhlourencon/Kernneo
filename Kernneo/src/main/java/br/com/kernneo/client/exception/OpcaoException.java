package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class OpcaoException extends Exception  implements IsSerializable {

	public OpcaoException() {
		super();
	}
	
	public OpcaoException(String msg) {
		super(msg);
	}
	
}
