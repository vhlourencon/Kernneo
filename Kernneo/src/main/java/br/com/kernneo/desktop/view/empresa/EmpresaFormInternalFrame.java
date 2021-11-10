package br.com.kernneo.desktop.view.empresa;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;
import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.desktop.view.cidade.CidadeFiltroFrame;
import br.com.kernneo.desktop.view.widget.ButtonBarComponent;
import br.com.kernneo.desktop.view.widget.GenericJInternalFrame;
import br.com.kernneo.server.negocio.Empresa;

public class EmpresaFormInternalFrame extends GenericJInternalFrame {

    public EmpresaModel empresa;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldRazaoSocial;
    private JTextField textFieldFantasia;
    private JTextField textFieldEnderecoRua;
    private JTextField textFieldEnderecoNumero;
    private JTextField textFieldEnderecoBairro;
    private JFormattedTextField textFieldEnderecoFone1;
    private JFormattedTextField textFieldEnderecoFone2;
    private JFormattedTextField textFieldFax;
    private JFormattedTextField textFieldCep;
    private JFormattedTextField textFieldCnpj;
    private JTextField textFieldInternet;
    private JTextField textFieldEmail;
    private JTextField textFieldResponsavelNome;
    private JFormattedTextField textFieldResponsavelCPF;
    private JFormattedTextField textFieldResponsavelTelefone;
    private JTextField textFieldCidade;

    private CidadeModel cidadeSelecionada;
    private JTextField textFieldIE;
    private JTextField textFieldIM;

