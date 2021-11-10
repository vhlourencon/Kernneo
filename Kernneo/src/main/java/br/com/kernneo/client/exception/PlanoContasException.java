package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class PlanoContasException extends Exception  implements IsSerializable {

	public PlanoContasException() {
		super();
	}
	
	public PlanoContasException(String msg) {
		super(msg);
	}
	
}
