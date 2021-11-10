package br.com.kernneo.server.band.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;



import com.google.gwt.core.client.GWT;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class GenericReport extends HttpServlet {

	private HttpServletRequest request;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.request = request;

		final String reportName = request.getParameter("reportName") + ".jasper";
		response.setContentType("application/pdf");

		Session session = br.com.kernneo.server.ConnectFactory.getSession();
		try {
			session.beginTransaction();
			List lista = getLista();
			Map map = getParametros();

			JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(lista);

			File file = new File(request.getSession().getServletContext().getRealPath("") + "/relatorios/band/" + reportName);

			InputStream inputStream = new FileInputStream(file);
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, map, jrds);

			byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentLength(report.length);

			ServletOutputStream out = response.getOutputStream();
			out.write(report);
			out.flush();
			out.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

	protected HttpServletRequest getRequest() {
		return this.request;
	}

	protected abstract List getLista();

	protected abstract Map getParametros();

	protected abstract void parametrosEmpresa();

}
