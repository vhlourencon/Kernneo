package br.com.kernneo.server.negocio;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;



import br.com.kernneo.client.exception.CaixaException;
import br.com.kernneo.client.exception.EmpresaException;
import br.com.kernneo.client.exception.ImpressoraException;
import br.com.kernneo.client.exception.MesaException;
import br.com.kernneo.client.exception.MesaRecebimentoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.CaixaRelatorioModel;
import br.com.kernneo.client.model.EmpresaModel;
import br.com.kernneo.client.model.MesaModel;
import br.com.kernneo.client.model.MesaRecebimentoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.client.types.MesaStatus;
import br.com.kernneo.server.SendMailUsingAuthentication;
import br.com.kernneo.server.dao.CaixaDAO;
import br.com.kernneo.server.report.CaixaResumidoReport;
import br.com.kernneo.server.report.FechaCaixaReport;

public class Caixa extends Negocio<CaixaModel, CaixaDAO, CaixaException> {

    public CaixaModel obterCaixaAberto() throws CaixaException {

	try {
	    return new CaixaDAO().obterCaixaAberto();
	} catch (SQLException e) {

	    e.printStackTrace();
	    throw new CaixaException("Erro ao obter informações: " + e.getMessage());
	}

    }

    public void fexarCaixaAberto(UsuarioModel usuarioFechamento) throws CaixaException {

	CaixaModel caixaModel = obterCaixaAberto();
	if (caixaModel == null) {
	    throw new CaixaException("Nenhum Caixa aberto!");
	} else {
	    try {
		ArrayList<MesaModel> listaDePedidos = new Mesa().obterTodosPedidosDeBalcaoNaoAbertosNoCaixaAberto();
		boolean pedidoSemReceber = false;
		for (MesaModel mesaModel : listaDePedidos) {
		    if (mesaModel.getStatus() == MesaStatus.balcao_feito ) {
			pedidoSemReceber = true;
			break;
		    }
		}
		if (pedidoSemReceber) {
		    throw new CaixaException("Erro ao fechar o caixa.\nExistem pedidos sem receber.\n Favor recebe-los antes de fechar o caixa!");
		}
		caixaModel.setAberto(false);
		caixaModel.setDataHoraFechamento(new Date());
		caixaModel.setUsuarioFechamento(usuarioFechamento);
		alterar(caixaModel);

		FechaCaixaReport fechaCaixaReport = new FechaCaixaReport(caixaModel);
		fechaCaixaReport.imprimir(LocalDeImpressao.caixa);

		StringBuffer stringBufferRelatorio = fechaCaixaReport.geraConteudo(LocalDeImpressao.caixa);

		String stringRelatorio = stringBufferRelatorio.toString();
		stringRelatorio = stringRelatorio.replaceAll("\n", "<br>");
		String email = "<div style='margin-bottom: -6px; padding: 30px;background: #E2E2E2;'>" + stringRelatorio + " </div> ";

		try {
		    EmpresaModel empresaModel = new Empresa().obterEmpresa();
		    if (empresaModel.getEmailUsuarioFechamentoCaixa() != null) {
			String destinatario[] = new String[] { empresaModel.getEmailUsuarioFechamentoCaixa().getEmail() };
			SendMailUsingAuthentication sendMailUsingAuthentication = new SendMailUsingAuthentication();
			sendMailUsingAuthentication.postMail(new Empresa().obterEmpresa(), destinatario, "Relatório de Fechamendo de Caixa", sendMailUsingAuthentication.getEmailHTML(email));
		    }
		} catch (Exception e) {

		    e.printStackTrace();
		}

	    } catch (ImpressoraException e) {

		e.printStackTrace();
		throw new CaixaException("Erro ao imprimir fechamento de caixa");
	    } catch (MesaException e1) {

		throw new CaixaException("Erro ao obter informaçoes dos pedidos em aberto");
	    }
	}

    }

    public CaixaModel abrirCaixa(UsuarioModel usuarioModel, MesaRecebimentoModel mesaRecebimentoInicial) throws CaixaException {

	CaixaModel vo = new CaixaModel();
	if (obterCaixaAberto() == null) {
	    vo.setAberto(true);
	    vo.setDataHoraAbertura(new Date());
	    vo.setUsuarioAbertura(usuarioModel);
	    vo = salvar(vo);

	    try {
		new MesaRecebimento().inserirRecebimentoNoCaixaAberto(usuarioModel, mesaRecebimentoInicial);
		vo = obterCaixaAberto();
		return vo;
	    } catch (MesaRecebimentoException e) {
		e.printStackTrace();
		throw new CaixaException("Erro ao inserir movimentacao inicial!");
	    }

	} else {
	    throw new CaixaException("Ja existe um caixa aberto");
	}

    }

