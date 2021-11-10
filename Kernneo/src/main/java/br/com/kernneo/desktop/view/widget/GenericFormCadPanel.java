package br.com.kernneo.desktop.view.widget;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import br.com.kernneo.client.model.GenericModel;

public class GenericFormCadPanel<GENERICMODEL extends GenericModel> extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected GENERICMODEL model;

    public GenericFormCadPanel() {

    }

    public void setBordaReq(JComponent comp) {
	  comp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red), BorderFactory.createEtchedBorder()));
    }

    public GENERICMODEL getModel() {
	return model;
    }

    public void setModel(GENERICMODEL model) {
	this.model = model;
    }

    public void habilitaCamposParaEdicao(boolean b) {

    }

}
