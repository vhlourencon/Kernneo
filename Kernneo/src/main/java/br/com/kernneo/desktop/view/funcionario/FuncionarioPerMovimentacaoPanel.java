package br.com.kernneo.desktop.view.funcionario;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.kernneo.client.model.FuncionarioModel;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FuncionarioPerMovimentacaoPanel extends JPanel
    {

        /**
        * 
        */
        private static final long serialVersionUID = 4602892477693713818L;
        private JCheckBox checkBoxExcluirUserFeito;
        private JCheckBox checkBoxExcluirUserPendente;
        private JCheckBox checkBoxExcluirOutrosFeito;
        private JCheckBox checkBoxExcluirOutrosPendentes;

        private FuncionarioModel model;
        private JCheckBox checkBoxSaldoEMconta;
        private JCheckBox checkBoxPermiteAcesso;
        private JCheckBox checkBoxAlterarPagamentoUserFeito;
        private JCheckBox checkBoxAlterarPagamentoUserPendente;
        private JCheckBox checkBoxAlterarPagamentoOutrosFeito;
        private JCheckBox checkBoxAlterarPagamentoOutrosPendentes;
        private JCheckBox checkBoxAlterarDataUserPendente;
        private JCheckBox checkBoxAlterarDataUserFeito;
        private JCheckBox checkBoxAlterarDataOutrosFeito;
        private JCheckBox checkBoxAlterarDataOutrosPendente;

        public FuncionarioPerMovimentacaoPanel() {
            setPreferredSize(new Dimension(559, 569));

            setLayout(null);

            JPanel p = new JPanel();
            p.setPreferredSize(new Dimension(240, 520));

            JPanel panel = new JPanel();
            panel.setBounds(10, 366, 487, 114);
            panel.setBorder(new TitledBorder(null, "Excluir", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setLayout(null);

            JPanel panel_2 = new JPanel();
            panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTOS DO USUARIO", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
            panel_2.setBounds(10, 20, 230, 80);
            panel.add(panel_2);
            panel_2.setLayout(null);

            checkBoxExcluirUserFeito = new JCheckBox("RECEBIDOS/PAGOS");
            checkBoxExcluirUserFeito.setBounds(6, 43, 180, 30);
            panel_2.add(checkBoxExcluirUserFeito);

            checkBoxExcluirUserPendente = new JCheckBox("PENDENTES");
            checkBoxExcluirUserPendente.setBounds(6, 23, 97, 23);
            panel_2.add(checkBoxExcluirUserPendente);

            JPanel panel_2_1 = new JPanel();
            panel_2_1.setLayout(null);
            panel_2_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTO DE OUTROS USU\u00C1RIOS", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
            panel_2_1.setBounds(244, 20, 230, 80);
            panel.add(panel_2_1);

            checkBoxExcluirOutrosFeito = new JCheckBox("RECEBIDOS/PAGOS");
            checkBoxExcluirOutrosFeito.setBounds(6, 43, 180, 30);
            panel_2_1.add(checkBoxExcluirOutrosFeito);

            checkBoxExcluirOutrosPendentes = new JCheckBox("PENDENTES");
            checkBoxExcluirOutrosPendentes.setBounds(6, 23, 97, 23);
            panel_2_1.add(checkBoxExcluirOutrosPendentes);

            JPanel panel_3 = new JPanel();
            panel_3.setBounds(10, 116, 487, 114);
            panel_3.setLayout(null);
            panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Alterar Pagamento/Recebimento", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

            JPanel panel_2_2 = new JPanel();
            panel_2_2.setLayout(null);
            panel_2_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTOS DO USUARIO", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
            panel_2_2.setBounds(10, 20, 230, 80);
            panel_3.add(panel_2_2);

            checkBoxAlterarPagamentoUserFeito = new JCheckBox("RECEBIDOS/PAGOS");
            checkBoxAlterarPagamentoUserFeito.setBounds(6, 43, 180, 30);
            panel_2_2.add(checkBoxAlterarPagamentoUserFeito);

            checkBoxAlterarPagamentoUserPendente = new JCheckBox("PENDENTES");
            checkBoxAlterarPagamentoUserPendente.setBounds(6, 23, 97, 23);
            panel_2_2.add(checkBoxAlterarPagamentoUserPendente);

            JPanel panel_2_1_1 = new JPanel();
            panel_2_1_1.setLayout(null);
            panel_2_1_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTO DE OUTROS USU\u00C1RIOS", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
            panel_2_1_1.setBounds(244, 20, 230, 80);
            panel_3.add(panel_2_1_1);

            checkBoxAlterarPagamentoOutrosFeito = new JCheckBox("RECEBIDOS/PAGOS");
            checkBoxAlterarPagamentoOutrosFeito.setBounds(6, 43, 180, 30);
            panel_2_1_1.add(checkBoxAlterarPagamentoOutrosFeito);

            checkBoxAlterarPagamentoOutrosPendentes = new JCheckBox("PENDENTES");
            checkBoxAlterarPagamentoOutrosPendentes.setBounds(6, 23, 97, 23);
            panel_2_1_1.add(checkBoxAlterarPagamentoOutrosPendentes);

            JPanel panel_3_1 = new JPanel();
            panel_3_1.setBounds(10, 241, 487, 114);
            panel_3_1.setLayout(null);
            panel_3_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Alterar Data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

            JPanel panel_2_2_1 = new JPanel();
            panel_2_2_1.setLayout(null);
            panel_2_2_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTOS DO USUARIO", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
            panel_2_2_1.setBounds(10, 20, 230, 80);
            panel_3_1.add(panel_2_2_1);

            checkBoxAlterarDataUserFeito = new JCheckBox("RECEBIDOS/PAGOS");
            checkBoxAlterarDataUserFeito.setBounds(6, 43, 180, 30);
            panel_2_2_1.add(checkBoxAlterarDataUserFeito);

            checkBoxAlterarDataUserPendente = new JCheckBox("PENDENTES");
            checkBoxAlterarDataUserPendente.setBounds(6, 23, 97, 23);
            panel_2_2_1.add(checkBoxAlterarDataUserPendente);

            JPanel panel_2_1_1_1 = new JPanel();
            panel_2_1_1_1.setLayout(null);
            panel_2_1_1_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LAN\u00C7AMENTO DE OUTROS USU\u00C1RIOS", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
            panel_2_1_1_1.setBounds(244, 20, 230, 80);
            panel_3_1.add(panel_2_1_1_1);

            checkBoxAlterarDataOutrosFeito = new JCheckBox("RECEBIDOS/PAGOS");
            checkBoxAlterarDataOutrosFeito.setBounds(6, 43, 180, 30);
            panel_2_1_1_1.add(checkBoxAlterarDataOutrosFeito);

            checkBoxAlterarDataOutrosPendente = new JCheckBox("PENDENTES");
            checkBoxAlterarDataOutrosPendente.setBounds(6, 23, 97, 23);
            panel_2_1_1_1.add(checkBoxAlterarDataOutrosPendente);

            JScrollPane srcPainel = new JScrollPane(p);
            p.setLayout(null);

            JPanel panel_4 = new JPanel();
            panel_4.setLayout(null);
            panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Visualizar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4.setBounds(10, 42, 487, 63);

            checkBoxSaldoEMconta = new JCheckBox("SALDO EM CONTA");
            checkBoxSaldoEMconta.setBounds(16, 22, 180, 23);
            panel_4.add(checkBoxSaldoEMconta);

            JPanel panel_5 = new JPanel();
            panel_5.setBackground(SystemColor.controlHighlight);
            panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
            panel_5.setBounds(10, 11, 509, 496);

            p.add(panel_5);
            panel_5.setLayout(null);
            panel_5.add(panel);
            panel_5.add(panel_3);
            panel_5.add(panel_3_1);
            panel_5.add(panel_4);

            checkBoxPermiteAcesso = new JCheckBox("PERMITE ACESSO");
            checkBoxPermiteAcesso.setBounds(10, 10, 487, 23);
            panel_5.add(checkBoxPermiteAcesso);
            checkBoxPermiteAcesso.setFont(new Font("Tahoma", Font.BOLD, 14));
            checkBoxPermiteAcesso.addItemListener(new ItemListener() {
                
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(model !=null)  { 
                        if(model.getPermissaoMovFinanceiraModel().isPermiteAcesso() != checkBoxPermiteAcesso.isSelected()) { 
                            setModel(getModel());
                        }
                      
                    }
                    
                    
                }
            });

            srcPainel.setBounds(0, 0, 548, 222);
            srcPainel.setPreferredSize(new Dimension(250, 250));

            add(srcPainel);

        }

        public FuncionarioModel getModel() {
            model.getPermissaoMovFinanceiraModel().setPermiteAcesso(checkBoxPermiteAcesso.isSelected());

            model.getPermissaoMovFinanceiraModel().setVisualizarSaldoConta(checkBoxSaldoEMconta.isSelected());

            model.getPermissaoMovFinanceiraModel().setAlterarDataOutrosFeito(checkBoxAlterarDataOutrosFeito.isSelected());
            model.getPermissaoMovFinanceiraModel().setAlterarDataOutrosPendente(checkBoxAlterarDataOutrosPendente.isSelected());
            model.getPermissaoMovFinanceiraModel().setAlterarDataUserFeito(checkBoxAlterarDataUserFeito.isSelected());
            model.getPermissaoMovFinanceiraModel().setAlterarDataUserPendente(checkBoxAlterarDataUserPendente.isSelected());

            model.getPermissaoMovFinanceiraModel().setAlterarPagamentoOutrosFeito(checkBoxAlterarPagamentoOutrosFeito.isSelected());
            model.getPermissaoMovFinanceiraModel().setAlterarPagamentoOutrosPendente(checkBoxAlterarPagamentoOutrosPendentes.isSelected());
            model.getPermissaoMovFinanceiraModel().setAlterarPagamentoUserFeito(checkBoxAlterarPagamentoUserFeito.isSelected());
            model.getPermissaoMovFinanceiraModel().setAlterarPagamentoUserPendente(checkBoxAlterarPagamentoUserPendente.isSelected());

            model.getPermissaoMovFinanceiraModel().setDeleteOutrosLancamentoFeito(checkBoxExcluirOutrosFeito.isSelected());
            model.getPermissaoMovFinanceiraModel().setDeleteOutrosLancamentoPendente(checkBoxExcluirOutrosPendentes.isSelected());
            model.getPermissaoMovFinanceiraModel().setDeleteUsuarioLancamentoFeito(checkBoxExcluirUserFeito.isSelected());
            model.getPermissaoMovFinanceiraModel().setDeleteUsuarioLancamentoPendente(checkBoxExcluirUserPendente.isSelected());

            return model;
        }

        public void setModel(FuncionarioModel model) {
            this.model = model;

            boolean b = model.getPermissaoMovFinanceiraModel().isPermiteAcesso();

            checkBoxExcluirOutrosPendentes.setEnabled(model.getPermissaoMovFinanceiraModel().isPermiteAcesso());
            checkBoxExcluirOutrosFeito.setEnabled(model.getPermissaoMovFinanceiraModel().isPermiteAcesso());
            checkBoxExcluirUserPendente.setEnabled(model.getPermissaoMovFinanceiraModel().isPermiteAcesso());
            checkBoxExcluirUserFeito.setEnabled(model.getPermissaoMovFinanceiraModel().isPermiteAcesso());

            checkBoxAlterarPagamentoOutrosFeito.setEnabled(b);
            checkBoxAlterarPagamentoOutrosPendentes.setEnabled(b);
            checkBoxAlterarPagamentoUserFeito.setEnabled(b);
            checkBoxAlterarPagamentoUserPendente.setEnabled(b);

            checkBoxAlterarDataOutrosFeito.setEnabled(b);
            checkBoxAlterarDataOutrosPendente.setEnabled(b);
            checkBoxAlterarDataUserFeito.setEnabled(b);
            checkBoxAlterarDataUserPendente.setEnabled(b);
            checkBoxSaldoEMconta.setEnabled(b);

            checkBoxPermiteAcesso.setSelected(b);

            checkBoxSaldoEMconta.setSelected(model.getPermissaoMovFinanceiraModel().isVisualizarSaldoConta());

            checkBoxExcluirOutrosPendentes.setSelected(model.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoPendente());
            checkBoxExcluirOutrosFeito.setSelected(model.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoFeito());
            checkBoxExcluirUserPendente.setSelected(model.getPermissaoMovFinanceiraModel().isDeleteUsuarioLancamentoPendente());
            checkBoxExcluirUserFeito.setSelected(model.getPermissaoMovFinanceiraModel().isDeleteUsuarioLancamentoFeito());

            checkBoxAlterarPagamentoOutrosFeito.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarPagamentoOutrosFeito());
            checkBoxAlterarPagamentoOutrosPendentes.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarPagamentoOutrosPendente());
            checkBoxAlterarPagamentoUserFeito.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarPagamentoUserFeito());
            checkBoxAlterarPagamentoUserPendente.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarPagamentoUserPendente());

            checkBoxAlterarDataOutrosFeito.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarDataOutrosFeito());
            checkBoxAlterarDataOutrosPendente.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarDataOutrosPendente());
            checkBoxAlterarDataUserFeito.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarDataUserFeito());
            checkBoxAlterarDataUserPendente.setSelected(model.getPermissaoMovFinanceiraModel().isAlterarDataUserPendente());

        }

        public JCheckBox getCheckBoxUsuariorecebidoPago() {
            return checkBoxExcluirUserFeito;
        }

        public void setCheckBoxUsuariorecebidoPago(JCheckBox checkBoxUsuariorecebidoPago) {
            this.checkBoxExcluirUserFeito = checkBoxUsuariorecebidoPago;
        }

        public JCheckBox getCheckBoxUsuarioPendente() {
            return checkBoxExcluirUserPendente;
        }

        public void setCheckBoxUsuarioPendente(JCheckBox checkBoxUsuarioPendente) {
            this.checkBoxExcluirUserPendente = checkBoxUsuarioPendente;
        }

        public JCheckBox getCheckBoxOutrosRecebidos() {
            return checkBoxExcluirOutrosFeito;
        }

        public void setCheckBoxOutrosRecebidos(JCheckBox checkBoxOutrosRecebidos) {
            this.checkBoxExcluirOutrosFeito = checkBoxOutrosRecebidos;
        }

        public JCheckBox getCheckBoxOutrosPendentes() {
            return checkBoxExcluirOutrosPendentes;
        }

        public void setCheckBoxOutrosPendentes(JCheckBox checkBoxOutrosPendentes) {
            this.checkBoxExcluirOutrosPendentes = checkBoxOutrosPendentes;
        }
    }
