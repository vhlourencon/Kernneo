package br.com.kernneo.desktop.view.financeiro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.text.View;

import br.com.kernneo.client.model.MovimentacaoModel;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class MovimentacaoFinanceiraReport
	{

		public MovimentacaoFinanceiraReport() {
		}

		public void gerar(HashMap<String, Object> params, ArrayList<MovimentacaoModel> lista)
				throws JRException, SQLException, ClassNotFoundException, FileNotFoundException {
			
		
			JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(lista);

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("br/com/kernneo/server/relatorios/reportMovFinanceira.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, jrds);

			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setTitle("Relatório de Movimentação Financeira");
			viewer.setVisible(true);
			


		}

		private String getPath() {
			return Paths.get("br/com/kernneo/server/relatorios").getFileName()

					.toString() + "/reportMovFinanceira.jasper";
		}
	}