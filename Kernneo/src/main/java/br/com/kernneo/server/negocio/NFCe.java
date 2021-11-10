package br.com.kernneo.server.negocio;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.ini4j.Wini;

import br.com.kernneo.client.exception.EmpresaException;
import br.com.kernneo.client.exception.NFCeException;
import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.client.model.IcmsCSOSNModel;
import br.com.kernneo.client.model.IcmsCSTModel;
import br.com.kernneo.client.model.ItemModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.NFCeModel;
import br.com.kernneo.client.types.CFOP;
import br.com.kernneo.client.types.CRT;
import br.com.kernneo.client.types.CSOSN;
import br.com.kernneo.client.types.CST;
import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.types.MovimentacaoTypes;
import br.com.kernneo.client.types.NFCeSituacao;
import br.com.kernneo.client.types.NFETipoAmbiente;
import br.com.kernneo.client.types.NFETipoEmissao;
import br.com.kernneo.server.ACBR;
import br.com.kernneo.server.dao.NFCeDAO;

public class NFCe extends Negocio<NFCeModel, NFCeDAO, NFCeException> {

	private String versao = "3.10";
	
	
	public void enviarEmail(NFCeModel nfceModel) throws NFCeException {

	}


	public ArrayList<NFCeModel> obterTodasPorMesa(MesaModel mesaModel) throws NFCeException {
		try {

			ArrayList<NFCeModel> lista = dao.obterTodasPorMesa(mesaModel);
			if (lista != null) {
				for (NFCeModel nfCeModel : lista) {
					nfCeModel.setMesa(mesaModel);
				}
			}
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new NFCeException("Erro ao obter informacoes de nfce!");
		}
	}

	private ArrayList<NFCeModel> obterNFCePorSerieEnumero(Long serie, Long numero) throws NFCeException {
		try {

			ArrayList<NFCeModel> lista = dao.obterNFCePorSerieEnumero(serie, numero);
			if (lista == null) {
				lista = new ArrayList<NFCeModel>();
			}
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new NFCeException("Erro ao obter informacoes de nfce!");
		}
	}

