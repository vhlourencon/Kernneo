package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class OpcaoItemException extends Exception  implements IsSerializable {

	public OpcaoItemException() {
		super();
	}
	
	public OpcaoItemException(String msg) {
		super(msg);
	}
	
}
