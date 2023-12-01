package br.com.kernneo.desktop.view.hospital;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class HospitalFiltroPanel extends GenericFiltroPanel<HospitalModel>
    {

        private JTextField textField;

        public HospitalFiltroPanel() {
            buttonOk.setSize(77, 30);
            buttonOk.setLocation(325, 40);

            setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            setLayout(null);
            setPreferredSize(new Dimension(536, 99));

            JLabel lblNewLabel_1 = new JLabel("Nome:");
            lblNewLabel_1.setBounds(23, 23, 101, 14);
            add(lblNewLabel_1);

            textField = new JTextField();
            textField.setBounds(23, 40, 292, 30);
            add(textField);
            textField.setColumns(10);
        }

        @Override
        public HospitalModel getModelFiltro() {
            if (modelFiltro == null) {
                setModelFiltro(new HospitalModel());
                modelFiltro.setNome("");
            }
            modelFiltro.setNome(textField.getText());

            return modelFiltro;
        }

    }
