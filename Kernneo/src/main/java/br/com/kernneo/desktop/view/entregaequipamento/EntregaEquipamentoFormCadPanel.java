package br.com.kernneo.desktop.view.entregaequipamento;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.EntregaEquipamentoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class EntregaEquipamentoFormCadPanel extends GenericFormCadPanel<EntregaEquipamentoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TitledBorder titledBorder;

    public EntregaEquipamentoFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(666, 325);
	setLayout(null);
	
	titledBorder  = new TitledBorder(null, "Informaçoes de Departamento", TitledBorder.LEADING, TitledBorder.TOP, null, null);

	JPanel panel = new JPanel();
	panel.setBorder(titledBorder);
	panel.setBounds(10, 11, 644, 302);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Equipamento:");
	labelNome.setBounds(389, 94, 92, 14);
	panel.add(labelNome);
	
	JComboBox comboBox = new JComboBox();
	comboBox.setBounds(389, 114, 232, 37);
	panel.add(comboBox);
	
	JComboBox comboBox_1 = new JComboBox();
	comboBox_1.setBounds(389, 46, 235, 37);
	panel.add(comboBox_1);
	
	JLabel lblUnidade = new JLabel("Unidade:");
	lblUnidade.setBounds(389, 26, 73, 14);
	panel.add(lblUnidade);
	
	JLabel lblDocumento = new JLabel("Documento:");
	lblDocumento.setBounds(21, 94, 73, 14);
	panel.add(lblDocumento);
	
	JComboBox comboBox_1_1 = new JComboBox();
	comboBox_1_1.setBounds(21, 114, 189, 37);
	panel.add(comboBox_1_1);
	
	JDateChooser dateChooser = new JDateChooser();
	dateChooser.setBounds(21, 46, 189, 37);
	panel.add(dateChooser);
	
	JLabel lblDataDaEntrega = new JLabel("Data da Entrega:");
	lblDataDaEntrega.setBounds(21, 26, 109, 14);
	panel.add(lblDataDaEntrega);
	
	JSpinner spinner = new JSpinner();
	spinner.setFont(new Font("Tahoma", Font.BOLD, 11));
	spinner.setBounds(220, 46, 159, 37);
	panel.add(spinner);
	
	JLabel lblDocumento_1_1 = new JLabel("Quantidade:");
	lblDocumento_1_1.setBounds(220, 26, 73, 14);
	panel.add(lblDocumento_1_1);
	
	JScrollPane scrollPaneObservacao = new JScrollPane((Component) null, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scrollPaneObservacao.setBounds(21, 187, 600, 90);
	panel.add(scrollPaneObservacao);
	
	JTextArea textAreaObservacao = new JTextArea();
	scrollPaneObservacao.setViewportView(textAreaObservacao);
	
	JLabel lblObservao = new JLabel("Observação:");
	lblObservao.setBounds(21, 166, 109, 14);
	panel.add(lblObservao);
	
	JLabel lblSolicitao = new JLabel("Solicitação:");
	lblSolicitao.setBounds(220, 94, 73, 14);
	panel.add(lblSolicitao);
	
	JComboBox comboBox_1_1_1 = new JComboBox();
	comboBox_1_1_1.setBounds(220, 114, 159, 37);
	panel.add(comboBox_1_1_1);

    }

    @Override
    public EntregaEquipamentoModel getModel() {
	
	return model;
    }

    @Override
    public void setModel(EntregaEquipamentoModel model) {
    	if (model == null) {
    	    model = new EntregaEquipamentoModel();
    	}

    	super.setModel(model);
	
	
//	textFieldDescricao.setText(model.getNome());

    }
    
    public TitledBorder getTitledBorder() {
		return titledBorder;
	}
}
