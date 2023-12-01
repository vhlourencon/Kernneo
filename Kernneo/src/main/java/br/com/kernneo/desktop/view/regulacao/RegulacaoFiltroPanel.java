package br.com.kernneo.desktop.view.regulacao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.ContaBancaria;
import br.com.kernneo.server.negocio.Estado;
import br.com.kernneo.server.negocio.Funcionario;
import br.com.kernneo.server.negocio.Hospital;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.client.model.FuncionarioModel;

public class RegulacaoFiltroPanel extends GenericFiltroPanel<RegulacaoModel>
	{

		private JTextField textField;
		private UtilDateModel modelDataInicial;

		private JDatePickerImpl datePickerDataInicial;
		private JDatePickerImpl datePickerDataFinal;
		private UtilDateModel modelDataFinal;
		protected ArrayList<CategoriaModel> listaDeCategoria;
		protected ArrayList<CidadeModel> listaDeCidades;
		protected ArrayList<HospitalModel> listaDeHospital;
		private JComboBox<HospitalModel> comboBoxHospital;
		private JComboBox<CategoriaModel> comboBoxCategoria;
		private JComboBox<CidadeModel> comboboxCidade;
		private JComboBox<String> comboBoxRecebidosPagos;
		private JComboBox<EstadoModel> comboBoxUF;
		private JComboBox<FuncionarioModel> comboBoxRegulador;
		private JComboBox<String> comboBoxResolvido;

		public RegulacaoFiltroPanel() throws Exception {
			buttonOk.setText("Pesquisar");
			buttonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			buttonOk.setSize(235, 45);
			buttonOk.setLocation(634, 195);

			setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			setLayout(null);
			setPreferredSize(new Dimension(930, 261));

			JLabel lblNewLabel_1 = new JLabel("Código:");
			lblNewLabel_1.setBounds(10, 102, 101, 14);
			add(lblNewLabel_1);

			textField = new JTextField();
			textField.setBounds(10, 117, 171, 40);
			add(textField);
			textField.setColumns(10);

			JLabel lblNewLabel_1_1 = new JLabel("Data Inicial: ");
			lblNewLabel_1_1.setBounds(10, 24, 101, 14);
			add(lblNewLabel_1_1);

			modelDataInicial = new UtilDateModel();
			modelDataInicial.setSelected(true);

			// model.setDate(20,04,2014);
			// Need this...
			Properties p = new Properties();
			p.put("text.today", "Hoje");
			p.put("text.month", "Mês");
			p.put("text.year", "Ano");

			JDatePanelImpl datePanelDataInicial = new JDatePanelImpl(modelDataInicial, p);
			datePanelDataInicial.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));

			datePickerDataInicial = new JDatePickerImpl(datePanelDataInicial, new DateLabelFormatter());
			datePickerDataInicial.setLocation(24, 21);
			datePickerDataInicial.setSize(267, 37);
			datePickerDataInicial.setTextEditable(true);
			datePickerDataInicial.setBounds(10, 41, 171, 40);

			modelDataFinal = new UtilDateModel();
			modelDataFinal.setSelected(true);

			JDatePanelImpl datePanelDataFinal = new JDatePanelImpl(modelDataFinal, p);
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

			comboBoxHospital = new JComboBox<HospitalModel>();
			AutoCompleteDecorator.decorate(comboBoxHospital);
			comboBoxHospital.setBounds(376, 116, 235, 40);
			add(comboBoxHospital);

			JLabel lblNewLabel_1_2 = new JLabel("Especialidade:");
			lblNewLabel_1_2.setBounds(376, 24, 101, 14);
			add(lblNewLabel_1_2);

			JLabel lblNewLabel_1_2_1 = new JLabel("Cidade de Origem:");
			lblNewLabel_1_2_1.setBounds(127, 177, 185, 14);
			add(lblNewLabel_1_2_1);

			comboboxCidade = new JComboBox<CidadeModel>();
			AutoCompleteDecorator.decorate(comboboxCidade);
			comboboxCidade.setBounds(125, 194, 237, 40);
			add(comboboxCidade);

			JLabel lblNewLabel_1_2_1_1 = new JLabel("Local de Atendimento:");
			lblNewLabel_1_2_1_1.setBounds(376, 98, 185, 14);
			add(lblNewLabel_1_2_1_1);

			comboBoxCategoria = new JComboBox<CategoriaModel>();
			AutoCompleteDecorator.decorate(comboBoxCategoria);
			comboBoxCategoria.setBounds(374, 39, 237, 40);
			add(comboBoxCategoria);

			comboBoxRecebidosPagos = new JComboBox<String>();
			AutoCompleteDecorator.decorate(comboBoxRecebidosPagos);
			comboBoxRecebidosPagos.setBounds(191, 117, 171, 40);
			comboBoxRecebidosPagos.addItem("- TODOS -");
			comboBoxRecebidosPagos.addItem("SIM");
			comboBoxRecebidosPagos.addItem("NÃO");
			add(comboBoxRecebidosPagos);

			JLabel lblRecebidospagos = new JLabel("Liminar:");
			lblRecebidospagos.setBounds(191, 102, 144, 15);
			add(lblRecebidospagos);

			JLabel labelCidade = new JLabel("UF:");
			labelCidade.setBounds(10, 177, 56, 15);
			add(labelCidade);

			comboBoxUF = new JComboBox<EstadoModel>();
			comboBoxUF.setBounds(10, 194, 101, 40);
			comboBoxUF.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						atualizaCidadesDoEstadoSelecionado();
					}

				}
			});
			add(comboBoxUF);

			JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Regulador:");
			lblNewLabel_1_2_1_1_1.setBounds(376, 179, 185, 14);
			add(lblNewLabel_1_2_1_1_1);

			comboBoxRegulador = new JComboBox<FuncionarioModel>();
			comboBoxRegulador.setBounds(376, 197, 235, 40);
			AutoCompleteDecorator.decorate(comboBoxRegulador);
			add(comboBoxRegulador);

			JLabel labelResolvido = new JLabel("Resolvido:");
			labelResolvido.setBounds(634, 24, 144, 15);
			add(labelResolvido);

			comboBoxResolvido = new JComboBox<String>();
			comboBoxResolvido.setBounds(634, 39, 171, 40);
			AutoCompleteDecorator.decorate(comboBoxResolvido);
			comboBoxResolvido.addItem("- TODOS -");
			comboBoxResolvido.addItem("SIM");
			comboBoxResolvido.addItem("NÃO");

			add(comboBoxResolvido);

			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);

			modelDataInicial.setValue(c.getTime());

			c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
			modelDataFinal.setValue(c.getTime());

			acaoObterComboboxListas();
			setCuiabaSelecionada();

		}

		private void acaoObterComboboxListas() throws Exception {
			try {
				Conexao.Executar(new Comando() {

					private ArrayList<FuncionarioModel> listaDeRegulador;

					@Override
					public void execute(Session session) throws Exception {

						EstadoModel estadoModelTodos = new EstadoModel();
						estadoModelTodos.setSigla("TODOS");
						comboBoxUF.addItem(estadoModelTodos);
						List<EstadoModel> listaDeEstados = new Estado().obterTodos(EstadoModel.class);
						for (EstadoModel estadoModel : listaDeEstados) {
							comboBoxUF.addItem(estadoModel);
						}

						CategoriaModel categoriaTodosModel = new CategoriaModel();
						categoriaTodosModel.setCategoria_nome_portugues("- TODOS - ");
						comboBoxCategoria.addItem(categoriaTodosModel);

						listaDeCategoria = new Categoria().obterTodos(CategoriaModel.class);
						for (CategoriaModel categoriaModel : listaDeCategoria) {
							comboBoxCategoria.addItem(categoriaModel);
						}

						listaDeCidades = new Cidade().obterTodos(CidadeModel.class);

						HospitalModel hospitalTodosModel = new HospitalModel();
						hospitalTodosModel.setNome("- TODOS - ");
						comboBoxHospital.addItem(hospitalTodosModel);

						listaDeHospital = new Hospital().obterTodos(HospitalModel.class);
						for (HospitalModel hospitalModel : listaDeHospital) {
							comboBoxHospital.addItem(hospitalModel);
						}

						FuncionarioModel funcionarioModelAux = new FuncionarioModel();
						funcionarioModelAux.setNome("- TODOS - ");
						comboBoxRegulador.addItem(funcionarioModelAux);
						listaDeRegulador = new Funcionario().obterTodos(FuncionarioModel.class);
						for (FuncionarioModel model : listaDeRegulador) {
							comboBoxRegulador.addItem(model);
						}
					}
				});
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);
				throw e1;
			}

		}

		protected void atualizaCidadesDoEstadoSelecionado() {
			if (listaDeCidades != null) {
				comboboxCidade.removeAllItems();
				CidadeModel cidadeModel = new CidadeModel();
				cidadeModel.setNome("- TODOS -");
				comboboxCidade.addItem(cidadeModel);
				for (CidadeModel cidadeAux : listaDeCidades) {
					if (getEstadoSelecionado().getId() == null) {
						comboboxCidade.addItem(cidadeAux);
					} else if (cidadeAux.getEstado().getId().compareTo(getEstadoSelecionado().getId()) == 0) {
						comboboxCidade.addItem(cidadeAux);
					}
				}
			}
		}

		private EstadoModel getEstadoSelecionado() {
			return (EstadoModel) comboBoxUF.getSelectedItem();
		}

		private void setCidadeSelecionada(CidadeModel cidadeModel) {
			if (cidadeModel != null) {
				for (int i = 0; i < comboBoxUF.getItemCount(); i++) {
					if (comboBoxUF.getItemAt(i).getId() != null && comboBoxUF.getItemAt(i).getId().compareTo(cidadeModel.getEstado().getId()) == 0) {
						comboBoxUF.setSelectedIndex(i);
					}
				}
				comboboxCidade.setSelectedItem(cidadeModel);
			} else {
				setCuiabaSelecionada();
			}
		}

		private void setCuiabaSelecionada() {
			for (CidadeModel cidadeModel : listaDeCidades) {
				if (cidadeModel.getNome().toLowerCase().equals("cuiabá")) {
					setCidadeSelecionada(cidadeModel);
				}
			}
		}

		@Override
		public RegulacaoModel getModelFiltro() {
			if (modelFiltro == null) {
				setModelFiltro(new RegulacaoModel());
				// getModelFiltro().setDescricao(null);
			}

			modelFiltro.setCodigo(textField.getText());
			// modelFiltro.setConta();

			HospitalModel hospitalSelecionado = (HospitalModel) comboBoxHospital.getSelectedItem();
			if (hospitalSelecionado.getId() == null) {
				hospitalSelecionado = null;
			}
			CidadeModel cidadeSelecionada = (CidadeModel) comboboxCidade.getSelectedItem();
			if (cidadeSelecionada.getId() == null) {
				cidadeSelecionada = null;
			}
			CategoriaModel categoriaSelecionada = (CategoriaModel) comboBoxCategoria.getSelectedItem();
			if (categoriaSelecionada.getId() == null) {
				categoriaSelecionada = null;
			}

			FuncionarioModel funcionarioSelecionado = (FuncionarioModel) comboBoxRegulador.getSelectedItem();
			if (funcionarioSelecionado.getId() == null) {
				funcionarioSelecionado = null;
			}

			modelFiltro.setHospital(hospitalSelecionado);
			modelFiltro.setEspecialidade(categoriaSelecionada);
			modelFiltro.setCidade(cidadeSelecionada);
			modelFiltro.setUsuarioSave(funcionarioSelecionado);

			if (comboBoxRecebidosPagos.getSelectedIndex() == 0) {
				modelFiltro.setFiltroLiminar(null);
			} else if (comboBoxRecebidosPagos.getSelectedIndex() == 1) {
				modelFiltro.setFiltroLiminar(true);
			} else if (comboBoxRecebidosPagos.getSelectedIndex() == 2) {
				modelFiltro.setFiltroLiminar(false);
			}

			
			if (comboBoxResolvido.getSelectedIndex() == 0) {
				modelFiltro.setFiltroResolvido(null);
			} else if (comboBoxResolvido.getSelectedIndex() == 1) {
				modelFiltro.setFiltroResolvido(true);
			} else if (comboBoxResolvido.getSelectedIndex() == 2) {
				modelFiltro.setFiltroResolvido(false);
			}

			
			modelFiltro.setDataHora(modelDataInicial.getValue());
			modelFiltro.setFiltroDataHoraFinal(modelDataFinal.getValue());

			return modelFiltro;
		}

		public JComboBox<CategoriaModel> getComboBoxCategoria() {
			return comboBoxCategoria;
		}

		public void setComboBoxCategoria(JComboBox<CategoriaModel> comboBoxCategoria) {
			this.comboBoxCategoria = comboBoxCategoria;
		}

		public JComboBox<String> getComboBoxRecebidosPagos() {
			return comboBoxRecebidosPagos;
		}

		public void setComboBoxRecebidosPagos(JComboBox<String> comboBoxRecebidosPagos) {
			this.comboBoxRecebidosPagos = comboBoxRecebidosPagos;
		}
	}
