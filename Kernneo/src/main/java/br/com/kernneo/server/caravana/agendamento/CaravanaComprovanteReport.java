package br.com.kernneo.server.caravana.agendamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.kernneo.client.caravana.model.CaravanaAgendamentoModel;
import br.com.kernneo.client.caravana.model.CaravanaComprovanteModel;
import br.com.kernneo.server.ConnectFactory;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CaravanaComprovanteReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.request = request;

		response.setContentType("application/pdf");

		List listaDeComprovanteModel = new ArrayList<CaravanaComprovanteModel>();
		ArrayList<CaravanaAgendamentoModel> listaDeAgendamentoModel = new ArrayList<CaravanaAgendamentoModel>();

		Map map = new HashMap<Object, Object>();
		String pIdAgendamento = getRequest().getParameter("idAgendamento");

		Session session = ConnectFactory.getSession();
		try {
			session.beginTransaction();

			new CaravanaAgendamento().imprimirListaDeAgendamento(listaDeAgendamentoModel);
			Long idAgendamendoLong = Long.valueOf(pIdAgendamento);

			CaravanaAgendamentoModel caravanaAgendamentoModel = new CaravanaAgendamento().obterPorId(CaravanaAgendamentoModel.class, idAgendamendoLong);
			listaDeComprovanteModel.add(ConverteAgendamentoComprovanteModel.converte(caravanaAgendamentoModel));
			listaDeAgendamentoModel.add(caravanaAgendamentoModel);

			JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(listaDeComprovanteModel);

			File file = new File(request.getSession().getServletContext().getRealPath("") + "/relatorios/caravana/comprovante.jasper");
			InputStream inputStream = new FileInputStream(file);
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, jrds);
			jasperPrint.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
			jasperPrint.setProperty("net.sf.jasperreports.default.font.name", "SansSerif");

			byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentLength(report.length);

			ServletOutputStream out = response.getOutputStream();
			out.write(report);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	protected HttpServletRequest getRequest() {
		return this.request;
	}

}
