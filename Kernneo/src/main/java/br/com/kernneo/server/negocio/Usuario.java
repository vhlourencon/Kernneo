package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.Collection;

import br.com.kernneo.client.exception.UsuarioException;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.dao.UsuarioDAO;

public class Usuario extends Negocio<UsuarioModel, UsuarioDAO, UsuarioException> {

	public Usuario() {
		super();
		dao = new UsuarioDAO();
	}

	public UsuarioModel obterPorLoginEsenha(String login, String senha) throws UsuarioException {
		UsuarioModel usuarioModel;
		try {
			usuarioModel = dao.obterPorLoginEsenha(login, senha);
			if (usuarioModel == null) {
				throw new UsuarioException("Usuario ou senha inválidos");
			} else {
				return usuarioModel;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			throw new UsuarioException("Erro de acesso ao banco");

		}

	}

	public UsuarioModel obterPorLogin(String login) throws UsuarioException {
		UsuarioModel usuarioModel;
		try {
			usuarioModel = dao.obterPorLogin(login);

			return usuarioModel;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new UsuarioException("Erro de acesso ao banco");

		}

	}

	public UsuarioModel obterPorCodigo(Long codigo) throws UsuarioException {
		try {
			return new UsuarioDAO().obterPorCodigo(codigo);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new UsuarioException("Erro de acesso ao banco");
		}
	}

	@Override
	public UsuarioException validar(UsuarioModel usuarioModel) {
		StringBuffer msg = new StringBuffer("");
		if (usuarioModel.getNome() == null || usuarioModel.getNome().trim().length() == 0) {
			msg.append("O campo Nome é de preenchimento obrigatório! <br>");
		}
		if (usuarioModel.isAcessoSistema()) {
			if (usuarioModel.getSenha() == null || usuarioModel.getSenha().trim().length() == 0) {
				msg.append("O campo Senha é de preenchimento obrigatório! <br>");
			} else {
				if (usuarioModel.getSenha().equals(usuarioModel.getConfirmaSenha()) == false) {
					msg.append("O campo Senha deve ser igual ao Confirma Senha <br>");
				}
			}
			if (usuarioModel.getLogin() == null || usuarioModel.getLogin().trim().length() == 0) {
				msg.append("O campo Login é de preenchimento obrigatório! <br>");
			} else {
				try {
					UsuarioModel modelAux = obterPorLogin(usuarioModel.getLogin());
					if (modelAux != null) {
						if (usuarioModel.getId() == null) {
							msg.append("Login ja cadastrado <br>");
						} else if (usuarioModel.getId().compareTo(modelAux.getId()) != 0) {
							msg.append("Login ja cadastrado <br>");
						}

					}
				} catch (UsuarioException e) {
					e.printStackTrace();
					msg.append("Erro ao verificar login do usuario! <br>");
				}
			}
		}

		if (usuarioModel.isAcessoPDV()) {
			if (usuarioModel.getSenhaPDV() == null || usuarioModel.getSenhaPDV().trim().length() == 00) {
				msg.append("O campo Senha PDV é de preenchimento obrigatório quando marcado acesso! <br>");
			}
			if (usuarioModel.getCodigo() != null) {
				try {

					UsuarioModel modelAux = obterPorCodigo(usuarioModel.getCodigo());
					if (modelAux != null) {
						if (usuarioModel.getId() == null) {
							msg.append("Login ja cadastrado <br>");
						} else if (usuarioModel.getId().compareTo(modelAux.getId()) != 0) {
							msg.append("Login ja cadastrado <br>");
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("Erro ao verificar código do usuario!<br>");
				}
			} else {
				try {
					Long ulitmoCodigo = obterUltimoCodigo();

					ulitmoCodigo = ulitmoCodigo + 1L;

					usuarioModel.setCodigo(ulitmoCodigo);
				} catch (Exception e) {
					e.printStackTrace();
					msg.append("Erro ao obter o código do produto!");
				}
			}
		}

		if (msg.length() > 0)
			return new UsuarioException(msg.toString());
		else
			return null;
	}

	public Long obterUltimoCodigo() throws Exception {

		try {

			return dao.obterUltimoCodigo();
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro ao executar a operação:\n" + e.getMessage());
		}

	}

}
