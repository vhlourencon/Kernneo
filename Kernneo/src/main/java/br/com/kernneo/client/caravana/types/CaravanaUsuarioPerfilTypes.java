package br.com.kernneo.client.caravana.types;

import java.util.LinkedHashMap;

public enum CaravanaUsuarioPerfilTypes {
	administrador, credenciamento, agendamento ;

	public static LinkedHashMap<String, String> getLinkedHashMap() {
		LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

		for (CaravanaUsuarioPerfilTypes enumTypes : CaravanaUsuarioPerfilTypes.values()) {
			listaHashMap.put(enumTypes.toString(), enumTypes.toString());
		}

		return listaHashMap;
	}

}