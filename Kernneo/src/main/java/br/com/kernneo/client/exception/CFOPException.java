package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CFOPException extends Exception  implements IsSerializable{

	public CFOPException() {
		super();
	}
	
	public CFOPException(String msg) {
		super(msg);
	}
	
}
