package br.com.kernneo.desktop.view.cidade;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.Hospital;
import br.com.kernneo.server.negocio.Veiculo;

public class CidadeListInternalFrame extends GenericListInternalFrame<Cidade, CidadeModel>
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public CidadeListInternalFrame() {

            setFormCadPanel(new CidadeFormCadPanel());
            setNegocio(new Cidade());
            setModel(new CidadeModel());
            setPagina(new GenericPagina<CidadeModel>());
            setColunasDaTabela(new String[] { "cod", "Nome" });
            setGenericFiltroPanel(new CidadeFiltroPanel(), new CidadeModel(), false);
            setTitle("Cadastro de Cidade");

            table.getColumnModel().getColumn(0).setResizable(true);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        }

        @Override
        public String[] modelToRow(CidadeModel model) {
            String id = String.valueOf(model.getId());
            String nome = model.getNome();

            String[] row = new String[] { id, nome };
            return row;

        }

    }
