package br.com.kernneo.desktop.view.cfop;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CFOPModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class CFOPFiltroPanel  extends GenericFiltroPanel<CFOPModel> {
    
    private JComboBox comboBox;
    private JTextField textField;
    private JTextField textFieldCFOP;
   
    public CFOPFiltroPanel() {
    	buttonOk.setSize(77, 28);
    	buttonOk.setLocation(408, 34); 

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(536, 80));
	
	JLabel lblNewLabel_1 = new JLabel("Descrição:");
	lblNewLabel_1.setBounds(106, 21, 101, 14);
	add(lblNewLabel_1);
	
	textField = new JTextField();
	textField.setBounds(106, 38, 292, 22);
	add(textField);
	textField.setColumns(10);
	
	JLabel lblCfop = new JLabel("CFOP:");
	lblCfop.setBounds(10, 21, 46, 14);
	add(lblCfop);
	
	textFieldCFOP = new JTextField();
	textFieldCFOP.setBounds(10, 38, 86, 22);
	add(textFieldCFOP);
	textFieldCFOP.setColumns(10);
    }

    @Override
    public CFOPModel getModelFiltro() {
	if (modelFiltro == null) { 
	    setModelFiltro(new CFOPModel());
	    
	    getModelFiltro().setDescricao(null);
	}
	
	modelFiltro.setDescricao(textField.getText());
	//modelFiltro.setCfop(textFieldCFOP.getText());
	

	return modelFiltro;
    }
}
