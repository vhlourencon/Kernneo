package br.com.kernneo.desktop.view.widget;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class ButtonBarComponent extends JPanel {

    private static final long serialVersionUID = 1L;
    public JButton btIncluir = new JButton(Icone.novo("btNovo.png"));
    public JButton btExcluir = new JButton(Icone.novo("btExcluir.png"));
    public JButton btAlterar = new JButton(Icone.novo("btEditar.png"));
    public JToggleButton btConsultar = new JToggleButton(Icone.novo("btPesquisa.png"), false);
    public JButton btSair = new JButton(Icone.novo("btSair.png"));

    public JButton btSalvar = new JButton(Icone.novo("btSalvar.png"));
    public JButton btCancelar = new JButton(Icone.novo("btCancelar.png"));

    public ButtonBarComponent() {
	setBorder(new LineBorder(new Color(0, 0, 0)));
	setarComponentes();
	

    }

    private void setarComponentes() {
	setLayout(new MigLayout("", "[106px][106px][106px][106px][106px]", "[41px]"));
	btIncluir.setText("Incluir");
	btIncluir.setToolTipText("Incluir (Ctrl + I)");
	btIncluir.setMnemonic(KeyEvent.VK_I);
	add(btIncluir, "cell 0 0,grow");
	btAlterar.setText("Alterar");
	btAlterar.setToolTipText("Alterar (Ctrl + E)");
	add(btAlterar, "cell 1 0,grow");
	btExcluir.setText("Excluir");
	btExcluir.setToolTipText("Excluir (Ctrl + D)");
	add(btExcluir, "cell 2 0,grow");
	btConsultar.setText("Filtrar");
	btConsultar.setToolTipText("Filtrar (Ctrl + F)");
	add(btConsultar, "cell 3 0,grow");
	btSair.setText("Sair");
	btSair.setToolTipText("Sair (Ctrl + W)");
	add(btSair, "cell 4 0,grow");
	
	btSalvar.setText("Salvar");
	

    }

   
    public void liberarComponentes(boolean liberar) {

	try {

	    btIncluir.setEnabled(liberar);
	    btConsultar.setEnabled(liberar);
	    btAlterar.setEnabled(liberar);
	    btSair.setEnabled(liberar);
	    btExcluir.setEnabled(liberar);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public JButton getBtIncluir() {
	return btIncluir;
    }

    public void setBtIncluir(JButton btIncluir) {
	this.btIncluir = btIncluir;
    }

    public JButton getBtExcluir() {
	return btExcluir;
    }

    public void setBtExcluir(JButton btExcluir) {
	this.btExcluir = btExcluir;
    }

    public JButton getBtAlterar() {
	return btAlterar;
    }

    public void setBtAlterar(JButton btAlterar) {
	this.btAlterar = btAlterar;
    }

    public JToggleButton getBtConsultar() {
	return btConsultar;
    }

    public void setBtConsultar(JToggleButton btConsultar) {
	this.btConsultar = btConsultar;
    }

    public JButton getBtSair() {
	return btSair;
    }

    public void setBtSair(JButton btSair) {
	this.btSair = btSair;
    }

    public JButton getBtSalvar() {
	return btSalvar;
    }

    public void setBtSalvar(JButton btSalvar) {
	this.btSalvar = btSalvar;
    }

    public JButton getBtCancelar() {
	return btCancelar;
    }

    public void setBtCancelar(JButton btCancelar) {
	this.btCancelar = btCancelar;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}
