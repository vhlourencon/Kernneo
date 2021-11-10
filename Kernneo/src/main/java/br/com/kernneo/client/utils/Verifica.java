package br.com.kernneo.client.utils;

public class Verifica {
    
    public static Boolean isNumber(String tecla) {

	if (tecla.equals("0")) {
		return true;
	}

	if (tecla.equals("1")) {
		return true;
	}
	if (tecla.equals("2")) {
		return true;
	}
	if (tecla.equals("3")) {
		return true;
	}
	if (tecla.equals("4")) {
		return true;
	}
	if (tecla.equals("5")) {
		return true;
	}
	if (tecla.equals("6")) {
		return true;
	}
	if (tecla.equals("7")) {
		return true;
	}
	if (tecla.equals("8")) {
		return true;
	}
	if (tecla.equals("9")) {
		return true;
	}

	return false;
}

}
