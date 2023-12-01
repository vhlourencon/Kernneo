package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;


public class EntregaEquipamentException extends Exception  implements IsSerializable {

	public EntregaEquipamentException() {
		super();
	}
	
	public EntregaEquipamentException(String msg) {
		super(msg);
	}
	
}
