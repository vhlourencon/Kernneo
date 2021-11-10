package br.com.kernneo.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicDesktopPaneUI;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.exception.UnidadeException;
import br.com.kernneo.desktop.view.bairro.BairroListInternalFrame;
import br.com.kernneo.desktop.view.cargo.CargoListInternalFrame;
import br.com.kernneo.desktop.view.cartao.CartaoListInternalFrame;
import br.com.kernneo.desktop.view.cfop.CFOPListInternalFrame;
import br.com.kernneo.desktop.view.cliente.ClienteListInternalFrame;
import br.com.kernneo.desktop.view.contabancaria.ContaBancariaListInternalFrame;
import br.com.kernneo.desktop.view.convenio.ConvenioListIternalFrame;
import br.com.kernneo.desktop.view.departamento.DepartamentoListInternalFrame;
import br.com.kernneo.desktop.view.empresa.EmpresaFormInternalFrame;
import br.com.kernneo.desktop.view.fornecedor.FornecedorListInternalFrame;
import br.com.kernneo.desktop.view.funcionario.FuncionarioListInternalFrame;
import br.com.kernneo.desktop.view.grupo.GrupoListInternalFrame;
import br.com.kernneo.desktop.view.observacao.ObservacaoListInternalFrame;
import br.com.kernneo.desktop.view.planocontas.PlanoContasListInternalFrame;
import br.com.kernneo.desktop.view.produto.ProdutoListInternalFrame;
import br.com.kernneo.desktop.view.subgrupo.SubGrupoListInternalFrame;
import br.com.kernneo.desktop.view.ticket.TicketListInternalFrame;
import br.com.kernneo.desktop.view.transportadora.TransportadoraListInternalFrame;
import br.com.kernneo.desktop.view.widget.SampleDesktopMgr;
import br.com.kernneo.server.ConnectFactory;
import javafx.geometry.Insets;

public class PrincipalDesktop extends JFrame {

