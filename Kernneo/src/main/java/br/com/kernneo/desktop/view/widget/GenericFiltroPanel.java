package br.com.kernneo.desktop.view.widget;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import br.com.kernneo.client.model.GenericModel;


public class GenericFiltroPanel<GENERICMODEL extends GenericModel> extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected GENERICMODEL modelFiltro;
    public JButton buttonOk = new JButton(Icone.novo("btOk.png"));
   
    public GenericFiltroPanel()  {
	buttonOk.setToolTipText("Ir e Pesquisar");
	buttonOk.setText("Ok");
	buttonOk.setBounds(0, 0, 77, 28);
    	
	add(buttonOk);
    }

    public void setBordaReq(JComponent comp) {
	comp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red), BorderFactory.createEtchedBorder()));
    }

   

    public GENERICMODEL getModelFiltro() {
        return modelFiltro;
    }

    public void setModelFiltro(GENERICMODEL modelFiltro) {
        this.modelFiltro = modelFiltro;
    }

    public void habilitaCamposParaEdicao(boolean b) {

    }

    

}
