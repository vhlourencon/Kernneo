package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.exception.CaixaException;
import br.com.kernneo.client.exception.ImpressoraException;
import br.com.kernneo.client.exception.ItemException;
import br.com.kernneo.client.exception.MesaException;
import br.com.kernneo.client.exception.MesaRecebimentoException;
import br.com.kernneo.client.exception.NFCeException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.client.model.EnderecoClienteModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.NFCeModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.MesaStatus;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.types.NFCeSituacao;
import br.com.kernneo.client.types.NFETipoEmissao;
import br.com.kernneo.client.types.PedidoDocumentoType;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.dao.MesaDAO;
import br.com.kernneo.server.report.FechaContaReport;
import br.com.kernneo.server.report.ProducaoReport;

public class Mesa extends Negocio<MesaModel, MesaDAO, MesaException> {

	public Mesa() {
		super();
		dao = new MesaDAO();
	}

	public GenericPagina<MesaModel> buscarPaginaDePedidosPosteior(GenericPagina<MesaModel> paginaAtual, MesaModel filtroModel) throws MesaException {
		GenericPagina<MesaModel> pagina = obterPaginaPosterior(paginaAtual, filtroModel);
		for (MesaModel mesaModel : pagina.getListaRegistros()) {
			mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
		}
		return pagina;
	}

	public GenericPagina<MesaModel> buscarPaginaDePedidosAnterior(GenericPagina<MesaModel> paginaAtual, MesaModel filtroModel) throws MesaException {
		GenericPagina<MesaModel> pagina = obterPaginaAnterior(paginaAtual, filtroModel);
		for (MesaModel mesaModel : pagina.getListaRegistros()) {
			mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
		}
		return pagina;
	}

	public GenericPagina<MesaModel> buscarPaginaDePedidosRecebidosAnterior(GenericPagina<MesaModel> paginaAtual, MesaModel filtroModel) throws MesaException {
		GenericPagina<MesaModel> pagina = obterPaginaRecebidoAnterior(paginaAtual, filtroModel);
		for (MesaModel mesaModel : pagina.getListaRegistros()) {
			mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
		}
		return pagina;
	}

	public GenericPagina<MesaModel> buscarPaginaDePedidosRecebidosPosterior(GenericPagina<MesaModel> paginaAtual, MesaModel filtroModel) throws MesaException {
		GenericPagina<MesaModel> pagina = obterPaginaRecebidoPosterior(paginaAtual, filtroModel);
		for (MesaModel mesaModel : pagina.getListaRegistros()) {
			mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
		}
		return pagina;
	}

	public MesaModel inserirItens(UsuarioModel usuario, MesaModel mesaModel) throws MesaException {
		try {

			/*
			 * lista para verificar se tem algum pedido sem fazer e imprimir os
			 * pedidos no final do metodo
			 */
			ArrayList<ItemModel> listaAux = new ArrayList<ItemModel>();
			for (ItemModel itemModel : mesaModel.getListaDeItens()) {
				if (itemModel.getId() == null) {
					listaAux.add(itemModel);
				}
			}

			if (mesaModel.getTipo() == MesaTipo.entrega) {

				mesaModel = factoryPedido(usuario, mesaModel);
				for (ItemModel itemModel : listaAux) {
					new Item().inserirItem(usuario, mesaModel, itemModel);
				}

			} else if (mesaModel.getTipo() == MesaTipo.balcao) {

				MesaModel mesaAux = obterModelAtualizado(mesaModel);
				if ((mesaAux.getStatus() == MesaStatus.balcao_fechado || mesaAux.getStatus() == MesaStatus.balcao_recebido) && listaAux.size() > 0) {
					throw new MesaException("Erro ao inserir item.</br>Pedido em recebimento ou  recebido!");
				} else {
					try {
						for (ItemModel itemModel : listaAux) {
							new Item().inserirItem(usuario, mesaModel, itemModel);
						}
					} catch (ItemException e) {
						e.printStackTrace();
						throw new MesaException(e.getLocalizedMessage());
					}

					if (mesaModel.getStatus() == MesaStatus.balcao_novo) {
						mesaModel.setStatus(MesaStatus.balcao_feito);
						alterar(mesaModel);
					}
				}
			} else if (mesaModel.getTipo() == MesaTipo.mesa) {
				MesaModel mesaAux = obterMesaNaoRecebidaPorNumero(usuario, mesaModel.getNumero());

				if ((mesaAux.getId() == null && mesaModel.getId() == null) || (mesaAux.getId() != null && mesaModel.getId() != null && mesaAux.getId().compareTo(mesaModel.getId()) == 0)) {
					if (mesaAux.getId() == null) {
						mesaAux = factoryMesa(usuario, mesaModel.getNumero());
					}

					if (mesaAux.getStatus() == MesaStatus.mesa_fechada && listaAux.size() > 0) {
						throw new MesaException("Mesa esta fechada!</br>Necessario reabrir para inserir os novos itens ou exclui-los.");
					} else {

						for (ItemModel itemModel : mesaModel.getListaDeItens()) {
							new Item().inserirItem(usuario, mesaAux, itemModel);
						}

					}

					mesaModel = mesaAux;
				} else {
					throw new MesaException("Mesa ja recebida");
				}

			}
			imprimePedidos(listaAux, usuario, mesaModel);

			mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
			return mesaModel;

		} catch (ItemException e) {
			e.printStackTrace();
			throw new MesaException(e.getLocalizedMessage());
		} catch (ImpressoraException e) {
			e.printStackTrace();
			throw new MesaException(e.getLocalizedMessage());
		}

	}

