package br.com.kernneo.desktop.view.ticket;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.TicketModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class TicketFiltroPanel  extends GenericFiltroPanel<TicketModel> {
    
    private JComboBox comboBox;
    private JTextField textField;
   
    public TicketFiltroPanel() {
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
    public TicketModel getModelFiltro() {
	if (modelFiltro == null) { 
	    setModelFiltro(new TicketModel());
	    
	    getModelFiltro().setDescricao(null);
	}
	
	modelFiltro.setDescricao(textField.getText());
	

	return modelFiltro;
    }
    
 
    
}