	public static void main(String[] args) {

		final PrincipalDesktop principal = new PrincipalDesktop();
		principal.setVisible(true);
		
		principal.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				principal.setVisible(false);
				principal.dispose();
				
				System.exit(0);
			}
		});
	}

	private static JDesktopPane jDesktopPane;

	public PrincipalDesktop() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// screenSize.width -= 42;
		screenSize.height -= 40;
		setSize(screenSize);
		setLocation(0, 0);

		jDesktopPane = new JDesktopPane();
		jDesktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		jDesktopPane.setDesktopManager(new SampleDesktopMgr());
		jDesktopPane.setUI(new BasicDesktopPaneUI());
		jDesktopPane.putClientProperty("JDesktopPane.dragMode", "outline");
		
	
		setContentPane(jDesktopPane);

		JMenuBar jMenuBar = new JMenuBar();

		// INÍCIO do Cadastro de Menus

		JMenu jMenuCadastro = new JMenu("Cadastro");
		jMenuBar.add(jMenuCadastro);

		JMenu jMenuEstoque = new JMenu("Estoque");
		jMenuBar.add(jMenuEstoque);

		JMenu jMenuFinanceiro = new JMenu("Financeiro");
		jMenuBar.add(jMenuFinanceiro);

		JMenu jMenuMovimentoFiscal = new JMenu("Movimentos Fiscais");
		jMenuBar.add(jMenuMovimentoFiscal);

		JMenu jMenuRelatorio = new JMenu("Relatórios");
		jMenuBar.add(jMenuRelatorio);

		JMenu jMenuConfiguracao = new JMenu("Configurações");
		jMenuBar.add(jMenuConfiguracao);

		JMenu jMenuSistema = new JMenu("Sistema");
		jMenuBar.add(jMenuSistema);

		// FIM do Cadastro de Menus

		// INÍCIO Submenus do Menu [Cadastro]

		JMenuItem jMenuItemCliente = new JMenuItem("Clientes");
		jMenuCadastro.add(jMenuItemCliente);

		JMenuItem jMenuItemCadastroDepartamentos = new JMenuItem("Departamentos");
		JMenuItem jMenuItemCadastroSair = new JMenuItem("Sair");

		jMenuCadastro.add(jMenuItemCadastroDepartamentos);

		JMenuItem jMenuItemFornecedor = new JMenuItem("Fornecedores");
		jMenuCadastro.add(jMenuItemFornecedor);

		JMenuItem jMenuItemFuncionarios = new JMenuItem("Funcionários");
		jMenuCadastro.add(jMenuItemFuncionarios);

		JMenuItem jMenuItemCargos = new JMenuItem("Cargos");
		jMenuCadastro.add(jMenuItemCargos);

		JMenuItem jMenuItemConvenio = new JMenuItem("Convênios");
		jMenuCadastro.add(jMenuItemConvenio);

		JMenuItem jMenuItemTransportadora = new JMenuItem("Transportadora");
		jMenuCadastro.add(jMenuItemTransportadora);

		JMenuItem jMenuItemBairro = new JMenuItem("Bairros");
		jMenuCadastro.add(jMenuItemBairro);

		JSeparator separator = new JSeparator();
		jMenuCadastro.add(separator);

		JMenuItem jMenuItemGrupo = new JMenuItem("Grupo");
		jMenuCadastro.add(jMenuItemGrupo);

		JMenuItem jMenuItemSubGrupo = new JMenuItem("SubGrupo");
		jMenuCadastro.add(jMenuItemSubGrupo);

		JMenuItem jMenuItemProduto = new JMenuItem("Produtos");
		jMenuCadastro.add(jMenuItemProduto);

		JSeparator separator_1 = new JSeparator();
		jMenuCadastro.add(separator_1);

		JMenuItem jMenuItemTicket = new JMenuItem("Ticket");
		jMenuCadastro.add(jMenuItemTicket);

		JMenuItem jMenuItemCartao = new JMenuItem("Cartão");
		jMenuCadastro.add(jMenuItemCartao);

		JMenuItem jMenuItemContaBancaria = new JMenuItem("Conta Bancária");
		jMenuCadastro.add(jMenuItemContaBancaria);

		JSeparator separator_2 = new JSeparator();
		jMenuCadastro.add(separator_2);

		JMenuItem jMenuItemPlanoContas = new JMenuItem("Plano de Contas");
		jMenuCadastro.add(jMenuItemPlanoContas);

		JMenuItem jMenuItemObservacao = new JMenuItem("Observações");
		jMenuCadastro.add(jMenuItemObservacao);

		JSeparator separator_3 = new JSeparator();
		jMenuCadastro.add(separator_3);

		JMenuItem jMenuItemCFOP = new JMenuItem("CFOP'S");
		jMenuCadastro.add(jMenuItemCFOP);
		jMenuCadastro.add(jMenuItemCadastroSair);
		jMenuCadastro.add(jMenuItemCadastroSair);

		// FIM Submenus do Menu [Cadastro]

		// INÍCIO Submenus do Menu [Estoque]

		JMenuItem jMenuItemLancamentoCompras = new JMenuItem("Lançamento de Compras");
		jMenuEstoque.add(jMenuItemLancamentoCompras);

		JMenuItem jMenuItemAlterarPreco = new JMenuItem("Alteração de Preços");
		jMenuEstoque.add(jMenuItemAlterarPreco);

		JMenuItem jMenuItemManterEstoque = new JMenuItem("Manutenção de Estoque");
		jMenuEstoque.add(jMenuItemManterEstoque);

		JMenuItem jMenuItemEntradaProduto = new JMenuItem("Entrada de Produtos");
		jMenuEstoque.add(jMenuItemEntradaProduto);

		JMenuItem jMenuItemSaidaProduto = new JMenuItem("Saída de Produtos");
		jMenuEstoque.add(jMenuItemSaidaProduto);

		JMenuItem jMenuItemProducao = new JMenuItem("Produção");
		jMenuEstoque.add(jMenuItemProducao);

		JMenuItem jMenuItemLancamentoPerda = new JMenuItem("Lançamento de Perdas");
		jMenuEstoque.add(jMenuItemLancamentoPerda);

		JMenuItem jMenuItemTransferenciaEstoque = new JMenuItem("Transferência de Estoque");
		jMenuEstoque.add(jMenuItemTransferenciaEstoque);

		// FIM Submenus do Menu [Estoque]

		// INÍCIO Submenus do Menu [Financeiro]

		JMenuItem jMenuItemMovimentoCaixa = new JMenuItem("Movimento de Caixa");
		jMenuFinanceiro.add(jMenuItemMovimentoCaixa);

		JMenuItem jMenuItemEspelhoCaixa = new JMenuItem("Espelho do Caixa");
		jMenuFinanceiro.add(jMenuItemEspelhoCaixa);

		JMenuItem jMenuItemContasPagar = new JMenuItem("Contas a Pagar");
		jMenuFinanceiro.add(jMenuItemContasPagar);

		JMenuItem jMenuItemContasReceber = new JMenuItem("Contas a Receber");
		jMenuFinanceiro.add(jMenuItemContasReceber);

		// FIM Submenus do Menu [Financeiro]

		// INÍCIO Submenus do Menu [Movimentos Fiscais]

		JMenu jMenuManterDocumentoFiscal = new JMenu("Manutenção de Documentos Fiscais");
		jMenuMovimentoFiscal.add(jMenuManterDocumentoFiscal);

		JMenuItem jMenuItemNFSe = new JMenuItem("NFC-e");
		jMenuManterDocumentoFiscal.add(jMenuItemNFSe);

		JMenuItem jMenuItemNFe = new JMenuItem("NF-e");
		jMenuManterDocumentoFiscal.add(jMenuItemNFe);

		JMenu jMenuEmitirDocumentoFiscal = new JMenu("Emissão de Documentos Fiscais");
		jMenuMovimentoFiscal.add(jMenuEmitirDocumentoFiscal);

		JMenuItem jMenuItemSPEDFiscal = new JMenuItem("SPED Fiscal");
		jMenuEmitirDocumentoFiscal.add(jMenuItemSPEDFiscal);

		// FIM Submenus do Menu [Movimentos Fiscais]

		// INÍCIO Submenus do Menu [Relatórios]

		JMenu jMenuRelatorioCadastro = new JMenu("Relatórios de Cadastro");
		jMenuRelatorio.add(jMenuRelatorioCadastro);

		JMenuItem jMenuItemTeste = new JMenuItem("Teste");
		jMenuRelatorioCadastro.add(jMenuItemTeste);

		JMenu jMenuRelatorioCompra = new JMenu("Relatórios de Compras");
		jMenuRelatorio.add(jMenuRelatorioCompra);

		JMenuItem jMenuItemTeste2 = new JMenuItem("Teste2");
		jMenuRelatorioCompra.add(jMenuItemTeste2);

		JMenu jMenuRelatorioComissao = new JMenu("Relatórios de Comissões");
		jMenuRelatorio.add(jMenuRelatorioComissao);

		JMenuItem jMenuItemTeste3 = new JMenuItem("Teste3");
		jMenuRelatorioComissao.add(jMenuItemTeste3);

		JMenu jMenuRelatorioEstoque = new JMenu("Relatórios de Estoque");
		jMenuRelatorio.add(jMenuRelatorioEstoque);

		JMenuItem jMenuItemTeste4 = new JMenuItem("Teste4");
		jMenuRelatorioEstoque.add(jMenuItemTeste4);

		JMenu jMenuRelatorioPenduraConvenio = new JMenu("Relatórios de Pendura/Convênio");
		jMenuRelatorio.add(jMenuRelatorioPenduraConvenio);

		JMenuItem jMenuItemTeste5 = new JMenuItem("Teste5");
		jMenuRelatorioPenduraConvenio.add(jMenuItemTeste5);

		JMenu jMenuRelatorioFinanceiro = new JMenu("Relatórios Financeiros");
		jMenuRelatorio.add(jMenuRelatorioFinanceiro);

		JMenuItem jMenuItemTeste6 = new JMenuItem("Teste6");
		jMenuRelatorioFinanceiro.add(jMenuItemTeste6);

		JMenu jMenuRelatorioVenda = new JMenu("Relatórios de Vendas");
		jMenuRelatorio.add(jMenuRelatorioVenda);

		JMenuItem jMenuItemTeste7 = new JMenuItem("Teste6");
		jMenuRelatorioVenda.add(jMenuItemTeste7);

		JMenu jMenuMovimentoPDV = new JMenu("Movimentos PDV");
		jMenuRelatorio.add(jMenuMovimentoPDV);

		JMenuItem jMenuItemTeste8 = new JMenuItem("Teste6");
		jMenuMovimentoPDV.add(jMenuItemTeste8);

		JMenu jMenuNotaFiscal = new JMenu("Notas Fiscais");
		jMenuRelatorio.add(jMenuNotaFiscal);

		JMenuItem jMenuItemTeste9 = new JMenuItem("Teste6");
		jMenuNotaFiscal.add(jMenuItemTeste9);

		// FIM Submenus do Menu [Relatórios]

		// INÍCIO Submenus do Menu [Configurações]

		// FIM Submenus do Menu [Configurações]

		// INÍCIO Submenus do Menu [Sistema]

		// FIM Submenus do Menu [Sistema]

		jMenuItemCadastroSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		jMenuItemCliente.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					ClienteListInternalFrame clienteListPanel = new ClienteListInternalFrame();
					clienteListPanel.setVisible(true);
					jDesktopPane.add(clienteListPanel);
				} catch (Exception excecao) {
					JOptionPane.showInternalMessageDialog(jDesktopPane,
							excecao.getMessage() + " " + excecao.getLocalizedMessage());

				}

			}
		});

		jMenuItemCadastroDepartamentos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DepartamentoListInternalFrame departamentoListPanel = new DepartamentoListInternalFrame();
				departamentoListPanel.setVisible(true);
				jDesktopPane.add(departamentoListPanel);

			}
		});

		jMenuItemCFOP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CFOPListInternalFrame cfopListPanel = new CFOPListInternalFrame();
				cfopListPanel.setVisible(true);
				jDesktopPane.add(cfopListPanel);

			}
		});

		jMenuItemGrupo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GrupoListInternalFrame grupoListPanel = new GrupoListInternalFrame();
				grupoListPanel.setVisible(true);
				jDesktopPane.add(grupoListPanel);

			}
		});

		jMenuItemSubGrupo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SubGrupoListInternalFrame subGrupoListPanel;
				try {
					subGrupoListPanel = new SubGrupoListInternalFrame();
					subGrupoListPanel.setVisible(true);
					jDesktopPane.add(subGrupoListPanel);
				} catch (CategoriaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		jMenuItemTicket.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TicketListInternalFrame ticketListPanel = new TicketListInternalFrame();
				ticketListPanel.setVisible(true);
				jDesktopPane.add(ticketListPanel);

			}
		});

		jMenuItemCartao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CartaoListInternalFrame cartaoListPanel = new CartaoListInternalFrame();
				cartaoListPanel.setVisible(true);
				jDesktopPane.add(cartaoListPanel);

			}
		});

		jMenuItemContaBancaria.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ContaBancariaListInternalFrame contaBancariaListPanel = new ContaBancariaListInternalFrame();
				contaBancariaListPanel.setVisible(true);
				jDesktopPane.add(contaBancariaListPanel);

			}
		});

		jMenuItemObservacao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ObservacaoListInternalFrame observacaoListPanel = new ObservacaoListInternalFrame();
				observacaoListPanel.setVisible(true);
				jDesktopPane.add(observacaoListPanel);

			}
		});

		jMenuItemPlanoContas.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PlanoContasListInternalFrame planoContasListPanel = new PlanoContasListInternalFrame();
				planoContasListPanel.setVisible(true);
				jDesktopPane.add(planoContasListPanel);

			}
		});

		jMenuItemBairro.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				BairroListInternalFrame bairroListPanel = new BairroListInternalFrame();
				bairroListPanel.setVisible(true);
				jDesktopPane.add(bairroListPanel);

			}
		});

		jMenuItemFornecedor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FornecedorListInternalFrame bairroListPanel = new FornecedorListInternalFrame();
				bairroListPanel.setVisible(true);
				jDesktopPane.add(bairroListPanel);

			}
		});

		jMenuItemConvenio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ConvenioListIternalFrame bairroListPanel = new ConvenioListIternalFrame();
				bairroListPanel.setVisible(true);
				jDesktopPane.add(bairroListPanel);

			}
		});

		jMenuItemTransportadora.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TransportadoraListInternalFrame bairroListPanel = new TransportadoraListInternalFrame();
				bairroListPanel.setVisible(true);
				jDesktopPane.add(bairroListPanel);

			}
		});

		setJMenuBar(jMenuBar);

		JMenuItem jMenuItemConfiguracaoEmpresa = new JMenuItem("Empresa");
		jMenuConfiguracao.add(jMenuItemConfiguracaoEmpresa);

		jMenuItemConfiguracaoEmpresa.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				EmpresaFormInternalFrame empresaFormInternalFrame = new EmpresaFormInternalFrame();
				empresaFormInternalFrame.setVisible(true);
				jDesktopPane.add(empresaFormInternalFrame);

			}
		});

		jMenuItemProduto.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ProdutoListInternalFrame bairroListPanel;
				try {
					bairroListPanel = new ProdutoListInternalFrame();
					bairroListPanel.setVisible(true);
					jDesktopPane.add(bairroListPanel);
				} catch (CategoriaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnidadeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		jMenuItemFuncionarios.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FuncionarioListInternalFrame bairroListPanel = new FuncionarioListInternalFrame();
				bairroListPanel.setVisible(true);
				jDesktopPane.add(bairroListPanel);

			}
		});

		jMenuItemCargos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CargoListInternalFrame bairroListPanel = new CargoListInternalFrame();
				bairroListPanel.setVisible(true);
				jDesktopPane.add(bairroListPanel);

			}
		});

		JMenu jMenuPDV = new JMenu("PDV");
		jMenuPDV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec(
							"C://Program Files (x86)//Google//Chrome//Application//chrome.exe  -start-fullscreen --app=http://127.0.0.1:8080/PrincipalWeb/PrincipalWeb.html");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		jMenuBar.add(jMenuPDV);

		String plaf = "";
		plaf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		plaf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		plaf = "javax.swing.plaf.metal.MetalLookAndFeel";
		plaf = "javax.swing.plaf.mac.MacLookAndFeel.Mac";
		plaf = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		UIManager.put("Table.alternateRowColor", Color.LIGHT_GRAY);
//	UIManager.put("FormattedTextField.contentMargins", new InsetsUIResource(0, 0, 0, 0));

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("FormattedTextField.margins", "FormattedTextField.contentMargins");
					// UIManager.getDefaults().put("TextField.font",
					// UIManager.getFont("TextField.font"));
					// UIManager.put("TextField.font", new Font("arial", Font.BOLD, 20 ));
					// UIManager.put("TextField.font", new Font("arial", Font.BOLD, 20 ));
					UIManager.getDefaults().put("TextField.contentMargins", new InsetsUIResource(0, 0, 0, 0));
					UIManager.getDefaults().put("TextField.minimumSize", new Dimension(10, 30));

					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			e.printStackTrace();
		}
		  //maximiza o frame
        this.setExtendedState(MAXIMIZED_BOTH);
		SwingUtilities.updateComponentTreeUI(this);

	}

	public static JDesktopPane getjDesktopPane() {
		return jDesktopPane;
	}

	public static void setjDesktopPane(JDesktopPane jDesktopPane) {
		PrincipalDesktop.jDesktopPane = jDesktopPane;
	}
}