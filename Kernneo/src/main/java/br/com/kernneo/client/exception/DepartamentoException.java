package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class DepartamentoException extends Exception  implements IsSerializable {

	public DepartamentoException() {
		super();
	}
	
	public DepartamentoException(String msg) {
		super(msg);
	}
	
}
