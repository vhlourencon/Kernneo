package br.com.kernneo.desktop.view.funcionario;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTree;
import java.awt.Choice;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JToggleButton;

public class FuncionarioPerMovimentacaoPanel extends JPanel {
	
	
	private JCheckBox checkBoxUsuariorecebidoPago;
	private JCheckBox checkBoxUsuarioPendente;
	private JCheckBox checkBoxOutrosRecebidos;
	private JCheckBox checkBoxOutrosPendentes;

	public FuncionarioPerMovimentacaoPanel( ) {
		setLayout(null);
		setPreferredSize(new Dimension(521, 260));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Excluir", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 488, 116);
		add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTOS DO USUARIO", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 20, 230, 80);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		 checkBoxUsuariorecebidoPago = new JCheckBox("RECEBIDOS/PAGOS");
		checkBoxUsuariorecebidoPago.setBounds(6, 43, 180, 30);
		panel_2.add(checkBoxUsuariorecebidoPago);
		
		 checkBoxUsuarioPendente = new JCheckBox("PENDENTES");
		checkBoxUsuarioPendente.setBounds(6, 23, 97, 23);
		panel_2.add(checkBoxUsuarioPendente);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTO DE OUTROS USU\u00C1RIOS", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel_2_1.setBounds(244, 20, 230, 80);
		panel.add(panel_2_1);
		
		checkBoxOutrosRecebidos = new JCheckBox("RECEBIDOS/PAGOS");
		checkBoxOutrosRecebidos.setBounds(6, 43, 180, 30);
		panel_2_1.add(checkBoxOutrosRecebidos);
		
		checkBoxOutrosPendentes = new JCheckBox("PENDENTES");
		checkBoxOutrosPendentes.setBounds(6, 23, 97, 23);
		panel_2_1.add(checkBoxOutrosPendentes);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Alterar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(82, 154, 381, 116);
		//setViewportView(panel_1);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Alterar Data");
		chckbxNewCheckBox_1.setBounds(16, 22, 97, 23);
		panel_1.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Alterar STATUS recebido/pago");
		chckbxNewCheckBox_2.setBounds(16, 48, 179, 23);
		panel_1.add(chckbxNewCheckBox_2);
	}

	public JCheckBox getCheckBoxUsuariorecebidoPago() {
		return checkBoxUsuariorecebidoPago;
	}

	public void setCheckBoxUsuariorecebidoPago(JCheckBox checkBoxUsuariorecebidoPago) {
		this.checkBoxUsuariorecebidoPago = checkBoxUsuariorecebidoPago;
	}

	public JCheckBox getCheckBoxUsuarioPendente() {
		return checkBoxUsuarioPendente;
	}

	public void setCheckBoxUsuarioPendente(JCheckBox checkBoxUsuarioPendente) {
		this.checkBoxUsuarioPendente = checkBoxUsuarioPendente;
	}

	public JCheckBox getCheckBoxOutrosRecebidos() {
		return checkBoxOutrosRecebidos;
	}

	public void setCheckBoxOutrosRecebidos(JCheckBox checkBoxOutrosRecebidos) {
		this.checkBoxOutrosRecebidos = checkBoxOutrosRecebidos;
	}

	public JCheckBox getCheckBoxOutrosPendentes() {
		return checkBoxOutrosPendentes;
	}

	public void setCheckBoxOutrosPendentes(JCheckBox checkBoxOutrosPendentes) {
		this.checkBoxOutrosPendentes = checkBoxOutrosPendentes;
	}
	
	
}
