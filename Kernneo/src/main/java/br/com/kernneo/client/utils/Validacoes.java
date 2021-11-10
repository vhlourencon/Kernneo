package br.com.kernneo.client.utils;


public class Validacoes {
    // Método que valida o CPF
    public boolean validacpf(String strCpf) { // formato XXX.XXX.XXX-XX
	if (strCpf == null) {
	    return false;
	} else {
	    if (!strCpf.substring(0, 1).equals("")) {
		try {
		    boolean validado = true;
		    int d1, d2;
		    int digito1, digito2, resto;
		    int digitoCPF;
		    String nDigResult;
		    strCpf = strCpf.replace('.', ' ');
		    strCpf = strCpf.replace('-', ' ');
		    strCpf = strCpf.replaceAll(" ", "");
		    d1 = d2 = 0;
		    digito1 = digito2 = resto = 0;

		    for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a
			// seguinte por 4 e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo
			// o primeiro digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		    }
		    ;

		    // Primeiro resto da divisão por 11.
		    resto = (d1 % 11);

		    // Se o resultado for 0 ou 1 o digito é 0 caso contrário o
		    // digito é 11 menos o resultado anterior.
		    if (resto < 2)
			digito1 = 0;
		    else
			digito1 = 11 - resto;

		    d2 += 2 * digito1;

		    // Segundo resto da divisão por 11.
		    resto = (d2 % 11);

		    // Se o resultado for 0 ou 1 o digito é 0 caso contrário o
		    // digito é 11 menos o resultado anterior.
		    if (resto < 2)
			digito2 = 0;
		    else
			digito2 = 11 - resto;

		    // Digito verificador do CPF que está sendo validado.
		    String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

		    // Concatenando o primeiro resto com o segundo.
		    nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		    // comparar o digito verificador do cpf com o primeiro resto
		    // + o segundo resto.
		    return nDigVerific.equals(nDigResult);
		} catch (Exception e) {
		    System.err.println("Erro !" + e);
		    return false;
		}
	    } else {
		return false;
	    }
	}
    }

    public boolean isTelefone(String numeroTelefone) {
	if (numeroTelefone == null) {
	    return false;
	} else {

	    if (numeroTelefone.contains("(") || numeroTelefone.contains(")") || numeroTelefone.contains("-")) {
		numeroTelefone = limparCaracterEspecial(numeroTelefone);
	    }
	    numeroTelefone = formatarTel(numeroTelefone);
	    return numeroTelefone.matches("^\\([1-9]{2}\\) [2-9][0-9]{3,4}\\-[0-9]{4}$");
	}
    }

    public String telFormatado(String numeroTelefone) {
	if (numeroTelefone == null) {
	    return null;
	} else {
	    if (numeroTelefone.contains("(") || numeroTelefone.contains(")") || numeroTelefone.contains("-")) {
		numeroTelefone = limparCaracterEspecial(numeroTelefone);
	    }
	    numeroTelefone = formatarTel(numeroTelefone);

	    return numeroTelefone;
	}
    }

    public String limparCaracterEspecial(String campo) {
	if (campo == null) {
	    return null;
	} else {
	    campo = campo.replace(" ", "");
	    campo = campo.replace("(", "");
	    campo = campo.replace(")", "");
	    campo = campo.replace("-", "");
	    campo = campo.replace(" ", "");
	    return campo;
	}
    }

    public static String formatarTel(String campo) {
	if (campo == null) {
	    return null;
	} else {
	    String formatado = "";
	    if (campo.length() == 10) {
		formatado = "(" + campo.substring(0, 2) + ") ";
		formatado += campo.substring(2, 6) + "-";
		formatado += campo.substring(6, 10);
	    } else if (campo.length() == 11) {
		formatado = "(" + campo.substring(0, 2) + ") ";
		formatado += campo.substring(2, 7) + "-";
		formatado += campo.substring(7, 11);
	    } else {
		formatado = campo;
	    }

	    return formatado;
	}
    }

    public String cepFormatado(String cep) {

	if (cep == null) {
	    return null;
	} else {
	    if (cep.contains("(") || cep.contains(")") || cep.contains("-")) {
		cep = limparCaracterEspecial(cep);
	    }
	    cep = formatarCep(cep);

	    return cep;
	}

    }

    public String formatarCep(String campo) {
	if (campo == null) {
	    return null;
	} else {
	    String formatado = "";
	    if (campo.length() == 8) {
		formatado = campo.substring(0, 5) + "-";
		formatado += campo.substring(5, 8);
	    } else {
		formatado = campo;
	    }

	    return formatado;
	}
    }

    public boolean isCep(String cep) {
	if (cep == null) {
	    return false;
	} else {

	    if (cep.contains("(") || cep.contains(")") || cep.contains("-")) {
		cep = limparCaracterEspecial(cep);
	    }
	    cep = formatarCep(cep);
	    return cep.matches("^\\d{5}-\\d{3}$");
	}
    }

}