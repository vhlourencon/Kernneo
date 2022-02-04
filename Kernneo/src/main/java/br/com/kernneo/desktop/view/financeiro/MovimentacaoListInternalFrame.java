package br.com.kernneo.desktop.view.financeiro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Departamento;
import br.com.kernneo.server.negocio.Movimentacao;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MovimentacaoListInternalFrame extends GenericListInternalFrame<Movimentacao, MovimentacaoModel>
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private JScrollPane jscrollPaneFooter;

        public MovimentacaoListInternalFrame() {
            table.setModel(new MovimentacaoListTableModel());
            // setFormCadPanel(new DepartamentoFormCadPanel());
            setNegocio(new Movimentacao());
            setModel(new MovimentacaoModel());
            setPagina(new GenericPagina<MovimentacaoModel>());
            //setColunasDaTabela(new String[] { "id", "Descrição", "Conta", "Tipo", "Valor", "Pago/Recebido", "Data Pago/Recebido" });
           
            table.getColumnModel().getColumn(0).setResizable(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            table.setDefaultRenderer(Object.class, dtcr);
           
            buttonBarComponent.removeAll();

            buttonBarComponent.setLayout(new MigLayout("", "[105px][105px][105px]", "[40px]"));

            // buttonBarComponent.btAlterar.setp
            buttonBarComponent.add(buttonBarComponent.btConsultar, "cell 0 0,grow");
            buttonBarComponent.add(buttonBarComponent.btImprimir, "cell 1 0,grow");
            buttonBarComponent.add(buttonBarComponent.btSair, "cell 2 0,grow");
            
            JTable footerTable = new JTable();
            footerTable.setFillsViewportHeight(true);
            footerTable.setColumnModel(table.getColumnModel());
            // Disable selection in the footer. Otherwise you can select the footer row
            // along with a row in the table and that can look quite strange.
            footerTable.setRowSelectionAllowed(false);
            footerTable.setColumnSelectionAllowed(false);
            footerTable.setTableHeader(null);
            footerTable.setRowHeight(24);
           

           
            repaint();
            validate();
            
           
            
             jscrollPaneFooter = new JScrollPane(footerTable);
             jscrollPaneFooter.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
            // jscrollPaneFooter.setBorder(new CompoundBorder(new
            // MatteBorder(1,1,1,1,Color.gray),new EmptyBorder(0,0,0,0)));
            jscrollPaneFooter.setPreferredSize(new Dimension(-1, 70));
            
            BorderLayout bl_holdingPanel = new BorderLayout();
            bl_holdingPanel.setVgap(5);
            panelCenter.setLayout(bl_holdingPanel);
                
                panelCenter.add(getjScrollPaneTable(),BorderLayout.CENTER);
                panelCenter.add(jscrollPaneFooter, BorderLayout.SOUTH);
                
               // getContentPane().add(panelCenter, BorderLayout.CENTER);
               // getContentPane().add(buttonBarComponent, BorderLayout.NORTH);
               

           
            //JPanel holdingPanel = new JPanel(bl_holdingPanel);
          //  holdingPanel.add(getjScrollPaneTable(), BorderLayout.CENTER);
          //  holdingPanel.add(jscrollPaneFooter, BorderLayout.SOUTH);
        //    getPanelCenter().setLayout(bl_holdingPanel);
        //    getPanelCenter().removeAll();
           
            
  
            
                setGenericFiltroPanel(new MovimentacaoFiltroPanel(), new MovimentacaoModel(), true);

            repaint();
            buttonBarComponent.repaint();
            getContentPane().repaint();
            validate();
            // acaoIrPrimeiraPagina();
            // setm

        }
        
       

       


        @Override
        public Object[] modelToRow(MovimentacaoModel movimentacaoModel) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String id = String.valueOf(movimentacaoModel.getId());
            String descricao = movimentacaoModel.getDescricao();
            String tipo = "";
            String conta = "";
            if (movimentacaoModel.getConta() != null) {
                conta = movimentacaoModel.getConta().getNome();
            }
            if (movimentacaoModel.getTipo() != null) {
                tipo = movimentacaoModel.getTipo().toString();
            }
            String dataHoraExecutado = "";
            if (movimentacaoModel.getDataHoraExecutado() != null) {
                dataHoraExecutado = formatter.format(movimentacaoModel.getDataHoraExecutado());
            }

            Object[] row = new Object[] { id, descricao, conta, tipo, movimentacaoModel.currencyFormat(movimentacaoModel.getValor()), movimentacaoModel.isExecutado(), dataHoraExecutado };
            return row;

        }

        public class MovimentacaoListTableModel extends DefaultTableModel
            {
              
                /**
             * 
             */
            private static final long serialVersionUID = 1L;

                public MovimentacaoListTableModel() {
                    super(new String[] { "id", "Descrição", "Conta", "Tipo", "Valor", "Pago/Recebido", "Data Pago/Recebido"}, 0);
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    Class clazz = String.class;
                    switch (columnIndex) {

                    case 5:
                        clazz = Boolean.class;
                        break;
                    }
                    return clazz;
                }

            }

    }
