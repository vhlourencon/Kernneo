package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class RelatorioException extends Exception  implements IsSerializable {

	public RelatorioException() {
		super();
	}
	
	public RelatorioException(String msg) {
		super(msg);
	}
	
}
