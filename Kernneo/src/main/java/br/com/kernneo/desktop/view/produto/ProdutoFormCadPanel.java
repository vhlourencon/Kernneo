package br.com.kernneo.desktop.view.produto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.exception.NCMException;
import br.com.kernneo.client.exception.SubGrupoException;
import br.com.kernneo.client.exception.UnidadeException;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.NCMModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.client.model.UnidadeModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.OrigemDaMercadoria;
import br.com.kernneo.desktop.view.fornecedor.FornecedorFiltroDialog;
import br.com.kernneo.desktop.view.ncm.NCMFiltroFrame;
import br.com.kernneo.desktop.view.subgrupo.SubGrupoFiltroFrame;
import br.com.kernneo.desktop.view.widget.ComboItem;
import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Unidade;

public class ProdutoFormCadPanel extends GenericFormCadPanel<ProdutoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
    private ArrayList<CategoriaModel> listaDeGrupos;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textFieldCodigo;
    private JTextField textFieldGeralGrupo;
    private JTextField textFieldGeralSubGrupo;
    private JTextField textFieldGeralFornecedor;
    private JTextField textFieldGeralCodigoDeBarras;
    private JTextField textFieldGeralNCM;

    private SubGrupoModel subGrupoSelecionado;
    private NCMModel ncmSelecionado;
    private FornecedorModel fornecedorSelecionado;
    private JRadioButton radioButtonEstrangeira;
    private JRadioButton radioButtonNacional;
    private JRadioButton radioButtonEstrangeiraImp;
    private JRadioButton radioButtonCozinha;
    private JRadioButton radioButtonCopa2;
    private JRadioButton radioButtonCopa3;
    private JRadioButton radioButtonBar;
    private JRadioButton radioButtonCopa1;
    private JRadioButton radioButtonNenhum;
    private DecimalFormattedField formattedTextFieldGeralPrecoCusto;
    private DecimalFormattedField formattedTextFieldGeralPrecoVenda;

    public ProdutoFormCadPanel() throws CategoriaException, UnidadeException {
	initialize();

    }

    private void initialize() throws CategoriaException, UnidadeException {

	setSize(837, 534);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es do Produto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 740, 90);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Descrição:");
	labelNome.setBounds(110, 24, 71, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(110, 42, 620, 23);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

	listaDeGrupos = new Categoria().obterTodos(CategoriaModel.class);

	JLabel lblCdigo = new JLabel("Código:");
	lblCdigo.setBounds(10, 24, 46, 14);
	panel.add(lblCdigo);

	textFieldCodigo = new JTextField();
	textFieldCodigo.setBounds(10, 42, 86, 23);
	panel.add(textFieldCodigo);
	textFieldCodigo.setColumns(10);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBounds(10, 112, 740, 399);
	add(tabbedPane);

	JPanel panel_2 = new JPanel();
	tabbedPane.addTab("Geral", null, panel_2, null);
	panel_2.setLayout(null);

	JPanel panel_3 = new JPanel();
	panel_3.setBorder(new TitledBorder(null, "Categorização", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel_3.setBounds(429, 11, 296, 122);
	panel_2.add(panel_3);
	panel_3.setLayout(null);

	JLabel lblGrupo = new JLabel("Grupo: ");
	lblGrupo.setBounds(10, 22, 46, 14);
	panel_3.add(lblGrupo);

	textFieldGeralGrupo = new JTextField();
	textFieldGeralGrupo.setEditable(false);
	textFieldGeralGrupo.setBounds(10, 37, 276, 23);
	panel_3.add(textFieldGeralGrupo);
	textFieldGeralGrupo.setColumns(10);

	JLabel lblSubGrupo = new JLabel("Sub Grupo:");
	lblSubGrupo.setBounds(10, 68, 104, 14);
	panel_3.add(lblSubGrupo);

	textFieldGeralSubGrupo = new JTextField();
	textFieldGeralSubGrupo.setEditable(false);
	textFieldGeralSubGrupo.setBounds(10, 84, 246, 23);
	panel_3.add(textFieldGeralSubGrupo);
	textFieldGeralSubGrupo.setColumns(10);

	JButton button_5 = new JButton("New button");
	button_5.setBounds(261, 83, 25, 24);
	panel_3.add(button_5);
	button_5.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		SubGrupoFiltroFrame subGrupoFiltroFrame;
		try {
		    subGrupoFiltroFrame = new SubGrupoFiltroFrame() {

		        @Override
		        public void getSubGrupoSelecionados(SubGrupoModel subGrupo) {
		    	setSubGrupoSelecionado(subGrupo);

		        }
		    };
		    subGrupoFiltroFrame.setVisible(true);
		} catch (CategoriaException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (SubGrupoException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		

	    }
	});

	textFieldGeralFornecedor = new JTextField();
	textFieldGeralFornecedor.setEditable(false);
	textFieldGeralFornecedor.setBounds(152, 43, 225, 23);
	panel_2.add(textFieldGeralFornecedor);
	textFieldGeralFornecedor.setColumns(10);

	JLabel lblFornecedor = new JLabel("Fornecedor:");
	lblFornecedor.setBounds(152, 22, 104, 14);
	panel_2.add(lblFornecedor);

	JButton button_6 = new JButton("New button");
	button_6.setBounds(387, 43, 32, 24);
	button_6.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		FornecedorFiltroDialog fornecedorFiltroDialog;
		try {
		    fornecedorFiltroDialog = new FornecedorFiltroDialog() {

		        @Override
		        public void getModelSelecionado(FornecedorModel model) {
		    	setFornecedorSelecionado(model);
		        }
		    };
		    fornecedorFiltroDialog.setVisible(true);
		} catch (FornecedorException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		

	    }
	});
	panel_2.add(button_6);

	textFieldGeralCodigoDeBarras = new JTextField();
	textFieldGeralCodigoDeBarras.setBounds(14, 93, 399, 23);
	panel_2.add(textFieldGeralCodigoDeBarras);
	textFieldGeralCodigoDeBarras.setColumns(10);

	JLabel lblCdigoDeBarras = new JLabel("Código de Barras (EAN):");
	lblCdigoDeBarras.setBounds(14, 74, 143, 14);
	panel_2.add(lblCdigoDeBarras);

	JLabel lblCdigoNcm = new JLabel("Código NCM:");
	lblCdigoNcm.setBounds(14, 22, 79, 14);
	panel_2.add(lblCdigoNcm);

	textFieldGeralNCM = new JTextField();
	textFieldGeralNCM.setEditable(false);
	textFieldGeralNCM.setBounds(14, 43, 94, 23);
	panel_2.add(textFieldGeralNCM);
	textFieldGeralNCM.setColumns(10);

	JButton button_7 = new JButton("New button");
	button_7.setBounds(110, 43, 32, 23);
	button_7.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		NCMFiltroFrame ncmFiltroFrame;
		try {
		    ncmFiltroFrame = new NCMFiltroFrame() {

		        @Override
		        public void getNCMSelecionado(NCMModel ncm) {
//		    	setNcmSelecionado(ncm);

		        }
		    };
		    ncmFiltroFrame.setVisible(true);
		} catch (NCMException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		

	    }
	});
	panel_2.add(button_7);

	JPanel jPanelOrigemDaMErcadoria = new JPanel();
	// panel_4.setLayout(null);
	jPanelOrigemDaMErcadoria.setBorder(new TitledBorder(null, "Origem da Mercadoria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	jPanelOrigemDaMErcadoria.setBounds(233, 124, 188, 129);
	panel_2.add(jPanelOrigemDaMErcadoria);
	jPanelOrigemDaMErcadoria.setLayout(null);

	radioButtonNacional = new JRadioButton("Nacional");
	radioButtonNacional.setBounds(18, 34, 109, 23);
	jPanelOrigemDaMErcadoria.add(radioButtonNacional);

	radioButtonEstrangeira = new JRadioButton("Estrangeira");
	radioButtonEstrangeira.setBounds(18, 60, 109, 23);
	jPanelOrigemDaMErcadoria.add(radioButtonEstrangeira);

	radioButtonEstrangeiraImp = new JRadioButton("Estrangeira - Imp. direta");
	radioButtonEstrangeiraImp.setBounds(18, 86, 183, 23);
	jPanelOrigemDaMErcadoria.add(radioButtonEstrangeiraImp);

	// Group the radio buttons.
	ButtonGroup groupOrigemDaMercadoria = new ButtonGroup();
	groupOrigemDaMercadoria.add(radioButtonNacional);
	groupOrigemDaMercadoria.add(radioButtonEstrangeira);
	groupOrigemDaMercadoria.add(radioButtonEstrangeiraImp);

	JPanel jPanelLocalDeImpressao = new JPanel();
	jPanelLocalDeImpressao.setBorder(new TitledBorder(null, "Local de Impress\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	jPanelLocalDeImpressao.setBounds(429, 143, 296, 110);
	panel_2.add(jPanelLocalDeImpressao);
	jPanelLocalDeImpressao.setLayout(null);

	radioButtonCozinha = new JRadioButton("Cozinha");
	radioButtonCozinha.setBounds(49, 31, 70, 23);
	jPanelLocalDeImpressao.add(radioButtonCozinha);

	radioButtonCopa2 = new JRadioButton("Copa 2");
	radioButtonCopa2.setBounds(193, 31, 70, 23);
	jPanelLocalDeImpressao.add(radioButtonCopa2);

	radioButtonCopa3 = new JRadioButton("Copa 3");
	radioButtonCopa3.setBounds(49, 57, 70, 23);
	jPanelLocalDeImpressao.add(radioButtonCopa3);

	radioButtonBar = new JRadioButton("Bar");
	radioButtonBar.setBounds(121, 57, 50, 23);
	jPanelLocalDeImpressao.add(radioButtonBar);

	radioButtonCopa1 = new JRadioButton("Copa 1");
	radioButtonCopa1.setBounds(121, 31, 70, 23);
	jPanelLocalDeImpressao.add(radioButtonCopa1);

	radioButtonNenhum = new JRadioButton("Nenhum");
	radioButtonNenhum.setBounds(193, 57, 70, 23);
	jPanelLocalDeImpressao.add(radioButtonNenhum);

	ButtonGroup groupLocalDeImpressao = new ButtonGroup();
	groupLocalDeImpressao.add(radioButtonCozinha);
	groupLocalDeImpressao.add(radioButtonCopa2);
	groupLocalDeImpressao.add(radioButtonCopa3);
	groupLocalDeImpressao.add(radioButtonBar);
	groupLocalDeImpressao.add(radioButtonCopa1);
	groupLocalDeImpressao.add(radioButtonNenhum);

	JPanel panel_6 = new JPanel();
	panel_6.setBorder(new TitledBorder(null, "Pre\u00E7os", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel_6.setBounds(10, 124, 213, 129);
	panel_2.add(panel_6);
	panel_6.setLayout(null);

	JLabel lblCusto = new JLabel("Custo:");
	lblCusto.setBounds(10, 23, 46, 14);
	panel_6.add(lblCusto);

	JLabel lblVena = new JLabel("Venda:");
	lblVena.setBounds(10, 73, 46, 14);
	panel_6.add(lblVena);

	formattedTextFieldGeralPrecoCusto = new DecimalFormattedField(DecimalFormattedField.REAL);
	formattedTextFieldGeralPrecoCusto.setBounds(10, 37, 71, 23);
	panel_6.add(formattedTextFieldGeralPrecoCusto);

	formattedTextFieldGeralPrecoVenda = new DecimalFormattedField(DecimalFormattedField.REAL);
	formattedTextFieldGeralPrecoVenda.setBounds(10, 87, 71, 23);
	panel_6.add(formattedTextFieldGeralPrecoVenda);

	JLabel lblUnidadeDeCompra = new JLabel("Unidade de Compra:");
	lblUnidadeDeCompra.setBounds(91, 23, 126, 14);
	panel_6.add(lblUnidadeDeCompra);

	JComboBox comboBoxUnidadeDeCompra = new JComboBox();
	comboBoxUnidadeDeCompra.setBounds(91, 37, 106, 23);
	panel_6.add(comboBoxUnidadeDeCompra);

	ArrayList<UnidadeModel> listaDeUnidades = new Unidade().obterTodos(UnidadeModel.class);
	for (UnidadeModel unidadeModel : listaDeUnidades) {
	    comboBoxUnidadeDeCompra.addItem(new ComboItem(unidadeModel.getDescricao(), unidadeModel.getId().toString()));
	}

	JLabel lblUnidadeDeVenda = new JLabel("Unidade de Venda:");
	lblUnidadeDeVenda.setBounds(91, 73, 106, 14);
	panel_6.add(lblUnidadeDeVenda);

	JComboBox comboBoxUnidadeDeVenda = new JComboBox();
	comboBoxUnidadeDeVenda.setBounds(91, 87, 106, 23);
	panel_6.add(comboBoxUnidadeDeVenda);

	JPanel panelTributo = new JPanel();
	tabbedPane.addTab("Tributos", null, panelTributo, null);
	panelTributo.setLayout(null);

	JLabel lblClasificaoIcms = new JLabel("Clasificação ICMS:");
	lblClasificaoIcms.setBounds(10, 11, 132, 14);
	panelTributo.add(lblClasificaoIcms);

	JButton btnNewButton = new JButton("New button");
	btnNewButton.setBounds(117, 26, 25, 24);
	panelTributo.add(btnNewButton);

	JFormattedTextField formattedTextField = new JFormattedTextField();
	formattedTextField.setBounds(10, 26, 103, 23);
	panelTributo.add(formattedTextField);

	textField = new JTextField();
	textField.setBounds(152, 26, 247, 24);
	panelTributo.add(textField);
	textField.setColumns(10);

	JLabel lblModalidadeBc = new JLabel("Modalidade BC:");
	lblModalidadeBc.setBounds(409, 11, 137, 14);
	panelTributo.add(lblModalidadeBc);

	JSeparator separator = new JSeparator();
	separator.setBounds(10, 186, 715, 2);
	panelTributo.add(separator);

	JComboBox comboBox_1 = new JComboBox();
	comboBox_1.setBounds(409, 26, 137, 23);
	panelTributo.add(comboBox_1);

	JLabel lblAliquota = new JLabel("Aliquota %:");
	lblAliquota.setBounds(556, 11, 70, 14);
	panelTributo.add(lblAliquota);

	JFormattedTextField formattedTextField_1 = new JFormattedTextField();
	formattedTextField_1.setBounds(556, 26, 70, 23);
	panelTributo.add(formattedTextField_1);

	JLabel lblNewLabel = new JLabel("Reducao BC%:");
	lblNewLabel.setBounds(636, 11, 89, 14);
	panelTributo.add(lblNewLabel);

	JFormattedTextField formattedTextField_2 = new JFormattedTextField();
	formattedTextField_2.setBounds(636, 26, 89, 23);
	panelTributo.add(formattedTextField_2);

	JLabel lblModalidadeBcSt = new JLabel("Modalidade BC ST:");
	lblModalidadeBcSt.setBounds(10, 67, 132, 14);
	panelTributo.add(lblModalidadeBcSt);

	JComboBox comboBox_2 = new JComboBox();
	comboBox_2.setBounds(10, 83, 171, 23);
	panelTributo.add(comboBox_2);

	JComboBox comboBox_3 = new JComboBox();
	comboBox_3.setBounds(636, 83, 89, 23);
	panelTributo.add(comboBox_3);

	JLabel lblUf = new JLabel("UF:");
	lblUf.setBounds(636, 67, 46, 14);
	panelTributo.add(lblUf);

	JFormattedTextField formattedTextField_3 = new JFormattedTextField();
	formattedTextField_3.setBounds(191, 83, 100, 23);
	panelTributo.add(formattedTextField_3);

	JFormattedTextField formattedTextField_4 = new JFormattedTextField();
	formattedTextField_4.setBounds(301, 83, 100, 23);
	panelTributo.add(formattedTextField_4);

	JFormattedTextField formattedTextField_5 = new JFormattedTextField();
	formattedTextField_5.setBounds(409, 83, 100, 23);
	panelTributo.add(formattedTextField_5);

	JFormattedTextField formattedTextField_6 = new JFormattedTextField();
	formattedTextField_6.setBounds(526, 83, 100, 23);
	panelTributo.add(formattedTextField_6);

	JLabel lblMvaSt = new JLabel("MVA ST (%):");
	lblMvaSt.setBounds(191, 67, 85, 14);
	panelTributo.add(lblMvaSt);

	JLabel lblReduoBcSt = new JLabel("Redução BC ST (%):");
	lblReduoBcSt.setBounds(301, 67, 122, 14);
	panelTributo.add(lblReduoBcSt);

	JLabel lblNewLabel_1 = new JLabel("BC Oper. Propria (%):");
	lblNewLabel_1.setBounds(504, 67, 122, 14);
	panelTributo.add(lblNewLabel_1);

	JLabel lblNewLabel_2 = new JLabel("Aliquita ST (%):");
	lblNewLabel_2.setBounds(409, 67, 100, 14);
	panelTributo.add(lblNewLabel_2);

	JLabel lblClasificaoCsosn = new JLabel("Clasificação CSOSN:");
	lblClasificaoCsosn.setBounds(10, 136, 132, 14);
	panelTributo.add(lblClasificaoCsosn);

	JFormattedTextField formattedTextField_7 = new JFormattedTextField();
	formattedTextField_7.setBounds(10, 151, 103, 23);
	panelTributo.add(formattedTextField_7);

	JButton button = new JButton("New button");
	button.setBounds(117, 151, 25, 24);
	panelTributo.add(button);

	textField_1 = new JTextField();
	textField_1.setColumns(10);
	textField_1.setBounds(152, 151, 247, 24);
	panelTributo.add(textField_1);

	JLabel lblCrditoSn = new JLabel("Crédito SN ($):");
	lblCrditoSn.setBounds(517, 136, 100, 14);
	panelTributo.add(lblCrditoSn);

	JFormattedTextField formattedTextField_8 = new JFormattedTextField();
	formattedTextField_8.setBounds(517, 151, 100, 23);
	panelTributo.add(formattedTextField_8);

	JLabel lblCfop = new JLabel("CFOP: ");
	lblCfop.setBounds(636, 136, 56, 14);
	panelTributo.add(lblCfop);

	JLabel lblAliquotaSn = new JLabel("Aliquota SN (%):");
	lblAliquotaSn.setBounds(409, 136, 122, 14);
	panelTributo.add(lblAliquotaSn);

	JFormattedTextField formattedTextField_10 = new JFormattedTextField();
	formattedTextField_10.setBounds(409, 151, 100, 23);
	panelTributo.add(formattedTextField_10);

	JFormattedTextField formattedTextField_9 = new JFormattedTextField();
	formattedTextField_9.setBounds(636, 151, 56, 23);
	panelTributo.add(formattedTextField_9);

	JButton button_1 = new JButton("New button");
	button_1.setBounds(700, 151, 25, 24);
	panelTributo.add(button_1);

	JSeparator separator_1 = new JSeparator();
	separator_1.setBounds(10, 244, 505, 2);
	panelTributo.add(separator_1);

	JLabel lblClasificaoIpi = new JLabel("Clasificação IPI:");
	lblClasificaoIpi.setBounds(10, 194, 132, 14);
	panelTributo.add(lblClasificaoIpi);

	JFormattedTextField formattedTextField_11 = new JFormattedTextField();
	formattedTextField_11.setBounds(10, 209, 103, 23);
	panelTributo.add(formattedTextField_11);

	JButton button_2 = new JButton("New button");
	button_2.setBounds(117, 209, 25, 24);
	panelTributo.add(button_2);

	textField_2 = new JTextField();
	textField_2.setColumns(10);
	textField_2.setBounds(152, 209, 247, 24);
	panelTributo.add(textField_2);

	JLabel label_1 = new JLabel("Aliquota SN (%):");
	label_1.setBounds(409, 194, 122, 14);
	panelTributo.add(label_1);

	JFormattedTextField formattedTextField_12 = new JFormattedTextField();
	formattedTextField_12.setBounds(409, 209, 100, 23);
	panelTributo.add(formattedTextField_12);

	JLabel lblClasificaoPis = new JLabel("Clasificação PIS:");
	lblClasificaoPis.setBounds(10, 251, 132, 14);
	panelTributo.add(lblClasificaoPis);

	JFormattedTextField formattedTextField_13 = new JFormattedTextField();
	formattedTextField_13.setBounds(10, 266, 103, 23);
	panelTributo.add(formattedTextField_13);

	JButton button_3 = new JButton("New button");
	button_3.setBounds(117, 266, 25, 24);
	panelTributo.add(button_3);

	textField_3 = new JTextField();
	textField_3.setColumns(10);
	textField_3.setBounds(152, 266, 247, 24);
	panelTributo.add(textField_3);

	JLabel label_2 = new JLabel("Aliquota SN (%):");
	label_2.setBounds(409, 251, 122, 14);
	panelTributo.add(label_2);

	JFormattedTextField formattedTextField_14 = new JFormattedTextField();
	formattedTextField_14.setBounds(409, 266, 100, 23);
	panelTributo.add(formattedTextField_14);

	JSeparator separator_2 = new JSeparator();
	separator_2.setBounds(10, 300, 499, 3);
	panelTributo.add(separator_2);

	JLabel lblClasificaoCofins = new JLabel("Clasificação  COFINS:");
	lblClasificaoCofins.setBounds(10, 308, 132, 14);
	panelTributo.add(lblClasificaoCofins);

	JFormattedTextField formattedTextField_15 = new JFormattedTextField();
	formattedTextField_15.setBounds(10, 323, 103, 23);
	panelTributo.add(formattedTextField_15);

	JButton button_4 = new JButton("New button");
	button_4.setBounds(117, 323, 25, 24);
	panelTributo.add(button_4);

	textField_4 = new JTextField();
	textField_4.setColumns(10);
	textField_4.setBounds(152, 323, 247, 24);
	panelTributo.add(textField_4);

	JLabel label_3 = new JLabel("Aliquota SN (%):");
	label_3.setBounds(409, 308, 122, 14);
	panelTributo.add(label_3);

	JFormattedTextField formattedTextField_16 = new JFormattedTextField();
	formattedTextField_16.setBounds(409, 323, 100, 23);
	panelTributo.add(formattedTextField_16);

	JSeparator separator_3 = new JSeparator();
	separator_3.setBounds(10, 357, 499, 3);
	panelTributo.add(separator_3);

	JSeparator separator_4 = new JSeparator();
	separator_4.setOrientation(SwingConstants.VERTICAL);
	separator_4.setBounds(526, 199, 5, 161);
	panelTributo.add(separator_4);

	JPanel panel_1 = new JPanel();
	panel_1.setBorder(new TitledBorder(null, "Imposto ECF", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel_1.setBounds(536, 199, 189, 142);
	panelTributo.add(panel_1);
	panel_1.setLayout(null);

	JRadioButton rdbtnIiIsento = new JRadioButton("II - Isento");
	rdbtnIiIsento.setBounds(6, 20, 109, 15);
	panel_1.add(rdbtnIiIsento);

	JRadioButton rdbtnNnNo = new JRadioButton("NN - Não Incidência");
	rdbtnNnNo.setBounds(6, 40, 143, 15);
	panel_1.add(rdbtnNnNo);

	JRadioButton rdbtnFfSubst = new JRadioButton("FF - Subst. Tributária");
	rdbtnFfSubst.setBounds(6, 60, 143, 15);
	panel_1.add(rdbtnFfSubst);

	JRadioButton rdbtnSiIsento = new JRadioButton("SI - Isento ISSQN");
	rdbtnSiIsento.setBounds(6, 80, 143, 15);
	panel_1.add(rdbtnSiIsento);

	JRadioButton rdbtnSfSubst = new JRadioButton("SF - Subst. Tributária ISSQN");
	rdbtnSfSubst.setBounds(6, 100, 177, 15);
	panel_1.add(rdbtnSfSubst);

	JRadioButton rdbtnNewRadioButton = new JRadioButton("TT - Tributado pelo ICSM");
	rdbtnNewRadioButton.setBounds(6, 120, 160, 15);
	panel_1.add(rdbtnNewRadioButton);

	JLabel lblNewLabel_3 = new JLabel("Alíquota ICMS ECF (%):");
	lblNewLabel_3.setBounds(535, 346, 132, 14);
	panelTributo.add(lblNewLabel_3);

	JFormattedTextField formattedTextField_17 = new JFormattedTextField();
	formattedTextField_17.setBounds(669, 343, 56, 23);
	panelTributo.add(formattedTextField_17);
	for (CategoriaModel grupoModel : listaDeGrupos) {
	    // comboBox.addItem(new ComboItem(grupoModel.getDescricao(),
	    // grupoModel.getId().toString()));

	}

    }

    @Override
    public ProdutoModel getModel() {
	model.setDescricao(textFieldDescricao.getText());
	if (textFieldCodigo.getText() == null || textFieldCodigo.getText().trim().length() == 0) {
	    model.setCodigo(null);
	} else {
	    model.setCodigo(Long.valueOf(textFieldCodigo.getText()));
	}
	//model.setSubGrupo(getSubGrupoSelecionado());
	model.setNcm(getNcmSelecionado());
	model.setCodigoDeBarras(textFieldGeralCodigoDeBarras.getText());
//	model.setPrecoDeCusto(formattedTextFieldGeralPrecoCusto.getDoubleValue());
//	model.setPrecoDeVenda(formattedTextFieldGeralPrecoVenda.getDoubleValue());

//	if (radioButtonBar.isSelected()) {
//	    model.setLocalDeImpressao(LocalDeImpressao.bar);
//	} else if (radioButtonCopa1.isSelected()) {
//	    model.setLocalDeImpressao(LocalDeImpressao.copa1);
//	} else if (radioButtonCopa2.isSelected()) {
//	    model.setLocalDeImpressao(LocalDeImpressao.copa2);
//	} else if (radioButtonCopa3.isSelected()) {
//	    model.setLocalDeImpressao(LocalDeImpressao.copa3);
//	} else if (radioButtonCozinha.isSelected()) {
//	    model.setLocalDeImpressao(LocalDeImpressao.cozinha);
//	} else if (radioButtonNenhum.isSelected()) {
//	    model.setLocalDeImpressao(LocalDeImpressao.bar);
//	}

	if (radioButtonEstrangeira.isSelected()) {
	//    model.setOrigemDaMercadoria(OrigemDaMercadoria.estrangeira);
	} else if (radioButtonNacional.isSelected()) {
	 //   model.setOrigemDaMercadoria(OrigemDaMercadoria.nacional);
	} else if (radioButtonEstrangeiraImp.isSelected()) {
	 //   model.setOrigemDaMercadoria(OrigemDaMercadoria.estrangeira_imp);
	}

	return model;
    }

    @Override
    public void setModel(ProdutoModel model) {

	if (model == null) {
	    model = new ProdutoModel();
	} else {

	}

	super.setModel(model);
	if (model.getCodigo() == null) {
	    textFieldCodigo.setText(null);
	} else {
	    textFieldCodigo.setText(String.valueOf(model.getCodigo()));
	}
	textFieldDescricao.setText(model.getDescricao());

//	setNcmSelecionado(model.getNcm());
	//setFornecedorSelecionado(model.getFornecedor());
	//setSubGrupoSelecionado(model.getSubGrupo());

	textFieldGeralCodigoDeBarras.setText(model.getCodigoDeBarras());
	if (model.getPrecoDeCusto() != null) {
	    formattedTextFieldGeralPrecoCusto.setText(String.valueOf(model.getPrecoDeCusto()));
	} else { 
	    formattedTextFieldGeralPrecoCusto.setText(null);
	}

	if (model.getPrecoDeVenda() != null) {

	    formattedTextFieldGeralPrecoVenda.setText(String.valueOf(model.getPrecoDeVenda()));
	} else  {
	    formattedTextFieldGeralPrecoCusto.setText(null);
	}

//	if (model.getLocalDeImpressao() == LocalDeImpressao.bar) {
//	    radioButtonBar.setSelected(true);
//	} else if (model.getLocalDeImpressao() == LocalDeImpressao.copa1) {
//	    radioButtonCopa1.setSelected(true);
//	} else if (model.getLocalDeImpressao() == LocalDeImpressao.copa2) {
//	    radioButtonCopa2.setSelected(true);
//	} else if (model.getLocalDeImpressao() == LocalDeImpressao.copa3) {
//	    radioButtonCopa3.setSelected(true);
//	} else if (model.getLocalDeImpressao() == LocalDeImpressao.cozinha) {
//	    radioButtonCozinha.setSelected(true);
//	} else if (model.getLocalDeImpressao() == LocalDeImpressao.nenhum) {
//	    radioButtonNenhum.setSelected(true);
//	} else {
//	    radioButtonNenhum.setSelected(true);
//	}

//	if (model.getOrigemDaMercadoria() == OrigemDaMercadoria.estrangeira) {
//	    radioButtonEstrangeira.setSelected(true);
//	} else if (model.getOrigemDaMercadoria() == OrigemDaMercadoria.nacional) {
//	    radioButtonNacional.setSelected(true);
//	} else if (model.getOrigemDaMercadoria() == OrigemDaMercadoria.estrangeira_imp) {
//	    radioButtonEstrangeiraImp.setSelected(true);
//	} else {
//	    radioButtonNacional.setSelected(true);
//	}
    }

    private int getPosicaoNaLista(CategoriaModel model) {
	int posicao = -1;
	int count = 1;
	if (model != null) {
	    for (CategoriaModel grupoModel : listaDeGrupos) {
		if (grupoModel.getId().toString().equals(model.getId().toString())) {

		    posicao = count;
		    break;
		}
		count++;
	    }
	}
	return posicao;
    }

    public ArrayList<CategoriaModel> getListaDeGrupos() {
	return listaDeGrupos;
    }

    public void setListaDeGrupos(ArrayList<CategoriaModel> listaDeGrupos) {
	this.listaDeGrupos = listaDeGrupos;
    }

    public SubGrupoModel getSubGrupoSelecionado() {
	return subGrupoSelecionado;
    }

    public void setSubGrupoSelecionado(SubGrupoModel subGrupoSelecionado) {
	this.subGrupoSelecionado = subGrupoSelecionado;

	if (subGrupoSelecionado != null) {

	    textFieldGeralGrupo.setText(this.subGrupoSelecionado.getGrupo().getDescricao());
	    textFieldGeralSubGrupo.setText(this.subGrupoSelecionado.getDescricao());
	} else {
	    textFieldGeralGrupo.setText(null);
	    textFieldGeralSubGrupo.setText(null);
	}
    }

    public NCMModel getNcmSelecionado() {
	return ncmSelecionado;
    }

//    public void setNcmSelecionado(NCMModel ncmSelecionado) {
//	this.ncmSelecionado = ncmSelecionado;
//	if (ncmSelecionado != null) {
//	    textFieldGeralNCM.setText(ncmSelecionado.getNcm());
//	} else {
//	    textFieldGeralNCM.setText(null);
//	}
//    }

    public FornecedorModel getFornecedorSelecionado() {
	return fornecedorSelecionado;
    }

    public void setFornecedorSelecionado(FornecedorModel fornecedorSelecionado) {
	this.fornecedorSelecionado = fornecedorSelecionado;
	if (fornecedorSelecionado != null) {
	    textFieldGeralFornecedor.setText(this.fornecedorSelecionado.getRazaoSocial());
	} else {
	    textFieldGeralFornecedor.setText(null);
	}
    }

}
