/*
 * JMoedaRealTextField.java
 *
 * Created on 7 de Julho de 2007, 06:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.kernneo.desktop.view.widget;

import java.math.BigDecimal;
import java.math.MathContext;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Cleiton
 */
public class JMoedaRealTextField  extends JTextField {
    
    //variavel de controle para saber se o usuario apertou a tecla "_".
    boolean teclado = false;
    
    //variavel de controle para saber se o digito esta sendo movido para o lado.
    boolean movendo = false;
    
    //variavel de controle para saber se deve-se acrescentar a palavra no plural ou nao.
    boolean plural = false;
    
    //variavel de controle para saber se � a primeira palavra que esta sendo inserida
    //na variavel numExtenso.
    boolean primeiraPalavra  = false;
    
    //variaveis para escrever o valor por extenso
    private String numExtenso    = "";
    private String zero          = "zero ";
    private String hum           = "Hum ";
    private String um            = "um ";
    private String dois          = "dois ";
    private String tres          = "tr�s ";
    private String quatro        = "quatro ";
    private String cinco         = "cinco ";
    private String seis          = "seis ";
    private String sete          = "sete ";
    private String oito          = "oito ";
    private String nove          = "nove ";
    private String dez           = "dez ";
    private String onze          = "onze ";
    private String doze          = "doze ";
    private String treze         = "treze ";
    private String quatorze      = "quatorze ";
    private String quinze        = "quinze ";
    private String dezesseis     = "dezesseis ";
    private String dezessete     = "dezessete ";
    private String dezoito       = "dezoito ";
    private String dezenove      = "dezenove ";
    private String vinte         = "vinte ";
    private String vintee        = "vinte e ";
    private String trinta        = "trinta ";
    private String trintae       = "trinta e ";
    private String quarenta      = "quarenta ";
    private String quarentae     = "quarenta e ";
    private String cinquenta     = "cinquenta ";
    private String cinquentae    = "cinquenta e ";
    private String sessenta      = "sessenta ";
    private String sessentae     = "sessenta e ";
    private String setenta       = "setenta ";
    private String setentae      = "setenta e ";
    private String oitenta       = "oitenta ";
    private String oitentae      = "oitenta e ";
    private String noventa       = "noventa ";
    private String noventae      = "noventa e ";
    private String cem           = "cem ";
    private String centoe        = "cento e ";
    private String duzentos      = "duzentos ";
    private String duzentose     = "duzentos e ";
    private String trezentos     = "trezentos ";
    private String trezentose    = "trezentos e ";
    private String quatrocentos  = "quatrocentos ";
    private String quatrocentose = "quatrocentos e ";
    private String quinhentos    = "quinhentos ";
    private String quinhentose   = "quinhentos e ";
    private String seiscentos    = "seiscentos ";
    private String seiscentose   = "seiscentos e ";
    private String setecentos    = "setecentos ";
    private String setecentose   = "setecentos e ";
    private String oitocentos    = "oitocentos ";
    private String oitocentose   = "oitocentos e ";
    private String novecentos    = "novecentos ";
    private String novecentose   = "novecentos e ";
    private String mil           = "mil ";
    private String mile          = "mil e ";
    private String milhao        = "milh�o ";
    private String milhoes       = "milh�es ";
    private String milhaov       = "milh�o, ";
    private String milhoesv      = "milh�es, ";
    private String milhaoe       = "milh�o e ";
    private String milhoese      = "milh�es e ";
    private String bilhao        = "bilh�o ";
    private String bilhoes       = "bilh�es ";
    private String bilhaov       = "bilh�o, ";
    private String bilhoesv      = "bilh�es, ";
    private String bilhaoe       = "bilh�o e ";
    private String bilhoese      = "bilh�es e ";
    private String trilhao       = "trilh�o ";
    private String trilhoes      = "trilh�es ";
    private String trilhaov      = "trilh�o, ";
    private String trilhoesv     = "trilh�es, ";
    private String trilhaoe      = "trilh�o e ";
    private String trilhoese     = "trilh�es e ";
    private String real          = "real.";
    private String reale         = "real e ";
    private String reais         = "reais. ";
    private String reaise        = "reais e ";
    private String centavo       = "centavo.";
    private String centavos      = "centavos.";
    
