package br.com.kernneo.desktop.view.ocorrencia;

import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Movimentacao;

public class OcorrenciaTableModel extends DefaultTableModel
{
  
    public OcorrenciaTableModel() {
        super(new String[] { "id", "Numero ", "Hora", "Veículo" , "Cidade"}, 0);    
    }

   
    
}