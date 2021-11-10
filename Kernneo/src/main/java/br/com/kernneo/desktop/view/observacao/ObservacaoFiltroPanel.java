package br.com.kernneo.desktop.view.observacao;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.ObservacaoModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class ObservacaoFiltroPanel  extends GenericFiltroPanel<ObservacaoModel> {
    
    private JComboBox comboBox;
    private JTextField textField;
   
    public ObservacaoFiltroPanel() {
    	buttonOk.setSize(77, 28);
    	buttonOk.setLocation(312, 37); 

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(536, 80));
	
	JLabel lblNewLabel_1 = new JLabel("Descrição:");
	lblNewLabel_1.setBounds(10, 24, 101, 14);
	add(lblNewLabel_1);
	
	textField = new JTextField();
	textField.setBounds(10, 41, 292, 20);
	add(textField);
	textField.setColumns(10);
    }

    @Override
    public ObservacaoModel getModelFiltro() {
	if (modelFiltro == null) { 
	    setModelFiltro(new ObservacaoModel());
	    
	    getModelFiltro().setDescricao(null);
	}
	
	modelFiltro.setDescricao(textField.getText());
	

	return modelFiltro;
    }
    
 
    
}
