package br.com.kernneo.client.caravana.types;

import java.util.LinkedHashMap;

public enum CaravanaEventoStatusTypes {

	cadastrado, iniciado, cancelado, suspenso, finalizado;

	public static LinkedHashMap<String, String> getLinkedHashMap() {
		LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

		for (CaravanaEventoStatusTypes enumTypes : CaravanaEventoStatusTypes.values()) {
			listaHashMap.put(enumTypes.toString(), enumTypes.toString());
		}

		return listaHashMap;
	}
}
