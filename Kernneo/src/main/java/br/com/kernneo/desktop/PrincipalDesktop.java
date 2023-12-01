package br.com.kernneo.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicDesktopPaneUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.hibernate.Session;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginService;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.exception.UnidadeException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.types.Modulo;
import br.com.kernneo.desktop.view.bairro.BairroListInternalFrame;
import br.com.kernneo.desktop.view.cargo.CargoListInternalFrame;
import br.com.kernneo.desktop.view.cartao.CartaoListInternalFrame;
import br.com.kernneo.desktop.view.cfop.CFOPListInternalFrame;
import br.com.kernneo.desktop.view.cidade.CidadeListInternalFrame;
import br.com.kernneo.desktop.view.cliente.ClienteListInternalFrame;
import br.com.kernneo.desktop.view.contabancaria.ContaBancariaListInternalFrame;
import br.com.kernneo.desktop.view.convenio.ConvenioListIternalFrame;
import br.com.kernneo.desktop.view.departamento.DepartamentoFormCadPanel;
import br.com.kernneo.desktop.view.departamento.DepartamentoListInternalFrame;
import br.com.kernneo.desktop.view.empresa.EmpresaFormInternalFrame;
import br.com.kernneo.desktop.view.entregaequipamento.EntregaEquipamentoListInternalFrame;
import br.com.kernneo.desktop.view.financeiro.MovimentacaoInternalFrame;
import br.com.kernneo.desktop.view.financeiro.MovimentacaoListInternalFrame;
import br.com.kernneo.desktop.view.fornecedor.FornecedorListInternalFrame;
import br.com.kernneo.desktop.view.funcionario.FuncionarioListInternalFrame;
import br.com.kernneo.desktop.view.grupo.GrupoFormCadPanel;
import br.com.kernneo.desktop.view.grupo.GrupoListInternalFrame;
import br.com.kernneo.desktop.view.hospital.HospitalFormCadPanel;
import br.com.kernneo.desktop.view.hospital.HospitalListInternalFrame;
import br.com.kernneo.desktop.view.observacao.ObservacaoListInternalFrame;
import br.com.kernneo.desktop.view.ocorrencia.OcorrenciaInternalFrame;
import br.com.kernneo.desktop.view.planocontas.PlanoContasListInternalFrame;
import br.com.kernneo.desktop.view.produto.ProdutoListInternalFrame;
import br.com.kernneo.desktop.view.regulacao.RegulacaoInternalFrame;
import br.com.kernneo.desktop.view.regulacao.RegulacaoListInternalFrame;
import br.com.kernneo.desktop.view.solicitacao.SolicitacaoGeralFrame;
import br.com.kernneo.desktop.view.solicitacao.SolicitacaoItemInternalFrame;
import br.com.kernneo.desktop.view.solicitacao.SolicitacaoListInternalFrame;
import br.com.kernneo.desktop.view.subgrupo.SubGrupoListInternalFrame;
import br.com.kernneo.desktop.view.ticket.TicketListInternalFrame;
import br.com.kernneo.desktop.view.transportadora.TransportadoraListInternalFrame;
import br.com.kernneo.desktop.view.veiculo.VeiculoListInternalFrame;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.desktop.view.widget.SampleDesktopMgr;
import br.com.kernneo.desktop.view.widget.TrippleDes;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.FuncionarioDAO;

