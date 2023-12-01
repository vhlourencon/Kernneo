package br.com.kernneo.desktop.view.veiculo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import net.miginfocom.swing.MigLayout;

public class VeiculoFormCadPanel extends GenericFormCadPanel<VeiculoModel>
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private JTextField textFieldNome;

        public VeiculoFormCadPanel() {
            initialize();

        }

        private void initialize() {

            // setSize(379, 100);
            setLayout(null);

            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "Informações do Veículo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
        public VeiculoModel getModel() {
            model.setNome(textFieldNome.getText());

            return model;
        }

        @Override
        public void setModel(VeiculoModel model) {

            if (model == null) {
                model = new VeiculoModel();
            }

            super.setModel(model);
            textFieldNome.setText(model.getNome());
            textFieldNome.requestFocus();

        }
    }
