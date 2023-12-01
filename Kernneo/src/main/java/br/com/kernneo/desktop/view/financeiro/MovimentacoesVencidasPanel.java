package br.com.kernneo.desktop.view.financeiro;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.utils.StringUtils;
import br.com.kernneo.desktop.view.widget.Icone;

import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

import javax.swing.JButton;



public class MovimentacoesVencidasPanel extends JPanel
    {
        private JTable tableSaldo;
        private JTable footerTable;
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        private JComboBox<CategoriaModel> comboBoxCategoria;
        private JComboBox<ClienteModel> comboBoxCliente;

        public MovimentacoesVencidasPanel(MovimentacaoInternalFrame movimentacaoInternalFrame) {
            tableSaldo = new JTable();
            tableSaldo.setFillsViewportHeight(true);
            tableSaldo.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            DefaultTableCellRenderer dtcrFooter = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel parent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    parent.getFont().deriveFont(Font.BOLD);
                    return parent;

                }
            };

            footerTable = new JTable();
            footerTable.setFillsViewportHeight(true);
            
            footerTable.setRowSelectionAllowed(false);
            footerTable.setColumnSelectionAllowed(false);
            footerTable.setTableHeader(null);
            footerTable.setRowHeight(24);
            
            
            String[] colunasTableSaldo = new String[] { "Categoria", "Cliente", "Tipo", "Data", "Qtd. Dias", "Valor" };
            tableSaldo.setModel(new DefaultTableModel(new Object[][] {
            }, colunasTableSaldo) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });

            String[] colunasTableTotal = new String[] { "Total" };
            
            footerTable.setModel(new DefaultTableModel(new Object[][] {
            }, colunasTableTotal) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });

            footerTable.setDefaultRenderer(String.class, dtcrFooter);
            // footerTable.getTableHeader()

            JScrollPane jPanelSaldo = new JScrollPane(tableSaldo);

            JScrollPane jscrollPaneFooter = new JScrollPane(footerTable);
            jscrollPaneFooter.setPreferredSize(new Dimension(-1, 30));

            BorderLayout bl_holdingPanel = new BorderLayout();
            bl_holdingPanel.setVgap(3);
            setLayout(bl_holdingPanel);
            
            
            JPanel jPanelFiltro = new JPanel();
            jPanelFiltro.setBorder(new TitledBorder(null, "Filtrar Por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            jPanelFiltro.setPreferredSize(new Dimension(180, -1));
            
           
  
            add(jPanelSaldo, BorderLayout.CENTER);
            add(jscrollPaneFooter, BorderLayout.SOUTH);
            add(jPanelFiltro, BorderLayout.EAST);
            jPanelFiltro.setLayout(null);
            
            JLabel lblNewLabel_1_2 = new JLabel("Categoria:");
            lblNewLabel_1_2.setBounds(12, 23, 101, 14);
            jPanelFiltro.add(lblNewLabel_1_2);
            
            comboBoxCategoria = new JComboBox();
            comboBoxCategoria.setBounds(10, 38, 160, 40);
            AutoCompleteDecorator.decorate(comboBoxCategoria);
            jPanelFiltro.add(comboBoxCategoria);
            
            JLabel lblNewLabel_1_2_1_1 = new JLabel("Conta:");
            lblNewLabel_1_2_1_1.setBounds(12, 94, 101, 14);
            jPanelFiltro.add(lblNewLabel_1_2_1_1);
            
             comboBoxCliente = new JComboBox();
             comboBoxCliente.setBounds(12, 109, 160, 40);
             AutoCompleteDecorator.decorate(comboBoxCliente);
       
            jPanelFiltro.add(comboBoxCliente);
            
        }

        public void setListaDeMovimentacoes(List<MovimentacaoModel> listaDeMovimentacaoModels) {
            DefaultTableModel defaultTableModel = (DefaultTableModel) tableSaldo.getModel();
            defaultTableModel.setRowCount(0);
            
            DefaultTableModel defaultTableModelVencidas = (DefaultTableModel) footerTable.getModel();
            defaultTableModelVencidas.setRowCount(0);

            
            BigDecimal bigDecimalSaldo = new BigDecimal(BigInteger.ZERO);
            for (MovimentacaoModel model : listaDeMovimentacaoModels) {
                String cliente = model.getCliente() == null  ? "" : model.getCliente().getNome(); 
                String categoria = model.getCategoria() == null ? "" : model.getCategoria().getCategoria_nome_portugues();
                if (categoria == null) {
                    categoria = "";
                }
                if(cliente == null )  {
                    cliente = ""; 
                }
                String tipo = model.getTipo() == null ? "" : model.getTipo().toString();
                String data= "";
                String qtdDias = "";
                if(model.getDataHora() != null) { 
                    data=   simpleDateFormat.format(model.getDataHora());
                    qtdDias = String.valueOf(getDifferenceDays( model.getDataHora(),new Date())); 
                }
                bigDecimalSaldo = bigDecimalSaldo.add(model.getValor());
                defaultTableModel.addRow(new String[] {  categoria, cliente, tipo, data, qtdDias , StringUtils.currencyFormat(model.getValor())});
            }
            defaultTableModelVencidas.addRow((new String[] { "<HTML><B>TOTAL: " + StringUtils.currencyFormat(bigDecimalSaldo)+ "</B></HTML> " }));


        }
        
        public static long getDifferenceDays(Date d1, Date d2) {
            long diff = d2.getTime() - d1.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }

        public JComboBox<CategoriaModel> getComboBoxCategoria() {
            return comboBoxCategoria;
        }

        public void setComboBoxCategoria(JComboBox<CategoriaModel> comboBoxCategoria) {
            this.comboBoxCategoria = comboBoxCategoria;
        }

        public JComboBox<ClienteModel> getComboBoxCliente() {
            return comboBoxCliente;
        }

        public void setComboBoxCliente(JComboBox<ClienteModel> comboBoxCliente) {
            this.comboBoxCliente = comboBoxCliente;
        }
        
         
        
        
    }