    public EmpresaFormInternalFrame() {
	try {
	    initialize();
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private void initialize() throws ParseException {

	setSize(775, 511);
	getContentPane().setLayout(new BorderLayout());

	

	
	JLabel labelRazao = new JLabel("Razão Social:");
	labelRazao.setBounds(22, 11, 82, 14);
	
	textFieldRazaoSocial = new JTextField();
	textFieldRazaoSocial.setBounds(22, 25, 318, 20);
	textFieldRazaoSocial.setColumns(10);

	JLabel labelNomeFantasia = new JLabel("Nome de Fantasia:");
	labelNomeFantasia.setBounds(350, 11, 392, 14);
	
	textFieldFantasia = new JTextField();
	textFieldFantasia.setBounds(350, 25, 382, 20);
	textFieldFantasia.setColumns(10);
	
	

	Box verticalBox = Box.createVerticalBox();
	verticalBox.setBounds(119, 161, 193, -100);
	
	
	
	
	
	

	

	JLabel lblEndereo = new JLabel("Endereço:");
	lblEndereo.setBounds(10, 11, 86, 14);
	
	textFieldEnderecoRua = new JTextField();
	textFieldEnderecoRua.setBounds(10, 27, 395, 22);
	textFieldEnderecoRua.setColumns(10);
	textFieldEnderecoRua.setSize(395, 20);

	JLabel lblNumero = new JLabel("Numero:");
	lblNumero.setBounds(417, 11, 84, 14);
	
	textFieldEnderecoNumero = new JTextField();
	textFieldEnderecoNumero.setBounds(415, 27, 86, 20);
	textFieldEnderecoNumero.setColumns(10);

	JLabel lblBairro = new JLabel("Bairro:");
	lblBairro.setBounds(508, 11, 46, 14);
	
	textFieldEnderecoBairro = new JTextField();
	textFieldEnderecoBairro.setBounds(508, 27, 198, 20);
	textFieldEnderecoBairro.setColumns(10);

	JLabel lblFone = new JLabel("Fone (2):");
	lblFone.setBounds(614, 58, 80, 14);
	
	JLabel lblFone_1 = new JLabel("Fone (1):");
	lblFone_1.setBounds(508, 58, 80, 14);
	
	JLabel lblFax = new JLabel("Fax:");
	lblFax.setBounds(415, 58, 46, 14);
	
	JLabel lblCep = new JLabel("CEP:");
	lblCep.setBounds(319, 59, 46, 14);
	
	JLabel lblInternet = new JLabel("Site:");
	lblInternet.setBounds(10, 105, 46, 14);
	
	JLabel lblEmail = new JLabel("Email:");
	lblEmail.setBounds(370, 105, 46, 14);
	
	JPanel panelInfoGerais = new JPanel();
	panelInfoGerais.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
	panelInfoGerais.setLayout(null);
	panelInfoGerais.add(verticalBox);
	panelInfoGerais.add(lblEndereo);
	panelInfoGerais.add(textFieldEnderecoRua);
	panelInfoGerais.add(lblNumero);
	panelInfoGerais.add(textFieldEnderecoNumero);
	panelInfoGerais.add(lblBairro);
	panelInfoGerais.add(textFieldEnderecoBairro);
	panelInfoGerais.add(lblFone);
	panelInfoGerais.add(lblFone_1);
	panelInfoGerais.add(lblFax);
	panelInfoGerais.add(lblInternet);
	panelInfoGerais.add(lblCep);
	panelInfoGerais.add(lblInternet);
	panelInfoGerais.add(lblEmail);

	
	

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBounds(22, 106, 721, 269);
	tabbedPane.addTab("Informações Gerais", null, panelInfoGerais, null);
	
	
	JPanel jPanelCenter = new JPanel();
	jPanelCenter.setLayout(null);
	jPanelCenter.add(labelRazao);
	jPanelCenter.add(textFieldRazaoSocial);
	jPanelCenter.add(labelNomeFantasia);
	jPanelCenter.add(textFieldFantasia);
	jPanelCenter.add(tabbedPane);
	
	JPanel panelResponsavel = new JPanel();
	panelResponsavel.setBorder(new TitledBorder(null, "Responsável pela empresa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panelResponsavel.setBounds(10, 153, 696, 71);
	panelInfoGerais.add(panelResponsavel);
	panelResponsavel.setLayout(null);

	JLabel lblNome = new JLabel("Nome:");
	lblNome.setBounds(10, 21, 83, 14);
	panelResponsavel.add(lblNome);

	JLabel lblCargo = new JLabel("CPF:");
	lblCargo.setBounds(362, 21, 62, 14);
	panelResponsavel.add(lblCargo);

	JLabel lblNome_1 = new JLabel("Telefone:");
	lblNome_1.setBounds(531, 21, 69, 14);
	panelResponsavel.add(lblNome_1);

	textFieldResponsavelNome = new JTextField();
	textFieldResponsavelNome.setBounds(10, 40, 342, 20);
	panelResponsavel.add(textFieldResponsavelNome);
	textFieldResponsavelNome.setColumns(10);

	MaskFormatter maskFormatterCPF = new javax.swing.text.MaskFormatter("***.***.***-**");

	textFieldResponsavelCPF = new JFormattedTextField(maskFormatterCPF);
	textFieldResponsavelCPF.setBounds(362, 40, 159, 20);
	panelResponsavel.add(textFieldResponsavelCPF);
	textFieldResponsavelCPF.setColumns(10);
	textFieldResponsavelCPF.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldResponsavelCPF.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});

	MaskFormatter mskTelefone3 = new MaskFormatter("(**) ****-****");
	mskTelefone3.setPlaceholderCharacter('_');

	textFieldResponsavelTelefone = new JFormattedTextField(mskTelefone3);
	textFieldResponsavelTelefone.setBounds(531, 40, 155, 20);
	panelResponsavel.add(textFieldResponsavelTelefone);
	textFieldResponsavelTelefone.setColumns(10);
	textFieldResponsavelTelefone.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldResponsavelTelefone.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});

	MaskFormatter mskTelefone1 = new MaskFormatter("(**) ****-****");
	mskTelefone1.setPlaceholderCharacter('_');

	MaskFormatter mskTelefone2 = new MaskFormatter("(**) ****-****");
	mskTelefone2.setPlaceholderCharacter('_');

	textFieldEnderecoFone2 = new JFormattedTextField(mskTelefone2);
	textFieldEnderecoFone2.setBounds(614, 74, 92, 20);
	textFieldEnderecoFone2.setColumns(10);
	textFieldEnderecoFone2.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldEnderecoFone2.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});

	panelInfoGerais.add(textFieldEnderecoFone2);

	textFieldEnderecoFone1 = new JFormattedTextField(mskTelefone1);
	textFieldEnderecoFone1.setBounds(508, 74, 96, 20);
	textFieldEnderecoFone1.setColumns(10);
	textFieldEnderecoFone1.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldEnderecoFone1.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});
	panelInfoGerais.add(textFieldEnderecoFone1);

	MaskFormatter mskFAX = new MaskFormatter("(**) ****-****");
	mskTelefone2.setPlaceholderCharacter('_');

	textFieldFax = new JFormattedTextField(mskFAX);
	textFieldFax.setBounds(415, 73, 86, 20);
	panelInfoGerais.add(textFieldFax);
	textFieldFax.setColumns(10);
	textFieldFax.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldFax.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});

	MaskFormatter maskFormatterCEP = new javax.swing.text.MaskFormatter("*****-***");
	maskFormatterCEP.setPlaceholderCharacter('_');

	textFieldCep = new JFormattedTextField(maskFormatterCEP);
	textFieldCep.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldCep.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});

	textFieldCep.setBounds(319, 74, 86, 20);
	panelInfoGerais.add(textFieldCep);
	textFieldCep.setColumns(10);

	textFieldInternet = new JTextField();
	textFieldInternet.setBounds(10, 122, 350, 20);
	panelInfoGerais.add(textFieldInternet);
	textFieldInternet.setColumns(10);

	textFieldEmail = new JTextField();
	textFieldEmail.setBounds(370, 122, 336, 20);
	panelInfoGerais.add(textFieldEmail);
	textFieldEmail.setColumns(10);

	JLabel lblNewLabel = new JLabel("Cidade:");
	lblNewLabel.setBounds(10, 58, 147, 14);
	panelInfoGerais.add(lblNewLabel);

	textFieldCidade = new JTextField();
	textFieldCidade.setEditable(false);

	textFieldCidade.setBounds(10, 74, 262, 20);
	panelInfoGerais.add(textFieldCidade);
	textFieldCidade.setColumns(10);

	JButton btnNewButton = new JButton(br.com.kernneo.desktop.view.widget.Icone.novo("btPesquisa.png"));
	btnNewButton.addActionListener(new ActionListener() {

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

	btnNewButton.setBounds(282, 72, 30, 23);
	panelInfoGerais.add(btnNewButton);

	ButtonBarComponent buttonBarComponent = new ButtonBarComponent();
	buttonBarComponent.setLayout(new MigLayout("", "[106px][106px]", "[41px]"));
	buttonBarComponent.remove(buttonBarComponent.btIncluir);
	buttonBarComponent.remove(buttonBarComponent.btAlterar);
	buttonBarComponent.remove(buttonBarComponent.btExcluir);
	buttonBarComponent.remove(buttonBarComponent.btConsultar);
	buttonBarComponent.remove(buttonBarComponent.btSair);
	buttonBarComponent.add(buttonBarComponent.btSalvar, "cell 0 0,grow");
	buttonBarComponent.add(buttonBarComponent.btSair, "cell 1 0,grow");

	buttonBarComponent.btSalvar.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		try {
		    int dialogButton = JOptionPane.YES_NO_OPTION;
		    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "Warning", dialogButton);
		    if (dialogResult == JOptionPane.YES_OPTION) {

			new Empresa().merge(getEmpresa());
			dispose();
		    }

		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

	    }
	});

	buttonBarComponent.btSair.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		try {
		    int dialogButton = JOptionPane.YES_NO_OPTION;
		    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja sair sem salvar as alterações?", "Warning", dialogButton);
		    if (dialogResult == JOptionPane.YES_OPTION) {

			dispose();
		    }

		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

	    }
	});

	getContentPane().add(buttonBarComponent, BorderLayout.NORTH);
	getContentPane().add(jPanelCenter, BorderLayout.CENTER);

	MaskFormatter mskCNPJ = new MaskFormatter("**.***.***/****-**");
	mskCNPJ.setPlaceholderCharacter('_');

	textFieldCnpj = new JFormattedTextField(mskCNPJ);
	textFieldCnpj.setBounds(22, 72, 318, 20);
	jPanelCenter.add(textFieldCnpj);
	textFieldCnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldCnpj.setColumns(10);
	textFieldCnpj.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		    e.consume(); // ignore event
		}
	    }
	});

	JLabel lblCnpj = new JLabel("CNPJ:");
	lblCnpj.setBounds(22, 56, 46, 14);
	jPanelCenter.add(lblCnpj);

	JLabel lblIncrioEstadual = new JLabel("Incrição Estadual:");
	lblIncrioEstadual.setBounds(350, 56, 118, 14);
	jPanelCenter.add(lblIncrioEstadual);

	textFieldIE = new JTextField();
	textFieldIE.setBounds(350, 72, 202, 20);
	jPanelCenter.add(textFieldIE);
	textFieldIE.setColumns(10);

	JLabel lblIncrioMunicipal = new JLabel("Incrição Municipal:");
	lblIncrioMunicipal.setBounds(558, 56, 140, 14);
	jPanelCenter.add(lblIncrioMunicipal);

	textFieldIM = new JTextField();
	textFieldIM.setBounds(558, 72, 174, 20);
	jPanelCenter.add(textFieldIM);
	textFieldIM.setColumns(10);

	try {
	    Empresa empresa = new Empresa();
	    Long ultimoId = empresa.obterUltimoId(EmpresaModel.class);
	    setEmpresa(new Empresa().obterPorId(EmpresaModel.class, ultimoId));
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

    }

    private void setCidadeSelecionada(CidadeModel cidade) {
	cidadeSelecionada = cidade;
	if (cidade != null) {

	    textFieldCidade.setText(cidade.getNome() + " - " + cidade.getEstado().getSigla());
	} else {
	    textFieldCidade.setText(null);
	}
    }

    public EmpresaModel getEmpresa() {

	empresa.setRazaoSocial(textFieldRazaoSocial.getText());
//	empresa.setNomeFantasia(textFieldFantasia.getText());
//	empresa.setCnpj(textFieldCnpj.getText());
//	empresa.setIe(textFieldIE.getText());
//	empresa.setIm(textFieldIM.getText());
//
//	empresa.setEndereco(textFieldEnderecoRua.getText());
//	empresa.setNumero(textFieldEnderecoNumero.getText());
//	empresa.setBairro(textFieldEnderecoBairro.getText());
	//empresa.setCidade(cidadeSelecionada);
	empresa.setCep(textFieldCep.getText());
	empresa.setTelefone1(textFieldEnderecoFone1.getText());
	empresa.setTelefone2(textFieldEnderecoFone2.getText());
	empresa.setFax(textFieldFax.getText());
	empresa.setSite(textFieldInternet.getText());
	empresa.setEmail(textFieldEmail.getText());

	empresa.setResponsavel(textFieldResponsavelNome.getText());
	empresa.setCpf(textFieldResponsavelCPF.getText());
	empresa.setResponsavelTelefone(textFieldResponsavelTelefone.getText());

	return empresa;
    }

    public void setEmpresa(EmpresaModel model) {

	if (model == null) {
	    model = new EmpresaModel();
	}
	empresa = model;

	textFieldRazaoSocial.setText(model.getRazaoSocial());
//	textFieldFantasia.setText(model.getNomeFantasia());
//	textFieldCnpj.setText(model.getCnpj());
//	textFieldIE.setText(model.getIe());
//	textFieldIM.setText(model.getIm());
//
//	textFieldEnderecoRua.setText(model.getEndereco());
//	textFieldEnderecoNumero.setText(model.getNumero());
//	textFieldEnderecoBairro.setText(model.getBairro());

	textFieldCep.setText(model.getCep());
	textFieldEnderecoFone1.setText(model.getTelefone1());
	textFieldEnderecoFone2.setText(model.getTelefone2());
	textFieldFax.setText(model.getFax());
	textFieldInternet.setText(model.getSite());
	textFieldEmail.setText(model.getEmail());

	textFieldResponsavelNome.setText(model.getResponsavel());
	textFieldResponsavelCPF.setText(model.getCpf());
	textFieldResponsavelTelefone.setText(model.getResponsavelTelefone());

	//setCidadeSelecionada(model.getCidade());

    }
}
