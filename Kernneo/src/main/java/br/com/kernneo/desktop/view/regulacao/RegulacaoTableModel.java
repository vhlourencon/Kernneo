package br.com.kernneo.desktop.view.regulacao;

import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Movimentacao;

public class RegulacaoTableModel extends DefaultTableModel
{
  
    public RegulacaoTableModel() {
        super(new String[] { "id", "Regulador" ,"Cód. de Solicitação", "Hora", "Cidade de Origem" , "Especialidade" , "Local de Atendimento", "Liminar" , "Resolvido"}, 0);    
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; 
    }
    
    @Override
	public Class<?> getColumnClass(int columnIndex) {
		Class clazz = String.class;
		switch (columnIndex) {

		case 7:
			clazz = Boolean.class;
			break;
		case 8:
			clazz = Boolean.class;
			break;
		}
		return clazz;
	}


   
    
}