package br.com.kernneo.server.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.exception.CaixaException;
import br.com.kernneo.client.exception.ContaException;
import br.com.kernneo.client.exception.ImpressoraException;
import br.com.kernneo.client.exception.MesaRecebimentoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.ContaType;
import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.server.dao.MesaRecebimentoDAO;
import br.com.kernneo.server.report.SangriaReport;


public class MesaRecebimento extends Negocio<MesaRecebimentoModel, MesaRecebimentoDAO, MesaRecebimentoException> {

	public MesaRecebimentoModel inserirRecebimento(UsuarioModel usuarioModel, MesaRecebimentoModel recebimento) throws MesaRecebimentoException {

		recebimento.setUsuario(usuarioModel);
		recebimento.setDataHora(new Date());
		merge(recebimento);

		return recebimento;

	}

	public void inserirListaDeRecebimento(UsuarioModel usuarioModel, ArrayList<MesaRecebimentoModel> listaDeRecebimento) throws MesaRecebimentoException {
		for (MesaRecebimentoModel recebimento : listaDeRecebimento) {
			recebimento.setUsuario(usuarioModel);
			recebimento.setDataHora(new Date());

			inserirRecebimento(usuarioModel, recebimento);
		}

	}

	public void inserirRecebimentoNoCaixaAberto(UsuarioModel usuarioModel, MesaRecebimentoModel recebimentoModel) throws MesaRecebimentoException {

		try {

			CaixaModel caixaModel = new Caixa().obterCaixaAberto();

			if (caixaModel == null) {
				throw new MesaRecebimentoException("CAIXA FECHADO!");
			} else {
				if (recebimentoModel.getId() == null || recebimentoModel.getCaixa() == null) {
					recebimentoModel.setCaixa(caixaModel);
					recebimentoModel = inserirRecebimento(usuarioModel, recebimentoModel);

					caixaModel.addRecebimento(recebimentoModel);

					if (recebimentoModel.getFormaDePagamento() == FormaDePagamento.convenio) {
						ContaModel contaModel = new ContaModel();
						contaModel.setTipo(ContaType.conta_a_receber);
						contaModel.setValor(recebimentoModel.getValor());

						if (recebimentoModel.getMesa().getTipo() == MesaTipo.entrega || recebimentoModel.getMesa().getTipo() == MesaTipo.balcao) {
							contaModel.setObs("Pedido nº " + recebimentoModel.getMesa().getId());
						} else {
							contaModel.setObs("Mesa nº " + recebimentoModel.getMesa().getNumero());
						}
						contaModel.setDataHora(new Date());
						contaModel.setCliente(recebimentoModel.getCliente());

						try {
							new Conta().salvar(contaModel);
						} catch (ContaException e) {
							e.printStackTrace();
							throw new MesaRecebimentoException("Erro ao gerar conta a receber");
						}
					}

					if (recebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.saida_caixa && recebimentoModel.getFormaDePagamento() == FormaDePagamento.dinheiro) {
						SangriaReport sangriaReport = new SangriaReport(usuarioModel, recebimentoModel);
						sangriaReport.imprimir(LocalDeImpressao.caixa);
					}

					new Caixa().alterar(caixaModel);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MesaRecebimentoException(e.getLocalizedMessage());
		}

	}

	public void removerRecebimento(UsuarioModel usuarioModel, MesaRecebimentoModel recebimentoModel) throws MesaRecebimentoException {

		try {

			CaixaModel caixaModel = recebimentoModel.getCaixa();
			if (caixaModel != null) {
				caixaModel.delRecebimento(recebimentoModel);
				new Caixa().alterar(caixaModel);
			}

			excluir(recebimentoModel);

		} catch (CaixaException e) {
			e.printStackTrace();
			throw new MesaRecebimentoException(e.getLocalizedMessage());
		}

	}

	// public void inserirListaDeRecebimentoDeMesa(UsuarioModel usuarioModel,
	// MesaModel mesaModel, ArrayList<MesaRecebimentoModel> listaDeRecebimento)
	// throws MesaRecebimentoException {
	// for (MesaRecebimentoModel recebimento : listaDeRecebimento) {
	// recebimento.setUsuario(usuarioModel);
	// recebimento.setDataHora(new Date());
	// recebimento.setMesa(mesaModel);
	//
	// inserirRecebimento(usuarioModel, recebimento);
	// }
	//
	// }

	// public void inserirRecebimentoDeMesa(UsuarioModel usuarioModel, MesaModel
	// mesaModel, MesaRecebimentoModel recebimento) throws
	// MesaRecebimentoException {
	//
	// recebimento.setUsuario(usuarioModel);
	// recebimento.setDataHora(new Date());
	// recebimento.setMesa(mesaModel);
	//
	// inserirRecebimento(usuarioModel, recebimento);
	//
	// }

	// public void inserirRecebimentoDeMEsaNoCaixaAberto(UsuarioModel
	// usuarioModel, MesaModel mesa, MesaRecebimentoModel mesaRecebimentoModel)
	// throws MesaRecebimentoException {
	//
	// try {
	// CaixaModel caixaModel;
	//
	// caixaModel = new Caixa().obterCaixaAberto();
	//
	// if (caixaModel == null) {
	// throw new MesaRecebimentoException("CAIXA FECHADO!");
	// } else {
	// MesaRecebimentoModel recebimentoEmDinheiro = new MesaRecebimentoModel();
	// recebimentoEmDinheiro.setValor(BigDecimal.ZERO);
	// recebimentoEmDinheiro.setMovimentacaoTypes(MovimentacaoTypes.entrada);
	// recebimentoEmDinheiro.setFormaDePagamento(FormaDePagamento.dinheiro);
	//
	// MesaRecebimentoModel recebimentoTrocoDoCliente = new
	// MesaRecebimentoModel();
	// recebimentoTrocoDoCliente.setValor(BigDecimal.ZERO);
	// recebimentoTrocoDoCliente.setMovimentacaoTypes(MovimentacaoTypes.troco);
	// recebimentoTrocoDoCliente.setFormaDePagamento(FormaDePagamento.dinheiro);
	//
	// MesaRecebimentoModel recebimentoCartaoCredito = new
	// MesaRecebimentoModel();
	// recebimentoCartaoCredito.setValor(BigDecimal.ZERO);
	// recebimentoCartaoCredito.setMovimentacaoTypes(MovimentacaoTypes.entrada);
	// recebimentoCartaoCredito.setFormaDePagamento(FormaDePagamento.cartao_credito);
	//
	// MesaRecebimentoModel recebimentoCartaoDebito = new
	// MesaRecebimentoModel();
	// recebimentoCartaoDebito.setValor(BigDecimal.ZERO);
	// recebimentoCartaoDebito.setMovimentacaoTypes(MovimentacaoTypes.entrada);
	// recebimentoCartaoDebito.setFormaDePagamento(FormaDePagamento.cartao_debito);
	//
	// MesaRecebimentoModel recebimentoCheque = new MesaRecebimentoModel();
	// recebimentoCheque.setValor(BigDecimal.ZERO);
	// recebimentoCheque.setMovimentacaoTypes(MovimentacaoTypes.entrada);
	// recebimentoCheque.setFormaDePagamento(FormaDePagamento.cartao_debito);
	//
	// if (mesaRecebimentoModel.getId() == null) {
	// mesaRecebimentoModel.setMesa(mesa);
	// mesaRecebimentoModel.setCaixa(caixaModel);
	// mesaRecebimentoModel = inserirRecebimento(usuarioModel,
	// mesaRecebimentoModel);
	//
	// if (mesaRecebimentoModel.getMovimentacaoTypes() ==
	// MovimentacaoTypes.entrada) {
	// if (mesaRecebimentoModel.getFormaDePagamento() ==
	// FormaDePagamento.dinheiro) {
	// recebimentoEmDinheiro.setValor(recebimentoEmDinheiro.getValor().add(mesaRecebimentoModel.getValor()));
	// } else if (mesaRecebimentoModel.getFormaDePagamento() ==
	// FormaDePagamento.cartao_credito) {
	// recebimentoCartaoCredito.setValor(recebimentoCartaoCredito.getValor().add(mesaRecebimentoModel.getValor()));
	// } else if (mesaRecebimentoModel.getFormaDePagamento() ==
	// FormaDePagamento.cartao_debito) {
	// recebimentoCartaoDebito.setValor(recebimentoCartaoDebito.getValor().add(mesaRecebimentoModel.getValor()));
	// } else if (mesaRecebimentoModel.getFormaDePagamento() ==
	// FormaDePagamento.cheque) {
	// recebimentoCheque.setValor(recebimentoCheque.getValor().add(mesaRecebimentoModel.getValor()));
	// }
	//
	// } else if (mesaRecebimentoModel.getMovimentacaoTypes() ==
	// MovimentacaoTypes.troco) {
	//
	// recebimentoTrocoDoCliente.setValor(recebimentoTrocoDoCliente.getValor().add(mesaRecebimentoModel.getValor()));
	//
	// }
	//
	// }
	//
	// recebimentoEmDinheiro.setValor(recebimentoEmDinheiro.getValor().subtract(recebimentoTrocoDoCliente.getValor()));
	//
	// caixaModel.addRecebimento(recebimentoEmDinheiro);
	// caixaModel.addRecebimento(recebimentoCheque);
	// caixaModel.addRecebimento(recebimentoCartaoCredito);
	// caixaModel.addRecebimento(recebimentoCartaoDebito);
	//
	// new Caixa().alterar(caixaModel);
	// }
	//
	// } catch (CaixaException e) {
	// e.printStackTrace();
	// throw new MesaRecebimentoException(e.getLocalizedMessage());
	// }
	//
	// }

	public void inserirListaDeRecebimentoNoCaixaAberto(UsuarioModel usuarioModel, ArrayList<MesaRecebimentoModel> listaDeRecebimentos) throws MesaRecebimentoException {

		try {
			CaixaModel caixaModel;
			caixaModel = new Caixa().obterCaixaAberto();

			if (caixaModel == null) {
				throw new MesaRecebimentoException("CAIXA FECHADO!");
			} else {

				for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimentos) {
					inserirRecebimentoNoCaixaAberto(usuarioModel, mesaRecebimentoModel);
				}

			}

		} catch (CaixaException e) {
			e.printStackTrace();
			throw new MesaRecebimentoException(e.getLocalizedMessage());
		}

	}

	// public void inserirListaDeRecebimentoNoCaixaAberto(UsuarioModel
	// usuarioModel, ArrayList<MesaRecebimentoModel> listaDeRecebimento) throws
	// MesaRecebimentoException {
	//
	// try {
	// CaixaModel caixaModel;
	//
	// caixaModel = new Caixa().obterCaixaAberto();
	//
	// if (caixaModel == null) {
	// throw new MesaRecebimentoException("CAIXA FECHADO!");
	// } else {
	// for (MesaRecebimentoModel mesaRecebimentoModel : listaDeRecebimento) {
	// mesaRecebimentoModel.setCaixa(caixaModel);
	// mesaRecebimentoModel = inserirRecebimento(usuarioModel,
	// mesaRecebimentoModel);
	// caixaModel.addRecebimento(mesaRecebimentoModel);
	//
	// }
	// new Caixa().alterar(caixaModel);
	// }
	//
	// } catch (CaixaException e) {
	// e.printStackTrace();
	// throw new MesaRecebimentoException(e.getLocalizedMessage());
	// }
	//
	// }

	public ArrayList<MesaRecebimentoModel> obterTodosPorMesa(MesaModel mesa) throws MesaRecebimentoException {

		try {
			ArrayList<MesaRecebimentoModel> listaDeMesaRecebimento = new MesaRecebimentoDAO().obterTodosPorMesa(mesa);
			if (listaDeMesaRecebimento == null) {
				listaDeMesaRecebimento = new ArrayList<MesaRecebimentoModel>();
			}

			return listaDeMesaRecebimento;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MesaRecebimentoException("Erro ao obter recebimentos da mesa!");
		}

	}

	public ArrayList<MesaRecebimentoModel> obterTodosPorCliente(ClienteModel cliente) throws MesaRecebimentoException {

		try {
			ArrayList<MesaRecebimentoModel> listaDeMesaRecebimento = new MesaRecebimentoDAO().obterTodosPorClienteSemReceber(cliente);
			return listaDeMesaRecebimento;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MesaRecebimentoException("Erro ao obter recebimentos da mesa!");
		}

	}

	public MesaRecebimento() {
		super();
		dao = new MesaRecebimentoDAO();
	}

	@Override
	public MesaRecebimentoException validar(MesaRecebimentoModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new MesaRecebimentoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(MesaRecebimentoModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

		filtro += " where 1=1 ";

		filtro += " order by id asc";

		return filtro;
	}

}
