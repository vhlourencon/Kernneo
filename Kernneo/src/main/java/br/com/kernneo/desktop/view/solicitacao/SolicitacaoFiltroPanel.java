package br.com.kernneo.desktop.view.solicitacao;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class SolicitacaoFiltroPanel  extends GenericFiltroPanel<SolicitacaoModel> {
    
    private JTextField textField;
   
    public SolicitacaoFiltroPanel() {
    	buttonOk.setSize(77, 30);
    	buttonOk.setLocation(312, 45); 

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(536, 86));
	
	JLabel lblNewLabel_1 = new JLabel("Descrição:");
	lblNewLabel_1.setBounds(10, 24, 101, 14);
	add(lblNewLabel_1);
	
	textField = new JTextField();
	textField.setBounds(10, 45, 292, 30);
	add(textField);
	textField.setColumns(10);
    }

    @Override
    public SolicitacaoModel getModelFiltro() {
	if (modelFiltro == null) { 
	    setModelFiltro(new SolicitacaoModel());
	    getModelFiltro().setNome(null);
	}
	
	modelFiltro.setNome(textField.getText());
	

	return modelFiltro;
    }
    
 
    
}
