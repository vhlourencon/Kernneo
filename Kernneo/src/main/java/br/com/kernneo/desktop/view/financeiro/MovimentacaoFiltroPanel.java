package br.com.kernneo.desktop.view.financeiro;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.time.DateUtils;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;

public class MovimentacaoFiltroPanel extends GenericFiltroPanel<MovimentacaoModel> {

    private JTextField textField;
    private DateModel<?> model;
   
    private JDatePickerImpl datePickerDataInicial;
    private JDatePickerImpl datePickerDataFinal;
    private UtilDateModel modelDataFinal;

    public MovimentacaoFiltroPanel() {
        buttonOk.setText("Pesquisar");
        buttonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
	buttonOk.setSize(127, 45);
	buttonOk.setLocation(482, 281);

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(619, 337));

	JLabel lblNewLabel_1 = new JLabel("Descrição:");
	lblNewLabel_1.setBounds(10, 102, 101, 14);
	add(lblNewLabel_1);

	textField = new JTextField();
	textField.setBounds(10, 117, 352, 40);
	add(textField);
	textField.setColumns(10);
	
	JLabel lblNewLabel_1_1 = new JLabel("Data Inicial: ");
	lblNewLabel_1_1.setBounds(10, 24, 101, 14);
	add(lblNewLabel_1_1);
	
	 model = new UtilDateModel();
     model.setSelected(true);

     // model.setDate(20,04,2014);
     // Need this...
     Properties p = new Properties();
     p.put("text.today", "Hoje");
     p.put("text.month", "Mês");
     p.put("text.year", "Ano");

     
     
    
     
     JDatePanelImpl datePanelDataInicial  = new JDatePanelImpl(model, p);
     datePanelDataInicial.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));
     
  
 
     datePickerDataInicial = new JDatePickerImpl(datePanelDataInicial, new DateLabelFormatter());
     datePickerDataInicial.setLocation(24, 21);
     datePickerDataInicial.setSize(267, 37);
     datePickerDataInicial.setTextEditable(true);
     datePickerDataInicial.setBounds(10, 41, 171, 40);
     
     modelDataFinal = new UtilDateModel();
     modelDataFinal.setSelected(true);
     
     JDatePanelImpl datePanelDataFinal  = new JDatePanelImpl(modelDataFinal, p);
     datePanelDataFinal.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));
    
     
     datePickerDataFinal = new JDatePickerImpl(datePanelDataFinal, new DateLabelFormatter());
     datePickerDataFinal.setLocation(24, 21);
     datePickerDataFinal.setSize(267, 37);
     datePickerDataFinal.setTextEditable(true);
     datePickerDataFinal.setBounds(191, 41, 171, 40);
     
     add(datePickerDataInicial);
     add(datePickerDataFinal);
     
	JLabel lblNewLabel_1_1_1 = new JLabel("Data Final:");
	lblNewLabel_1_1_1.setBounds(191, 24, 99, 14);
	add(lblNewLabel_1_1_1);
	
	JComboBox comboBox = new JComboBox();
	 AutoCompleteDecorator.decorate(comboBox);
	comboBox.setBounds(374, 194, 235, 40);
	add(comboBox);
	
	JLabel lblNewLabel_1_2 = new JLabel("Categoria:");
	lblNewLabel_1_2.setBounds(374, 102, 101, 14);
	add(lblNewLabel_1_2);
	
	JLabel lblNewLabel_1_2_1 = new JLabel("Cliente:");
	lblNewLabel_1_2_1.setBounds(374, 24, 101, 14);
	add(lblNewLabel_1_2_1);
	
	JComboBox comboBox_1 = new JComboBox();
	 AutoCompleteDecorator.decorate(comboBox_1);
	comboBox_1.setBounds(372, 41, 237, 40);
	add(comboBox_1);
	
	JLabel lblNewLabel_1_2_1_1 = new JLabel("Conta:");
	lblNewLabel_1_2_1_1.setBounds(374, 176, 101, 14);
	add(lblNewLabel_1_2_1_1);
	
	JComboBox comboBox_1_1 = new JComboBox();
	 AutoCompleteDecorator.decorate(comboBox_1_1);
	comboBox_1_1.setBounds(372, 117, 237, 40);
	add(comboBox_1_1);
	
	JPanel panel_2 = new JPanel();
	panel_2.setLayout(null);
	panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
	panel_2.setBackground(Color.WHITE);
	panel_2.setBounds(10, 286, 352, 40);
	add(panel_2);
	
	JRadioButton radioButtonCredito = new JRadioButton("Crédito");
	radioButtonCredito.setSelected(true);
	radioButtonCredito.setFont(new Font("Tahoma", Font.BOLD, 15));
	radioButtonCredito.setBounds(6, 5, 109, 30);
	panel_2.add(radioButtonCredito);
	
	JRadioButton radioButtonDebito = new JRadioButton("Débito");
	radioButtonDebito.setFont(new Font("Tahoma", Font.BOLD, 15));
	radioButtonDebito.setBounds(129, 5, 109, 30);
	panel_2.add(radioButtonDebito);
	
	JLabel labelTipo = new JLabel("Tipo:");
	labelTipo.setBounds(10, 176, 46, 15);
	add(labelTipo);
	
	
    }

    @Override
    public MovimentacaoModel getModelFiltro() {
	if (modelFiltro == null) {
	    setModelFiltro(new MovimentacaoModel());
	    getModelFiltro().setDescricao(null);
	}

	modelFiltro.setDescricao(textField.getText());

	return modelFiltro;
    }
}
