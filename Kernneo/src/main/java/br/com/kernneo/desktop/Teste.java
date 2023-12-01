package br.com.kernneo.desktop;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;



import br.com.kernneo.client.types.MovimentacaoRecorrenciaTypes;
import br.com.kernneo.server.PropertiesFactory;

public class Teste
	{

		public static void main(String[] args) throws IOException, ParseException {
			Teste teste = new Teste();
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 10);
			System.out.println(calendar.getTime());
			System.out.println(teste.getProximoDiaUtil(calendar.getTime()));
			

		}
		
		  public Date getProximoDiaUtil(Date date) {
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTime(date);
	        	while (!isDiaUtil(calendar.getTime())) {
	        		calendar.add(Calendar.DAY_OF_YEAR, 1);
	        	}
	        	return calendar.getTime(); 	
			}
		  public boolean isDiaUtil(Date date) {
			  
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTime(date);
	        	System.out.println(calendar.get(Calendar.DAY_OF_WEEK) + " " + Calendar.SATURDAY);
	        	if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
	        			|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) { 
	        		return false; 
	        	}
	        	return true; 
	        }

	}
