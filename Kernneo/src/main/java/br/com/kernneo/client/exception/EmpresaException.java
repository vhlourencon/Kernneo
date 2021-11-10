package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EmpresaException extends Exception  implements IsSerializable {

	public EmpresaException() {
		super();
	}
	
	public EmpresaException(String msg) {
		super(msg);
	}
	
}
