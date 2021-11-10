package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EnderecoFornecedorException extends Exception  implements IsSerializable {

	public EnderecoFornecedorException() {
		super();
	}
	
	public EnderecoFornecedorException(String msg) {
		super(msg);
	}
	
}
