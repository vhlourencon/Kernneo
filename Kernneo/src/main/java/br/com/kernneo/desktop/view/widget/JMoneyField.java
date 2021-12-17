package br.com.kernneo.desktop.view.widget;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

public class JMoneyField extends JFormattedTextField {

    private static final long serialVersionUID = -5712106034509737967L;
    private static final SimpleAttributeSet nullAttribute = new SimpleAttributeSet();

    /** 
     * Creates a new instance of JMoneyField 
     */
    public JMoneyField() {
        this.setHorizontalAlignment(JTextField.RIGHT);
        this.setDocument(new MoneyFieldDocument());
        this.addFocusListener(new MoneyFieldFocusListener());
        this.setText("0,00");
        this.addCaretListener(new CaretListener() {

            public void caretUpdate(CaretEvent e) {
                if (e.getDot() != getText().length()) {
                    getCaret().setDot(getText().length());
                }
            }
        });
    }

    
    public Double getValor() {

		String valor = getText().replaceAll("\\D+", "");
		valor = valor.substring(0, valor.length() - 2) + "." + valor.substring(valor.length() - 2);
		Double valorDouble = Double.valueOf(valor);

		return valorDouble;
	}
    
    public void setValor(Double valor) {
		Double valorAux = valor;

		String parte1 = valorAux.toString().substring(0, valorAux.toString().indexOf("."));
		String parte2 = valorAux.toString().substring(valorAux.toString().indexOf(".") + 1, valorAux.toString().length());

		if (parte2.length() == 1) {
			parte2 = parte2 + "0";
		}

		String newText = formatMoney(parte1 + parte2);
		this.setText(newText);
	}

	

	private static String formatMoney(String v) {
		v = v.replaceAll("\\D+", ""); // Get only digits
		v = v.replaceFirst("^0+(?!)", ""); // remove leading zeros

		if (v.length() > 2) {
			v =  v.substring(0, v.length() - 2) + "," + v.substring(v.length() - 2);
		} else if (v.length() == 2) {
			v = "0," + v;
		} else if (v.length() == 1) {
			v = "0,0" + v;
		}
		int count = 0;
		for (int i = v.indexOf(',') - 1; i > 1; i--) {
			if (count % 3 == 0 && count != 0) {
				v = v.substring(0, i + 1) + "." + v.substring(i + 1);
			}
			count++;
		}
		if (v.equalsIgnoreCase(""))
			return "0,00";
		else
			return v;
	}
    private final class MoneyFieldFocusListener extends FocusAdapter {

        @Override
        public void focusGained(FocusEvent e) {
            selectAll();
        }
    }

    private final class MoneyFieldDocument extends PlainDocument {

        /** 
         *  
         */
        private static final long serialVersionUID = -3802846632709128803L;

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            String original = getText(0, getLength());

            // Permite apenas digitar até 16 caracteres (9.999.999.999,99)  
            if (original.length() < 16) {
                StringBuffer mascarado = new StringBuffer();
                if (a != nullAttribute) {
                    //limpa o campo  
                    remove(-1, getLength());

                    mascarado.append((original + str).replaceAll("[^0-9]", ""));
                    for (int i = 0; i < mascarado.length(); i++) {
                        if (!Character.isDigit(mascarado.charAt(i))) {
                            mascarado.deleteCharAt(i);
                        }
                    }
                    Long number = new Long(mascarado.toString());

                    mascarado.replace(0, mascarado.length(), number.toString());

                    if (mascarado.length() < 3) {
                        if (mascarado.length() == 1) {
                            mascarado.insert(0, "0");
                            mascarado.insert(0, ",");
                            mascarado.insert(0, "0");
                        } else if (mascarado.length() == 2) {
                            mascarado.insert(0, ",");
                            mascarado.insert(0, "0");
                        }
                    } else {
                        mascarado.insert(mascarado.length() - 2, ",");
                    }

                    if (mascarado.length() > 6) {
                        mascarado.insert(mascarado.length() - 6, '.');
                        if (mascarado.length() > 10) {
                            mascarado.insert(mascarado.length() - 10, '.');
                            if (mascarado.length() > 14) {
                                mascarado.insert(mascarado.length() - 14, '.');
                            }
                        }
                    }
                    super.insertString(0, mascarado.toString(), a);
                } else {
                    if (original.length() == 0) {
                        super.insertString(0, "0,00", a);
                    }
                }
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            if (len == getLength()) {
                super.remove(0, len);
                if (offs != -1) {
                    insertString(0, "", nullAttribute);
                }
            } else {
                String original = getText(0, getLength()).substring(0, offs) + getText(0, getLength()).substring(offs + len);
                super.remove(0, getLength());
                insertString(0, original, null);
            }
        }
    }
}  