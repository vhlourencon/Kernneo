package br.com.kernneo.desktop.view.grupo;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class GrupoFiltroPanel  extends GenericFiltroPanel<CategoriaModel> {
    
    private JComboBox comboBox;
    private JTextField textField;
   
    public GrupoFiltroPanel() {
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
    public CategoriaModel getModelFiltro() {
	if (modelFiltro == null) { 
	    setModelFiltro(new CategoriaModel());
	    getModelFiltro().setCategoria_nome_portugues(null);
	}
	
	modelFiltro.setCategoria_nome_portugues(textField.getText());
	

	return modelFiltro;
    }
    
 
    
}
