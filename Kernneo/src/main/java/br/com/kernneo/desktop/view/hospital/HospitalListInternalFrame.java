package br.com.kernneo.desktop.view.hospital;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.Hospital;
import br.com.kernneo.server.negocio.Veiculo;

public class HospitalListInternalFrame extends GenericListInternalFrame<Hospital, HospitalModel>
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public HospitalListInternalFrame() {

            setFormCadPanel(new HospitalFormCadPanel());
            setNegocio(new Hospital());
            setModel(new HospitalModel());
            setPagina(new GenericPagina<HospitalModel>());
            setColunasDaTabela(new String[] { "cod", "Nome" });
            setGenericFiltroPanel(new HospitalFiltroPanel(), new HospitalModel(), false);
            setTitle("Cadastro de Hospital");

            table.getColumnModel().getColumn(0).setResizable(true);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        }

        @Override
        public String[] modelToRow(HospitalModel model) {
            String id = String.valueOf(model.getId());
            String nome = model.getNome();

            String[] row = new String[] { id, nome };
            return row;

        }

    }
