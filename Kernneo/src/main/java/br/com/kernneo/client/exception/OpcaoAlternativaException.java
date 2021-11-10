package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class OpcaoAlternativaException extends Exception  implements IsSerializable {

	public OpcaoAlternativaException() {
		super();
	}
	
	public OpcaoAlternativaException(String msg) {
		super(msg);
	}
	
}
