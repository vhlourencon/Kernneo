package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.CargoException;
import br.com.kernneo.client.model.CargoModel;
import br.com.kernneo.server.dao.CargoDAO;

public class Cargo extends Negocio<CargoModel, CargoDAO, CargoException> {

    public Cargo() {
	super();
	dao = new CargoDAO();
    }

    @Override
    public CargoException validar(CargoModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo Descricao  é de preenchimento obrigatório!");
	}

	if (msg.length() > 0)
	    return new CargoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CargoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	
	String deletado = "and deletado = false";
	filtro += " where 1=1 " + deletado; 

	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) {
	    filtro += " and descricao like('%" + vo.getDescricao() + "%')";
	}

	filtro += " order by id asc";

	return filtro;
    }

}
