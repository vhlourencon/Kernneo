package br.com.kernneo.server.negocio;

import java.sql.SQLException;

import br.com.kernneo.client.exception.SubGrupoException;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.server.dao.SubGrupoDAO;

public class SubGrupo extends Negocio<SubGrupoModel, SubGrupoDAO, SubGrupoException> {

    public SubGrupo() {
	super();
	dao = new SubGrupoDAO();
    }

   

    @Override
    public SubGrupoException validar(SubGrupoModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.getDescricao() == null || vo.getDescricao().trim().length() == 0) {
	    msg.append("O campo DESCRIÇÃO  é de preenchimento obrigatório! \n");
	}

	if (vo.getGrupo() == null) {
	    msg.append("O campo GRUPO  é de preenchimento obrigatório! \n");
	}

	if (msg.length() > 0)
	    return new SubGrupoException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(SubGrupoModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " where 1=1 ";

	if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) {
	    filtro += " and descricao like('%" + vo.getDescricao() + "%')";
	}

	if (vo.getGrupo() != null) {
	    filtro += "where id_grupo=" + vo.getGrupo().getId();
	}

	filtro += " order by id asc";

	System.out.println(filtro);

	return filtro;
    }

}
