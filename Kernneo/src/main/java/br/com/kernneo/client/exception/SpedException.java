package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SpedException  extends Exception  implements IsSerializable {

	public SpedException() {
		super();
	}
	
	public SpedException(String msg) {
		super(msg);
	}
	
}
