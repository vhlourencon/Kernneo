package br.com.kernneo.desktop.view.hospital;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import net.miginfocom.swing.MigLayout;

public class HospitalFormCadPanel extends GenericFormCadPanel<HospitalModel>
    {
       
        private static final long serialVersionUID = 1L;
        private JTextField textFieldNome;
        private TitledBorder titledBorder; 

        public HospitalFormCadPanel() {
            initialize();

        }

        private void initialize() {

            // setSize(379, 100);
            setLayout(null);
            
            titledBorder = new TitledBorder(null, "Informações do Hospital", TitledBorder.LEADING, TitledBorder.TOP, null, null);

            JPanel panel = new JPanel();
            panel.setBorder(titledBorder);
            panel.setBounds(10, 11, 446, 99);
            add(panel, "cell 0 0");
            panel.setLayout(null);

            JLabel labelNome = new JLabel("Nome:");
            labelNome.setBounds(20, 26, 46, 14);
            panel.add(labelNome);

            textFieldNome = new JTextField();
            textFieldNome.setBounds(20, 43, 400, 30);

            panel.add(textFieldNome);
            textFieldNome.setColumns(50);

        }

        public JTextField getTextFieldDescricao() {
            return textFieldNome;
        }

        public void setTextFieldDescricao(JTextField textFieldDescricao) {
            this.textFieldNome = textFieldDescricao;
        }

        @Override
        public HospitalModel getModel() {
            model.setNome(textFieldNome.getText());

            return model;
        }

        @Override
        public void setModel(HospitalModel model) {

            if (model == null) {
                model = new HospitalModel();
            }

            super.setModel(model);
            textFieldNome.setText(model.getNome());
            textFieldNome.requestFocus();
            textFieldNome.selectAll();
        }
        
        public TitledBorder getTitledBorder() {
    		return titledBorder;
    	}
    }
