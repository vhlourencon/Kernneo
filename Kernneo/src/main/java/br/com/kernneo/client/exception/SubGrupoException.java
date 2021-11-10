package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class SubGrupoException extends Exception   implements IsSerializable{

    public SubGrupoException() {
	super();
    }

    public SubGrupoException(String msg) {
	super(msg);
    }

}
