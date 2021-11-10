package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class NCMException extends Exception  implements IsSerializable {

	public NCMException() {
		super();
	}
	
	public NCMException(String msg) {
		super(msg);
	}
	
}
