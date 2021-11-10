package br.com.kernneo.server.negocio;

import java.util.ArrayList;

import br.com.kernneo.client.exception.ClienteException;
import br.com.kernneo.client.exception.EnderecoClienteException;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.EnderecoClienteModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.ClienteDAO;

public class Cliente extends Negocio<ClienteModel, ClienteDAO, ClienteException> {

	public Cliente() {
		super();
		dao = new ClienteDAO();
	}

	public ArrayList<ClienteModel> obterTodosComEndereco() throws ClienteException {
		try {
			ArrayList<ClienteModel> lista = obterTodos(ClienteModel.class);

			if (lista != null) {
				for (ClienteModel clienteModel : lista) {
					clienteModel.setListaEndereco(new EnderecoCliente().obterEnderecosPorCliente(clienteModel));
				}
			}

			return lista;
		} catch (EnderecoClienteException e) {
			e.printStackTrace();
			throw new ClienteException(e.getLocalizedMessage());
		}
	}

	public GenericPagina<ClienteModel> buscaPaginaAnteriorComModelCompleto(GenericPagina<ClienteModel> paginaAtual, ClienteModel clienteModel) throws ClienteException {
		GenericPagina<ClienteModel> pagina = new Cliente().obterPaginaAnterior(paginaAtual, clienteModel);
		for (ClienteModel cliente : pagina.getListaRegistros()) {
			try {
				cliente.setListaEndereco(new EnderecoCliente().obterEnderecosPorCliente(cliente));
				// cliente.setListaDeContasApagar(new
				// MesaRecebimento().obterTodosPorCliente(cliente));
			} catch (Exception e) {
				e.printStackTrace();
				throw new ClienteException("Erro ao obter dados dos clientes");
			}
		}

		return pagina;
	}

	public GenericPagina<ClienteModel> buscaPaginaPosteriorComModelCompleto(GenericPagina<ClienteModel> paginaAtual, ClienteModel clienteModel) throws ClienteException {
		GenericPagina<ClienteModel> pagina = new Cliente().obterPaginaPosterior(paginaAtual, clienteModel);
		for (ClienteModel cliente : pagina.getListaRegistros()) {
			try {
				cliente.setListaEndereco(new EnderecoCliente().obterEnderecosPorCliente(cliente));
				// cliente.setListaDeContasApagar(new
				// MesaRecebimento().obterTodosPorCliente(cliente));
			} catch (Exception e) {
				e.printStackTrace();
				throw new ClienteException("Erro ao obter dados dos clientes");
			}
		}
		return pagina;
	}

	@Override
	public ClienteException validar(ClienteModel vo) {
		StringBuffer msg = new StringBuffer("");
		if (vo.getNome() == null) {
			msg.append("Campo nome de preenchimento obrigatório. \n");
		}

		if (msg.length() > 0)
			return new ClienteException(msg.toString());
		else
			return null;
	}

	public ClienteModel salvarComEndereco(ClienteModel vo) throws ClienteException {
		ClienteException exc = validar(vo);
		if (exc == null) {
			try {

				if (vo.getId() == null) {
					vo = salvar(vo);
				} else {
					merge(vo);
				}

				for (EnderecoClienteModel enderecoClienteModel : vo.getListaEndereco()) {
					enderecoClienteModel.setCliente(vo);
					if (enderecoClienteModel.getId() == null) {
						new EnderecoCliente().salvar(enderecoClienteModel);
					} else {
						new EnderecoCliente().merge(enderecoClienteModel);
					}
				}

				return vo;
			} catch (Exception e) {
				throw (ClienteException) new Exception("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	@Override
	public String getSqlFiltro(ClienteModel vo) {
		String filtro = super.getSqlFiltro(vo);
		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('%" + vo.getNome() + "%')";
		}
		filtro += " order by id asc";

		return filtro;
	}

}
