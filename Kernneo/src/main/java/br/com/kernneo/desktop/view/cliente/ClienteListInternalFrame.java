package br.com.kernneo.desktop.view.cliente;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Cliente;

public class ClienteListInternalFrame extends GenericListInternalFrame<Cliente, ClienteModel>
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public ClienteListInternalFrame() {

            setFormCadPanel(new ClienteFormCadPanel());
            setNegocio(new Cliente());
            setModel(new ClienteModel());
            setPagina(new GenericPagina<ClienteModel>());
            setColunasDaTabela(new String[] { "cod", "Nome" });
            setGenericFiltroPanel(new ClienteFiltroPanel(), new ClienteModel(), false);
            setTitle("Cadastro de Clientes");

            table.getColumnModel().getColumn(0).setResizable(true);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        }

        @Override
        public String[] modelToRow(ClienteModel model) {
            String id = String.valueOf(model.getId());
            String descricao = model.getNome();

            String[] row = new String[] { id, descricao };
            return row;

        }
        
      

    }
