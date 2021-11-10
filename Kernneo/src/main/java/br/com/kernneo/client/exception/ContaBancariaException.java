package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ContaBancariaException extends Exception  implements IsSerializable {

	public ContaBancariaException() {
		super();
	}
	
	public ContaBancariaException(String msg) {
		super(msg);
	}
	
}
