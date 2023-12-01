package br.com.kernneo.desktop.view.solicitacao;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.SolicitacaoItemModel;
import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class SolicitacaoItemFiltroPanel extends JPanel
	{

		private JTextField textFieldDocumentoNome;
		private SolicitacaoItemModel modelFiltro;
		private JTextField textFieldUnidadeNome;
		private JTextField textFieldEquipamentoNome;

		public SolicitacaoItemFiltroPanel() {

			setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setLayout(null);
			setPreferredSize(new Dimension(743, 95));

			JLabel lblNewLabel_1 = new JLabel("Documento:");
			lblNewLabel_1.setBounds(23, 27, 101, 14);
			add(lblNewLabel_1);

			textFieldDocumentoNome = new JTextField();
			textFieldDocumentoNome.setBounds(23, 45, 226, 27);
			add(textFieldDocumentoNome);
			textFieldDocumentoNome.setColumns(10);
			textFieldDocumentoNome.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					acaoPesquisar();

				}
			});

			JLabel lblNewLabel_1_1 = new JLabel("Unidade:");
			lblNewLabel_1_1.setBounds(259, 27, 101, 14);
			add(lblNewLabel_1_1);

			textFieldUnidadeNome = new JTextField();
			textFieldUnidadeNome.setColumns(10);
			textFieldUnidadeNome.setBounds(259, 45, 226, 27);
			textFieldUnidadeNome.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					acaoPesquisar();

				}
			});
			add(textFieldUnidadeNome);

			JLabel lblNewLabel_1_1_1 = new JLabel("Equipamento:");
			lblNewLabel_1_1_1.setBounds(495, 27, 101, 14);
			add(lblNewLabel_1_1_1);

			textFieldEquipamentoNome = new JTextField();
			textFieldEquipamentoNome.setColumns(10);
			textFieldEquipamentoNome.setBounds(495, 45, 226, 27);
			textFieldEquipamentoNome.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					acaoPesquisar();

				}
			});
			add(textFieldEquipamentoNome);
		}

		public void acaoPesquisar() {

		}

		public String getFiltroEquipamentoNome() {
			return textFieldEquipamentoNome.getText();
		}

		public String getFiltroUnidadeNome() {
			return textFieldUnidadeNome.getText();
		}

		public String getFiltroDocumentoNome() {
			return textFieldDocumentoNome.getText();
		}

		public SolicitacaoItemModel getModelFiltro() {
			if (modelFiltro == null) {
				modelFiltro = new SolicitacaoItemModel();
				// setModelFiltro(new CompiladoModel());
				// getModelFiltro().setNome(null);
			}

			// modelFiltro.setEquipamentoNome(textField_2.getText());

			return modelFiltro;
		}
	}
