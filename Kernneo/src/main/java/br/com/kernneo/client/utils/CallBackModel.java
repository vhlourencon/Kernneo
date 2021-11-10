package br.com.kernneo.client.utils;

import br.com.kernneo.client.model.GenericModel;

public interface CallBackModel<T extends Object> {

	void execute(T t);
}
