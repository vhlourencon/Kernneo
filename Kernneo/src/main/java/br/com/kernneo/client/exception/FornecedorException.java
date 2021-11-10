package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class FornecedorException extends Exception  implements IsSerializable {

	public FornecedorException() {
		super();
	}
	
	public FornecedorException(String msg) {
		super(msg);
	}
	
}