    /** Creates a new instance of JMoedaRealTextField */
    public JMoedaRealTextField() {
        setSize(158, 20);
        setDocument(new PlainDocument() {
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                char c[] = str.toCharArray();
                if (!Character.isDigit(c[0]) && str.equals("R$ ___.___.___.___.___,__") || str.equals("_")) {
                    if (!teclado) {
                        super.insertString(offs, str, a);
                        teclado = false;
                    }
                }
                
                if (Character.isDigit(c[0])) {
                    if (movendo) {
                        super.insertString(offs, str, a);
                    } else {
                        if (getText(3, 1).equals("_")) {
                            inserirDigito(offs);
                            setCaretPosition(offs - 1);
                            select(offs - 1, offs);
                            replaceSelection("");
                            super.insertString(offs - 1, str, a);
                            movendo = false;
                        }
                    }
                }
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setCaretPosition(25);
                evt.consume();
            }
            
        });
        
        addKeyListener(new java.awt.event.KeyAdapter() {
            boolean ok = false;
            
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == evt.VK_BACK_SPACE) {
                    try {
                        apagarDigito();
                        setCaretPosition(25);
                        movendo = false;
                        evt.consume();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                
                if (evt.getKeyCode() == evt.VK_DELETE) {
                    try {
                        apagarDigito();
                        setCaretPosition(25);
                        movendo = false;
                        evt.consume();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                
                if (evt.getKeyCode() == evt.VK_MINUS) {
                    teclado = true;
                    evt.consume();
                }
                
                if (evt.getKeyCode() == evt.VK_HOME || evt.getKeyCode() == evt.VK_END) {
                    evt.consume();
                }
                
                if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_RIGHT) {
                    evt.consume();
                }
                
                if (evt.getKeyCode() == evt.VK_V) {
                    evt.consume();
                }
            }
        });
        
        setText("R$ ___.___.___.___.___,__");
        setCaretPosition(25);
    }
    
    public void inserirDigito(int offs) throws BadLocationException {
        if (getText(3, 1).equals("_")) {
            movendo = true;
            for (int i = 4; i <= 25; i++) {
                if (getText(i, 1).equals("_") || getText(i, 1).equals(".") || getText(i, 1).equals(",")) {
                    setCaretPosition(i + 1);
                } else {
                    if (i < 25) {
                        if (i == 23 || i == 19 || i == 15 || i == 11 || i == 7) {
                            String s = getText(i, 1);
                            setCaretPosition(i - 2);
                            select(i - 2, i - 1);
                            replaceSelection(s);
                        } else {
                            String s = getText(i, 1);
                            setCaretPosition(i - 1);
                            select(i - 1, i);
                            replaceSelection(s);
                        }
                    }
                }
            }
        }
    }
    
    public void apagarDigito() throws BadLocationException {
        if (!getText(24, 1).equals("_")) {
            movendo = true;
            for (int i = 23; i >= 3; i--) {
                if (getText(i, 1).equals("_") || getText(i, 1).equals(".") || getText(i, 1).equals(",")) {
                    if (getText(i, 1).equals("_")) {
                        if (!getText(i + 1, 1).equals(".") && !getText(i + 1, 1).equals(",")) {
                            select(i + 1, i + 2);
                            replaceSelection("_");
                            break;
                        } else {
                            select(i + 2, i + 3);
                            replaceSelection("_");
                            break;
                        }
                    } else {
                        setCaretPosition(i - 1);
                    }
                } else {
                    if (i == 21 || i == 17 || i == 13 || i == 9 || i == 5) {
                        String s = getText(i, 1);
                        setCaretPosition(i + 2);
                        select(i + 2, i + 3);
                        replaceSelection(s);
                    } else {
                        String s = getText(i, 1);
                        setCaretPosition(i + 1);
                        select(i + 1, i + 2);
                        replaceSelection(s);
                        if (i == 3) {
                            select(i, i + 1);
                            replaceSelection("_");
                        }
                    }
                }
            }
        }
    }
    
    public BigDecimal getValor() throws BadLocationException {
        for (int i = 3; i < 25; i++) {
            setCaretPosition(i);
            char c[] = getText(i, 1).toCharArray();
            if (Character.isDigit(c[0])) {
                String valor1 = getText(i, 25 - i);
                String valor2 = "";
                char ponto = '.';
                char virgula = ',';
                for (int x = 0; x < valor1.length(); x++) {
                    if(valor1.charAt(x) != ponto){
                        if (valor1.charAt(x) != virgula) {
                            valor2 += String.valueOf(valor1.charAt(x));
                        } else {
                            valor2 += ".";
                        }
                    }
                }
                if (valor2.length() == 1) {
                    valor2 = "0.0" + valor2;
                } else {
                    if (valor2.length() == 2) {
                        valor2 = "0." + valor2;
                    }
                }
                BigDecimal bd = new BigDecimal(valor2, MathContext.UNLIMITED );
                setCaretPosition(25);
                return bd;
            }
        }
        setCaretPosition(25);
        return null;
    }
    
