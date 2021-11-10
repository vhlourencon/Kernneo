package br.com.kernneo.desktop.view.funcionario;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

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
import br.com.kernneo.server.negocio.Bairro;
import br.com.kernneo.server.negocio.Cargo;
import br.com.kernneo.server.negocio.Unidade;

public class FuncionarioFormCadPanel extends GenericFormCadPanel<FuncionarioModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldNome;
    private JTextField textFieldCodigo;

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
    private JCheckBox chckbxAtivo;
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

    public FuncionarioFormCadPanel()   {
	try {
	    initialize();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    private void initialize() throws ParseException, UnidadeException, BairroException, CargoException {

	setSize(544, 382);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es do Funcionário", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 523, 90);
	add(panel);
	panel.setLayout(null);

	JPanel panel_1 = new JPanel();
	panel_1.setBorder(new LineBorder(Color.GRAY, 1, true));
	panel_1.setBounds(10, 38, 179, 35);
	panel.add(panel_1);
	panel_1.setLayout(null);

	chckbxAtivo = new JCheckBox("Ativo");
	chckbxAtivo.setBounds(98, 7, 61, 23);
	panel_1.add(chckbxAtivo);
	chckbxAtivo.setSelected(true);

	textFieldCodigo = new JTextField();
	textFieldCodigo.setBounds(10, 7, 86, 23);
	panel_1.add(textFieldCodigo);
	textFieldCodigo.setColumns(10);
	textFieldCodigo.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume();
		}
	    }
	});

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(199, 36, 45, 14);
	panel.add(labelNome);

	textFieldNome = new JTextField();
	textFieldNome.setBounds(199, 50, 314, 23);
	panel.add(textFieldNome);
	textFieldNome.setColumns(10);

	JLabel lblCdigo = new JLabel("Código:");
	lblCdigo.setBounds(10, 19, 46, 14);
	panel.add(lblCdigo);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBounds(10, 112, 523, 254);
	add(tabbedPane);

	// Group the radio buttons.
	ButtonGroup groupOrigemDaMercadoria = new ButtonGroup();

	ButtonGroup groupLocalDeImpressao = new ButtonGroup();

	ArrayList<UnidadeModel> listaDeUnidades = new Unidade().obterTodos(UnidadeModel.class);
	for (UnidadeModel unidadeModel : listaDeUnidades) {
	    // comboBoxUnidadeDeCompra.addItem(new
	    // ComboItem(unidadeModel.getDescricao(),
	    // unidadeModel.getId().toString()));
	}

	JPanel panel_2 = new JPanel();
	panel_2.setLayout(null);
	tabbedPane.addTab("Endereço", null, panel_2, null);

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

	listaDeBairros = new Bairro().obterTodos(BairroModel.class);
	for (BairroModel bairroModel : listaDeBairros) {
	    comboBox_1.addItem(new ComboItem(bairroModel.getNome(), bairroModel.getId().toString()));

	}
	comboBox_1.setSelectedIndex(-1);

	panel_2.add(comboBox_1);

	JPanel panel_3 = new JPanel();
	tabbedPane.addTab("R.H.", null, panel_3, null);
	panel_3.setLayout(null);

	JLabel lblCargo = new JLabel("Cargo:");
	lblCargo.setBounds(20, 77, 46, 14);
	panel_3.add(lblCargo);

	comboBoxRhCargo = new JComboBox();
	comboBoxRhCargo.setBounds(20, 96, 229, 23);
	panel_3.add(comboBoxRhCargo);

	listaDeCargos = new Cargo().obterTodos(CargoModel.class);

	for (CargoModel cargoModel : listaDeCargos) {
	    comboBoxRhCargo.addItem(new ComboItem(cargoModel.getDescricao(), cargoModel.getId().toString()));
	}

	JLabel lblDataDeAdmisso = new JLabel("Data de Admissão: ");
	lblDataDeAdmisso.setBounds(259, 77, 130, 14);
	panel_3.add(lblDataDeAdmisso);

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	datePickerDataDeAdmissao = new JXDatePicker();
	datePickerDataDeAdmissao.getEditor().setLocation(0, 31);
	datePickerDataDeAdmissao.setBounds(259, 96, 127, 24);
	panel_3.add(datePickerDataDeAdmissao);
	datePickerDataDeAdmissao.setFormats(format);

	JLabel lblDataDeDemisso = new JLabel("Data de Demissão: ");
	lblDataDeDemisso.setBounds(396, 77, 122, 14);
	panel_3.add(lblDataDeDemisso);

	datePickerDataDeDemissao = new JXDatePicker();
	datePickerDataDeDemissao.getEditor().setLocation(0, 31);
	datePickerDataDeDemissao.setBounds(396, 96, 122, 24);
	panel_3.add(datePickerDataDeDemissao);
	datePickerDataDeDemissao.setFormats(format);

	JLabel lblDataDeNascimento = new JLabel("Data de Nascimento: ");
	lblDataDeNascimento.setBounds(388, 25, 130, 14);
	panel_3.add(lblDataDeNascimento);

	datePickerDataDeNascimento = new JXDatePicker();
	datePickerDataDeNascimento.setBounds(391, 42, 127, 24);
	panel_3.add(datePickerDataDeNascimento);

	datePickerDataDeNascimento.setFormats(format);

	JLabel lblCpf = new JLabel("CPF:");
	lblCpf.setBounds(20, 21, 46, 18);
	panel_3.add(lblCpf);

	JLabel lblRg = new JLabel("RG:");
	lblRg.setBounds(152, 25, 46, 14);
	panel_3.add(lblRg);

	JFormattedTextField formattedTextField = new JFormattedTextField();
	formattedTextField.setBounds(20, 42, 122, 23);
	panel_3.add(formattedTextField);

	textField = new JTextField();
	textField.setBounds(152, 42, 97, 23);
	panel_3.add(textField);
	textField.setColumns(10);

	textField_1 = new JTextField();
	textField_1.setBounds(256, 42, 122, 23);
	panel_3.add(textField_1);
	textField_1.setColumns(10);

	JLabel lblCarteiraProfissional = new JLabel("Carteira Profissional:");
	lblCarteiraProfissional.setBounds(256, 25, 122, 14);
	panel_3.add(lblCarteiraProfissional);

	JFormattedTextField formattedTextField_1 = new JFormattedTextField();
	formattedTextField_1.setBounds(20, 146, 122, 23);
	panel_3.add(formattedTextField_1);

	JLabel lblSalrio = new JLabel("Salário:");
	lblSalrio.setBounds(20, 130, 97, 14);
	panel_3.add(lblSalrio);

	JLabel lblComisso = new JLabel("Comissão:");
	lblComisso.setBounds(152, 130, 97, 14);
	panel_3.add(lblComisso);

	JFormattedTextField formattedTextField_2 = new JFormattedTextField();
	formattedTextField_2.setBounds(152, 146, 97, 23);
	panel_3.add(formattedTextField_2);

	JLabel lblObservao = new JLabel("Observação:");
	lblObservao.setBounds(259, 130, 119, 14);
	panel_3.add(lblObservao);

	JTextArea textArea = new JTextArea();
	textArea.setBounds(259, 145, 249, 70);
	panel_3.add(textArea);

    }

    @Override
    public FuncionarioModel getModel() {
	model.setNome(textFieldNome.getText());
	if (textFieldCodigo.getText() == null || textFieldCodigo.getText().trim().length() == 0) {
	    model.setCodigo(null);
	} else {
	    model.setCodigo(Long.valueOf(textFieldCodigo.getText()));
	}

	if (chckbxAtivo.isSelected()) {
	    model.setAtivo(true);
	} else {
	    model.setAtivo(false);
	}

	if (model.getEnderecoFuncionario() == null) {
	    model.setEnderecoFuncionario(new EnderecoFuncionarioModel());
	    model.getEnderecoFuncionario().setFuncionario(model);
	}

	if (model.getFuncionarioRH() == null) {
	    model.setFuncionarioRH(new FuncionarioRHModel());
	    model.getFuncionarioRH().setFuncionario(model);
	}

//	model.getEnderecoFuncionario().setEndereco(textFieldEndereco.getText());
	model.getEnderecoFuncionario().setNumero(textFieldEnderecoNumero.getText());
	model.getEnderecoFuncionario().setTelefone(formattedTextFieldTelefone.getText());
	model.getEnderecoFuncionario().setCelular(formattedTextFieldCelular.getText());
	model.getEnderecoFuncionario().setEmail(textFieldEmail.getText());
//	model.getEnderecoFuncionario().setCidade(getCidadeSelecionada());
	model.getEnderecoFuncionario().setBairro(getBairroSelecionaro());

	model.getFuncionarioRH().setCargo(getCargoSelecionado());

	return model;
    }

    @Override
    public void setModel(FuncionarioModel model) {

	if (model == null) {
	    model = new FuncionarioModel();
	}
	super.setModel(model);
	if (model.getCodigo() == null) {
	    textFieldCodigo.setText(null);
	} else {
	    textFieldCodigo.setText(String.valueOf(model.getCodigo()));
	}

	if (model.isAtivo()) {
	    chckbxAtivo.setSelected(true);
	} else {
	    chckbxAtivo.setSelected(false);
	}
	textFieldNome.setText(model.getNome());

	if (model.getEnderecoFuncionario() == null) {
	    setCidadeSelecionada(null);
	    setBairroSelecionado(null);
	    textFieldEndereco.setText(null);
	    textFieldEnderecoNumero.setText(null);
	    formattedTextFieldCelular.setText(null);
	    formattedTextFieldTelefone.setText(null);
	    textFieldEmail.setText(null);

	} else {
	 //   setCidadeSelecionada(model.getEnderecoFuncionario().getCidade());
	    setBairroSelecionado(model.getEnderecoFuncionario().getBairro());
//	    textFieldEndereco.setText(model.getEnderecoFuncionario().getEndereco());
	    textFieldEnderecoNumero.setText(model.getEnderecoFuncionario().getNumero());
	    formattedTextFieldCelular.setText(model.getEnderecoFuncionario().getCelular());
	    formattedTextFieldTelefone.setText(model.getEnderecoFuncionario().getTelefone());
	    textFieldEmail.setText(model.getEnderecoFuncionario().getEmail());

	}

	if (model.getFuncionarioRH() == null) {
	    setCargoSelecionado(null);
	} else {
	    setCargoSelecionado(model.getFuncionarioRH().getCargo());
	}

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
