package br.com.kernneo.desktop.view.financeiro;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.seguranca.AcaoModel;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.PaginacaoButtonBarComponent;
import javax.swing.Icon;
import java.awt.Dialog.ModalExclusionType;

public abstract class DatePickerDialog extends JDialog
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public JButton buttonOk = new JButton(Icone.novo("btOk.png"));

        private ArrayList<CidadeModel> listaDeCidades;
        private JLabel lblEntercSeleciona;

        private UtilDateModel model;
        private JPanel panel;

        private TitledBorder titledBorder;
        
        
        
        
        public UtilDateModel getModel() {
            return model;
        }
        public void setModel(UtilDateModel model) {
            this.model = model;
        }
        public void setTextBottom(String textBottom) { 
            lblEntercSeleciona.setText(textBottom);
        }
        public void setTitelBorder(String tituloBorderPanel) { 
           titledBorder.setTitle(tituloBorderPanel);
        }

        public DatePickerDialog() {
            setRootPaneCheckingEnabled(false);
            setResizable(false);
            getContentPane().setLayout(new BorderLayout(0, 1));
            setModal(true);
            setPreferredSize(new Dimension(250, 420));
            setSize(319, 190);

            JPanel panelSouth = new JPanel();
            getContentPane().add(panelSouth, BorderLayout.SOUTH);

            model = new UtilDateModel();
            model.setSelected(true);

            // model.setDate(20,04,2014);
            // Need this...
            Properties p = new Properties();
            p.put("text.today", "Hoje");
            p.put("text.month", "Mês");
            p.put("text.year", "Ano");

             panel = new JPanel();
            panel.setLayout(null);
            panel.setPreferredSize(new Dimension(100, 100));
            buttonOk.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getDataSelecionada(model.getValue());
                }
            });
            
            // buttonOk.setToolTipText("");
            buttonOk.setText("Ok");
            buttonOk.setBounds(24, 69, 125, 37);

            // panel.setBounds(10, 10, 321, 77);

            // panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,
            // 1, 1, 1, Color.red), BorderFactory.createEtchedBorder()));
             titledBorder = new TitledBorder(null, "Selecione uma Data", TitledBorder.LEADING, TitledBorder.TOP, null, null);
            panel.setBorder(titledBorder);

            // Don't know about the formatter, but there it is...
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            datePanel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // acaoObterPosicao();

                }
            });
            datePanel.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));

            // datePanel.setBounds(24, 64, 267, 193);

            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new br.com.kernneo.desktop.view.util.DateLabelFormatter());
            datePicker.setLocation(24, 21);
            datePicker.setSize(267, 37);
            datePicker.setTextEditable(true);
            datePicker.getJFormattedTextField().addKeyListener(new KeyListener() {
                
                @Override
                public void keyTyped(KeyEvent e) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                       getDataSelecionada(model.getValue());
                    }
                    
                }
            });
            panel.setLayout(null);
            panel.add(datePicker);
            panel.add(buttonOk);

            lblEntercSeleciona = new JLabel("Clique \"Ok\" para  ALTERAR");
            panelSouth.add(lblEntercSeleciona);

            panel.setBorder(new TitledBorder(null, "Selecione uma Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            getContentPane().add(panel, BorderLayout.CENTER);

            JButton buttonOk_1 = new JButton(Icone.novo("btCancelarOP.gif"));
            buttonOk_1.setText("Cancelar");
            buttonOk_1.setBounds(159, 69, 132, 37);
            buttonOk_1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();

                }
            });
            panel.add(buttonOk_1);

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                }
            });

        }

        public abstract void getDataSelecionada(Date dataSelecionada);

        public void centerOnScreen(final boolean absolute) {
            final int width = this.getWidth();
            final int height = this.getHeight();
            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width / 2) - (width / 2);
            int y = (screenSize.height / 2) - (height / 2);
            if (!absolute) {
                x /= 2;
                y /= 2;
            }
            this.setLocation(x, y);
        }

// Center on parent ( absolute true/false (exact center or 25% upper left) )
        public void centerOnParent(final Window child, final boolean absolute) {
            child.pack();
            boolean useChildsOwner = child.getOwner() != null ? ((child.getOwner() instanceof JFrame) || (child.getOwner() instanceof JDialog)) : false;
            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            final Dimension parentSize = useChildsOwner ? child.getOwner().getSize() : screenSize;
            final Point parentLocationOnScreen = useChildsOwner ? child.getOwner().getLocationOnScreen() : new Point(0, 0);
            final Dimension childSize = child.getSize();
            childSize.width = Math.min(childSize.width, screenSize.width);
            childSize.height = Math.min(childSize.height, screenSize.height);
            child.setSize(childSize);
            int x;
            int y;
            if ((child.getOwner() != null) && child.getOwner().isShowing()) {
                x = (parentSize.width - childSize.width) / 2;
                y = (parentSize.height - childSize.height) / 2;
                x += parentLocationOnScreen.x;
                y += parentLocationOnScreen.y;
            } else {
                x = (screenSize.width - childSize.width) / 2;
                y = (screenSize.height - childSize.height) / 2;
            }
            if (!absolute) {
                x /= 2;
                y /= 2;
            }
            child.setLocation(x, y);
        }
    }