    public void setValor(BigDecimal valor) {
        setText("R$ ___.___.___.___.___,__");
        String sValor = String.valueOf(valor);
        String soNumero = "";
        char ponto = '.';
        if (sValor != "") {
            for (int i = 0; i < sValor.length(); i++) {
                if(sValor.charAt(i) != ponto){
                    try {
                        getDocument().insertString(25, String.valueOf(sValor.charAt(i)), null);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    //setText(String.valueOf(sValor.charAt(i)));
                    //soNumero += String.valueOf(sValor.charAt(i));
                }
            }
        }
    }
    
    public String getPorExtenso() throws BadLocationException {
        numExtenso = "";
        for (int i = 3; i <= 25; i++) {
            if (!getText(i, 1).equals("_")) {
                if (getText(i, 1).equals(".") && !numExtenso.equals("")) {
                    switch (i) {
                        case 6 :
                            if (plural) {
                                if (!getText(i - 3, 3).equals("000")) {
                                    if (!getText(i + 1, 15).equals("000.000.000.000")) {
                                        if (getText(i + 1, 7).equals("000.000.000")) {
                                            numExtenso = numExtenso + trilhoese;
                                        } else {
                                            numExtenso = numExtenso + trilhoesv;
                                        }
                                    } else {
                                        numExtenso = numExtenso + trilhoes + "de ";
                                        plural = true;
                                    }
                                }
                            } else { /** singular **/
                                if (!getText(i - 3, 3).equals("000")) {
                                    if (!getText(i + 1, 15).equals("000.000.000.000")) {
                                        if (getText(i + 1, 11).equals("000.000.000")) {
                                            numExtenso = numExtenso + trilhaoe;
                                        } else {
                                            numExtenso = numExtenso + trilhaov;
                                        }
                                    } else {
                                        numExtenso = numExtenso + trilhao + "de ";
                                        plural = true;
                                    }
                                }
                            }
                            break;
                        case 10 :
                            if (plural) {
                                if (!getText(i - 3, 3).equals("000")) {
                                    if (!getText(i + 1, 11).equals("000.000.000")) {
                                        if (getText(i + 1, 7).equals("000.000")) {
                                            numExtenso = numExtenso + bilhoese;
                                        } else {
                                            numExtenso = numExtenso + bilhoesv;
                                        }
                                    } else {
                                        if (getText(3, 3).equals("___") || getText(3, 7).equals("__0") ||
                                                getText(3, 7).equals("_00") || getText(3, 7).equals("000")) {
                                            numExtenso = numExtenso + bilhoes + "de ";
                                            plural = true;
                                        }
                                    }
                                }
                            } else {/** singular **/
                                if (!getText(i - 3, 3).equals("000")) {
                                    if (!getText(i + 1, 11).equals("000.000.000")) {
                                        if (getText(i + 1, 7).equals("000.000")) {
                                            numExtenso = numExtenso + bilhaoe;
                                            plural = true;
                                        } else {
                                            numExtenso = numExtenso + bilhaov;
                                            plural = true;
                                        }
                                    } else {
                                        if (getText(3, 3).equals("___") || getText(3, 7).equals("__0") ||
                                                getText(3, 7).equals("_00") || getText(3, 7).equals("000")) {
                                            numExtenso = numExtenso + bilhao + "de ";
                                            plural = true;
                                        }
                                    }
                                }
                            }
                            break;
                        case 14 :
                            if (plural) {
                                if (!getText(i - 3, 3).equals("000")) {
                                    if (!getText(i + 1, 7).equals("000.000")) {
                                        if (getText(i + 1, 3).equals("000")/* && getText(i + 1, 1).equals("0") || getText(i + 1, 3).equals("100")*/) {
                                            numExtenso = numExtenso + milhoese;
                                            plural = true;
                                        } else {
                                            numExtenso = numExtenso + milhoesv;
                                            plural = true;
                                        }
                                    } else {
                                        if (getText(3, 7).equals("___.___") || getText(3, 7).equals("___.__0") ||
                                                getText(3, 7).equals("___._00") || getText(3, 7).equals("___.000") ||
                                                getText(3, 7).equals("__0.000") || getText(3, 7).equals("_00.000") ||
                                                getText(3, 7).equals("000.000")) {
                                            numExtenso = numExtenso + milhoes + "de ";
                                            plural = true;
                                        }
                                    }
                                }
                            } else {/** singular **/
                                if (!getText(i - 3, 3).equals("000")) {
                                    if (!getText(i + 1, 7).equals("000.000")) {
                                        if (getText(i + 1, 3).equals("000")) {
                                            numExtenso = numExtenso + milhaoe;
                                            plural = true;
                                        } else {
                                            numExtenso = numExtenso + milhaov;
                                            plural = true;
                                        }
                                    } else {
                                        if (getText(3, 7).equals("___.___") || getText(3, 7).equals("___.__0") ||
                                                getText(3, 7).equals("___._00") || getText(3, 7).equals("___.000") ||
                                                getText(3, 7).equals("__0.000") || getText(3, 7).equals("_00.000") ||
                                                getText(3, 7).equals("000.000")) {
                                            numExtenso = numExtenso + milhao + "de ";
                                            plural = true;
                                        }
                                    }
                                }
                            }
                            break;
                        case 18 :
                            if (!getText(i - 3, 3).equals("000")) {
                                if (!getText(i + 1, 3).equals("000")) {
                                    //if (getText(i + 1, 1).equals("0") || getText(i + 1, 3).equals("100")) {
                                    numExtenso = numExtenso + mile;
                                    plural = true;
                                } else {
                                    numExtenso = numExtenso + mil;
                                    plural = true;
                                }
                            }
                            break;
                    }
                    
                } else {
                    if (getText(i, 1).equals(",") && !numExtenso.equals("")) {
                        if (plural) {
                            if (!getText(i + 1, 2).equals("00")) {
                                numExtenso = numExtenso + reaise;
                                plural = false;
                            } else {
                                numExtenso = numExtenso + reais;
                                plural = false;
                            }
                        } else {
                            if (!getText(i + 1, 2).equals("00")) {
                                numExtenso = numExtenso + reale;
                            } else {
                                numExtenso = numExtenso + real;
                            }
                        }
                    } else {
                        if (i == 25) {
                            if (!numExtenso.equals("")) {
                                if (plural) {
                                    numExtenso = numExtenso + centavos;
                                    plural = false;
                                } else {
                                    if (!getText(i - 2, 2).equals("00")) {
                                        numExtenso = numExtenso + centavo;
                                    }
                                }
                            }
                        } else {
                            if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19) {
                                montarCentena(i);
                            } else {
                                if (i == 4 || i == 8 || i == 12 || i == 16 || i == 20 || i == 23) {
                                    montarDezena(i);
                                } else {
                                    if (i == 5 || i == 9 || i == 13 || i == 17 || i == 21 || i == 24) {
                                        if (!getText(i - 1, 1).equals("1")) {
                                            montarUnidade(i);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return numExtenso;
    }
    
    public void montarCentena(int i) throws BadLocationException {
        if (numExtenso.equals("")) {
            primeiraPalavra  = true;
        }
        switch (Integer.parseInt(getText(i, 1))) {
            case 9 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + novecentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + novecentose;
                    plural = true;
                }
                break;
            case 8 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + oitocentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + oitocentose;
                    plural = true;
                }
                break;
            case 7 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + setecentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + setecentose;
                    plural = true;
                }
                break;
            case 6 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + seiscentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + seiscentose;
                    plural = true;
                }
                break;
            case 5 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + quinhentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + quinhentose;
                    plural = true;
                }
                break;
            case 4 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + quatrocentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + quatrocentose;
                    plural = true;
                }
                break;
            case 3 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + trezentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + trezentose;
                    plural = true;
                }
                break;
            case 2 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + duzentos;
                    plural = true;
                } else {
                    numExtenso = numExtenso + duzentose;
                    plural = true;
                }
                break;
            case 1 :
                if (getText(i + 1, 1).equals("0") && getText(i + 2, 1).equals("0")) {
                    numExtenso = numExtenso + cem;
                    plural = true;
                } else {
                    numExtenso = numExtenso + centoe;
                    plural = true;
                }
                break;
            case 0 : break;
        }
        if (primeiraPalavra) {
            String a = String.valueOf(numExtenso.charAt(0));
            String b = a.toUpperCase();
            numExtenso = numExtenso.replaceFirst(a, b);
            primeiraPalavra = false;
        }
    }
    
    public void montarDezena(int i) throws BadLocationException {
        if (numExtenso.equals("")) {
            primeiraPalavra  = true;
        }
        switch (Integer.parseInt(getText(i, 1))) {
            case 9 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + noventae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + noventa;
                    plural = true;
                }
                break;
            case 8 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + oitentae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + oitenta;
                    plural = true;
                }
                break;
            case 7 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + setentae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + setenta;
                    plural = true;
                }
                break;
            case 6 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + sessentae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + sessenta;
                    plural = true;
                }
                break;
            case 5 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + cinquentae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + cinquenta;
                    plural = true;
                }
                break;
            case 4 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + quarentae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + quarenta;
                    plural = true;
                }
                break;
            case 3 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + trintae;
                    plural = true;
                } else {
                    numExtenso = numExtenso + trinta;
                    plural = true;
                }
                break;
            case 2 :
                if (!getText(i + 1, 1).equals("0")) {
                    numExtenso = numExtenso + vintee;
                    plural = true;
                } else {
                    numExtenso = numExtenso + vinte;
                    plural = true;
                }
                break;
            case 1 :
                montarUnidade(i);
                break;
            case 0 : break;
        }
        if (primeiraPalavra) {
            String a = String.valueOf(numExtenso.charAt(0));
            String b = a.toUpperCase();
            numExtenso = numExtenso.replaceFirst(a, b);
            primeiraPalavra = false;
        }
    }
    
    public void montarUnidade(int i) throws BadLocationException {
        if (numExtenso.equals("")) {
            primeiraPalavra  = true;
        }
        switch (Integer.parseInt(getText(i, 1))) {
            case 9 :
                numExtenso = numExtenso + nove;
                plural = true;
                break;
            case 8 :
                numExtenso = numExtenso + oito;
                plural = true;
                break;
            case 7 :
                numExtenso = numExtenso + sete;
                plural = true;
                break;
            case 6 :
                numExtenso = numExtenso + seis;
                plural = true;
                break;
            case 5 :
                numExtenso = numExtenso + cinco;
                plural = true;
                break;
            case 4 :
                numExtenso = numExtenso + quatro;
                plural = true;
                break;
            case 3 :
                numExtenso = numExtenso + tres;
                plural = true;
                break;
            case 2 :
                numExtenso = numExtenso + dois;
                plural = true;
                break;
            case 1 :
                if (i == 4 || i == 8 || i == 12 || i == 16 || i == 20 || i == 23) {
                    switch (Integer.parseInt(getText(i + 1, 1))) {
                        case 9 :
                            numExtenso = numExtenso + dezenove;
                            plural = true;
                            break;
                        case 8 :
                            numExtenso = numExtenso + dezoito;
                            plural = true;
                            break;
                        case 7 :
                            numExtenso = numExtenso + dezessete;
                            plural = true;
                            break;
                        case 6 :
                            numExtenso = numExtenso + dezesseis;
                            plural = true;
                            break;
                        case 5 :
                            numExtenso = numExtenso + quinze;
                            plural = true;
                            break;
                        case 4 :
                            numExtenso = numExtenso + quatorze;
                            plural = true;
                            break;
                        case 3 :
                            numExtenso = numExtenso + treze;
                            plural = true;
                            break;
                        case 2 :
                            numExtenso = numExtenso + doze;
                            plural = true;
                            break;
                        case 1 :
                            numExtenso = numExtenso + onze;
                            plural = true;
                            break;
                        case 0 :
                            numExtenso = numExtenso + dez;
                            plural = true;
                            break;
                    }
                } else {
                    if (i == 5 || i == 9 || i == 13 || i == 17) {
                        if (getText(i - 2, 3).equals("__1") || getText(i - 2, 3).equals("_01") || getText(i - 2, 3).equals("001")) {
                            if (numExtenso.equals("")/* || numExtenso.indexOf(hum) == -1*/) {
                                //numExtenso = numExtenso + hum; esta linha adiciona a palavra 'Hum'
                                numExtenso = numExtenso + um;
                            } else {
                                numExtenso = numExtenso + um;
                            }
                        } else {
                            numExtenso = numExtenso + um;
                        }
                    } else {
                        numExtenso = numExtenso + um;
                    }
                }
                break;
            case 0 : break;
        }
        if (primeiraPalavra) {
            String a = String.valueOf(numExtenso.charAt(0));
            String b = a.toUpperCase();
            numExtenso = numExtenso.replaceFirst(a, b);
            primeiraPalavra = false;
        }
    }
}
