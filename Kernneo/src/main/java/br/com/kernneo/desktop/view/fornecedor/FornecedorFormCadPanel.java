package br.com.kernneo.desktop.view.fornecedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.desktop.view.cidade.CidadeFiltroFrame;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class FornecedorFormCadPanel extends GenericFormCadPanel<FornecedorModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldFornRazao;
    private JTextField textFieldFornAbreviatura;
    private JTextField textFieldFornFantasia;
    private JTextField textFieldEnderecoRua;
    private JTextField textFieldEnderecoNumero;
    private JTextField textFieldEnderecoBairro;
    private JTextField textFieldEnderecoFone1;
    private JTextField textFieldEnderecoFone2;
    private JTextField textFieldFax;
    private JTextField textFieldCep;
    private JTextField textFieldCnpj;
    private JTextField textFieldIE;
    private JTextField textFieldInternet;
    private JTextField textFieldEmail;
    private JTextField textFieldContatoNome1;
    private JTextField textFieldContatoCargo1;
    private JTextField textFieldContatoNome2;
    private JTextField textFieldContatoCargo2;
    private JComboBox comboBoxFornTipo;
    private JTextField textFieldCPF;
    private JTextField textFieldRG;
    private JTextField textFieldCidade;

    private CidadeModel cidadeSelecionada;

    public FornecedorFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(758, 447);
	setLayout(null);
	JLabel labelRazao = new JLabel("Razão Social:");
	labelRazao.setBounds(22, 11, 82, 14);
	add(labelRazao);

	textFieldFornRazao = new JTextField();
	textFieldFornRazao.setBounds(22, 25, 410, 20);
	add(textFieldFornRazao);
	textFieldFornRazao.setColumns(10);

	JLabel labelTipo = new JLabel("Tipo:");
	labelTipo.setBounds(442, 11, 46, 14);
	add(labelTipo);

	comboBoxFornTipo = new JComboBox();
	comboBoxFornTipo.setBounds(442, 25, 97, 20);
	add(comboBoxFornTipo);
	comboBoxFornTipo.setModel(new DefaultComboBoxModel(new String[] { "Fisica", "Juridica" }));
	comboBoxFornTipo.setSelectedIndex(0);

	JLabel lblAbreviatura = new JLabel("Abreviatura:");
	lblAbreviatura.setBounds(549, 11, 72, 14);
	add(lblAbreviatura);

	textFieldFornAbreviatura = new JTextField();
	textFieldFornAbreviatura.setBounds(549, 25, 184, 20);
	add(textFieldFornAbreviatura);
	textFieldFornAbreviatura.setColumns(10);

	JLabel labelNomeFantasia = new JLabel("Nome de Fantasia:");
	labelNomeFantasia.setBounds(22, 57, 121, 14);
	add(labelNomeFantasia);
	// setTextFieldPK(textFieldFornCod);

	textFieldFornFantasia = new JTextField();
	textFieldFornFantasia.setBounds(22, 75, 711, 20);
	add(textFieldFornFantasia);
	textFieldFornFantasia.setColumns(10);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBounds(22, 106, 721, 323);
	add(tabbedPane);

	JPanel panelInfoGerais = new JPanel();
	panelInfoGerais.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
	tabbedPane.addTab("Informações Gerais", null, panelInfoGerais, null);
	panelInfoGerais.setLayout(null);

	Box verticalBox = Box.createVerticalBox();
	verticalBox.setBounds(119, 161, 193, -100);
	panelInfoGerais.add(verticalBox);

	JLabel lblEndereo = new JLabel("Endereço:");
	lblEndereo.setBounds(10, 11, 86, 14);
	panelInfoGerais.add(lblEndereo);

	textFieldEnderecoRua = new JTextField();
	textFieldEnderecoRua.setBounds(10, 27, 395, 22);
	panelInfoGerais.add(textFieldEnderecoRua);
	textFieldEnderecoRua.setColumns(10);
	textFieldEnderecoRua.setSize(395, 20);

	JLabel lblNumero = new JLabel("Numero:");
	lblNumero.setBounds(417, 11, 84, 14);
	panelInfoGerais.add(lblNumero);

	textFieldEnderecoNumero = new JTextField();
	textFieldEnderecoNumero.setBounds(415, 27, 86, 20);
	panelInfoGerais.add(textFieldEnderecoNumero);
	textFieldEnderecoNumero.setColumns(10);

	JLabel lblBairro = new JLabel("Bairro:");
	lblBairro.setBounds(508, 11, 46, 14);
	panelInfoGerais.add(lblBairro);

	textFieldEnderecoBairro = new JTextField();
	textFieldEnderecoBairro.setBounds(508, 27, 198, 20);
	panelInfoGerais.add(textFieldEnderecoBairro);
	textFieldEnderecoBairro.setColumns(10);

	JLabel lblFone = new JLabel("Fone (2):");
	lblFone.setBounds(614, 58, 80, 14);
	panelInfoGerais.add(lblFone);

	JLabel lblFone_1 = new JLabel("Fone (1):");
	lblFone_1.setBounds(508, 58, 80, 14);
	panelInfoGerais.add(lblFone_1);

	JLabel lblFax = new JLabel("Fax:");
	lblFax.setBounds(415, 58, 46, 14);
	panelInfoGerais.add(lblFax);

	JLabel lblCep = new JLabel("CEP:");
	lblCep.setBounds(319, 59, 46, 14);
	panelInfoGerais.add(lblCep);

	JLabel lblCnpj = new JLabel("CNPJ:");
	lblCnpj.setBounds(10, 114, 46, 14);
	panelInfoGerais.add(lblCnpj);

	JLabel lblInscrioEstadual = new JLabel("Inscrição Estadual:");
	lblInscrioEstadual.setBounds(223, 114, 116, 14);
	panelInfoGerais.add(lblInscrioEstadual);

	JLabel lblInternet = new JLabel("Site:");
	lblInternet.setBounds(10, 165, 46, 14);
	panelInfoGerais.add(lblInternet);

	JLabel lblEmail = new JLabel("Email:");
	lblEmail.setBounds(370, 165, 46, 14);
	panelInfoGerais.add(lblEmail);

	JPanel panelContato = new JPanel();
	panelContato.setBorder(new TitledBorder(null, "Pessoas para Contato", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panelContato.setBounds(10, 213, 696, 71);
	panelInfoGerais.add(panelContato);
	panelContato.setLayout(null);

	JLabel lblNome = new JLabel("Nome (1):");
	lblNome.setBounds(10, 21, 83, 14);
	panelContato.add(lblNome);

	JLabel lblCargo = new JLabel("Cargo (1):");
	lblCargo.setBounds(210, 21, 62, 14);
	panelContato.add(lblCargo);

	JLabel lblNome_1 = new JLabel("Nome (2):");
	lblNome_1.setBounds(345, 21, 69, 14);
	panelContato.add(lblNome_1);

	JLabel lblCargo_1 = new JLabel("Cargo (2):");
	lblCargo_1.setBounds(541, 21, 77, 14);
	panelContato.add(lblCargo_1);

	textFieldContatoNome1 = new JTextField();
	textFieldContatoNome1.setBounds(10, 40, 190, 20);
	panelContato.add(textFieldContatoNome1);
	textFieldContatoNome1.setColumns(10);

	textFieldContatoCargo1 = new JTextField();
	textFieldContatoCargo1.setBounds(210, 40, 121, 20);
	panelContato.add(textFieldContatoCargo1);
	textFieldContatoCargo1.setColumns(10);

	textFieldContatoNome2 = new JTextField();
	textFieldContatoNome2.setBounds(341, 40, 190, 20);
	panelContato.add(textFieldContatoNome2);
	textFieldContatoNome2.setColumns(10);

	textFieldContatoCargo2 = new JTextField();
	textFieldContatoCargo2.setBounds(541, 40, 145, 20);
	panelContato.add(textFieldContatoCargo2);
	textFieldContatoCargo2.setColumns(10);

	textFieldEnderecoFone2 = new JTextField();
	textFieldEnderecoFone2.setBounds(614, 74, 92, 20);
	panelInfoGerais.add(textFieldEnderecoFone2);
	textFieldEnderecoFone2.setColumns(10);

	textFieldEnderecoFone1 = new JTextField();
	textFieldEnderecoFone1.setBounds(508, 74, 96, 20);
	panelInfoGerais.add(textFieldEnderecoFone1);
	textFieldEnderecoFone1.setColumns(10);

	textFieldFax = new JTextField();
	textFieldFax.setBounds(415, 73, 86, 20);
	panelInfoGerais.add(textFieldFax);
	textFieldFax.setColumns(10);

	textFieldCep = new JTextField();
	textFieldCep.setBounds(319, 74, 86, 20);
	panelInfoGerais.add(textFieldCep);
	textFieldCep.setColumns(10);

	textFieldCnpj = new JTextField();
	textFieldCnpj.setBounds(10, 130, 203, 20);
	panelInfoGerais.add(textFieldCnpj);
	textFieldCnpj.setColumns(10);

	textFieldIE = new JTextField();
	textFieldIE.setBounds(223, 130, 137, 20);
	panelInfoGerais.add(textFieldIE);
	textFieldIE.setColumns(10);

	textFieldInternet = new JTextField();
	textFieldInternet.setBounds(10, 182, 350, 20);
	panelInfoGerais.add(textFieldInternet);
	textFieldInternet.setColumns(10);

	textFieldEmail = new JTextField();
	textFieldEmail.setBounds(370, 182, 336, 20);
	panelInfoGerais.add(textFieldEmail);
	textFieldEmail.setColumns(10);

	JLabel lblNewLabel_1 = new JLabel("CPF:");
	lblNewLabel_1.setBounds(370, 115, 80, 14);
	panelInfoGerais.add(lblNewLabel_1);

	textFieldCPF = new JTextField();
	textFieldCPF.setBounds(370, 131, 131, 20);
	panelInfoGerais.add(textFieldCPF);
	textFieldCPF.setColumns(10);

	JLabel lblRg = new JLabel("RG:");
	lblRg.setBounds(508, 115, 46, 14);
	panelInfoGerais.add(lblRg);

	textFieldRG = new JTextField();
	textFieldRG.setBounds(508, 131, 198, 20);
	panelInfoGerais.add(textFieldRG);
	textFieldRG.setColumns(10);

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
    public FornecedorModel getModel() {

//	model.setAbreviatura(textFieldFornAbreviatura.getText());
//	model.setFantasia(textFieldFornFantasia.getText());
//	model.setTipo(comboBoxFornTipo.getSelectedIndex());
//	model.setRazaoSocial(textFieldFornRazao.getText());
//
//	model.setEndereco(textFieldEnderecoRua.getText());
//	model.setNumero(textFieldEnderecoNumero.getText());
//	
//	model.setCnpj(textFieldCnpj.getText());
//	model.setIe(textFieldIE.getText());
//	model.setCpf(textFieldCPF.getText());
//	model.setRg(textFieldRG.getText());
//	model.setSite(textFieldInternet.getText());
//	model.setEmail(textFieldEmail.getText());
//
//	model.setFax(textFieldFax.getText());
//	model.setCep(textFieldCep.getText());
//
//	model.setFone1(textFieldEnderecoFone1.getText());
//	model.setFone2(textFieldEnderecoFone2.getText());
//	model.setContatoNome1(textFieldContatoNome1.getText());
//	model.setContatoCargo1(textFieldContatoCargo1.getText());
//	model.setContatoNome2(textFieldContatoNome2.getText());
//	model.setContatoCargo2(textFieldContatoCargo2.getText());
//	model.setCidade(cidadeSelecionada);

	return model;
    }

    @Override
    public void setModel(FornecedorModel model) {

	if (model == null) {
	    model = new FornecedorModel();
	}

	super.setModel(model);
//	textFieldFornAbreviatura.setText(model.getAbreviatura());
//	textFieldFornFantasia.setText(model.getFantasia());
//	comboBoxFornTipo.setSelectedIndex(model.getTipo());
//	textFieldFornRazao.setText(model.getRazaoSocial());
//	textFieldEnderecoRua.setText(model.getEndereco());
//	textFieldEnderecoNumero.setText(model.getNumero());
//	textFieldEnderecoBairro.setText("");
//	textFieldCnpj.setText(model.getCnpj());
//	textFieldIE.setText(model.getIe());
//	textFieldCPF.setText(model.getCpf());
//	textFieldRG.setText(model.getRg());
//	textFieldInternet.setText(model.getSite());
//	textFieldEmail.setText(model.getEmail());
//	textFieldFax.setText(model.getFax());
//	textFieldCep.setText(model.getCep());
//	textFieldEnderecoFone1.setText(model.getFone1());
//	textFieldEnderecoFone2.setText(model.getFone2());
//	textFieldContatoCargo1.setText(model.getContatoCargo1());
//	textFieldContatoCargo2.setText(model.getContatoCargo2());
//	textFieldContatoNome1.setText(model.getContatoNome1());
//	textFieldContatoNome2.setText(model.getContatoNome2());
//	setCidadeSelecionada(model.getCidade());

	
    }
}
