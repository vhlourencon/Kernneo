package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.HospitalException;
import br.com.kernneo.client.exception.VeiculoException;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.server.dao.HospitalDAO;
import br.com.kernneo.server.dao.VeiculoDAO;

public class Hospital extends Negocio<HospitalModel, HospitalDAO, HospitalException>
    {
        public Hospital() {
            super();
            dao = new HospitalDAO();
        }

        @Override
        public HospitalException validar(HospitalModel vo) {
            StringBuffer msg = new StringBuffer("");
            if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
                msg.append("O campo NOME  é de preenchimento obrigatório! \n");
            }

            if (msg.length() > 0) 
                return new HospitalException(msg.toString());
            else
                return null;
        }

        @Override
        public String getSqlFiltro(HospitalModel vo) {
        	String filtro = super.getSqlFiltro(vo);

            if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
                filtro += " and nome like('%" + vo.getNome() + "%')";
            }

            filtro += " order by id asc";

            return filtro;
        }

    }
