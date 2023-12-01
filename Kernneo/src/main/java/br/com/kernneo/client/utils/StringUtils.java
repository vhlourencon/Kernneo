package br.com.kernneo.client.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * This class contains general methods to work with Strings.
 * 
 * @author Carlos Tasada
 * 
 */
public final class StringUtils {
	private static SimpleDateFormat	formatterHora = new SimpleDateFormat("HH:mm");
	private StringUtils() {
	}
	
    public static String currencyFormat(BigDecimal n) {
        return java.text.NumberFormat.getCurrencyInstance().format(n);
    }

	
	public static String reformatCartaoSus(String cartaoSus) {
		String text = cartaoSus;
		if (text != null) {
			text = text.trim();
			text = text.replaceAll("\\D+", "");
			if (text.length() == 15) {
				text =     text.substring(0, 3) +  " " + text.substring(3, 7) + " " + text.substring(7, 11) + " " + text.substring(11,15);
			}
		}
		return text; 
	}
	public static String reformatPhone(String telefone) {
		String text = telefone;
		if (text != null) {
			text = text.replaceAll("\\D+", "");
			if (text.length() == 11) {
				text =    "(" + text.substring(0, 2) + ") " + text.substring(2, 7) + "-" + text.substring(7, 11);
			}
		}
		return text; 
	}

	public static String getDataHoraFormatada(Date data) {
		String dataFormatada = "";

		if (data != null) {
			dataFormatada = DateTimeFormat.getFormat("HH:mm - dd/MM/yyyy").format(data);
		}

		return dataFormatada;
	}

	public static String getDataFormatada(Date data) {
		String dataFormatada = "";

		if (data != null) {
			dataFormatada = DateTimeFormat.getFormat("dd/MM/yyyy").format(data);
		}

		return dataFormatada;
	}

	public static String getDoubleFormatadoEmDinheiro(BigDecimal valor) {
		String valorString;
		if (valor != null) {
			valorString = NumberFormat.getSimpleCurrencyFormat().format(valor);
			if (valor.compareTo(BigDecimal.ZERO) < 0) {
				valorString = "- " + NumberFormat.getSimpleCurrencyFormat().format(valor.multiply(new BigDecimal(-1)));

			}
		} else {
			valorString = NumberFormat.getSimpleCurrencyFormat().format(0.0);
		}

		return valorString;

	}

	/**
	 * This method returns an String where the ingested value is repeated n
	 * times.
	 * 
	 * @param s
	 *            Input String
	 * @param n
	 *            Number of times is repeated
	 * @return new String with the parameter repeated
	 */
	public static String repeat(String s, int n) {
		if (s == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
	public  static String getHoraFormat(Date data) {
		return formatterHora.format(data);
	}
	
}