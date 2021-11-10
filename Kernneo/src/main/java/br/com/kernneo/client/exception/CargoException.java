package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CargoException extends Exception  implements IsSerializable {

	public CargoException() {
		super();
	}
	
	public CargoException(String msg) {
		super(msg);
	}
	
}