	public MesaModel deletarItens(UsuarioModel funcionario, MesaModel mesaModel, ArrayList<ItemModel> listaTeItensParaExcluir) throws MesaException {

		if (mesaModel.getTipo() == MesaTipo.balcao) {
			MesaModel mesaAux = obterModelAtualizado(mesaModel);
			if (mesaAux.getStatus() == MesaStatus.balcao_cancelado) {
				throw new MesaException("Não é possivel excluir item de um pedido cancelado!");
			} else if (mesaAux.getStatus() == MesaStatus.balcao_fechado) {
				throw new MesaException("Não é possivel excluir item de um pedido fechado!");
			} else if (mesaAux.getStatus() == MesaStatus.balcao_recebido) {
				throw new MesaException("Não é possivel excluir item de um pedido recebido!");
			}
		}
		try {
			for (ItemModel itemModel : listaTeItensParaExcluir) {

				mesaModel.getListaDeItens().remove(itemModel);
				if (itemModel.getId() != null) {
					new Item().excluirItem(itemModel);
				}
			}
		} catch (ItemException e) {

			e.printStackTrace();
			throw new MesaException("Erro ao excluir itens!");
		}
		return mesaModel;
	}

	//
	public MesaModel transferirItens(UsuarioModel funcionario, MesaModel mesaModel, ArrayList<ItemModel> listaTeItensParaTransferir, Long numeroDaMesa) throws MesaException {

		for (ItemModel itemModel : listaTeItensParaTransferir) {

			mesaModel.getListaDeItens().size();
			mesaModel.getListaDeItens().remove(itemModel);

		}

		MesaModel mesaNova = obterMesaNaoRecebidaPorNumero(funcionario, numeroDaMesa);
		mesaNova.setListaDeItens(listaTeItensParaTransferir);
		mesaNova = inserirItens(funcionario, mesaNova);

		return mesaModel;

	}

