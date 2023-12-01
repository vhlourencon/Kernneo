package br.com.kernneo.desktop.view.cidade;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class CidadeFiltroPanel extends GenericFiltroPanel<CidadeModel>
    {

        private JTextField textField;

        public CidadeFiltroPanel() {
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
        public CidadeModel getModelFiltro() {
            if (modelFiltro == null) {
                setModelFiltro(new CidadeModel());
                modelFiltro.setNome("");
            }
            modelFiltro.setNome(textField.getText());

            return modelFiltro;
        }

    }
