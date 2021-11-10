package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ComposicaoProdutoException extends Exception  implements IsSerializable{

	public ComposicaoProdutoException() {
		super();
	}
	
	public ComposicaoProdutoException(String msg) {
		super(msg);
	}
	
}
