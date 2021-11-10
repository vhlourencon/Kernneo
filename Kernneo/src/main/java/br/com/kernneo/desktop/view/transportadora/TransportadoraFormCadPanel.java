package br.com.kernneo.desktop.view.transportadora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.TransportadoraModel;
import br.com.kernneo.desktop.view.cidade.CidadeFiltroFrame;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class TransportadoraFormCadPanel extends GenericFormCadPanel<TransportadoraModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldCidade;

    private CidadeModel cidadeSelecionada;
    private JTextField textFieldNome;
    private JTextField textFieldIE;
    private JFormattedTextField textFieldTelefone;
    private JFormattedTextField textFieldCelular;
    private JTextField textFieldEndereco;
    private JTextField textFieldEmail;
    private JTextField textFieldSite;
    private JFormattedTextField formattedTextFieldCnpj;
    private MaskFormatter mskTelefone;
    private MaskFormatter mskCelular;

    public TransportadoraFormCadPanel() {
	try {
	    initialize();
	} catch (ParseException e) {

	    e.printStackTrace();
	}

    }

    private void initialize() throws ParseException {

	setSize(554, 331);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Transportadora", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 533, 295);
	add(panel);
	panel.setLayout(null);

	JLabel lblNome_2 = new JLabel("Nome:");
	lblNome_2.setBounds(10, 41, 46, 14);
	panel.add(lblNome_2);

	textFieldNome = new JTextField();
	textFieldNome.setBounds(10, 57, 302, 20);
	panel.add(textFieldNome);
	textFieldNome.setColumns(10);

	JLabel lblCnpj_1 = new JLabel("CNPJ:");
	lblCnpj_1.setBounds(322, 40, 46, 14);
	panel.add(lblCnpj_1);

	MaskFormatter msk = new MaskFormatter("##.###.###/####-##");
	msk.setPlaceholderCharacter('_');
	msk.setOverwriteMode(true);
	msk.setValidCharacters("0123456789");

	formattedTextFieldCnpj = new JFormattedTextField(msk);
	formattedTextFieldCnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
	formattedTextFieldCnpj.setBounds(322, 57, 183, 20);
	panel.add(formattedTextFieldCnpj);

	JLabel lblNewLabel = new JLabel("Cidade:");
	lblNewLabel.setBounds(10, 140, 147, 14);
	panel.add(lblNewLabel);

	textFieldCidade = new JTextField();
	textFieldCidade.setBounds(10, 156, 262, 20);
	panel.add(textFieldCidade);
	textFieldCidade.setEditable(false);
	textFieldCidade.setColumns(10);

	JButton buttonProcurarCidade = new JButton(br.com.kernneo.desktop.view.widget.Icone.novo("btPesquisa.png"));
	buttonProcurarCidade.setBounds(282, 156, 30, 23);
	panel.add(buttonProcurarCidade);

	JLabel lblIe = new JLabel("IE:");
	lblIe.setBounds(322, 140, 46, 14);
	panel.add(lblIe);

	textFieldIE = new JTextField();
	textFieldIE.setBounds(322, 156, 183, 20);
	panel.add(textFieldIE);
	textFieldIE.setColumns(10);

	JLabel lblTelefone = new JLabel("Telefone:");
	lblTelefone.setBounds(10, 187, 123, 14);
	panel.add(lblTelefone);

	mskTelefone = new MaskFormatter("(**) ****-****");
	// mskTelefone.setValidCharacters("0123456789");
	mskTelefone.setPlaceholderCharacter('_');
	// mskTelefone.setOverwriteMode(true);
	// mskTelefone.setAllowsInvalid(true);

	textFieldTelefone = new JFormattedTextField(mskTelefone);
	textFieldTelefone.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldTelefone.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
	       char c = e.getKeyChar();
	       if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	          e.consume();  // ignore event
	       }
	    }
	 });

	textFieldTelefone.setBounds(10, 206, 145, 20);
	panel.add(textFieldTelefone);
	textFieldTelefone.setColumns(10);

	JLabel lblCelular = new JLabel("Celular:");
	lblCelular.setBounds(170, 187, 46, 14);
	panel.add(lblCelular);

	mskCelular = new MaskFormatter("(**) ****-****");
	// mskCelular.setValidCharacters("0123456789");
	mskCelular.setPlaceholderCharacter('_');
	// mskCelular.setOverwriteMode(true);
	// mskCelular.setAllowsInvalid(true);

	textFieldCelular = new JFormattedTextField(mskCelular);
	textFieldCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
	textFieldCelular.setBounds(167, 206, 145, 20);
	panel.add(textFieldCelular);
	textFieldCelular.setColumns(10);
	textFieldCelular.addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
	       char c = e.getKeyChar();
	       if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	          e.consume();  // ignore event
	       }
	    }
	 });

	JLabel lblEndereo_1 = new JLabel("Endereço:");
	lblEndereo_1.setBounds(10, 92, 123, 14);
	panel.add(lblEndereo_1);

	textFieldEndereco = new JTextField();
	textFieldEndereco.setBounds(10, 109, 495, 20);
	panel.add(textFieldEndereco);
	textFieldEndereco.setColumns(10);

	JLabel lblEmail = new JLabel("Email:");
	lblEmail.setBounds(322, 187, 46, 14);
	panel.add(lblEmail);

	textFieldEmail = new JTextField();
	textFieldEmail.setBounds(322, 206, 183, 20);
	panel.add(textFieldEmail);
	textFieldEmail.setColumns(10);

	JLabel lblSite = new JLabel("Site:");
	lblSite.setBounds(10, 237, 46, 14);
	panel.add(lblSite);

	textFieldSite = new JTextField();
	textFieldSite.setBounds(10, 255, 495, 20);
	panel.add(textFieldSite);
	textFieldSite.setColumns(10);
	buttonProcurarCidade.addActionListener(new ActionListener() {

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

    }

    private void setCidadeSelecionada(CidadeModel cidade) {
	cidadeSelecionada = cidade;
	if (cidade != null) {

	    textFieldCidade.setText(cidade.getNome() + " - " + cidade.getEstado().getSigla());
	} else {
	    textFieldCidade.setText(null);
	}
    }

    @Override
    public TransportadoraModel getModel() {
	model.setCidade(cidadeSelecionada);
	model.setNomeFantasia(textFieldNome.getText());

	String cnpjFormatado = formattedTextFieldCnpj.getText();
	// cnpjFormatado = cnpjFormatado.replace(".", "");
	// cnpjFormatado = cnpjFormatado.replace("/", "");
	// cnpjFormatado = cnpjFormatado.replace("-", "");
	// cnpjFormatado = cnpjFormatado.replace("_", "");

	model.setCnpj(cnpjFormatado);
	model.setLogradouro(textFieldEndereco.getText());
	model.setCidade(cidadeSelecionada);
	model.setIe(textFieldIE.getText());
	model.setEmail(textFieldEmail.getText());
	model.setSite(textFieldSite.getText());

	String celularFormatado = textFieldCelular.getText();
	model.setCelular(celularFormatado);

	String teleFoneFormatado = textFieldTelefone.getText();
	model.setTelefone(teleFoneFormatado);

	return model;
    }

    @Override
    public void setModel(TransportadoraModel model) {

	if (model == null) {
	    model = new TransportadoraModel();
	}

	super.setModel(model);

	textFieldNome.setText(model.getNomeFantasia());
	formattedTextFieldCnpj.setText(model.getCnpj());
	textFieldEndereco.setText(model.getLogradouro());
	textFieldIE.setText(model.getIe());

	String foneFormatado = "";
	if (model.getTelefone() != null) {
	    foneFormatado = model.getTelefone();
	    foneFormatado = foneFormatado.replace("(", "");
	    foneFormatado = foneFormatado.replace(")", "");
	    foneFormatado = foneFormatado.replace("-", "");
	}

	String celularFormatado = "";
	if (model.getCelular() != null) {
	    celularFormatado = model.getCelular();
	    // celularFormatado = celularFormatado.replace(" ", "");
	    // celularFormatado = celularFormatado.replace(")", "");
	    // celularFormatado = celularFormatado.replace("-", "");
	}

	textFieldTelefone.setText(model.getTelefone());
	textFieldCelular.setText(model.getCelular());

	textFieldSite.setText(model.getSite());
	textFieldEmail.setText(model.getEmail());

	setCidadeSelecionada(model.getCidade());

    }

    public static String formatString(String value, String pattern) {
	MaskFormatter mf;
	try {
	    mf = new MaskFormatter(pattern);
	    mf.setValueContainsLiteralCharacters(false);
	    return mf.valueToString(value);
	} catch (ParseException ex) {
	    ex.printStackTrace();
	    return value;
	}
    }
}
