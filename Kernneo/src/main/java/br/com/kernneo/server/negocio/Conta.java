package br.com.kernneo.server.negocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.kernneo.client.exception.ContaException;
import br.com.kernneo.client.exception.ImpressoraException;
import br.com.kernneo.client.model.ContaModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.client.types.ContaType;
import br.com.kernneo.client.types.LocalDeImpressao;
import br.com.kernneo.server.dao.ContaDAO;
import br.com.kernneo.server.report.ContasReceberReport;

public class Conta extends Negocio<ContaModel, ContaDAO, ContaException> {

    public Conta() {
	super();
	dao = new ContaDAO();
    }

    public void imprimirRelatorioContaReport(UsuarioModel usuarioModel, ContaModel filtroContaModel) throws ContaException {
	ArrayList<ContaModel> listaDeConta = obterTodosComFiltro(filtroContaModel);
	ContasReceberReport contasReceberReport = new ContasReceberReport(usuarioModel, listaDeConta, filtroContaModel);
	try {

	    contasReceberReport.imprimir(LocalDeImpressao.caixa);
	} catch (ImpressoraException e) {
	    e.printStackTrace();
	    throw new ContaException(e.getLocalizedMessage());
	}

    }

    public ContaModel quitarConta(UsuarioModel usuarioModel, ContaModel contaModel) throws ContaException {
	contaModel.setDataHoraQuitado(new Date());
	contaModel.setQuitado(true);
	contaModel.setUsuarioQuitador(usuarioModel);
	alterar(contaModel);

	return contaModel;

    }

    @Override
    public ContaException validar(ContaModel vo) {
	StringBuffer msg = new StringBuffer("");
	if (vo.isQuitado()) {
	    if (vo.getUsuarioQuitador() == null) {
		msg.append("O usuario quitador deve ser preenchido. \n");
	    }
	} else {
	    vo.setDataHoraQuitado(null);
	    vo.setUsuarioQuitador(null);
	}
	if (msg.length() > 0)
	    return new ContaException(msg.toString());
	else
	    return null;
    }

    @Override
    public String getSqlFiltro(ContaModel vo) {
	String filtro = super.getSqlFiltro(vo);
	if (vo.getTipo() != null && vo.getTipo() == ContaType.conta_a_pagar) {
	    filtro += " and tipo='" + ContaType.conta_a_pagar.toString() + "'";
	}

	if (vo.getTipo() != null && vo.getTipo() == ContaType.conta_a_receber) {
	    filtro += " and tipo='" + ContaType.conta_a_receber.toString() + "'";
	}

	if (vo.getCliente() != null && vo.getCliente().getId() != null) {
	    filtro += " and id_cliente=" + vo.getCliente().getId();
	}

	if (vo.getDataHora() != null && vo.getDataHoraQuitado() != null) {
	    Calendar calendarDataIni = Calendar.getInstance();
	    calendarDataIni.setTime(vo.getDataHora());
	    calendarDataIni.set(Calendar.HOUR_OF_DAY, 0);
	    calendarDataIni.set(Calendar.MINUTE, 0);
	    calendarDataIni.set(Calendar.SECOND, 0);
	    Date dataIni = calendarDataIni.getTime();

	    Calendar calendarDataFim = Calendar.getInstance();
	    calendarDataFim.setTime(vo.getDataHoraQuitado());
	    calendarDataFim.set(Calendar.HOUR_OF_DAY, 23);
	    calendarDataFim.set(Calendar.MINUTE, 59);
	    calendarDataFim.set(Calendar.SECOND, 59);

	    Date dataFim = calendarDataFim.getTime();

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    filtro += " and dataHora between '" + sdf.format(dataIni) + "' and '" + sdf.format(dataFim) + "'";

	}

	// if ( vo.isQuitado()) {
	// filtro += " and g.quitado=1";
	// } else {
	// filtro += " and g.quitado=0";
	// }

	filtro += " order by id asc";

	return filtro;
    }

}
