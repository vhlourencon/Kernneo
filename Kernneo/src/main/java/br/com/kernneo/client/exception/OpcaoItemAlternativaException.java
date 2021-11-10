package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class OpcaoItemAlternativaException extends Exception  implements IsSerializable {

	public OpcaoItemAlternativaException() {
		super();
	}
	
	public OpcaoItemAlternativaException(String msg) {
		super(msg);
	}
	
}