	public MesaModel transferirMesa(UsuarioModel funcionario, MesaModel mesaModel, Long numeroDaMesa) throws MesaException {

		try {
			ArrayList<ItemModel> listaTeItensParaTransferir = new ArrayList<ItemModel>();
			for (ItemModel itemModel : mesaModel.getListaDeItens()) {
				listaTeItensParaTransferir.add(itemModel);
			}

			ArrayList<MesaRecebimentoModel> listaDeRecebimentos = new ArrayList<MesaRecebimentoModel>();
			for (MesaRecebimentoModel mesaRecebimentoModel : mesaModel.getListaDeRecedimentos()) {
				listaDeRecebimentos.add(mesaRecebimentoModel);
			}

			excluir(mesaModel);

			/*
			 * transferir itens
			 */
			MesaModel mesaNova = factoryMesa(funcionario, numeroDaMesa);
			mesaNova.setListaDeItens(listaTeItensParaTransferir);
			inserirItens(funcionario, mesaNova);

			/*
			 * transferir recebimentos
			 */
			for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimentos) {
				mesaRecebimentoModel.setMesa(mesaNova);
				new MesaRecebimento().merge(mesaRecebimentoModel);
			}

			mesaNova = obterModelAtualizadoComDetalhes(mesaNova);

			return mesaNova;
		} catch (MesaRecebimentoException e) {
			e.printStackTrace();
			throw new MesaException("Essa Mesa ja foi fechada e recebida");
		}

	}

	public MesaModel fecharMesa(UsuarioModel usuario, MesaModel mesaModel, int quantidadeDePessoas, boolean isRecebimentoParcial) throws MesaException {
		MesaModel mesaAux = obterMesaNaoRecebidaPorNumero(usuario, mesaModel.getNumero());
		if ((mesaAux.getId() == null && mesaModel.getId() == null) || (mesaAux.getId() != null && mesaModel.getId() != null && mesaAux.getId().compareTo(mesaModel.getId()) == 0)) {

			// mesaModel.setStatus(MesaStatus.aberta);
			// alterar(mesaModel);

			mesaModel = inserirItens(usuario, mesaModel);
			mesaModel.setQuantiadadeDePessoas(quantidadeDePessoas);
			if (isRecebimentoParcial == false) {
				mesaModel.setStatus(MesaStatus.mesa_fechada);
			}

			alterar(mesaModel);

			mesaModel = obterMesaDetalhadaNaoRecebidaPorNumero(usuario, mesaModel.getNumero());

			return mesaModel;

		} else {
			throw new MesaException("Essa Mesa ja foi fechada e recebida");
		}

	}

	public MesaModel reabrirMesa(UsuarioModel usuario, MesaModel mesaModel) throws MesaException {
		MesaModel mesaAux = obterMesaNaoRecebidaPorNumero(usuario, mesaModel.getNumero());

		if ((mesaAux.getId() == null && mesaModel.getId() == null) || (mesaAux.getId() != null && mesaModel.getId() != null && mesaAux.getId().compareTo(mesaModel.getId()) == 0)) {
			mesaModel.setStatus(MesaStatus.mesa_aberta);
			alterar(mesaModel);
			mesaModel = inserirItens(usuario, mesaModel);

			return mesaModel;

		} else {
			throw new MesaException("Essa Mesa ja foi fechada e recebida");
		}

	}

	public MesaModel receberMesa(UsuarioModel funcionario, MesaModel mesaModel, int quantidadeDePessoas) throws MesaException {

		try {

			ArrayList<MesaRecebimentoModel> listaDeRecebimentoAux = new ArrayList<MesaRecebimentoModel>();
			for (MesaRecebimentoModel mesaRecebimentoModel : mesaModel.getListaDeRecedimentos()) {
				listaDeRecebimentoAux.add(mesaRecebimentoModel);
			}
			
			

			PedidoDocumentoType pedidoDocumentoTypeAux = mesaModel.getDocumentoType();
			NFCeModel ultimaNFceEmitida = mesaModel.getNfceUltimaEmitida();

			if (mesaModel.getTipo() == MesaTipo.entrega) {

				if (mesaModel.getStatus() == MesaStatus.entrega_novo) {

					EnderecoClienteModel enderecoClienteModel = mesaModel.getEnderecoCliente();
					if (enderecoClienteModel != null) {
						enderecoClienteModel.setCliente(mesaModel.getCliente());
						if (mesaModel.getEnderecoCliente().getId() == null) {
							enderecoClienteModel = new EnderecoCliente().salvar(enderecoClienteModel);
						} else {
							new EnderecoCliente().alterar(enderecoClienteModel);

						}
					}

					mesaModel.setEnderecoCliente(enderecoClienteModel);
					mesaModel.setDataHoraDeAbertura(new Date());
					mesaModel.setStatus(MesaStatus.entrega_iniciado);

					mesaModel = inserirItens(funcionario, mesaModel);

					mesaModel.setListaDeRecedimentos(listaDeRecebimentoAux);
					mesaModel.setEnderecoCliente(enderecoClienteModel);

					for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimentoAux) {
						mesaRecebimentoModel.setMesa(mesaModel);
						new MesaRecebimento().inserirRecebimento(funcionario, mesaRecebimentoModel);
					}

					alterar(mesaModel);

				} else if (mesaModel.getStatus() == MesaStatus.entrega_iniciado) {
					for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimentoAux) {
						mesaRecebimentoModel.setMesa(mesaModel);
						new MesaRecebimento().inserirRecebimento(funcionario, mesaRecebimentoModel);

					}

				} else if (mesaModel.getStatus() == MesaStatus.entrega_saiu) {

					for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimentoAux) {
						mesaRecebimentoModel.setMesa(mesaModel);
						new MesaRecebimento().inserirRecebimentoNoCaixaAberto(funcionario, mesaRecebimentoModel);
					}

					mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
					if (mesaModel.getCalcTotalDeEntradasComOtroco().compareTo(mesaModel.getCalcValorTotalDaMesa()) >= 0) {
						mesaModel.setStatus(MesaStatus.entrega_recebido);

					}

				}

			} else if (mesaModel.getTipo() == MesaTipo.balcao) {

				MesaModel mesaAux = obterModelAtualizadoComDetalhes(mesaModel);
				if (mesaAux.getStatus() == MesaStatus.balcao_cancelado) {
					throw new MesaException("Pedido Cancelado!");
				} else if (mesaAux.getStatus() == MesaStatus.balcao_feito) {
					throw new MesaException("Pedido  em Aberto!\nFavor Fechar o pedido antes de receber");
				} else if (mesaAux.getStatus() == MesaStatus.balcao_fechado || mesaAux.getStatus() == MesaStatus.balcao_novo) {
					mesaModel = new Mesa().inserirItens(funcionario, mesaModel);
					try {
						for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimentoAux) {
							mesaRecebimentoModel.setMesa(mesaModel);
							new MesaRecebimento().inserirRecebimentoNoCaixaAberto(funcionario, mesaRecebimentoModel);
						}
					} catch (MesaRecebimentoException e) {
						e.printStackTrace();
						throw new MesaException(e.getLocalizedMessage());
					}

					mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
					if (mesaModel.getCalcTotalDeEntradasComOtroco().compareTo(mesaModel.getCalcValorTotalDaMesa()) >= 0) {
						mesaModel.setStatus(MesaStatus.balcao_recebido);
					}
				}

				mesaModel.setNfceUltimaEmitida(ultimaNFceEmitida);
				if (pedidoDocumentoTypeAux == PedidoDocumentoType.recibo) {
					try {
						FechaContaReport fechaContaReport = new FechaContaReport(funcionario, mesaModel, LocalDeImpressao.caixa.toString());
						fechaContaReport.imprimir(LocalDeImpressao.caixa);
					} catch (ImpressoraException e) {
						e.printStackTrace();
					}
				} else if (pedidoDocumentoTypeAux == PedidoDocumentoType.nfce) {
					emitirNFCe(mesaModel, NFETipoEmissao._1_EMISSAO_NORMAL);
				} else if (pedidoDocumentoTypeAux == PedidoDocumentoType.nfce_em_contigencia) {
					emitirNFCe(mesaModel, NFETipoEmissao._9_CONTINGENCIA_OFF_LINE);
				}

			} else if (mesaModel.getTipo() == MesaTipo.mesa) {
				MesaModel mesaAux = obterMesaNaoRecebidaPorNumero(funcionario, mesaModel.getNumero());

				if ((mesaAux.getId() == null && mesaModel.getId() == null) || (mesaAux.getId() != null && mesaModel.getId() != null && mesaAux.getId().compareTo(mesaModel.getId()) == 0)) {

					
					for (MesaRecebimentoModel recebimentoModel : listaDeRecebimentoAux) {
						recebimentoModel.setMesa(mesaModel);
						new MesaRecebimento().inserirRecebimentoNoCaixaAberto(funcionario, recebimentoModel);
					}
					mesaModel = inserirItens(funcionario, mesaModel);
					
					mesaModel = obterMesaDetalhadaNaoRecebidaPorNumero(funcionario, mesaModel.getNumero());
					if (mesaModel.getStatus() == MesaStatus.mesa_fechada && mesaModel.getCalcTotalDeEntradasComOtroco().compareTo(mesaModel.getCalcValorTotalDaMesa()) >= 0) {
						mesaModel.setStatus(MesaStatus.mesa_recebida);
					}

				} else {
					if (mesaModel.getStatus() != MesaStatus.mesa_recebida) {
						throw new MesaException(" Essa Mesa ja foi fechada e recebida!");
					}
				}
				
				alterar(mesaModel);
			//	mesaModel = obterModelAtualizadoComDetalhes(mesaModel);
				mesaModel = obterModelAtualizadoComDetalhes(mesaModel);

				if (mesaModel.getStatus() == MesaStatus.mesa_recebida) {
					CaixaModel caixaModel = new Caixa().obterCaixaAberto();
					caixaModel.addMesa(mesaModel);
					new Caixa().merge(caixaModel);

					mesaModel.setNfceUltimaEmitida(ultimaNFceEmitida);
					if (pedidoDocumentoTypeAux == PedidoDocumentoType.recibo) {
						FechaContaReport fechaContaReport = new FechaContaReport(funcionario, mesaModel, LocalDeImpressao.caixa.toString());
						fechaContaReport.imprimir(LocalDeImpressao.caixa);
					} else if (pedidoDocumentoTypeAux == PedidoDocumentoType.nfce) {
						emitirNFCe(mesaModel, NFETipoEmissao._1_EMISSAO_NORMAL);
					} else if (pedidoDocumentoTypeAux == PedidoDocumentoType.nfce_em_contigencia) {
						emitirNFCe(mesaModel, NFETipoEmissao._9_CONTINGENCIA_OFF_LINE);
					}
				}
			}

			mesaModel.setQuantiadadeDePessoas(quantidadeDePessoas);
			mesaModel.setUsuarioRecebimento(funcionario);
			if (mesaModel.getStatus() == MesaStatus.mesa_recebida || mesaModel.getStatus() == MesaStatus.balcao_recebido || mesaModel.getStatus() == MesaStatus.entrega_recebido) {
				mesaModel.setLogTotal(mesaModel.getCalcValorTotalDaMesa());
				mesaModel.setLogSubTotal(mesaModel.getCalcSubTotal());
				mesaModel.setLogTaxaDeServico(mesaModel.getCalcTaxaDeServicoEmDinheiro());
				mesaModel.setLogTaxaDeEntrega(mesaModel.getCalcTaxaDeEntregaEmDinheiro());
				mesaModel.setLogDesconto(mesaModel.getCalcTotalDeDesconto());

				mesaModel.setDataHoraDeRecebimento(new Date());
				mesaModel.setUsuarioRecebimento(funcionario);

			}
			alterar(mesaModel);
			mesaModel = obterModelAtualizadoComDetalhes(mesaModel);

		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException(e.getLocalizedMessage());
		}
		return mesaModel;

	}

	/*
	 * Factory de Mesa Detalhada
	 */
	public MesaModel factoryMesaDetalhada(UsuarioModel usuario, Long numero) throws MesaException {
		MesaModel mesa = factoryMesa(usuario, numero);
		mesa = obterMesaDetalhadaNaoRecebidaPorNumero(usuario, numero);
		return mesa;
	}

	/*
	 * Factory de Mesa
	 */
	public MesaModel factoryMesa(UsuarioModel usuarioAbertura, Long numero) throws MesaException {
		MesaModel mesa = obterMesaNaoRecebidaPorNumero(usuarioAbertura, numero);
		if (mesa.getId() == null) {
			mesa = salvar(mesa);
		} else {
			mesa = merge(mesa);
		}

		return mesa;
	}

	/*
	 * Factory de pedido
	 */
	public MesaModel factoryPedido(UsuarioModel usuarioAbertura, MesaModel pedido) throws MesaException {
		pedido.setUsuarioAbertura(usuarioAbertura);
		if (pedido.getId() == null) {
			pedido = salvar(pedido);
		} else {
			pedido = merge(pedido);
		}

		return pedido;
	}

	public MesaModel obterModelNovo(UsuarioModel usuarioAbertura) throws MesaException {

		try {
			Long idEmpresa = new Empresa().obterUltimoId(EmpresaModel.class);
			EmpresaModel empresaModel = null;
			if (idEmpresa == null) {
				throw new MesaException("Nenhuma configuração encontrada para empresa");
			} else {
				empresaModel = new Empresa().obterPorId(EmpresaModel.class, idEmpresa);
				if (empresaModel == null) {
					throw new MesaException("Nenhuma configuração encontrada para empresa");
				} else {
					MesaModel mesaModel = new MesaModel();
					mesaModel.setDataHoraDeAbertura(new Date());
					mesaModel.setTaxaDeServico(empresaModel.getTaxaDeServico());
					mesaModel.setTaxaDeEntrega(empresaModel.getTaxaDeEntrega());
					mesaModel.setQuantiadadeDePessoas(1);
					mesaModel.setUsuarioAbertura(usuarioAbertura);
					mesaModel.setConfigEmpresaTelaDeProdutosAposConsulta(empresaModel.isConfigMostraTelaDeProdutosAposConsultaDaMesa());
					mesaModel.setConfigEmpresaTelaDeProdutosExtendida(empresaModel.isConfigMostraTelaDeProdutoExtendida());
					mesaModel.setConfigEmpresaPermiteAlterarDocumento(empresaModel.isPermirAlterarDocumentoPadrao());
					mesaModel.setDocumentoType(empresaModel.getPedidoDocumentoPadraoType());
					mesaModel.setConfigEmpresaRealizarPedidoSemCobrar(empresaModel.isPermiteUsuarioRealizarPedidoSemCobrar());
					mesaModel.setConfigEmpresaCobraTxdEntregaPorBairro(empresaModel.isConfigTxDeServicoCobrarPorBairro());
					mesaModel.setTipoDeCalculoFracionado(empresaModel.getTipoDeCalculoFracionado());

					return mesaModel;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter dados ");
		}

	}

	public MesaModel obterEntregaNovo(UsuarioModel usuarioAertura) throws MesaException {
		try {

			MesaModel mesaModel = obterModelNovo(usuarioAertura);
			mesaModel.setTipo(MesaTipo.entrega);
			mesaModel.setStatus(MesaStatus.entrega_novo);

			return mesaModel;

		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter dados da mesa");
		}
	}

	/*
     *  
     */
	public MesaModel obterMesaNaoRecebidaPorNumero(UsuarioModel usuarioAbertura, Long numero) throws MesaException {
		try {
			MesaModel mesaModel = dao.obterMesaNaoRecebidaPorNumero(numero);
			if (mesaModel == null) {
				mesaModel = obterModelNovo(usuarioAbertura);
				mesaModel.setStatus(MesaStatus.mesa_aberta);
				mesaModel.setTipo(MesaTipo.mesa);
				mesaModel.setNumero(numero);
			}

			return mesaModel;

		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter dados da mesa");
		}
	}

	public MesaModel obterMesaDetalhadaNaoRecebidaPorNumero(UsuarioModel usuarioAbertura, Long numero) throws MesaException {
		try {
			MesaModel mesa = obterMesaNaoRecebidaPorNumero(usuarioAbertura, numero);
			if (mesa.getId() != null) {
				obterModelAtualizadoComDetalhes(mesa);
			}

			return mesa;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter dados da mesa");
		}
	}

	// public MesaModel obterDetalhesDaMesa(MesaModel mesa) throws MesaException
	// {
	// try {
	// mesa.setListaDeItens(new Item().obterTodosPorMesa(mesa));
	// mesa.setListaDeRecedimentos(new
	// MesaRecebimento().obterTodosPorMesa(mesa));
	//
	// return mesa;
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new MesaException("Erro ao obter dados da mesa");
	// }
	//
	// }
	
	
	public MesaModel cancelarPedidoEntrega(UsuarioModel funcionario, MesaModel pedidoModel,String motivo) throws MesaException {

		MesaModel pedidoModelAux = obterModelAtualizadoComDetalhes(pedidoModel);
		if (pedidoModelAux.getStatus() == MesaStatus.entrega_recebido) {
			throw new MesaException("Pedido já recebido \nNão é possivel cancelar");
		} else if (pedidoModelAux.getStatus() == MesaStatus.entrega_cancelado) {
			throw new MesaException("Pedido já cancelado");
		} else {
			pedidoModelAux.setStatus(MesaStatus.entrega_cancelado);
			pedidoModelAux.setMotivoCancelamento(motivo);
			alterar(pedidoModelAux);
			

			return pedidoModelAux;
		}
	}


	private void imprimePedidos(ArrayList<ItemModel> lista, UsuarioModel funcionarioModel, MesaModel mesa) throws ImpressoraException {

		// lista da COPA
		ArrayList<ItemModel> listaDaCopa1 = obterTodosPorLocalDeImpressao(lista, LocalDeImpressao.copa1);
		if (listaDaCopa1.size() > 0) {
			new ProducaoReport(listaDaCopa1, funcionarioModel, mesa).imprimir(LocalDeImpressao.copa1);
		}

		// lista da COPA2
		ArrayList<ItemModel> listaDaCopa2 = obterTodosPorLocalDeImpressao(lista, LocalDeImpressao.copa2);
		if (listaDaCopa2.size() > 0) {
			new ProducaoReport(listaDaCopa2, funcionarioModel, mesa).imprimir(LocalDeImpressao.copa2);
		}

		// lista da COPA3
		ArrayList<ItemModel> listaDaCopa3 = obterTodosPorLocalDeImpressao(lista, LocalDeImpressao.copa3);
		if (listaDaCopa3.size() > 0) {
			new ProducaoReport(listaDaCopa3, funcionarioModel, mesa).imprimir(LocalDeImpressao.copa3);
		}

		// lista da COZINHA
		ArrayList<ItemModel> listaDaCozinha = obterTodosPorLocalDeImpressao(lista, LocalDeImpressao.cozinha);
		if (listaDaCozinha.size() > 0) {
			ProducaoReport producaoReport = new ProducaoReport(listaDaCozinha, funcionarioModel, mesa);
			producaoReport.imprimir(LocalDeImpressao.cozinha);
		}

		// lista ddo Bar
		ArrayList<ItemModel> listaDoBar = obterTodosPorLocalDeImpressao(lista, LocalDeImpressao.bar);
		if (listaDoBar.size() > 0) {
			new ProducaoReport(listaDoBar, funcionarioModel, mesa).imprimir(LocalDeImpressao.bar);
		}

		// lista do Caixa
		ArrayList<ItemModel> listaDoCaixa = obterTodosPorLocalDeImpressao(lista, LocalDeImpressao.caixa);
		if (listaDoCaixa.size() > 0) {
			new ProducaoReport(listaDoCaixa, funcionarioModel, mesa).imprimir(LocalDeImpressao.caixa);
		}

	}

	public ArrayList<ItemModel> obterTodosPorLocalDeImpressao(ArrayList<ItemModel> listaDePedidos, LocalDeImpressao localDeImpressao) {
		ArrayList<ItemModel> lista = new ArrayList<ItemModel>();

		for (ItemModel pedidoModel : listaDePedidos) {
			if (localDeImpressao == LocalDeImpressao.bar && pedidoModel.getProduto().isImprimeBar()) {
				lista.add(pedidoModel);
			}

			if (localDeImpressao == LocalDeImpressao.copa1 && pedidoModel.getProduto().isImprimeCopa1()) {
				lista.add(pedidoModel);
			}

			if (localDeImpressao == LocalDeImpressao.copa2 && pedidoModel.getProduto().isImprimeCopa2()) {
				lista.add(pedidoModel);
			}

			if (localDeImpressao == LocalDeImpressao.copa3 && pedidoModel.getProduto().isImprimeCopa3()) {
				lista.add(pedidoModel);
			}

			if (localDeImpressao == LocalDeImpressao.cozinha && pedidoModel.getProduto().isImprimeCozinha()) {
				lista.add(pedidoModel);
			}

			if (localDeImpressao == LocalDeImpressao.caixa && pedidoModel.getProduto().isImprimeCaixa()) {
				lista.add(pedidoModel);
			}
		}

		return lista;
	}

	public ArrayList<MesaModel> obterTodasMesasNaoRecebidas() throws MesaException {
		try {
			ArrayList<MesaModel> lista = dao.obterTodasMesasNaoRecebidas();
			if (lista == null) {
				lista = new ArrayList<MesaModel>();
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter mesas!");
		}
	}

	@Override
	public MesaException validar(MesaModel vo) {

		StringBuffer msg = new StringBuffer("");
		// if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
		// msg.append("O campo Nome  é de preenchimento obrigatório!");
		// }

		if (msg.length() > 0)
			return new MesaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(MesaModel vo) {
		String filtro = getGenericFiltro(vo);
		filtro += " and 1=1 ";

		if (vo.getTipo() != null) {
			if (vo.getTipo().equals(MesaTipo.entrega)) {
				filtro += " and tipo='" + MesaTipo.entrega.toString() + "'";
			} else if (vo.getTipo().equals(MesaTipo.mesa)) {
				filtro += " and tipo='" + MesaTipo.mesa.toString() + "'";
			}
		}
		 if ( vo.getCliente() != null )  { 
			 filtro += " and id_cliente="  + vo.getCliente().getId();
		 }

		filtro += " order by id desc";

		return filtro;
	}

	public MesaModel saiuParaEtrega(UsuarioModel usuarioModel, MesaModel mesa, boolean saiuParaEntrega) throws MesaException {
		if (saiuParaEntrega) {
			mesa.setDataHoraEntrega(new Date());
			mesa.setStatus(MesaStatus.entrega_saiu);
			alterar(mesa);
			mesa = obterModelAtualizadoComDetalhes(mesa);

			for (MesaRecebimentoModel mesaRecebimentoModel : mesa.getListaDeRecedimentos()) {
				if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.troco_cliente) {
					try {
						new MesaRecebimento().inserirRecebimentoNoCaixaAberto(usuarioModel, mesaRecebimentoModel);
					} catch (MesaRecebimentoException e) {
						e.printStackTrace();
						throw new MesaException(e.getLocalizedMessage());
					}
				}
			}

			try {
				FechaContaReport fechaContaReport = new FechaContaReport(usuarioModel, mesa, LocalDeImpressao.caixa.toString());
				fechaContaReport.imprimir(LocalDeImpressao.caixa);
			} catch (ImpressoraException e) {
				e.printStackTrace();
				throw new MesaException(e.getLocalizedMessage());
			}
		} else {
			mesa.setDataHoraEntrega(null);
			mesa.setStatus(MesaStatus.entrega_iniciado);
			alterar(mesa);
		}

		return mesa;
	}

	public MesaModel obterModelAtualizado(MesaModel mesaModel) throws MesaException {
		mesaModel = new Mesa().obterPorId(mesaModel);
		return mesaModel;

	}

	public MesaModel obterModelAtualizadoComDetalhes(MesaModel mesaModel) throws MesaException {
		mesaModel = obterModelAtualizado(mesaModel);
		try {
			mesaModel.setListaDeItens(new Item().obterTodosPorMesa(mesaModel));
			mesaModel.setListaDeRecedimentos(new MesaRecebimento().obterTodosPorMesa(mesaModel));
			mesaModel.setListaDeNFCeEmitidas(new NFCe().obterTodasPorMesa(mesaModel));

		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException(e.getLocalizedMessage());
		}

		return mesaModel;

	}

	public MesaModel emitirNFCe(MesaModel mesa, NFETipoEmissao nfeTipoEmissao) throws MesaException {

		try {
			NFCeModel nfCeModel = mesa.getNfceUltimaEmitida();
			if (nfCeModel == null) {
				nfCeModel = new NFCe().emitirNFCeNova(mesa, nfeTipoEmissao);
			} else {
				if (nfCeModel.getNfCeSituacao() == NFCeSituacao.autorizada) {
					throw new MesaException("NFCe Ja autorizada");
				} else if (nfCeModel.getNfCeSituacao() == NFCeSituacao.emitida_em_contigencia) {

					if (nfeTipoEmissao == NFETipoEmissao._1_EMISSAO_NORMAL) {
						nfCeModel = new NFCe().enviarNFCeEmModoNomal(nfCeModel,false);
					} else if (nfeTipoEmissao == NFETipoEmissao._9_CONTINGENCIA_OFF_LINE) {
						nfCeModel = new NFCe().enviarNFCeEmModoContigencia(nfCeModel);
					}

				} else if (nfCeModel.getNfCeSituacao() == NFCeSituacao.cancelada) {
					nfCeModel = new NFCe().emitirNFCeNova(mesa, nfeTipoEmissao);
				} else if (nfCeModel.getNfCeSituacao() == NFCeSituacao.nao_emitida) {
					nfCeModel = new NFCe().emitirNFCeNova(mesa, nfeTipoEmissao);
				} else if (nfCeModel.getNfCeSituacao() == NFCeSituacao.rejeitada) {
					nfCeModel = new NFCe().reEmitirNFCe(nfCeModel, nfeTipoEmissao);
				}

			}

			mesa.setNfceUltimaEmitida(nfCeModel);
			alterar(mesa);
			return mesa;
		} catch (NFCeException e) {
			e.printStackTrace();
			throw new MesaException(e.getLocalizedMessage());
		}

	}

	public MesaModel emitirNFCeEmModoNormal(MesaModel mesaModel) throws MesaException {

		return emitirNFCe(mesaModel, NFETipoEmissao._1_EMISSAO_NORMAL);

	}

	/*
	 * BALCAO
	 */

	public ArrayList<MesaModel> obterTodosPedidosDeBalcaoNaoAbertosNoCaixaAberto() throws MesaException {
		try {
			CaixaModel caixa = new Caixa().obterCaixaAberto();
			ArrayList<MesaModel> lista = null;
			if (caixa != null) {
				lista = dao.obterTodosPedidosDeBalcaoNaoAbertosNoCaixa(caixa);
			}
			if (lista == null) {
				lista = new ArrayList<MesaModel>();
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter PEDIDOS!");
		} catch (CaixaException e) {

			e.printStackTrace();
			throw new MesaException("Erro ao obter caixa!");
		}
	}

	public MesaModel obterPedidoDeBalcaoPorNumero(UsuarioModel usuarioAbertura, CaixaModel caixaModel, Long numero) throws MesaException {
		try {

			MesaModel mesaModel = dao.obterPedidoBalcaoPorCaixaEnumero(caixaModel, numero);
			if (mesaModel == null) {
				mesaModel = obterModelNovo(usuarioAbertura);
				mesaModel.setStatus(MesaStatus.balcao_novo);
				mesaModel.setTipo(MesaTipo.balcao);
				mesaModel.setNumero(numero);
				mesaModel.setCaixaAbertura(caixaModel);
			}

			return mesaModel;

		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter dados da mesa");
		}
	}

	public MesaModel factoryPedidoBalcao(UsuarioModel usuarioAbertura, Long numero) throws MesaException {
		try {
			MesaModel mesa = null;
			Long numAux = numero;
			CaixaModel caixaModel = new Caixa().obterCaixaAberto();
			if (caixaModel == null) {
				throw new MesaException("Caixa Fechado! Favor abrir o caixa antes de fazer pedidos");
			}
			if (numAux == null) {
				numAux = caixaModel.getUltimoNumeroDePedido() + 1;
				mesa = obterPedidoDeBalcaoPorNumero(usuarioAbertura, caixaModel, numAux);
				while (mesa.getId() != null) {
					numAux = numAux + 1;
					mesa = obterPedidoDeBalcaoPorNumero(usuarioAbertura, caixaModel, numAux);
				}
				caixaModel.setUltimoNumeroDePedido(numAux);
				new Caixa().alterar(caixaModel);

			} else {
				mesa = obterPedidoDeBalcaoPorNumero(usuarioAbertura, caixaModel, numero);
				if (mesa.getId() != null && mesa.getStatus() == MesaStatus.balcao_novo) {
					throw new MesaException("Numero do pedido em uso");
				}
			}

			if (mesa.getId() == null) {
				mesa = salvar(mesa);
			} else {
				mesa = merge(mesa);
			}

			return mesa;
		} catch (CaixaException e) {
			e.printStackTrace();
			throw new MesaException("Erro ao obter informacoes do caixa");
		}

	}

	public MesaModel factoryPedidoBalcaoDetalhado(UsuarioModel usuarioAbertura, Long numero) throws MesaException {
		MesaModel mesa = factoryPedidoBalcao(usuarioAbertura, numero);
		mesa = obterModelAtualizadoComDetalhes(mesa);
		return mesa;
	}

	public MesaModel reabrirPedidoDeBalcao(UsuarioModel usuarioModel, MesaModel mesa) throws MesaException {
		MesaModel mesaAux = obterModelAtualizado(mesa);
		if (mesaAux.getStatus() == MesaStatus.balcao_recebido) {
			throw new MesaException("Pedido já recebido! ");
		} else if (mesaAux.getStatus() == MesaStatus.balcao_cancelado) {
			throw new MesaException("Pedido cancelado! ");
		} else {
			mesa.setStatus(MesaStatus.balcao_feito);
			alterar(mesa);
			mesa = obterModelAtualizadoComDetalhes(mesa);
			return mesa;
		}

	}

	public MesaModel cancelarPedidoDeBalcao(UsuarioModel funcionario, MesaModel balcaoModel) throws MesaException {

		MesaModel balcaoModelAux = obterModelAtualizado(balcaoModel);
		if (balcaoModelAux.getStatus() == MesaStatus.balcao_recebido) {
			throw new MesaException("Pedido já recebido \nNão é possivel cancelar");
		} else if (balcaoModelAux.getStatus() == MesaStatus.balcao_fechado) {
			throw new MesaException("Pedido fechado\nNão é possivel cancelar \n Favor reabrir o pedido");
		} else {
			balcaoModelAux.setStatus(MesaStatus.balcao_cancelado);
			alterar(balcaoModelAux);

			return balcaoModelAux;
		}
	}

	public MesaModel fecharPedidoDeBalcao(UsuarioModel usuarioModel, MesaModel mesa) throws MesaException {
		MesaModel mesaAux = obterModelAtualizado(mesa);
		if (mesaAux.getStatus() == MesaStatus.balcao_recebido) {
			mesa = obterModelAtualizadoComDetalhes(mesa);
			return mesa;
		} else if (mesaAux.getStatus() == MesaStatus.balcao_cancelado) {
			throw new MesaException("Pedido cancelado! ");
		} else {
			mesa = inserirItens(usuarioModel, mesa);
			mesa.setStatus(MesaStatus.balcao_fechado);
			alterar(mesa);
			mesa = obterModelAtualizadoComDetalhes(mesa);

			return mesa;
		}

	}

	public String getSqlFiltroRecebidos(MesaModel vo) {
		String filtro = getGenericFiltro(vo);
		filtro += " and 1=1 ";
		filtro += " and (status='" + MesaStatus.balcao_recebido.toString() + "' or ";
		filtro += "  status='" + MesaStatus.mesa_recebida.toString() + "' or ";
		filtro += "  status='" + MesaStatus.entrega_recebido.toString() + "')";

		filtro += " order by id desc";

		return filtro;
	}

	public GenericPagina<MesaModel> obterPaginaRecebidoPosterior(GenericPagina<MesaModel> pagina, MesaModel filtroModel) throws MesaException {

		try {
			return dao.obterPaginaPosterior(pagina, getSqlFiltroRecebidos(filtroModel));
		} catch (Exception e) {
			e.printStackTrace();
			throw (MesaException) new Exception("Ocorreu um erro ao tentar obter os dados da pagina :\n" + e.getMessage());
		}

	}

	public GenericPagina<MesaModel> obterPaginaRecebidoAnterior(GenericPagina<MesaModel> pagina, MesaModel filtroModel) throws MesaException {

		try {
			return dao.obterPaginaAnterior(pagina, getSqlFiltroRecebidos(filtroModel));
		} catch (Exception e) {

			e.printStackTrace();
			throw (MesaException) new Exception("Ocorreu um erro ao tentar obter os dados da pagina :\n" + e.getMessage());
		}

	}

}
