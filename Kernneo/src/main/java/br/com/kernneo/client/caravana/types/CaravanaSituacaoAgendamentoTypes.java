package br.com.kernneo.client.caravana.types;

import java.util.LinkedHashMap;

public enum CaravanaSituacaoAgendamentoTypes {
	aprovado, reprovado, em_analise;

	public static LinkedHashMap<String, String> getLinkedHashMap() {
		LinkedHashMap<String, String> listaHashMap = new LinkedHashMap<String, String>();

		for (CaravanaSituacaoAgendamentoTypes enumTypes : CaravanaSituacaoAgendamentoTypes.values()) {
			listaHashMap.put(enumTypes.toString(), enumTypes.toString());
		}

		return listaHashMap;
	}

}