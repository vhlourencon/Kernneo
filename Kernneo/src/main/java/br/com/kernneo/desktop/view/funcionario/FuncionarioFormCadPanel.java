package br.com.kernneo.desktop.view.funcionario;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.error.ErrorInfo;

import br.com.kernneo.client.exception.BairroException;
import br.com.kernneo.client.exception.CargoException;
import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.exception.UnidadeException;
import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.CargoModel;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.EnderecoFuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.FuncionarioRHModel;
import br.com.kernneo.client.model.NCMModel;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.client.model.UnidadeModel;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.bairro.BairroListInternalFrame;
import br.com.kernneo.desktop.view.cidade.CidadeFiltroFrame;
import br.com.kernneo.desktop.view.widget.ComboItem;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import br.com.kernneo.desktop.view.widget.TrippleDes;
import br.com.kernneo.server.negocio.Bairro;
import br.com.kernneo.server.negocio.Cargo;
import br.com.kernneo.server.negocio.Unidade;
import javax.swing.JTree;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

public class FuncionarioFormCadPanel extends GenericFormCadPanel<FuncionarioModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SubGrupoModel subGrupoSelecionado;
	private NCMModel ncmSelecionado;
	private FornecedorModel fornecedorSelecionado;
	private JTextField textFieldCidade;
	private JTextField textFieldUF;
	private JTextField textFieldEndereco;
	private JTextField textFieldEmail;
	private JTextField textFieldEnderecoNumero;
	private JFormattedTextField formattedTextFieldTelefone;
	private JFormattedTextField formattedTextFieldCelular;

	private CidadeModel cidadeSelecionada;
	private BairroModel bairroSelecionaro;
	private CargoModel cargoSelecionado;
	private MaskFormatter mskTelefone;
	private MaskFormatter mskCelular;
	private JComboBox comboBox_1;
	private ArrayList<BairroModel> listaDeBairros;
	private JTextField textField;
	private JTextField textField_1;
	private JXDatePicker datePickerDataDeNascimento;
	private JXDatePicker datePickerDataDeAdmissao;
	private JXDatePicker datePickerDataDeDemissao;
	private JComboBox comboBoxRhCargo;
	private ArrayList<CargoModel> listaDeCargos;
	private JTextField nomeTextField;
	private JTextField textFieldLogin;
	private JPasswordField confirmaPasswordField;
	private JPasswordField passWordField;

	private JCheckBox checkBoxAtivo;

	private SecretKey secretKey;

	private TrippleDes td;

	private FuncionarioPerMovimentacaoPanel funcionarioPerMovimentacaoPanel;

	public FuncionarioFormCadPanel() {
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initialize() throws Exception {

		setSize(580, 615);
		setLayout(null);

		td = new TrippleDes();

		/*
		 * Cipher Info Algorithm : for the encryption of electronic data mode of
		 * operation : to avoid repeated blocks encrypt to the same values. padding:
		 * ensuring messages are the proper length necessary for certain ciphers
		 * mode/padding are not used with stream cyphers.
		 */

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es do Funcionário", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 543, 335);
		add(panel);
		panel.setLayout(null);

		nomeTextField = new JTextField();
		nomeTextField.setBounds(20, 54, 388, 30);
		panel.add(nomeTextField);
		nomeTextField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(20, 32, 46, 14);
		panel.add(lblNewLabel);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(
				new TitledBorder(null, "Acesso ao Sistema", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(20, 97, 388, 220);
		panel.add(panel_5);

		JLabel lblCdigo = new JLabel("Login:");
		lblCdigo.setBounds(20, 37, 46, 14);
		panel_5.add(lblCdigo);

		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(20, 58, 279, 30);
		panel_5.add(textFieldLogin);

		checkBoxAtivo = new JCheckBox("Ativo");
		checkBoxAtivo.setSelected(true);
		checkBoxAtivo.setBounds(305, 58, 61, 30);
		panel_5.add(checkBoxAtivo);
		checkBoxAtivo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				FuncionarioModel funcionarioModel = getModel();
				funcionarioModel.setSenhaTemp(new String(passWordField.getPassword()));
				funcionarioModel.setConfirmaSenhaTemp(new String(confirmaPasswordField.getPassword()));

				setModel(funcionarioModel);
			}
		});
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(20, 99, 346, 100);
		panel_5.add(panel_1);

		confirmaPasswordField = new JPasswordField();
		confirmaPasswordField.setBounds(174, 52, 162, 30);
		panel_1.add(confirmaPasswordField);

		passWordField = new JPasswordField();
		passWordField.setBounds(174, 11, 162, 30);
		panel_1.add(passWordField);

		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setBounds(10, 11, 86, 30);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Confirmar Senha:");
		lblNewLabel_1_1.setBounds(10, 52, 116, 30);
		panel_1.add(lblNewLabel_1_1);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 350, 543, 254);

		// Group the radio buttons.
		ButtonGroup groupOrigemDaMercadoria = new ButtonGroup();

		ButtonGroup groupLocalDeImpressao = new ButtonGroup();

