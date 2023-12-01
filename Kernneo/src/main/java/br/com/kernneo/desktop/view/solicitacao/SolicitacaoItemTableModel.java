package br.com.kernneo.desktop.view.solicitacao;

import javax.swing.table.DefaultTableModel;

public class SolicitacaoItemTableModel extends DefaultTableModel
{
  
    public SolicitacaoItemTableModel() {
        super(new String[] { "id", "Solicitação" ,"Documento", "Unidade", "Equipamento" , "Quantidade" }, 0);    
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; 
    }
    
    
}