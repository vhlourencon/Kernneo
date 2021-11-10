package br.com.kernneo.desktop.view.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;



//import javax.swing.text.JTextComponent;
public class JPanelPad extends JPanel {

    private static final long serialVersionUID = 1L;
    public static int TP_JPANEL = 0; // constante criada para manter a
    // constru��o anterior de
    // org.freedom.componentes.JPanelPad
    private JLayeredPane lpn = new JLayeredPane();
    private boolean initFirstFocus = true;
    private Component firstFocus = null;

    public JPanelPad() {
	setLayout(new GridLayout(1, 1));
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	add(lpn);
    }

    public JPanelPad(String titulo, Color cortitulo) {
	setLayout(new GridLayout(1, 1));
	setBorder(SwingParams.getPanelLabel(titulo, cortitulo, TitledBorder.DEFAULT_JUSTIFICATION));
	add(lpn);
    }

    public JPanelPad(Dimension dm) {
	setLayout(new GridLayout(1, 1));
	setPreferredSize(dm);
	setBorder(javax.swing.BorderFactory.createEtchedBorder());
	lpn.setPreferredSize(dm);
	add(lpn);
    }

    public void tiraBorda() {
	setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    public JPanelPad(int Larg, int Alt) {
	this(new Dimension(Larg, Alt));
    }

    public JLabelPad adic(Component comp, int X, int Y, int Larg, int Alt, String label) {
	JLabelPad lbTmp = new JLabelPad(label);
	adic(lbTmp, X, Y - 20, Larg, 20);
	adic(comp, X, Y, Larg, Alt);
	return lbTmp;

    }

    public void adic(Component comp, int x, int y, int larg, int alt) {
	comp.setBounds(x, y, larg, alt);
	lpn.add(comp, JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * Abaixo constru��es referentes ao org.freedom.componentes.JPanelPad
     **/

    public JPanelPad(int tppanel) {
	super();
    }

    public JPanelPad(LayoutManager arg0) {
	super(arg0);
    }

    public JPanelPad(int tppanel, boolean arg0) {
	super(arg0);
    }

    public JPanelPad(int tppanel, LayoutManager arg0) {
	super(arg0);
    }

    public JPanelPad(int tppanel, LayoutManager arg0, boolean arg1) {
	super(arg0, arg1);
    }

    public void setInitFirstFocus(boolean initFirstFocus) {
	this.initFirstFocus = initFirstFocus;
    }

    public boolean getInitFirstFocus() {
	return this.initFirstFocus;
    }

    public boolean firstFocus() {
	boolean ret = false;
	if (firstFocus != null) {
	    if (firstFocus.isFocusable()) {
		firstFocus.requestFocus();
		ret = true;
		// System.out.println("Pegou foco");
	    }
	}
	return ret;
    }

    public void setFirstFocus(Component firstFocus) {
	this.firstFocus = firstFocus;
    }

    public Component getFirstFocus() {
	return this.firstFocus;
    }
}
