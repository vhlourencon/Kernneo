package br.com.kernneo.server.negocio;

import java.math.BigDecimal;

import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.dao.MovimentacaoDAO;

public class Movimentacao extends Negocio<MovimentacaoModel, MovimentacaoDAO, MovimentacaoException> {

	public Movimentacao() {
		super();
		dao = new MovimentacaoDAO();
	}

	@Override
	public MovimentacaoModel salvar(MovimentacaoModel vo) throws MovimentacaoException {
		MovimentacaoException exc = validar(vo);
		if (exc == null) {
			try {
				// CaixaModel caixaModel = new Caixa().obterCaixaAberto();
				// if (caixaModel == null) {
				// throw new MovimentacaoException("Não existe nenhum caixa aberto! Favor abrir
				// um caixa");
				// }
				// vo.setCaixa(caixaModel);
				BigDecimal valorAux = vo.getValor();
				if(vo.getTipo() == MovimentacaoFinanceiraTypes.credito) { 
					if(valorAux.compareTo(BigDecimal.ZERO) < 0) { 
						valorAux = valorAux.negate(); 
					}
				}
				if(vo.getTipo() == MovimentacaoFinanceiraTypes.debito) { 
					if(valorAux.compareTo(BigDecimal.ZERO) > 0) { 
						valorAux = valorAux.negate(); 
					}
				}
				vo.setValor(valorAux);
				dao.salvar(vo);
				return vo;

			} catch (Exception e) {
				throw new MovimentacaoException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
			}
		} else {
			throw exc;
		}
	}

	@Override
	public MovimentacaoException validar(MovimentacaoModel vo) {
		StringBuffer msg = new StringBuffer("");
		if (vo.getConta() == null) {
			msg.append("O campo Conta  é de preenchimento obrigatório! \n");
		}

		if (vo.getDataHora() == null) {
			msg.append("O campo Data  é de preenchimento obrigatório! \n");
		}

		if (vo.getValor() == null) {
			msg.append("O campo Valor  é de preenchimento obrigatório! \n");
		}

		if (msg.length() > 0)
			return new MovimentacaoException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(MovimentacaoModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
		String deletado = "and deletado = false";
		filtro += " where 1=1 " + deletado;

		// if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
		// filtro += " and nome like('%" + vo.getNome() + "%')";
		// }

		filtro += " order by id asc";

		return filtro;
	}

}
