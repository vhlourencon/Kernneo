package br.com.kernneo.desktop.view.contabancaria;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.hibernate.cfg.Settings;

import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import br.com.kernneo.desktop.view.widget.JMoedaRealTextField;
import br.com.kernneo.desktop.view.widget.JMoneyField;
import javax.swing.SwingConstants;

public class ContaBancariaFormCadPanel extends GenericFormCadPanel<ContaBancariaModel>
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private JTextField textFieldDescricao;
        private JFormattedTextField textField;
        private DecimalFormat dFormat;
        private JFormattedTextField textFieldChequeEspecial;

        public ContaBancariaFormCadPanel() {
            initialize();

        }

        private void initialize() {

            setSize(536, 181);
            setLayout(null);

            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de ContaBancaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setBounds(10, 11, 516, 159);
            add(panel);
            panel.setLayout(null);

            JLabel labelNome = new JLabel("Nome:");
            labelNome.setBounds(10, 26, 46, 14);
            panel.add(labelNome);

            textFieldDescricao = new JTextField();
            textFieldDescricao.setBounds(10, 42, 438, 30);
            panel.add(textFieldDescricao);
            textFieldDescricao.setColumns(10);

            JLabel lblNewLabel = new JLabel("Saldo Inicial:");
            lblNewLabel.setBounds(10, 84, 85, 14);
            panel.add(lblNewLabel);

            dFormat = new DecimalFormat("#,##0.00");
            NumberFormatter Formatter = new NumberFormatter(dFormat);
            Formatter.setFormat(dFormat);
            Formatter.setAllowsInvalid(false);

          
            textField = new JFormattedTextField();
            textField.setHorizontalAlignment(JTextField.RIGHT);  
            textField.setFormatterFactory(new DefaultFormatterFactory(Formatter));
            textField.setBounds(10, 101, 126, 30);
            panel.add(textField);
            textField.setColumns(10);
            
            JLabel lblChequeEspecial = new JLabel("Cheque Especial:");
            lblChequeEspecial.setBounds(146, 83, 85, 14);
            panel.add(lblChequeEspecial);
            
             textFieldChequeEspecial = new JFormattedTextField();
            textFieldChequeEspecial.setHorizontalAlignment(SwingConstants.RIGHT);
            textFieldChequeEspecial.setColumns(10);
            textFieldChequeEspecial.setFormatterFactory(new DefaultFormatterFactory(Formatter));
            textFieldChequeEspecial.setBounds(146, 100, 126, 30);
            panel.add(textFieldChequeEspecial);

        }

        @Override
        public ContaBancariaModel getModel() {
            model.setNome(textFieldDescricao.getText());
            model.getMovimentacaoInicial().setValor(BigDecimal.valueOf(getValor(textField)));
            model.setChequeEspecial(BigDecimal.valueOf(getValor(textFieldChequeEspecial)));
            return model;
        }

        public Double getValor(JFormattedTextField formattedTextField) {
            String text = formattedTextField.getText();  
            
            String valor = text.replaceAll("\\D+", "");
            valor = valor.substring(0, valor.length() - 2) + "." + valor.substring(valor.length() - 2);
            Double valorDouble = Double.valueOf(valor);
            if(text.charAt(0) == '-') { 
                valorDouble = valorDouble * (-1);
            }

            return valorDouble;
        }
        
       


      

        @Override
        public void setModel(ContaBancariaModel model) {

            if (model == null) {
                model = new ContaBancariaModel();
            }

            super.setModel(model);

            textFieldDescricao.setText(model.getNome());
             textField.setValue(model.getMovimentacaoInicial().getValor().doubleValue());
             textFieldChequeEspecial.setValue(model.getChequeEspecial());
             System.out.println(model.getChequeEspecial());
         //   setValor(model.getMovimentacaoInicial().getValor());

        }
    }
