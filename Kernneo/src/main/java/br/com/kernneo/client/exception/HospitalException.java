package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class HospitalException extends Exception implements IsSerializable {

	public HospitalException() {
		super();
	}
	
	public HospitalException(String msg) {
		super(msg);
	}
	
	
	
}
