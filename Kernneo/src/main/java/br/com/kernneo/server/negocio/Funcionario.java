package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.kernneo.client.exception.EnderecoFuncionarioException;
import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.EnderecoFuncionarioException;
import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.FuncionarioDAO;
import br.com.kernneo.server.dao.FuncionarioDAO;
import br.com.kernneo.server.dao.FuncionarioDAO;

public class Funcionario extends Negocio<FuncionarioModel, FuncionarioDAO, FuncionarioException> {

	public Funcionario() {
		super();
		dao = new FuncionarioDAO();
	}

	public GenericPagina<FuncionarioModel> buscaPaginaAnteriorComModelCompleto(
			GenericPagina<FuncionarioModel> paginaAtual, FuncionarioModel funcionarioModel)
			throws FuncionarioException {
		GenericPagina<FuncionarioModel> pagina = new Funcionario().obterPaginaAnterior(paginaAtual, funcionarioModel);
		for (FuncionarioModel funcionario : pagina.getListaRegistros()) {
			try {
				funcionario.setEnderecoFuncionario(new EnderecoFuncionario().obterEnderecosPorFuncionario(funcionario));
			} catch (EnderecoFuncionarioException e) {
				e.printStackTrace();
			}
		}

		return pagina;
	}

	public GenericPagina<FuncionarioModel> buscaPaginaPosteriorComModelCompleto(
			GenericPagina<FuncionarioModel> paginaAtual, FuncionarioModel funcionarioModel)
			throws FuncionarioException {
		GenericPagina<FuncionarioModel> pagina = new Funcionario().obterPaginaPosterior(paginaAtual, funcionarioModel);
		for (FuncionarioModel funcionario : pagina.getListaRegistros()) {
			try {
				funcionario.setEnderecoFuncionario(new EnderecoFuncionario().obterEnderecosPorFuncionario(funcionario));
			} catch (EnderecoFuncionarioException e) {
				e.printStackTrace();
			}
		}
		return pagina;
	}

	@Override
	public FuncionarioException validar(FuncionarioModel vo) {

		StringBuffer msg = new StringBuffer("");
		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome é de preenchimento obrigatório!");
		}

		if (vo.isAtivo()) {
			boolean confirmaSenha = true;
			boolean senha = true;
			if (vo.getSenhaTemp() == null || vo.getSenhaTemp().trim().length() == 0) {
				msg.append("O campo Senha é de preenchimento obrigatório!");
				senha = false;
			}
			if (vo.getConfirmaSenhaTemp() == null || vo.getConfirmaSenhaTemp().trim().length() == 0) {
				msg.append("O campo Confirma Senha é de preenchimento obrigatório!");
				confirmaSenha = false;
			}
			if (confirmaSenha && senha) {
				if (vo.getSenhaTemp().equals(vo.getConfirmaSenhaTemp()) == false) {
					msg.append("O campo Senha deve ser Igual ao Confirma Senha!");
				}
			}

			if (vo.getLogin() == null || vo.getLogin().trim().length() == 0) {
				msg.append("O campo Login é de preenchimento obrigatório!");
			} else {
				FuncionarioModel funcionarioOld = dao.obterPorLogin(vo.getLogin());
				if (funcionarioOld != null) {
					if (vo.getId() == null) {
						msg.append("Ja existe um usuario cadastrado com esse Login!");
					} else {
						if (funcionarioOld.getId().compareTo(vo.getId()) != 0) {
							msg.append("Ja existe um usuario cadastrado com esse Login!");
						}
					}
				}
			}

		}

		if (msg.length() > 0)
			return new FuncionarioException(msg.toString());
		else
			return null;
	}

	public FuncionarioModel salvarComEndereco(FuncionarioModel vo) throws FuncionarioException {
		FuncionarioException exc = validar(vo);
		if (exc == null) {
			try {
				FuncionarioDAO funcionarioDao = new FuncionarioDAO();
				funcionarioDao.salvarComEndereco(vo);
				return vo;
			} catch (Exception e) {
				throw (FuncionarioException) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	public ArrayList<FuncionarioModel> obterFuncionarioComEnderecoFiltro(FuncionarioModel filtroModel)
			throws FuncionarioException {

		FuncionarioDAO dao = new FuncionarioDAO();
		ArrayList<FuncionarioModel> lista = null;
		try {
			lista = dao.obterTodosComEnderecoComFiltro(getSqlFiltro(filtroModel));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (FuncionarioModel model : lista) {
			try {
				model.setEnderecoFuncionario(new EnderecoFuncionario().obterEnderecosPorFuncionario(model));
			} catch (EnderecoFuncionarioException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	@Override
	public String getSqlFiltro(FuncionarioModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
		String deletado = "and deletado = false";
		filtro += " where 1=1 " + deletado;

		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('%" + vo.getNome() + "%')";
		}

		filtro += " order by id asc";

		return filtro;
	}

}
