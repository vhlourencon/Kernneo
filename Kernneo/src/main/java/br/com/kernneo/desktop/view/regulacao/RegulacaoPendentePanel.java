package br.com.kernneo.desktop.view.regulacao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;

import br.com.kernneo.client.model.RegulacaoModel;



public class RegulacaoPendentePanel extends JPanel
    {
        private JTable tableSaldo;
        private JTable footerTable;
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
		private JPopupMenu popup;
		private JMenuItem menuItemCopy;
		private static int rowIndex;
		private static int columnIndex;

        public RegulacaoPendentePanel(RegulacaoInternalFrame regulacaoInternalFrame) {
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
            
            
            String[] colunasTableSaldo = new String[] { "Código", "Cidade", "Especialidade", "Local de Atendimento", "Data", "Qtd. Dias" };
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
            
           
  
            add(jPanelSaldo, BorderLayout.CENTER);
            add(jscrollPaneFooter, BorderLayout.SOUTH);
            
            popup = new JPopupMenu();
            popup.setName("popupMenu");
            menuItemCopy = new JMenuItem();
            menuItemCopy.setText("Copiar");
            menuItemCopy.setName("Copiar");
            menuItemCopy.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    StringSelection stringSelection = new StringSelection(String.valueOf(tableSaldo
                            .getModel().getValueAt(rowIndex, columnIndex)));
                    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clpbrd.setContents(stringSelection, null);
                }
            });
            popup.add(menuItemCopy);
            setupPopupMenu(tableSaldo, popup);
            
        }

        public void setListaDeMovimentacoes(List<RegulacaoModel> listaDeMovimentacaoModels) {
            DefaultTableModel defaultTableModel = (DefaultTableModel) tableSaldo.getModel();
            defaultTableModel.setRowCount(0);
            
            DefaultTableModel defaultTableModelVencidas = (DefaultTableModel) footerTable.getModel();
            defaultTableModelVencidas.setRowCount(0);

            
            BigDecimal bigDecimalSaldo = new BigDecimal(BigInteger.ZERO);
            for (RegulacaoModel model : listaDeMovimentacaoModels) {
                String cidade = model.getCidade() == null  ? "" : model.getCidade().getNome(); 
                String categoria = model.getEspecialidade() == null ? "" : model.getEspecialidade().getCategoria_nome_portugues();
                if (categoria == null) {
                    categoria = "";
                }
                if(cidade == null )  {
                	cidade = ""; 
                }
                String local = model.getHospital() == null ? "" : model.getHospital().getNome();
                String data= "";
                String qtdDias = "";
                if(model.getDataHora() != null) { 
                    data=   simpleDateFormat.format(model.getDataHora());
                    qtdDias = String.valueOf(getDifferenceDays( model.getDataHora(),new Date())); 
                }
               // bigDecimalSaldo = bigDecimalSaldo.add(model.getValor());
                defaultTableModel.addRow(new String[] { model.getCodigo(),cidade,  categoria, local,   data, qtdDias });
            }
            defaultTableModelVencidas.addRow((new String[] { "<HTML><B>TOTAL de PENDENTES: " + listaDeMovimentacaoModels.size() + "</B></HTML> " }));

        }
        
        public static long getDifferenceDays(Date d1, Date d2) {
            long diff = d2.getTime() - d1.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }

        
        
         
        public static void setupPopupMenu(final JTable table,
                final JPopupMenu popupMenu) {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        int r = table.rowAtPoint(e.getPoint());
                        if (r >= 0 && r < table.getRowCount()) {
                        	rowIndex = table.rowAtPoint(e.getPoint());
                            columnIndex = table.columnAtPoint(e.getPoint());

                            popupMenu.show(table, e.getX(), e.getY());
                        } else {
                            table.clearSelection();
                        }
                    }
                }
            });
        }
        
        
    }