	private NFCeModel criarNFCe(EmpresaModel empresaModel, MesaModel mesaModel, Long numero, Long serie, NFETipoAmbiente nfeTipoAmbiente, NFETipoEmissao nfeTipoEmissao) throws NFCeException {

		try {

			NFCeModel nfceModel = new NFCeModel();
			nfceModel.setNumero(numero);
			nfceModel.setSerie(serie);
			nfceModel.setNfeTipoAmbiente(nfeTipoAmbiente);
			nfceModel.setMesa(mesaModel);
			nfceModel.setNfeTipoEmissao(nfeTipoEmissao);
			nfceModel.setEmpresaEmitente(empresaModel);
			nfceModel.setDataHoraEmissao(new Date());

			ACBR acbr = new ACBR();
			// acbr.conectar();
			String nfceString = converteParaNFCeACBR(nfceModel).toString();
			String[] retornoCriarNFE = acbr.enviar("NFE.CriarNFe(" + removerAcentos(nfceString) + ")");
			if (retornoCriarNFE[0].equals("OK")) {
				nfceModel.setPath(retornoCriarNFE[1]);
				return nfceModel;
			} else {
				throw new NFCeException(retornoCriarNFE[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NFCeException(e.getLocalizedMessage());
		}

	}

	public NFCeModel assinarNFCe(NFCeModel nfce) throws NFCeException {

		try {
			ACBR acbr = new ACBR();
			// acbr.conectar();
			final String[] retornoAssinarNFE = acbr.enviar("NFE.AssinarNFe(\"" + nfce.getPath() + "\")");

			if (retornoAssinarNFE[0].equals("OK")) {
				nfce.setPath(retornoAssinarNFE[1]);
				return nfce;
			} else {
				throw new NFCeException(retornoAssinarNFE[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NFCeException(e.getLocalizedMessage());
		}

	}

	public NFCeModel validarNFCe(NFCeModel nfce) throws NFCeException {

		try {

			ACBR acbr = new ACBR();
			// acbr.conectar();
			String[] retornoValidarNFE = acbr.enviar("NFE.ValidarNFe(" + nfce.getPath() + ")");
			if (retornoValidarNFE[0].equals("OK")) {
				return nfce;
			} else {
				throw new NFCeException(retornoValidarNFE[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NFCeException(e.getLocalizedMessage());
		}

	}

	public NFCeModel enviarNFCeEmModoNomal(NFCeModel nfceModel, boolean imprimir) throws NFCeException {

		try {

			nfceModel.setNfeTipoEmissao(NFETipoEmissao._1_EMISSAO_NORMAL);

			ACBR acbr = new ACBR();
			// acbr.conectar();
			acbr.enviar("NFe.SetFormaEmissao(1)");

			nfceModel.setxMotivo(null);
			nfceModel.setcStat(0);
			
			String imprimirStr = "0"; 
			if ( imprimir) { 
				imprimirStr = "1";
			}
			
			

			String[] retornoEnviarNFCE = acbr.enviar("NFE.EnviarNFE(\"" + nfceModel.getPath() + "\",1,0," + imprimirStr + ",1)");
			if (retornoEnviarNFCE[0].equals("OK")) {

				File arquivo = new File("c:\\kernneo\\nfceTemp.ini");
				FileWriter fw = new FileWriter(arquivo, false);
				fw.write(retornoEnviarNFCE[1]);
				fw.close();

				Wini ini = new Wini(new File("c:\\kernneo\\", "nfceTemp.ini"));
				String cstat = (ini.get("RETORNO", "CStat"));

				String xMotivo = (ini.get("RETORNO", "XMotivo"));
				String chNfce = (ini.get("RETORNO", "ChNFe"));
				nfceModel.setxMotivo(xMotivo);
				nfceModel.setChaveNFCe(chNfce);

				if (cstat != null) {
					int intCstat = Integer.valueOf(cstat);
					nfceModel.setcStat(intCstat);
				}

				if (nfceModel.getcStat() == 100) {
					nfceModel.setNfCeSituacao(NFCeSituacao.autorizada);
				} else {
					nfceModel.setNfCeSituacao(NFCeSituacao.rejeitada);
				}
				return nfceModel;

			} else {
				if (retornoEnviarNFCE[1].trim().toLowerCase().contains("erro http: 500")) {
					nfceModel.setNfeTipoEmissao(NFETipoEmissao._9_CONTINGENCIA_OFF_LINE);
					return nfceModel;
				} else {
					throw new NFCeException(retornoEnviarNFCE[1]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NFCeException(e.getLocalizedMessage());
		}

	}

	public NFCeModel enviarNFCeEmModoContigencia(NFCeModel nfceModel) throws NFCeException {

		try {
			nfceModel.setNfeTipoEmissao(NFETipoEmissao._9_CONTINGENCIA_OFF_LINE);
			nfceModel.setNfCeSituacao(NFCeSituacao.emitida_em_contigencia);
			nfceModel.setxMotivo(null);
			nfceModel.setcStat(0);

			ACBR acbr = new ACBR();
			acbr.enviar("NFe.SetFormaEmissao(9)");
			acbr.enviar("NFE.ImprimirDanfe(" + nfceModel.getPath() + ")");

			return nfceModel;
		} catch (Exception e) {

			e.printStackTrace();
			throw new NFCeException("Erro ao enviar nota em contigencia");
		}

	}

	public boolean statusServico() throws Exception {

		try {
			ACBR acbr = new ACBR();
			// acbr.conectar();
			String[] retorno = acbr.enviar("NFe.statusServico()");

			if (retorno[0].equals("OK")) {
				return true;
			} else if (retorno[0].equals("ERRO")) {
				return false;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public NFCe() {
		super();
		dao = new NFCeDAO();
	}

	public NFCeModel emitirNFCeNova(MesaModel mesaModel, NFETipoEmissao nfeTipoEmissao) throws NFCeException {
		try {
			EmpresaModel empresaModel = new Empresa().obterEmpresa();

			Long serie = empresaModel.getNfceSerieHomologacao();
			Long numero = empresaModel.getNfceNumeroHomologacao();
			NFETipoAmbiente nfeTipoAmbiente = empresaModel.getNfeTipoAmbiente();

			if (empresaModel.getNfeTipoAmbiente() == NFETipoAmbiente._1_PRODUCAO) {
				serie = empresaModel.getNfceSerieProducao();
				numero = empresaModel.getNfceNumeroProducao();
			}

			if (serie == null) {
				serie = 1L;
			}

			if (numero == null) {
				numero = 0L;
			}

			numero = numero + 1;
			ArrayList<NFCeModel> lista = obterNFCePorSerieEnumero(serie, numero);
			while (lista.size() > 0) {
				numero = numero + 1;
				lista = obterNFCePorSerieEnumero(serie, numero);

			}

			NFCeModel nfCeModel = emitirNFCe(empresaModel, mesaModel, numero, serie, nfeTipoAmbiente, nfeTipoEmissao, true);

			if (nfCeModel.getNfCeSituacao() == NFCeSituacao.autorizada || nfCeModel.getNfCeSituacao() == NFCeSituacao.emitida_em_contigencia) {

				if (nfCeModel.getNfeTipoAmbiente() == NFETipoAmbiente._1_PRODUCAO) {
					empresaModel.setNfceNumeroProducao(nfCeModel.getNumero());
				} else if (nfCeModel.getNfeTipoAmbiente() == NFETipoAmbiente._2_HOMOLOGACAO) {
					empresaModel.setNfceNumeroHomologacao(nfCeModel.getNumero());
				}
				new Empresa().alterar(empresaModel);

			}

			return nfCeModel;
		} catch (EmpresaException e) {
			e.printStackTrace();
			throw new NFCeException(e.getLocalizedMessage());
		}

	}

	public NFCeModel reEmitirNFCe(NFCeModel nfceReemitirModel, NFETipoEmissao nfeTipoEmissao) throws NFCeException {

		NFCeModel nfCeModel = emitirNFCe(nfceReemitirModel.getEmpresaEmitente(), nfceReemitirModel.getMesa(), nfceReemitirModel.getNumero(), nfceReemitirModel.getSerie(), nfceReemitirModel.getNfeTipoAmbiente(), nfeTipoEmissao,true);

		return nfCeModel;
	}

	/*
	 * emitir ou recriar NFCe
	 */
	public NFCeModel emitirNFCe(EmpresaModel empresaModel, MesaModel mesaModel, Long numero, Long serie, NFETipoAmbiente nfeTipoAmbiente, NFETipoEmissao nfeTipoEmissao, boolean imprimir) throws NFCeException {

		NFCeModel nfCeModel = criarNFCe(empresaModel, mesaModel, numero, serie, nfeTipoAmbiente, nfeTipoEmissao);
		nfCeModel = assinarNFCe(nfCeModel);
		nfCeModel = validarNFCe(nfCeModel);

		if (nfeTipoEmissao == NFETipoEmissao._1_EMISSAO_NORMAL) {
			nfCeModel = enviarNFCeEmModoNomal(nfCeModel, imprimir);
		}

		if (nfCeModel.getNfeTipoEmissao() == NFETipoEmissao._9_CONTINGENCIA_OFF_LINE) {
			nfCeModel = enviarNFCeEmModoContigencia(nfCeModel);
		}

		return nfCeModel;

	}

	// public NFCeModel emitirNFCeEmModoNormal(MesaModel mesa, NFCeModel
	// nfCeModel) throws NFCeException {
	//
	// nfCeModel = criarNFCe(mesa, nfCeModel, NFETipoEmissao._1_EMISSAO_NORMAL);
	// nfCeModel = assinarNFCe(nfCeModel);
	// nfCeModel = validarNFCe(nfCeModel);
	//
	// nfCeModel = enviarNFCeEmModoNomal(nfCeModel);
	//
	// return nfCeModel;
	//
	// }

	// public NFCeModel emitirNFCeEmContigencia(NFCeModel nfCeModel) throws
	// NFCeException {
	//
	// nfCeModel = enviarNFCeEmModoNomal(nfCeModel);
	// if (nfCeModel.getNfCeSituacao() == NFCeSituacao.emitida_em_contigencia) {
	// throw new
	// NFCeException("Serviço inoperante - Tente novamente mais tarde!");
	// }
	//
	// return nfCeModel;
	//
	// }

	@Override
	public NFCeException validar(NFCeModel vo) {
		StringBuffer msg = new StringBuffer("");

		if (msg.length() > 0)
			return new NFCeException(msg.toString());
		else
			return null;
	}

	@Override
	public String getSqlFiltro(NFCeModel vo) {
		String filtro = super.getSqlFiltro(vo);

		filtro += " order by id asc";
		return filtro;
	}

	public StringBuffer converteParaNFCeACBR(NFCeModel nfCeModel) throws NFCeException {

		StringBuffer erroNfce = new StringBuffer();

		StringBuffer nfce = new StringBuffer();
		nfce.append("\n");
		nfce.append("[infNFe]\n");
		nfce.append("versao=" + getVersao() + "\n");
		nfce.append("\n");

		nfce.append("[Identificacao]\n");
		nfce.append("natOp=venda\n");
		nfce.append("mod=65\n");
		nfce.append("cNF=" + nfCeModel.getMesa().getId() + "\n");
		nfce.append("nNF=" + nfCeModel.getNumero() + "\n");
		nfce.append("serie=" + nfCeModel.getSerie() + "\n");
		nfce.append("idDest=1\n");
		nfce.append("indFinal=1\n");

		if (nfCeModel.getMesa().getTipo() == MesaTipo.mesa) {
			nfce.append("indPres=1\n");
		} else if (nfCeModel.getMesa().getTipo() == MesaTipo.entrega) {
			nfce.append("indPres=4\n");
		} else if (nfCeModel.getMesa().getTipo() == MesaTipo.balcao) {
			nfce.append("indPres=1\n");
		}

		nfce.append("finNFe=1\n");
		nfce.append("tpImp=4\n");
		nfce.append("tpEmis=" + nfCeModel.getNfeTipoEmissao().getValor() + "\n");

		String dataHoraEmissaoStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(nfCeModel.getDataHoraEmissao());
		if (nfCeModel.getNfeTipoEmissao() == NFETipoEmissao._9_CONTINGENCIA_OFF_LINE) {
			nfce.append("xJust=internet fora do ar\n");
			nfce.append("dhCont=" + dataHoraEmissaoStr + "\n");
		}
		nfce.append("Emissao=" + dataHoraEmissaoStr + "\n");
		nfce.append("\n");

		/*
		 * Add informações da Empresa
		 */
		nfce.append(addInfoEmitente(nfCeModel));

		// nfce.append("cMun=" +
		// empresaEmitente.getBairro().getCidade().getCodigoIbge() + "\n");
		// nfce.append("UF=" +
		// empresaEmitente.getBairro().getCidade().getEstado().getSigla() +
		// "\n");

		nfce.append("[Destinatario]\n");
		nfce.append("cpf=\n");
		nfce.append("cnpj=\n");
		nfce.append("\n");

		/*
		 * Produtos
		 */
		int i = 1;

		for (ItemModel itemModel : nfCeModel.getMesa().getListaDeItens()) {
			nfce.append(addItem(nfCeModel, itemModel, i));
			i++;
		}

		/*
		 * taxa de servico
		 */
		if (nfCeModel.getMesa().getTipo() == MesaTipo.mesa) {
			nfce.append(addTaxaDeServicoItem(nfCeModel, i));

		}

		nfce.append("[Retirada]\n");
		nfce.append("[Entrega]\n");
		nfce.append("[Avulsa]\n");
		nfce.append("\n");

		nfce.append("[Transportador]\n");
		nfce.append("modFrete=9\n");
		nfce.append("\n");

		i = 1;

		for (MesaRecebimentoModel mesaRecebimentoModel : nfCeModel.getMesa().getListaDeRecedimentos()) {
			if (mesaRecebimentoModel.getMovimentacaoTypes() == MovimentacaoTypes.entrada_mesa && mesaRecebimentoModel.getFormaDePagamento() != FormaDePagamento.dinheiro) {
				nfce.append("[PAG" + getNumeroFormatado(i) + "]\n");
				nfce.append("vPag=" + mesaRecebimentoModel.getValor() + "\n");
				if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cheque) {
					nfce.append("tPag=02\n");
				} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cartao_credito) {
					nfce.append("tPag=03\n");
				} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.cartao_debito) {
					nfce.append("tPag=04\n");
				} else if (mesaRecebimentoModel.getFormaDePagamento() == FormaDePagamento.convenio) {
					nfce.append("tPag=05\n");
				}
				i++;
			}
		}

		if (nfCeModel.getMesa().getCalTotalDeEntradasEmDinheiroComOtroco().compareTo(BigDecimal.ZERO) != 0) {

			nfce.append("[PAG" + getNumeroFormatado(i) + "]\n");
			nfce.append("tPag=01\n");
			nfce.append("vPag=" + nfCeModel.getMesa().getCalTotalDeEntradasEmDinheiroComOtroco() + "\n");
		}

		nfce.append("\n");
		nfce.append("[Total]\n");

		nfce.append("ValorProduto=" + nfCeModel.getMesa().getCalcValorTotalDaMesaSemDesconto() + "\n");
		nfce.append("vDesc=" + nfCeModel.getMesa().getCalcTotalDeDesconto() + "\n");
		nfce.append("ValorNota=" + nfCeModel.getMesa().getCalcValorTotalDaMesa() + "\n");
		nfce.append("\n");

		if (erroNfce.length() > 0) {
			throw new NFCeException(erroNfce.toString());
		} else {
			return nfce;
		}

	}

	private StringBuffer addInfoEmitente(NFCeModel nfCeModel) throws NFCeException {
		StringBuffer nfce = new StringBuffer();
		StringBuffer erroNfce = new StringBuffer();

		if (nfCeModel.getEmpresaEmitente() == null) {
			erroNfce.append("Nenhuma configuração de Empresa encontrada");
		} else {
			try {
				nfce.append("[Emitente]\n");

				nfce.append("CNPJ=" + nfCeModel.getEmpresaEmitente().getCnpj() + "\n");
				nfce.append("xNome=" + nfCeModel.getEmpresaEmitente().getRazaoSocial() + "\n");
				nfce.append("xFant= " + nfCeModel.getEmpresaEmitente().getFantasia() + "\n");
				nfce.append("IE=" + nfCeModel.getEmpresaEmitente().getIe() + "\n");
				nfce.append("CRT=" + nfCeModel.getEmpresaEmitente().getCodigoDeRegimeTributario().getText() + "\n");
				nfce.append("xLgr=" + nfCeModel.getEmpresaEmitente().getLogradouro() + "\n");
				nfce.append("nro=" + nfCeModel.getEmpresaEmitente().getNumero() + "\n");
				nfce.append("xBairro=" + nfCeModel.getEmpresaEmitente().getBairro().getNome() + "\n");
				nfce.append("xMun=" + nfCeModel.getEmpresaEmitente().getBairro().getCidade().getNome() + "\n");

				nfce.append("cMun=" + nfCeModel.getEmpresaEmitente().getBairro().getCidade().getCodigoIbge() + "\n");
				nfce.append("UF=" + nfCeModel.getEmpresaEmitente().getBairro().getCidade().getEstado().getSigla() + "\n");
				nfce.append("cUF=" + nfCeModel.getEmpresaEmitente().getBairro().getCidade().getEstado().getCodigoIbge() + "\n");

				/*
				 * Usar ambiente de teste de Manaus
				 */
//				nfce.append("cMun=1302603\n");
//				nfce.append("UF=AM\n");
//				nfce.append("cUF=13\n");
				nfce.append("\n");
			} catch (Exception e) {
				e.printStackTrace();
				erroNfce.append("Erro na configuração de endereço da empresa");
			}

		}

		if (erroNfce.length() > 0) {
			throw new NFCeException(erroNfce.toString());
		} else {
			return nfce;
		}

	}

	private StringBuffer addItem(NFCeModel nfCeModel, ItemModel itemModel, int posicaoItem) throws NFCeException {
		StringBuffer nfce = new StringBuffer();
		StringBuffer erroNfce = new StringBuffer();

		nfce.append("[Produto" + getNumeroFormatado(posicaoItem) + "]\n");

		CFOP nfceCFOP = itemModel.getProduto().getNfceCFOP();
		if (itemModel.getProduto().isNfceUsaConfigEmpresa()) {
			nfceCFOP = nfCeModel.getEmpresaEmitente().getNfceCfop();
		}

		if (nfceCFOP == null) {
			erroNfce.append("Erro na configuração do CFOP do produto Cod. " + itemModel.getProduto().getCodigo() + "\n");
		} else {
			nfce.append("cfop=" + nfceCFOP + "\n");
		}

		nfce.append("codigo='" + itemModel.getProduto().getCodigo() + "'\n");

		if (itemModel.getProduto().getComposicao() == null) {
			erroNfce.append("Erro na configuração do COMPOSIÇÃO do produto Cod. " + itemModel.getProduto().getCodigo() + "\n");
		} else {
			if (itemModel.getProduto().getComposicao().getUnidadeTypes() == null) {
				erroNfce.append("Erro na configuração do COMPOSIÇÃO do produto Cod. " + itemModel.getProduto().getCodigo() + "\n");
			} else {
				nfce.append("uCom='" + itemModel.getProduto().getComposicao().getUnidadeTypes().toString() + "'\n");
			}
		}

		nfce.append("Quantidade=" + itemModel.getCalcQuantidade() + "\n");
		nfce.append("ValorUnitario=" + itemModel.getCalcValorUnitario() + "\n");
		nfce.append("ValorTotal=" + itemModel.getCalcValorTotal() + "\n");

		if (itemModel.getProduto().getNcmString() == null) {
			erroNfce.append("Erro na configuração de NCM do produto Cod. " + itemModel.getProduto().getCodigo() + "\n");
		} else {
			if (itemModel.getProduto().getNcmString().length() != 8) {
				erroNfce.append("Erro na configuração de NCM do produto Cod. " + itemModel.getProduto().getCodigo() + "\n");
			} else {
				nfce.append("NCM=" + itemModel.getProduto().getNcmString() + "\n");
			}
		}

		if (nfCeModel.getNfeTipoAmbiente() == NFETipoAmbiente._2_HOMOLOGACAO) {
			nfce.append("Descricao=NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL\n");
		} else {
			nfce.append("Descricao=" + removeCaracteres(itemModel.getCalcNome()) + "\n");
		}

		nfce.append("\n");

		nfce.append("[ICMS" + getNumeroFormatado(posicaoItem) + "]\n");
		if (itemModel.getProduto().getOrigemDaMercadoria() == null) {
			erroNfce.append("Erro na configuração de Origem da Mercadoria do produto Cod. " + itemModel.getProduto().getCodigo() + "\n");
		} else {
			nfce.append("orig=" + itemModel.getProduto().getOrigemDaMercadoria().getValor() + "\n");
		}

		if (nfCeModel.getEmpresaEmitente().getCodigoDeRegimeTributario() == CRT.simples_nacional) {
			nfce.append(addIcmsCSOSN(nfCeModel, itemModel));
		} else {
			nfce.append(addIcmsCST(nfCeModel, itemModel));
		}

		nfce.append("\n");

		if (erroNfce.length() > 0) {
			throw new NFCeException(erroNfce.toString());
		} else {
			return nfce;
		}

	}

	private StringBuffer addTaxaDeServicoItem(NFCeModel nfCeModel, int posicaoItem) throws NFCeException {
		StringBuffer nfce = new StringBuffer();
		StringBuffer erroNfce = new StringBuffer();

		nfce.append("[Produto" + getNumeroFormatado(posicaoItem) + "]\n");
		nfce.append("cfop=" + "5102" + "\n");
		nfce.append("codigo='0'\n");
		nfce.append("uCom='UN'\n");
		nfce.append("Quantidade=1\n");

		nfce.append("ValorUnitario=" + nfCeModel.getMesa().getCalcTaxaDeServicoEmDinheiro() + "\n");
		nfce.append("ValorTotal=" + nfCeModel.getMesa().getCalcTaxaDeServicoEmDinheiro() + "\n");
		nfce.append("NCM=00000000\n");

		if (nfCeModel.getNfeTipoAmbiente() == NFETipoAmbiente._2_HOMOLOGACAO) {
			nfce.append("Descricao=NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL\n");
		} else {
			nfce.append("xProd=TAXA DE SERVIÇO\n");
		}

		nfce.append("\n");

		nfce.append("[ICMS" + getNumeroFormatado(posicaoItem) + "]\n");
		nfce.append("orig=0\n");
		if (nfCeModel.getEmpresaEmitente().getCodigoDeRegimeTributario() == CRT.regime_normal) {
			nfce.append("CST=" + "" + "41\n");
		} else {
			nfce.append("CSOSN=" + "" + "102\n");
		}

		nfce.append("\n");

		if (erroNfce.length() > 0) {
			throw new NFCeException(erroNfce.toString());
		} else {
			return nfce;
		}

	}

	public String addIcmsCST(NFCeModel nfCeModel, ItemModel itemModel) throws NFCeException {
		try {
			String stringAux = "";

			IcmsCSTModel icmsCSTModel = itemModel.getProduto().getNfceIcmsCstConfig();
			if (itemModel.getProduto().isNfceUsaConfigEmpresa()) {
				icmsCSTModel = nfCeModel.getEmpresaEmitente().getNfceIcmsCstConfig();
			}
			if (icmsCSTModel == null) {
				throw new NFCeException("Erro na configuração do Icms CST do produto: Cod. " + itemModel.getProduto().getCodigo());
			}

			if (icmsCSTModel.getCst() == CST._00 || icmsCSTModel.getCst() == CST._20) {
				stringAux += "modBC=" + icmsCSTModel.getModBC().getValor() + "\n";

				BigDecimal bigDecimalVbC = itemModel.getCalcValorTotal();
				BigDecimal pICMS = icmsCSTModel.getpICMS();

				if (icmsCSTModel.getCst() == CST._20) {

					BigDecimal pRedBC = icmsCSTModel.getpRedBC();
					stringAux += "pRedBC=" + pRedBC + "\n";

					BigDecimal bigDecimalPredBCAux = new BigDecimal(1).subtract(pRedBC.divide(new BigDecimal(100)));
					bigDecimalVbC = bigDecimalVbC.multiply(bigDecimalPredBCAux);

				}

				stringAux += "vBC=" + bigDecimalVbC + "\n";
				stringAux += "pICMS=" + pICMS + "\n";

				BigDecimal bigDecimalVICMS = bigDecimalVbC.multiply(pICMS.divide(new BigDecimal(100)));
				stringAux = "vICMS=" + bigDecimalVICMS + "\n";

				addvBCparaNfce(nfCeModel, bigDecimalVbC);
				addvICMSparaNFCe(nfCeModel, bigDecimalVICMS);

			}

			return stringAux;
		} catch (Exception e) {

			e.printStackTrace();
			throw new NFCeException("Erro na configuração do Icms CST do produto: Cod. " + itemModel.getProduto().getCodigo());

		}
	}

	private void addvICMSparaNFCe(NFCeModel nfCeModel, BigDecimal bigDecimalvICMS) {
		BigDecimal bigDecimalvICMSAux = nfCeModel.getvICMS();
		if (bigDecimalvICMSAux == null) {
			bigDecimalvICMSAux = BigDecimal.ZERO;
		}

		bigDecimalvICMSAux = bigDecimalvICMSAux.add(bigDecimalvICMS);
		nfCeModel.setvICMS(bigDecimalvICMSAux);
	}

	private void addvBCparaNfce(NFCeModel nfCeModel, BigDecimal bigDecimalvBC) {
		BigDecimal bigDecimalVBCAux = nfCeModel.getvBC();
		if (bigDecimalVBCAux == null) {
			bigDecimalVBCAux = BigDecimal.ZERO;
		}

		bigDecimalVBCAux = bigDecimalVBCAux.add(bigDecimalvBC);
		nfCeModel.setvBC(bigDecimalVBCAux);

	}

	public String addIcmsCSOSN(NFCeModel nfCeModel, ItemModel itemModel) throws NFCeException {
		try {
			String stringAux = "";
			IcmsCSOSNModel icmsCSOSNModel = itemModel.getProduto().getNfceIcmsCsosnConfig();
			if (itemModel.getProduto().isNfceUsaConfigEmpresa()) {
				icmsCSOSNModel = nfCeModel.getEmpresaEmitente().getNfceIcmsCsosnConfig();
			}
			if (icmsCSOSNModel == null) {
				throw new NFCeException("Erro na configuração do Icms CSOSN do produto: Cod. " + itemModel.getProduto().getCodigo());
			}

			if (icmsCSOSNModel.getCsosn() == CSOSN._500) {
				stringAux += "CSOSN=" + CSOSN._500.getValor() + "\n";

				stringAux += "vBCSTRet=" + icmsCSOSNModel.getvBCSTRet() + "\n";
				stringAux += "vICMSSTRet=" + icmsCSOSNModel.getvICMSSTRet() + "\n";
			} else if (icmsCSOSNModel.getCsosn() == CSOSN._900) {
				stringAux += "CSOSN=" + CSOSN._500.getValor() + "\n";
			} else {
				stringAux += "CSOSN=" + icmsCSOSNModel.getCsosn().getValor() + "\n";
			}
			return stringAux;
		} catch (Exception e) {

			e.printStackTrace();
			throw new NFCeException("Erro na configuração do Icms CSOSN do produto: Cod. " + itemModel.getProduto().getCodigo());
		}

	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public String removeCaracteres(String texto) {
		String out = texto.replace('(', ' ').replace(')', ' ');
		return out;
	}

	public String getNumeroFormatado(int numero) {

		String numString = "";
		if (numero < 10) {
			numString = "00" + numero;
		} else if (numero >= 10 && numero < 100) {
			numString = "0" + numero;
		} else if (numero >= 100) {
			numString = String.valueOf(numero);
		}

		return numString;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

}
