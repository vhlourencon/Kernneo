package br.com.kernneo.server.caravana.usuario;

import java.sql.SQLException;

import br.com.kernneo.client.caravana.model.CaravanaUsuarioModel;
import br.com.kernneo.server.band.negocio.BandNegocio;

public class CaravanaUsuario extends BandNegocio<CaravanaUsuarioModel, CaravanaUsuarioDAO> {

	public CaravanaUsuario() {
		super();
		dao = new CaravanaUsuarioDAO();
	}

	public CaravanaUsuarioModel obterPorLoginEsenha(String login, String senha) throws Exception {
		try {
			CaravanaUsuarioModel usuarioModel = dao.obterPorLoginEsenha(login, senha);
			if (usuarioModel == null) {
				System.out.println("1");	 
				throw new Exception("Usuario ou senha inválidos");
			} else {
				System.out.println("2");
				return usuarioModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		}

	}

	@Override
	public Exception validar(CaravanaUsuarioModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Nome  é de preenchimento obrigatório! \n");
		}

		if (vo.getLogin() == null || vo.getLogin().trim().length() == 0) {
			msg.append("O campo Login  é de preenchimento obrigatório! \n");
		}

		if (vo.getSenha() == null || vo.getSenha().trim().length() == 0) {
			msg.append("O campo Senha é de preenchimento obrigatório! <br>");
		} else {
			if (vo.getSenha().equals(vo.getConfirmaSenha()) == false) {
				msg.append("O campo Senha deve ser igual ao Confirma Senha <br>");
			}
		}

		if (vo.getPerfil() == null) {
			msg.append("O campo Acesso é de preenchimento obrigatório");
		}

		if (msg.length() > 0)
			return new Exception(msg.toString());
		else
			return null;

	}

}
