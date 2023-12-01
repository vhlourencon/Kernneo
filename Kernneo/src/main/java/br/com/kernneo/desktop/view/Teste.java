package br.com.kernneo.desktop.view;

import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;



import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Teste {

	 public static void main(String args[]) throws Exception {
//		 System.out.println(new Teste().getPath());
//			JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(new ArrayList<String>());
//
//			File file = new File(new Teste().getPath());
//			InputStream inputStream = new FileInputStream(file);
//			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, new HashMap<String, Object>(), jrds);
//
//			 JasperExportManager.exportReportToPdfFile(jasperPrint,"c:\\genesis\\temp\\1.pdf");
//
//	        PDDocument document = PDDocument.load(new File("C:/genesis/temp/1.pdf"));
//
//	        PrintService myPrintService = findPrintService("SAMU-ADM");
//
//	        PrinterJob job = PrinterJob.getPrinterJob();
//	        job.setPageable(new PDFPageable(document));
//	        job.setPrintService(myPrintService);
//	        job.print();
		 

         Date dataAtual = new Date(); 
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         calendar.set(Calendar.HOUR_OF_DAY,20);
         int hora = calendar.get(Calendar.HOUR_OF_DAY);
         if(hora >= 7 && hora < 19) { 
         	calendar.set(Calendar.HOUR_OF_DAY, 7);
         } else if( hora >= 19) { 
         	calendar.set(Calendar.HOUR_OF_DAY, 19);
         } else { 
         	calendar.add( Calendar.HOUR, ((5 + hora)  * (-1)));
         }
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
         System.out.println(calendar.getTime());
    

	    }       

	    private static PrintService findPrintService(String printerName) {
	        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
	        for (PrintService printService : printServices) {
	        	System.out.println(printService.getName());
	            if (printService.getName().trim().equals(printerName)) {
	                return printService;
	            }
	        }
	        return null;
	    }
	    private String getPath() {
	    	return Paths.get("relatorios")
	                .toAbsolutePath()
	                .toString() + "\\reportToro.jasper" ;
	    }

}
