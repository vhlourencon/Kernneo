package br.com.kernneo.server.band.negocio;

import br.com.kernneo.client.band.model.BandAgenciaModel;
import br.com.kernneo.client.band.model.BandEmpresaModel;
import br.com.kernneo.client.band.model.BandPiModel;
import br.com.kernneo.client.exception.ProdutoException;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.server.band.dao.BandAgenciaDAO;
import br.com.kernneo.server.band.dao.BandEmpresaDAO;
import br.com.kernneo.server.band.dao.BandPiDAO;

public class BandPi extends BandNegocio<BandPiModel, BandPiDAO> {

	public BandPi() {
		super();
		dao = new BandPiDAO();
	}

	public BandPiModel obterPorCodigo(String codigo) throws Exception {

		return dao.obterPorCodigo(codigo);
	}

	@Override
	public Exception validar(BandPiModel vo) {
		StringBuffer msg = new StringBuffer("");
		if (vo.getEmpresa() == null) {
			msg.append("O campo Empresa  é de preenchimento obrigatório! \n");
		}

		if (vo.getAgencia() == null) {
			msg.append("O campo Agencia  é de preenchimento obrigatório! \n");
		}

		if (vo.getCodigo() == null || vo.getCodigo().trim().length() == 0) {
			msg.append("O campo Código  é de preenchimento obrigatório! \n");
		}
//		} else {
//			
//			try {
//				BandPiModel modelAux = obterPorCodigo(vo.getCodigo());
//				if (modelAux != null) {
//					if (vo.getId() == null) {
//						msg.append("Código já cadastrado! \n");
//					} else if (vo.getId().compareTo(modelAux.getId()) != 0) {
//						msg.append("Código já cadastrado! \n");
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				msg.append("Erro ao verificar código do Pedido\n");
//			}
//
//		}

		if (msg.length() > 0)
			return new ProdutoException(msg.toString());
		else
			return null;
	}

}
