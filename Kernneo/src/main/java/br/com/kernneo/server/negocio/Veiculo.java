package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.VeiculoException;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.server.dao.VeiculoDAO;

public class Veiculo extends Negocio<VeiculoModel, VeiculoDAO, VeiculoException>
    {
        public Veiculo() {
            super();
            dao = new VeiculoDAO();
        }

        @Override
        public VeiculoException validar(VeiculoModel vo) {
            StringBuffer msg = new StringBuffer("");
            if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
                msg.append("O campo NOME  é de preenchimento obrigatório! \n");
            }

            if (msg.length() > 0) 
                return new VeiculoException(msg.toString());
            else
                return null;
        }

        @Override
        public String getSqlFiltro(VeiculoModel vo) {
            String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
            filtro += " where 1=1 ";

            if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
                filtro += " and nome like('%" + vo.getNome() + "%')";
            }

            filtro += " order by id asc";

            return filtro;
        }

    }
