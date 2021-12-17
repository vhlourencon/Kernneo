package br.com.kernneo.server.negocio;

import java.math.BigDecimal;

import br.com.kernneo.client.exception.ContaBancariaException;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.dao.ContaBancariaDAO;

public class ContaBancaria extends Negocio<ContaBancariaModel, ContaBancariaDAO, ContaBancariaException> {
	
	
	@Override
	public ContaBancariaModel salvar(ContaBancariaModel vo) throws ContaBancariaException {
		vo = super.salvar(vo);
	     if(vo.getMovimentacaoInicial() == null) { 
	    	 vo.setMovimentacaoInicial(new MovimentacaoModel());
	    	 vo.getMovimentacaoInicial().setValor(BigDecimal.ZERO);
	     }
	     vo.getMovimentacaoInicial().setTipo(null);
	     vo.getMovimentacaoInicial().setContaMovimentacaoInicial(true);
	     vo.getMovimentacaoInicial().setConta(vo);
	     alterar(vo);
	     vo = obterPorId(vo);
	     
		return vo;
	}

	public ContaBancaria() {
		super();
		dao = new ContaBancariaDAO();
	}

	@Override
	public ContaBancariaException validar(ContaBancariaModel vo) {
		StringBuffer msg = new StringBuffer("");
		if (vo.getNome() == null || vo.getNome().trim().length() == 0) {
			msg.append("O campo Descricao  é de preenchimento obrigatório!");
		}

		if (msg.length() > 0)
			return new ContaBancariaException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(ContaBancariaModel vo) {
		String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

		filtro += " where 1=1 ";

		if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
			filtro += " and nome like('%" + vo.getNome() + "%')";
		}

		filtro += " order by id asc";

		return filtro;
	}

}