//	ArrayList<UnidadeModel> listaDeUnidades = new Unidade().obterTodos(UnidadeModel.class);
		// for (UnidadeModel unidadeModel : listaDeUnidades) {
		// comboBoxUnidadeDeCompra.addItem(new
		// ComboItem(unidadeModel.getDescricao(),
		// unidadeModel.getId().toString()));
		// }
		 funcionarioPerMovimentacaoPanel = new FuncionarioPerMovimentacaoPanel();

		tabbedPane.addTab("Mov. Financeira", null, funcionarioPerMovimentacaoPanel, null);

		JTabbedPane tabbedPanelPermissoes = new JTabbedPane(JTabbedPane.TOP);
		tabbedPanelPermissoes.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPanelPermissoes.setBounds(10, 11, 619, 204);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		// tabbedPane.addTab("Endereço", null, panel_2, null);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(10, 62, 46, 14);
		panel_2.add(lblBairro);

		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(322, 61, 114, 14);
		panel_2.add(lblNumero);

		textFieldEnderecoNumero = new JTextField();
		textFieldEnderecoNumero.setColumns(10);
		textFieldEnderecoNumero.setBounds(322, 78, 183, 23);
		panel_2.add(textFieldEnderecoNumero);

		JLabel label_5 = new JLabel("Cidade:");
		label_5.setBounds(10, 109, 147, 14);
		panel_2.add(label_5);

		textFieldCidade = new JTextField();
		textFieldCidade.setEditable(false);
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(10, 125, 262, 23);
		panel_2.add(textFieldCidade);

		JButton button_5 = new JButton((Icon) null);
		button_5.setText("...");
		button_5.setBounds(282, 125, 30, 23);
		panel_2.add(button_5);
		button_5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CidadeFiltroFrame cidadeFiltroFrame;
				try {
					cidadeFiltroFrame = new CidadeFiltroFrame() {

						@Override
						public void getCidadeSelecionada(CidadeModel cidade) {
							setCidadeSelecionada(cidade);
						}
					};
					cidadeFiltroFrame.setVisible(true);
				} catch (EstadoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CidadeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JLabel lblUf_1 = new JLabel("UF:");
		lblUf_1.setBounds(322, 109, 46, 14);
		panel_2.add(lblUf_1);

		textFieldUF = new JTextField();
		textFieldUF.setEditable(false);
		textFieldUF.setColumns(10);
		textFieldUF.setBounds(322, 125, 183, 23);
		panel_2.add(textFieldUF);

		JLabel label_7 = new JLabel("Telefone:");
		label_7.setBounds(10, 156, 123, 14);
		panel_2.add(label_7);

		mskTelefone = new MaskFormatter("(**) ****-****");
		mskTelefone.setPlaceholderCharacter('_');

		mskCelular = new MaskFormatter("(**) ****-****");
		mskCelular.setPlaceholderCharacter('_');

		formattedTextFieldTelefone = new JFormattedTextField(mskTelefone);
		formattedTextFieldTelefone.setColumns(10);
		formattedTextFieldTelefone.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextFieldTelefone.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});

		formattedTextFieldTelefone.setBounds(10, 175, 145, 25);
		panel_2.add(formattedTextFieldTelefone);

		JLabel label_8 = new JLabel("Celular:");
		label_8.setBounds(170, 156, 46, 14);
		panel_2.add(label_8);

		formattedTextFieldCelular = new JFormattedTextField(mskCelular);
		formattedTextFieldCelular.setColumns(10);
		formattedTextFieldCelular.setBounds(167, 175, 145, 25);
		formattedTextFieldTelefone.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextFieldTelefone.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});

		panel_2.add(formattedTextFieldCelular);

		JLabel label_9 = new JLabel("Endereço:");
		label_9.setBounds(10, 11, 123, 14);
		panel_2.add(label_9);

		textFieldEndereco = new JTextField();
		textFieldEndereco.setColumns(10);
		textFieldEndereco.setBounds(10, 28, 495, 23);
		panel_2.add(textFieldEndereco);

		JLabel label_10 = new JLabel("Email:");
		label_10.setBounds(322, 156, 46, 14);
		panel_2.add(label_10);

		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(322, 175, 183, 25);
		panel_2.add(textFieldEmail);

		JButton btnNewButton = new JButton("+");
		btnNewButton.setBounds(282, 78, 28, 23);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final BairroListInternalFrame bairroListPanel = new BairroListInternalFrame() {
					@Override
					public void eventoAcaoSalvar(BairroModel model) {
						comboBox_1.addItem(new ComboItem(model.getNome(), model.getId().toString()));
						listaDeBairros.add(model);
						setBairroSelecionado(model);
						hide();
					}
				};
				bairroListPanel.setVisible(true);
				bairroListPanel.eventoBotaoIncluir();
				PrincipalDesktop.getjDesktopPane().add(bairroListPanel);

			}
		});
		panel_2.add(btnNewButton);

		comboBox_1 = new JComboBox();
		comboBox_1.setEditable(true);
		comboBox_1.setBounds(10, 79, 262, 23);
		AutoCompleteDecorator.decorate(comboBox_1);

		// listaDeBairros = new Bairro().obterTodos(BairroModel.class);
		// for (BairroModel bairroModel : listaDeBairros) {
		// comboBox_1.addItem(new ComboItem(bairroModel.getNome(),
		// bairroModel.getId().toString()));

		// }
		comboBox_1.setSelectedIndex(-1);

		panel_2.add(comboBox_1);

		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setBounds(20, 77, 46, 14);

		comboBoxRhCargo = new JComboBox();
		comboBoxRhCargo.setBounds(20, 96, 229, 23);

		// listaDeCargos = new Cargo().obterTodos(CargoModel.class);

		// for (CargoModel cargoModel : listaDeCargos) {
		// comboBoxRhCargo.addItem(new ComboItem(cargoModel.getDescricao(),
		// cargoModel.getId().toString()));
		// }

		JLabel lblDataDeAdmisso = new JLabel("Data de Admissão: ");
		lblDataDeAdmisso.setBounds(259, 77, 130, 14);

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		datePickerDataDeAdmissao = new JXDatePicker();
		datePickerDataDeAdmissao.getEditor().setLocation(0, 31);
		datePickerDataDeAdmissao.setBounds(259, 96, 127, 24);
		datePickerDataDeAdmissao.setFormats(format);

		JLabel lblDataDeDemisso = new JLabel("Data de Demissão: ");
		lblDataDeDemisso.setBounds(396, 77, 122, 14);

		datePickerDataDeDemissao = new JXDatePicker();
		datePickerDataDeDemissao.getEditor().setLocation(0, 31);
		datePickerDataDeDemissao.setBounds(396, 96, 122, 24);
		datePickerDataDeDemissao.setFormats(format);

		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento: ");
		lblDataDeNascimento.setBounds(388, 25, 130, 14);

		datePickerDataDeNascimento = new JXDatePicker();
		datePickerDataDeNascimento.setBounds(391, 42, 127, 24);

		datePickerDataDeNascimento.setFormats(format);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(20, 21, 46, 18);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(152, 25, 46, 14);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(20, 42, 122, 23);

		textField = new JTextField();
		textField.setBounds(152, 42, 97, 23);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(256, 42, 122, 23);
		textField_1.setColumns(10);

		JLabel lblCarteiraProfissional = new JLabel("Carteira Profissional:");
		lblCarteiraProfissional.setBounds(256, 25, 122, 14);

		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(20, 146, 122, 23);

		JLabel lblSalrio = new JLabel("Salário:");
		lblSalrio.setBounds(20, 130, 97, 14);

		JLabel lblComisso = new JLabel("Comissão:");
		lblComisso.setBounds(152, 130, 97, 14);

		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setBounds(152, 146, 97, 23);

		JLabel lblObservao = new JLabel("Observação:");
		lblObservao.setBounds(259, 130, 119, 14);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(259, 145, 249, 70);

		add(tabbedPane);

	}

	@Override
	public FuncionarioModel getModel() {

		model.setNome(nomeTextField.getText());
		model.setAtivo(checkBoxAtivo.isSelected());
		model.setLogin(textFieldLogin.getText());
	
		model.setSenhaTemp(new String(passWordField.getPassword()));
		model.setConfirmaSenhaTemp(new String(confirmaPasswordField.getPassword()));
		model.setSenha(td.encrypt(model.getSenhaTemp()));
		
		model.getPermissaoMovFinanceiraModel().setDeleteOutrosLancamentoPendente(funcionarioPerMovimentacaoPanel.getCheckBoxOutrosPendentes().isSelected());;
		model.getPermissaoMovFinanceiraModel().setDeleteOutrosLancamentoFeito(funcionarioPerMovimentacaoPanel.getCheckBoxOutrosRecebidos().isSelected());;
		
		model.getPermissaoMovFinanceiraModel().setDeleteUsuarioLancamentoPendente(funcionarioPerMovimentacaoPanel.getCheckBoxUsuarioPendente().isSelected());;
		model.getPermissaoMovFinanceiraModel().setDeleteUsuarioLancamentoFeito(funcionarioPerMovimentacaoPanel.getCheckBoxUsuariorecebidoPago().isSelected());;
		
		
		return model;
	}

	@Override
	public void setModel(FuncionarioModel model) {

		if (model == null) {
			model = new FuncionarioModel();
		}
		super.setModel(model);
		
		nomeTextField.setText(model.getNome());
		passWordField.setText(null);
		confirmaPasswordField.setText(null);
		
		

		if (model.getSenha() != null) {
			try {
				String decryptedText = td.decrypt(model.getSenha());
				passWordField.setText(decryptedText);
				confirmaPasswordField.setText(decryptedText);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		textFieldLogin.setText(model.getLogin());
		checkBoxAtivo.setSelected(model.isAtivo());

		passWordField.setEnabled(model.isAtivo());
		confirmaPasswordField.setEnabled(model.isAtivo());

		textFieldLogin.setEnabled(model.isAtivo());
		
		/*
		 * PERMISSAO DE MOV. FINANCEIRA
		 */
		// lima os checkbox 
		 funcionarioPerMovimentacaoPanel.getCheckBoxOutrosPendentes().setSelected(false);
		 funcionarioPerMovimentacaoPanel.getCheckBoxOutrosRecebidos().setSelected(false);
		 funcionarioPerMovimentacaoPanel.getCheckBoxUsuarioPendente().setSelected(false);
		 funcionarioPerMovimentacaoPanel.getCheckBoxUsuariorecebidoPago().setSelected(false);
		 
		 funcionarioPerMovimentacaoPanel.getCheckBoxOutrosPendentes().setSelected(model.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoPendente());
		 funcionarioPerMovimentacaoPanel.getCheckBoxOutrosRecebidos().setSelected(model.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoFeito());
		 funcionarioPerMovimentacaoPanel.getCheckBoxUsuarioPendente().setSelected(model.getPermissaoMovFinanceiraModel().isDeleteUsuarioLancamentoPendente());
		 funcionarioPerMovimentacaoPanel.getCheckBoxUsuariorecebidoPago().setSelected(model.getPermissaoMovFinanceiraModel().isDeleteUsuarioLancamentoFeito());
		 

	}

	public SubGrupoModel getSubGrupoSelecionado() {
		return subGrupoSelecionado;
	}

	public void setSubGrupoSelecionado(SubGrupoModel subGrupoSelecionado) {
		this.subGrupoSelecionado = subGrupoSelecionado;

		if (subGrupoSelecionado != null) {

			// textFieldGeralGrupo.setText(this.subGrupoSelecionado.getGrupo().getDescricao());
			// textFieldGeralSubGrupo.setText(this.subGrupoSelecionado.getDescricao());
		} else {
			// textFieldGeralGrupo.setText(null);
			// textFieldGeralSubGrupo.setText(null);
		}
	}

	public NCMModel getNcmSelecionado() {
		return ncmSelecionado;
	}

	public void setNcmSelecionado(NCMModel ncmSelecionado) {
		this.ncmSelecionado = ncmSelecionado;
		if (ncmSelecionado != null) {
			// textFieldGeralNCM.setText(ncmSelecionado.getNcm());
		} else {
			// textFieldGeralNCM.setText(null);
		}
	}

	public FornecedorModel getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}

	public void setFornecedorSelecionado(FornecedorModel fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
		if (fornecedorSelecionado != null) {
			// textFieldGeralFornecedor.setText(this.fornecedorSelecionado.getRazaoSocial());
		} else {
			// textFieldGeralFornecedor.setText(null);
		}
	}

	public CidadeModel getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(CidadeModel cidadeSelecionada) {
		this.cidadeSelecionada = cidadeSelecionada;
		if (cidadeSelecionada == null) {
			textFieldUF.setText(null);
			textFieldCidade.setText(null);

		} else {
			textFieldCidade.setText(cidadeSelecionada.getNome());
			textFieldUF.setText(cidadeSelecionada.getEstado().getSigla());
		}
	}

	public BairroModel getBairroSelecionaro() {

		if (comboBox_1.getSelectedIndex() <= 0) {
			return null;
		} else {
			Object item = comboBox_1.getSelectedItem();
			String value = ((ComboItem) item).getValue();

			BairroModel model = null;

			for (BairroModel bairro : listaDeBairros) {
				if (bairro.getId().toString().trim().equals(value)) {
					model = bairro;
					break;
				}
			}
			return model;
		}

	}

	private int getPosicaoNaListaDeBairro(BairroModel model) {
		int posicao = -1;
		int count = 0;
		if (model != null) {
			for (BairroModel grupoModel : listaDeBairros) {
				if (grupoModel.getId().toString().equals(model.getId().toString())) {

					posicao = count;
					break;
				}
				count++;
			}
		}
		return posicao;
	}

	private int getPosicaoNaListaDeCargo(CargoModel model) {
		int posicao = -1;
		int count = 0;
		if (model != null) {
			for (CargoModel cargoModel : listaDeCargos) {
				if (cargoModel.getId().toString().equals(model.getId().toString())) {

					posicao = count;
					break;
				}
				count++;
			}
		}
		return posicao;
	}

	public void setBairroSelecionado(BairroModel bairroSelecionaro) {
		this.bairroSelecionaro = bairroSelecionaro;

		comboBox_1.setSelectedIndex(getPosicaoNaListaDeBairro(bairroSelecionaro));

	}

	public CargoModel getCargoSelecionado() {
		if (comboBoxRhCargo.getSelectedIndex() < 0) {
			return null;
		} else {
			Object item = comboBoxRhCargo.getSelectedItem();
			String value = ((ComboItem) item).getValue();

			CargoModel model = null;

			for (CargoModel cargo : listaDeCargos) {
				if (cargo.getId().toString().trim().equals(value)) {
					model = cargo;
					break;
				}
			}
			return model;
		}
	}

	public void setCargoSelecionado(CargoModel cargoSelecionado) {
		this.cargoSelecionado = cargoSelecionado;
		comboBoxRhCargo.setSelectedIndex(getPosicaoNaListaDeCargo(cargoSelecionado));
	}
}
