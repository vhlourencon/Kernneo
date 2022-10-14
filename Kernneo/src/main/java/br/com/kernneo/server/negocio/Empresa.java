package br.com.kernneo.server.negocio;

import br.com.kernneo.client.exception.EmpresaException;
import br.com.kernneo.client.exception.NFCeException;
import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.server.dao.EmpresaDAO;

public class Empresa extends Negocio<EmpresaModel, EmpresaDAO, EmpresaException> {

  

   

    public Empresa() {
	super();
	dao = new EmpresaDAO();
    }

    @Override
    public EmpresaException validar(EmpresaModel vo) {
	StringBuffer msg = new StringBuffer("");

	try {
	    Long ultimoId = obterUltimoId(EmpresaModel.class);
	    EmpresaModel empresaModel = obterPorId(EmpresaModel.class, ultimoId);
	    if (empresaModel != null && vo.getId() != empresaModel.getId()) {
		msg.append("Empresa ja cadastrada");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	if (msg.length() > 0)
	    return new EmpresaException(msg.toString());
	else
	    return null;
    }

    public EmpresaModel obterEmpresa() throws EmpresaException {
	try {
	    Long ultimoId = obterUltimoId(EmpresaModel.class);
	    EmpresaModel empresaModel = obterPorId(EmpresaModel.class, ultimoId);
	    if (empresaModel == null) {
		empresaModel = new EmpresaModel();
	    }
	    return empresaModel;
	} catch (Exception e) {
	    throw new EmpresaException("Ocorreu um erro ao tentar obter Id:\n" + e.getMessage());
	}

    }

    @Override
    public String getSqlFiltro(EmpresaModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
	String deletado = "and deletado = false";
	filtro += " where 1=1 " + deletado;

	if (vo.getFantasia() != null && vo.getFantasia().trim().length() > 0) {
	    filtro += " and fantasia like('%" + vo.getFantasia() + "%')";
	}

	filtro += " order by id asc";

	return filtro;
    }

    public EmpresaModel obterPorId(String id) throws EmpresaException {
	try {
	    EmpresaModel model = dao.obterPorId(id);
	    return model;
	} catch (Exception e) {
	    throw new EmpresaException("Ocorreu um erro ao tentar obter Id:\n" + e.getMessage());
	}

    }
}
