package br.com.kernneo.desktop.view.veiculo;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.Veiculo;

public class VeiculoListInternalFrame extends GenericListInternalFrame<Veiculo, VeiculoModel>
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public VeiculoListInternalFrame() {

            setFormCadPanel(new VeiculoFormCadPanel());
            setNegocio(new Veiculo());
            setModel(new VeiculoModel());
            setPagina(new GenericPagina<VeiculoModel>());
            setColunasDaTabela(new String[] { "cod", "Nome" });
            setGenericFiltroPanel(new VeiculoFiltroPanel(), new VeiculoModel(), false);
            setTitle("Cadastro de Veículos");

            table.getColumnModel().getColumn(0).setResizable(true);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        }

        @Override
        public String[] modelToRow(VeiculoModel model) {
            String id = String.valueOf(model.getId());
            String nome = model.getNome();

            String[] row = new String[] { id, nome };
            return row;

        }

    }
