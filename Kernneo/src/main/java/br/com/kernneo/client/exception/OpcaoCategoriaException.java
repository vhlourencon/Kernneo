package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class OpcaoCategoriaException extends Exception  implements IsSerializable {

	public OpcaoCategoriaException() {
		super();
	}
	
	public OpcaoCategoriaException(String msg) {
		super(msg);
	}
	
}
