package br.com.kernneo.desktop.view.cliente;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class ClienteFiltroPanel extends GenericFiltroPanel<ClienteModel>
    {

        private JComboBox comboBox;
        private JTextField textField;

        public ClienteFiltroPanel() {
            buttonOk.setSize(77, 30);
            buttonOk.setLocation(325, 40);

            setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            setLayout(null);
            setPreferredSize(new Dimension(536, 99));

            JLabel lblNewLabel_1 = new JLabel("Descrição:");
            lblNewLabel_1.setBounds(23, 23, 101, 14);
            add(lblNewLabel_1);

            textField = new JTextField();
            textField.setBounds(23, 40, 292, 30);
            add(textField);
            textField.setColumns(10);
        }

        @Override
        public ClienteModel getModelFiltro() {
            if (modelFiltro == null) {
                setModelFiltro(new ClienteModel());

                 modelFiltro.setNome("");
            }
            modelFiltro.setNome(textField.getText());

            return modelFiltro;
        }

    }
