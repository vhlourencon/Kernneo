package br.com.kernneo.desktop.view.widget;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class PaginacaoButtonBarComponent extends JPanel {

    private static final long serialVersionUID = 1L;

    public JButton btPrim = null;
    public JButton btAnt = null;
    public JButton btProx = null;
    public JButton btUlt = null;

    private JLabel labelQuantidadeDePaginas;

    private JTextField textField;

    public PaginacaoButtonBarComponent() {
	btPrim = new JButton(Icone.novo("btPrim.png"));
	btAnt = new JButton(Icone.novo("btAnt.png"));
	btProx = new JButton(Icone.novo("btProx.png"));
	btUlt = new JButton(Icone.novo("btUlt.png"));

	btPrim.setToolTipText("Primeiro (Ctrl + PageUp)");
	btAnt.setToolTipText("Anterior (PageUp)");
	btProx.setToolTipText("Próximo (PageDown)");
	btUlt.setToolTipText("Último (Ctrl + PageDow)");

	setLayout(new GridLayout(1, 9));
	setPreferredSize(new Dimension(343, 30));

	add(btPrim); // 0
	add(btAnt); // 1

	JLabel lblPagina = new JLabel("Pagina:");
	lblPagina.setHorizontalAlignment(SwingConstants.CENTER);
	add(lblPagina);

	textField = new JTextField();
	add(textField);
	textField.setColumns(10);

	JLabel labelDe = new JLabel("de");
	labelDe.setHorizontalAlignment(SwingConstants.CENTER);
	add(labelDe);

	labelQuantidadeDePaginas = new JLabel("-");
	add(labelQuantidadeDePaginas);
	add(btProx); // 2
	add(btUlt); // 3
    }

    public void liberarComponentes(boolean liberar) {

	try {

	    btPrim.setEnabled(liberar);
	    btProx.setEnabled(liberar);
	    btAnt.setEnabled(liberar);
	    btUlt.setEnabled(liberar);

	    textField.setEnabled(liberar);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public JButton getBtPrim() {
	return btPrim;
    }

    public void setBtPrim(JButton btPrim) {
	this.btPrim = btPrim;
    }

    public JButton getBtAnt() {
	return btAnt;
    }

    public void setBtAnt(JButton btAnt) {
	this.btAnt = btAnt;
    }

    public JButton getBtProx() {
	return btProx;
    }

    public void setBtProx(JButton btProx) {
	this.btProx = btProx;
    }

    public JButton getBtUlt() {
	return btUlt;
    }

    public void setBtUlt(JButton btUlt) {
	this.btUlt = btUlt;
    }

    public JLabel getLabelQuantidadeDePaginas() {
	return labelQuantidadeDePaginas;
    }

    public void setLabelQuantidadeDePaginas(JLabel labelQuantidadeDePaginas) {
	this.labelQuantidadeDePaginas = labelQuantidadeDePaginas;
    }

    public JTextField getTextField() {
	return textField;
    }

    public void setTextField(JTextField textField) {
	this.textField = textField;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}