    public Caixa() {
	super();
	dao = new CaixaDAO();
    }

    @Override
    public CaixaException validar(CaixaModel vo) {
	StringBuffer msg = new StringBuffer("");
	// if (vo.getUsuarioAbertura() == null) {
	// msg.append("O campo data Hora de Abertura  é de preenchimento obrigatório!");
	// }

	if (msg.length() > 0)
	    return new CaixaException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(CaixaModel vo) {
	String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";

	filtro += " order by id asc";

	return filtro;
    }

    public ArrayList<CaixaRelatorioModel> obterCaixasFechadosNoPeridoAgrupado(Date dataIni, Date dataFim, String agrupamento) throws CaixaException {
	try {
	    Calendar calendarDataIni = Calendar.getInstance();
	    calendarDataIni.setTime(dataIni);
	    calendarDataIni.set(Calendar.HOUR_OF_DAY, 0);
	    calendarDataIni.set(Calendar.MINUTE, 0);
	    calendarDataIni.set(Calendar.SECOND, 0);
	    dataIni = calendarDataIni.getTime();

	    Calendar calendarDataFim = Calendar.getInstance();
	    calendarDataFim.setTime(dataFim);
	    calendarDataFim.set(Calendar.HOUR_OF_DAY, 23);
	    calendarDataFim.set(Calendar.MINUTE, 59);
	    calendarDataFim.set(Calendar.SECOND, 59);

	    dataFim = calendarDataFim.getTime();

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println(" data ini " + sdf.format(calendarDataIni.getTime()));
	    System.out.println(" data fim " + sdf.format(calendarDataFim.getTime()));

	    return dao.obterCaixasFechadosNoPeridoAgrupado(dataIni, dataFim, agrupamento);
	} catch (SQLException e) {
	    e.printStackTrace();
	    throw new CaixaException(e.getLocalizedMessage());
	}

    }

    public ArrayList<CaixaModel> obterCaixasFechadosNoPerido(Date dataIni, Date dataFim) throws CaixaException {
	try {
	    Calendar calendarDataIni = Calendar.getInstance();
	    calendarDataIni.setTime(dataIni);
	    calendarDataIni.set(Calendar.HOUR_OF_DAY, 0);
	    calendarDataIni.set(Calendar.MINUTE, 0);
	    calendarDataIni.set(Calendar.SECOND, 0);
	    dataIni = calendarDataIni.getTime();

	    Calendar calendarDataFim = Calendar.getInstance();
	    calendarDataFim.setTime(dataFim);
	    calendarDataFim.set(Calendar.HOUR_OF_DAY, 23);
	    calendarDataFim.set(Calendar.MINUTE, 59);
	    calendarDataFim.set(Calendar.SECOND, 59);

	    dataFim = calendarDataFim.getTime();

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println(" data ini " + sdf.format(calendarDataIni.getTime()));
	    System.out.println(" data fim " + sdf.format(calendarDataFim.getTime()));

	    return dao.obterCaixasFEchadosNoPerido(dataIni, dataFim);
	} catch (SQLException e) {
	    e.printStackTrace();
	    throw new CaixaException(e.getLocalizedMessage());
	}

    }

    public void imprimirListaDeCaixa(ArrayList<CaixaModel> listaDECaixas, Date dataInicial, Date dataFinal, boolean resumido) throws CaixaException {
	if (listaDECaixas != null) {
	    try {
		if (resumido) {
		    CaixaResumidoReport caixaResumidoReport = new CaixaResumidoReport(listaDECaixas, dataInicial, dataFinal);
		    caixaResumidoReport.imprimir(LocalDeImpressao.caixa);
		} else {
		    for (CaixaModel caixaModel : listaDECaixas) {
			FechaCaixaReport fechaCaixaReport = new FechaCaixaReport(caixaModel);
			fechaCaixaReport.imprimir(LocalDeImpressao.caixa);
		    }
		}
	    } catch (ImpressoraException e) {
		throw new CaixaException(e.getLocalizedMessage());
	    }
	} else {
	    throw new CaixaException("Lista nula");
	}

    }

}
