package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ItemException extends Exception implements IsSerializable {

	public ItemException() {
		super();
	}
	
	public ItemException(String msg) {
		super(msg);
	}
	
	
	
}