public class PrincipalDesktop extends JFrame
	{

		private static final long serialVersionUID = 4425154413662486877L;
		public static FuncionarioModel usarioLogado;
		private static JDesktopPane jDesktopPane;

		public static FuncionarioModel getUsarioLogado() {
			return usarioLogado;
		}

		public static void setUsarioLogado(FuncionarioModel usarioLogado) {
			PrincipalDesktop.usarioLogado = usarioLogado;
		}

		public static void main(String[] args) {
			try {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						System.out.println(UIManager.getColor("InternalFrame:InternalFrameTitlePane[Enabled].textForeground"));
						break;
					}
				}
				Object[] objs = javax.swing.UIManager.getLookAndFeel().getDefaults().keySet().toArray();

				for (int i = 0; i < objs.length; i++) {
					if (objs[i].toString().toLowerCase().contains("internalframe")) {
						System.out.println(objs[i] + " : " +
								javax.swing.UIManager.getDefaults().get(objs[i]));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			PrincipalDesktop principalDesktop = new PrincipalDesktop();
			principalDesktop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			principalDesktop.setExtendedState(MAXIMIZED_BOTH);
			principalDesktop.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					principalDesktop.setVisible(false);
					principalDesktop.dispose();
					System.exit(0);
				}
			});

			SwingUtilities.updateComponentTreeUI(principalDesktop);

			JXLoginPane jxLoginPane = new JXLoginPane();
			jxLoginPane.setLoginService(new LoginService() {

				@Override
				public boolean authenticate(String name, char[] password, String server) throws Exception {
					TrippleDes td = new TrippleDes();

					PrincipalDesktop.setUsarioLogado(null);
					String passwordString = new String(password);

					if (passwordString.equals("")) {
						 return true;
					}

					FuncionarioModel funcionarioModel = null;
					Session session = ConnectFactory.getSession();
					try {
						session.beginTransaction();
						funcionarioModel = new FuncionarioDAO().obterPorLogin(name);
						session.getTransaction().commit();
					} catch (Exception e) {
						session.getTransaction().rollback();
						e.printStackTrace();
						JXErrorPane.showDialog(e);
						throw e;
					} finally {
						if (session != null && session.isOpen()) {
							session.close();
						}
					}
					if (funcionarioModel != null && funcionarioModel.getSenha() != null && td.decrypt(funcionarioModel.getSenha()).equals(passwordString)) {
						PrincipalDesktop.setUsarioLogado(funcionarioModel);
						return true;
					} else {
						return false;
					}
				}
			});

			JXLoginPane.showLoginDialog(principalDesktop, jxLoginPane);
			if (jxLoginPane.getStatus() == JXLoginPane.Status.SUCCEEDED) {
				principalDesktop.createMenu(getUsarioLogado());
				principalDesktop.setVisible(true);
			} else {
				principalDesktop.setVisible(false);
				principalDesktop.dispose();
				System.exit(0);
			}
		}

		private void createMenu(FuncionarioModel funcionarioModel) {
			createMenuCompilado();
			/*
			//createMenuZeca();
			if (funcionarioModel.getModuloDeAcesso() == Modulo.ZECA) {
				createMenuZeca();
			} else if (funcionarioModel.getModuloDeAcesso() == Modulo.CARUELH) {
			//	createMenuCaruelh();
			} else if (funcionarioModel.getModuloDeAcesso() == Modulo.SAMU) {
			//	createMenuSamu();
			}
			*/
		}

		public PrincipalDesktop() {

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			// screenSize.width -= 42;
			screenSize.height -= 40;
			// setSize(screenSize);
			// setLocation(0, 0);

			jDesktopPane = new JDesktopPane();
			jDesktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
			jDesktopPane.setDesktopManager(new SampleDesktopMgr());
			jDesktopPane.setUI(new BasicDesktopPaneUI());
			jDesktopPane.putClientProperty("JDesktopPane.dragMode", "outline");

			setContentPane(jDesktopPane);

			// createMenu();

		}

		private void createMenuSamu() {
			JMenuBar jMenuBar = new JMenuBar();

			JMenu jMenuCadastro = new JMenu("Cadastro");
			jMenuBar.add(jMenuCadastro);

			JMenuItem jMenuItemCadVeiculo = new JMenuItem("Veículos");
			jMenuItemCadVeiculo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new VeiculoListInternalFrame());
				}
			});

			JMenuItem jMenuItemCadHospital = new JMenuItem("Hospitais");
			jMenuItemCadHospital.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new HospitalListInternalFrame());
				}
			});

			JMenuItem jMenuItemFuncionarios = new JMenuItem("Funcionários");
			jMenuItemFuncionarios.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new FuncionarioListInternalFrame());
				}
			});

			JMenuItem jMenuItemCadCidade = new JMenuItem("Cidades");
			jMenuItemCadCidade.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new CidadeListInternalFrame());
				}
			});

			jMenuCadastro.add(jMenuItemCadVeiculo);
			jMenuCadastro.add(jMenuItemFuncionarios);
			jMenuCadastro.add(jMenuItemCadHospital);
			jMenuCadastro.add(jMenuItemCadCidade);

			JMenu jMenuOcorrencias = new JMenu("Ocorrencias");
			jMenuBar.add(jMenuOcorrencias);

			JMenuItem jMenuItemRegistroOcorrencias = new JMenuItem("Registro de Ocorrências");
			jMenuOcorrencias.add(jMenuItemRegistroOcorrencias);
			jMenuItemRegistroOcorrencias.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						OcorrenciaInternalFrame internalFrame = new OcorrenciaInternalFrame(PrincipalDesktop.getUsarioLogado());
						internalFrame.setVisible(true);
						jDesktopPane.add(internalFrame, 0);
						internalFrame.pack();
						internalFrame.setMaximum(true);
						internalFrame.setSelected(true);
						internalFrame.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
						excecao.printStackTrace();
					}

				}
			});

			JMenuItem jMenuItemMovimentoCaixa = new JMenuItem("Movimentação Diária");
			jMenuOcorrencias.add(jMenuItemMovimentoCaixa);
			jMenuItemMovimentoCaixa.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						MovimentacaoInternalFrame clienteListPanel = new MovimentacaoInternalFrame(PrincipalDesktop.getUsarioLogado());
						clienteListPanel.setVisible(true);
						jDesktopPane.add(clienteListPanel, 0);
						clienteListPanel.pack();
						clienteListPanel.setMaximum(true);
						clienteListPanel.setSelected(true);
						clienteListPanel.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

						excecao.printStackTrace();

					}

				}

			});
			setJMenuBar(jMenuBar);
		}

		private void createMenuCaruelh() {
			JMenuBar jMenuBar = new JMenuBar();

			JMenu jMenuCadastro = new JMenu("Cadastro");
			jMenuBar.add(jMenuCadastro);

			JMenuItem jMenuItemCadHospital = new JMenuItem("Hospitais");
			jMenuItemCadHospital.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new HospitalListInternalFrame());
				}
			});
			

			JMenuItem jMenuItemCadEspecialidades = new JMenuItem("Especialidades");
			jMenuItemCadEspecialidades.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					 GrupoListInternalFrame grupoListInternalFrame = new GrupoListInternalFrame();
					 grupoListInternalFrame.setTitle("Cadastro de Especialiades");
					 ((GrupoFormCadPanel) grupoListInternalFrame.getFormCadPanel()).getTitledBorder().setTitle("Informações de Especialidade");
					acaoAbrirInternalFrame(grupoListInternalFrame);
				}
			});

			JMenuItem jMenuItemFuncionarios = new JMenuItem("Funcionários");
			jMenuItemFuncionarios.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new FuncionarioListInternalFrame());
				}
			});

			JMenuItem jMenuItemCadCidade = new JMenuItem("Cidades");
			jMenuItemCadCidade.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new CidadeListInternalFrame());
				}
			});
			jMenuCadastro.add(jMenuItemCadEspecialidades);
			jMenuCadastro.add(jMenuItemFuncionarios);
			jMenuCadastro.add(jMenuItemCadHospital);
			jMenuCadastro.add(jMenuItemCadCidade);

			JMenu jMenuOcorrencias = new JMenu("Regulação");
			jMenuBar.add(jMenuOcorrencias);

			JMenuItem jMenuItemRegistroOcorrencias = new JMenuItem("Cadastro");
			jMenuOcorrencias.add(jMenuItemRegistroOcorrencias);
			jMenuItemRegistroOcorrencias.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						RegulacaoInternalFrame internalFrame = new RegulacaoInternalFrame(PrincipalDesktop.getUsarioLogado());
						BasicInternalFrameUI ui = (BasicInternalFrameUI) internalFrame.getUI();
						JComponent titleBar = ui.getNorthPane();
						UIDefaults d = new UIDefaults();
						d.put("InternalFrame:InternalFrameTitlePane.titleAlignment", "LEADING");
						titleBar.putClientProperty("Nimbus.Overrides", d);
						internalFrame.setVisible(true);
						jDesktopPane.add(internalFrame, 0);
						internalFrame.pack();
						internalFrame.setMaximum(true);
						internalFrame.setSelected(true);
						internalFrame.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
						excecao.printStackTrace();
					}

				}
			});

			JMenuItem jMenuItemMovimentoCaixa = new JMenuItem("Consulta");
			jMenuOcorrencias.add(jMenuItemMovimentoCaixa);
			jMenuItemMovimentoCaixa.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						RegulacaoListInternalFrame clienteListPanel = new RegulacaoListInternalFrame();
						clienteListPanel.setVisible(true);
						jDesktopPane.add(clienteListPanel, 0);
						clienteListPanel.pack();
						clienteListPanel.setMaximum(true);
						clienteListPanel.setSelected(true);
						clienteListPanel.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

						excecao.printStackTrace();

					}

				}

			});
			setJMenuBar(jMenuBar);
		}
		
		private void createMenuCompilado() {
			JMenuBar jMenuBar = new JMenuBar();

			JMenu jMenuCadastro = new JMenu("Cadastro");
			jMenuBar.add(jMenuCadastro);

			JMenuItem jMenuItemCadUnidade = new JMenuItem("Unidade");
			jMenuItemCadUnidade.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					HospitalListInternalFrame listInternalFrame = new HospitalListInternalFrame();
					listInternalFrame.setTitle("Cadastro de Unidade");
					 ((HospitalFormCadPanel) listInternalFrame.getFormCadPanel()).getTitledBorder().setTitle("Informações de Unidade");
					 acaoAbrirInternalFrame(listInternalFrame);
				}
			});
			

			JMenuItem jMenuItemCadDocumento = new JMenuItem("Documento");
			jMenuItemCadDocumento.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					 GrupoListInternalFrame grupoListInternalFrame = new GrupoListInternalFrame();
					 grupoListInternalFrame.setTitle("Cadastro de Documento");
					 ((GrupoFormCadPanel) grupoListInternalFrame.getFormCadPanel()).getTitledBorder().setTitle("Informações de Documento");
					 acaoAbrirInternalFrame(grupoListInternalFrame);
				}
			});

			JMenuItem jMenuItemCadEquipamento = new JMenuItem("Equipamento");
			jMenuItemCadEquipamento.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					DepartamentoListInternalFrame listInternalFrame = new DepartamentoListInternalFrame();
					listInternalFrame.setTitle("Cadastro de Equipamento");
					 ((DepartamentoFormCadPanel) listInternalFrame.getFormCadPanel()).getTitledBorder().setTitle("Informações de Equipamento");
					 acaoAbrirInternalFrame(listInternalFrame);
				}
			});

			jMenuCadastro.add(jMenuItemCadUnidade);
			jMenuCadastro.add(jMenuItemCadDocumento);
			jMenuCadastro.add(jMenuItemCadEquipamento);
			jMenuCadastro.addSeparator();
			
			JMenuItem jMenuItemCadCompilado = new JMenuItem("Solicitação");
			jMenuItemCadCompilado.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new SolicitacaoListInternalFrame());
				}
			});
			jMenuCadastro.add(jMenuItemCadCompilado);

			JMenu jMenuEquipamentos = new JMenu("Equipamentos");
			jMenuBar.add(jMenuEquipamentos);
			
			JMenu jMenuItemSolicitacao = new JMenu("Solicitações");
			jMenuEquipamentos.add(jMenuItemSolicitacao);
			
			JMenuItem jMenuItemRegistroOcorrencias = new JMenuItem("Lançar Item");
			jMenuItemSolicitacao.add(jMenuItemRegistroOcorrencias);
			jMenuItemRegistroOcorrencias.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						SolicitacaoItemInternalFrame internalFrame = new SolicitacaoItemInternalFrame();
						BasicInternalFrameUI ui = (BasicInternalFrameUI) internalFrame.getUI();
						JComponent titleBar = ui.getNorthPane();
						UIDefaults d = new UIDefaults();
						d.put("InternalFrame:InternalFrameTitlePane.titleAlignment", "LEADING");
						titleBar.putClientProperty("Nimbus.Overrides", d);
						internalFrame.setVisible(true);
						jDesktopPane.add(internalFrame, 0);
						internalFrame.pack();
						internalFrame.setMaximum(true);
						internalFrame.setSelected(true);
						internalFrame.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
						excecao.printStackTrace();
					}

				}
			});

			JMenuItem jMenuItemMovimentoCaixa = new JMenuItem("Consulta");
			jMenuItemSolicitacao.add(jMenuItemMovimentoCaixa);
			jMenuItemMovimentoCaixa.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						acaoAbrirInternalFrame(new SolicitacaoGeralFrame());

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

						excecao.printStackTrace();

					}

				}

			});
			
			JMenu jMenuEntrega = new JMenu("Entrega");
			JMenuItem jMenuItemEntregaLancarItem = new JMenuItem("Lançar Item");
			jMenuEquipamentos.add(jMenuEntrega);
			jMenuEntrega.add(jMenuItemEntregaLancarItem);
			jMenuItemEntregaLancarItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						acaoAbrirInternalFrame(new EntregaEquipamentoListInternalFrame());
					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

						excecao.printStackTrace();

					}

				}

			});
			jMenuBar.add(jMenuEquipamentos);
			
			setJMenuBar(jMenuBar);
		}

		protected void acaoAbrirInternalFrame(JInternalFrame internalFrame) {
			try {
				internalFrame.setVisible(true);
				jDesktopPane.add(internalFrame, 0);
				internalFrame.pack();
				internalFrame.setSelected(true);
			} catch (Exception excecao) {
				JOptionPane.showInternalMessageDialog(jDesktopPane, excecao.getMessage() + " " + excecao.getLocalizedMessage());
			}

		}

		private void createMenuZeca() {

			JMenuBar jMenuBar = new JMenuBar();

			JMenu jMenuCadastro = new JMenu("Cadastro");
			jMenuBar.add(jMenuCadastro);

			JMenuItem jMenuItemCliente = new JMenuItem("Clientes");
			jMenuCadastro.add(jMenuItemCliente);
			jMenuItemCliente.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new ClienteListInternalFrame());
				}
			});

			JMenuItem jMenuItemFuncionarios = new JMenuItem("Funcionários");
			jMenuCadastro.add(jMenuItemFuncionarios);
			jMenuItemFuncionarios.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new FuncionarioListInternalFrame());

				}
			});

			JSeparator separator_1 = new JSeparator();
			jMenuCadastro.add(separator_1);

			JMenuItem jMenuItemGrupo = new JMenuItem("Categoria de Contas");
			jMenuCadastro.add(jMenuItemGrupo);
			jMenuItemGrupo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new GrupoListInternalFrame());
				}
			});

			JMenuItem jMenuItemContaBancaria = new JMenuItem("Conta Bancária");
			jMenuCadastro.add(jMenuItemContaBancaria);
			jMenuItemContaBancaria.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAbrirInternalFrame(new ContaBancariaListInternalFrame());
				}
			});

			JSeparator separator_2 = new JSeparator();
			jMenuCadastro.add(separator_2);

			JMenuItem jMenuItemCadastroSair = new JMenuItem("Sair");
			jMenuCadastro.add(jMenuItemCadastroSair);
			jMenuItemCadastroSair.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					System.exit(0);

				}
			});

			JMenu jMenuFinanceiro = new JMenu("Financeiro");
			jMenuBar.add(jMenuFinanceiro);

			JMenuItem jMenuItemMovimentoCaixa = new JMenuItem("Movimentação Diária");
			jMenuFinanceiro.add(jMenuItemMovimentoCaixa);
			jMenuItemMovimentoCaixa.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						MovimentacaoInternalFrame clienteListPanel = new MovimentacaoInternalFrame(PrincipalDesktop.getUsarioLogado());
						clienteListPanel.setVisible(true);
						jDesktopPane.add(clienteListPanel, 0);
						clienteListPanel.pack();
						clienteListPanel.setMaximum(true);
						clienteListPanel.setSelected(true);
						clienteListPanel.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

						excecao.printStackTrace();

					}

				}
			});

			JMenu jMenuRelatorio = new JMenu("Relatórios");
			jMenuBar.add(jMenuRelatorio);

			JMenuItem jMenuItemRelatorioMovimentacao = new JMenuItem("Movimentação Financeira");
			jMenuRelatorio.add(jMenuItemRelatorioMovimentacao);
			jMenuItemRelatorioMovimentacao.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						MovimentacaoListInternalFrame listPanel = new MovimentacaoListInternalFrame();
						listPanel.setVisible(true);
						jDesktopPane.add(listPanel, 0);
						listPanel.pack();
						listPanel.setMaximum(true);
						listPanel.setSelected(true);
						listPanel.setMaximizable(true);

					} catch (Exception excecao) {
						JOptionPane.showMessageDialog(jDesktopPane, excecao.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

						excecao.printStackTrace();

					}

				}
			});

			setJMenuBar(jMenuBar);

		}
		
		

		private void createMenu() {

			JMenuBar jMenuBar = new JMenuBar();

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

			JMenuItem jMenuItemMovimentoCaixa = new JMenuItem("Movimento de Caixa");
			jMenuFinanceiro.add(jMenuItemMovimentoCaixa);

			JMenuItem jMenuItemEspelhoCaixa = new JMenuItem("Espelho do Caixa");
			jMenuFinanceiro.add(jMenuItemEspelhoCaixa);

			JMenuItem jMenuItemContasPagar = new JMenuItem("Contas a Pagar");
			jMenuFinanceiro.add(jMenuItemContasPagar);

			JMenuItem jMenuItemContasReceber = new JMenuItem("Contas a Receber");
			jMenuFinanceiro.add(jMenuItemContasReceber);

			// FIM Submenus do Menu [Financeiro]

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
						JOptionPane.showInternalMessageDialog(jDesktopPane, excecao.getMessage() + " " + excecao.getLocalizedMessage());

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
						runtime.exec("C://Program Files (x86)//Google//Chrome//Application//chrome.exe  -start-fullscreen --app=http://127.0.0.1:8080/PrincipalWeb/PrincipalWeb.html");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			jMenuBar.add(jMenuPDV);
		}

		public static JDesktopPane getjDesktopPane() {
			return jDesktopPane;
		}

		public static void setjDesktopPane(JDesktopPane jDesktopPane) {
			PrincipalDesktop.jDesktopPane = jDesktopPane;
		}

		public static void Loading(JInternalFrame jInternalFrame) {
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Loading...");
			JProgressBar jpb = new JProgressBar();
			jpb.setIndeterminate(false);
			int max = 1000;
			jpb.setMaximum(max);
			panel.add(label);
			panel.add(jpb);
			frame.add(panel);
			frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			// frame.setModal(true);
			frame.setResizable(false);
			frame.pack();
			frame.setSize(200, 90);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// frame.setUndecorated(true);
			frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			new Task_IntegerUpdate(frame, jpb, max, label).execute();

		}

		static class Task_IntegerUpdate extends SwingWorker<Void, Integer>
			{

				JProgressBar jpb;
				int max;
				JLabel label;
				JFrame j;

				public Task_IntegerUpdate(JFrame jFrame, JProgressBar jpb, int max, JLabel label) {
					this.jpb = jpb;
					this.max = max;
					this.label = label;
					this.j = jFrame;

				}

				@Override
				protected void process(List<Integer> chunks) {
					int i = chunks.get(chunks.size() - 1);
					jpb.setValue(i); // The last value in this array is all we care about.
					// System.out.println(i);
					label.setText("Loading " + i + " of " + max);
				}

				@Override
				protected Void doInBackground() throws Exception {
					for (int i = 0; i < max; i++) {
						Thread.sleep(1); // Illustrating long-running code.
						publish(i);
					}
					return null;
				}

				@Override
				protected void done() {
					try {
						get();

						j.setVisible(false);
						j.dispose();
						// JOptionPane.showMessageDialog(jpb.getParent(), "Success", "Success",
						// JOptionPane.INFORMATION_MESSAGE);
						// frame.getContentPane().add(c);
					} catch (ExecutionException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
	}
