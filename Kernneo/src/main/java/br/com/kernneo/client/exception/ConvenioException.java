package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ConvenioException extends Exception  implements IsSerializable {

	public ConvenioException() {
		super();
	}
	
	public ConvenioException(String msg) {
		super(msg);
	}
	
}
