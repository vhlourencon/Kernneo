package br.com.kernneo.desktop.view.produto;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.desktop.view.widget.ComboItem;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;
import br.com.kernneo.server.negocio.Categoria;

public class ProdutoFiltroPanel  extends GenericFiltroPanel<ProdutoModel> {
    
    private JComboBox comboBox;
    private JTextField textField;
    private ArrayList<CategoriaModel> listaDeGrupos;

    public ProdutoFiltroPanel() throws CategoriaException {
    	buttonOk.setSize(77, 28);
    	buttonOk.setLocation(449, 33); 

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(536, 80));

	
	comboBox = new JComboBox();
	comboBox.setBounds(10, 37, 201, 20);
	
	comboBox.addItem("-- TODOS --");
	
	listaDeGrupos = new Categoria().obterTodos(CategoriaModel.class);
	for (CategoriaModel grupoModel : listaDeGrupos ) {
	    comboBox.addItem(new ComboItem(grupoModel.getDescricao(), grupoModel.getId().toString()));

	}

	comboBox.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		//GrupoModel grupo = getGrupoSelecionado();
		//getFiltroModel().setGrupo(getGrupoSelecionado());
		//acaoObterTodos();

	    }
	});

	add(comboBox);
	
	JLabel lblNewLabel = new JLabel("Grupo:");
	lblNewLabel.setBounds(10, 21, 46, 14);
	add(lblNewLabel);
	
	JLabel lblNewLabel_1 = new JLabel("Descrição:");
	lblNewLabel_1.setBounds(221, 21, 101, 14);
	add(lblNewLabel_1);
	
	textField = new JTextField();
	textField.setBounds(221, 38, 218, 20);
	add(textField);
	textField.setColumns(10);
    }

    @Override
    public ProdutoModel getModelFiltro() {
	if (modelFiltro == null) { 
	    setModelFiltro(new ProdutoModel());
	    //getModelFiltro().setGrupo(null);
	    getModelFiltro().setDescricao(null);
	}
	
	modelFiltro.setDescricao(textField.getText());
	
	if ( getGrupoSelecionado() == null) { 
	  //  modelFiltro.setSubGrupo(null);
	} else { 
	    SubGrupoModel subGrupoModel = new SubGrupoModel(); 
	    subGrupoModel.setGrupo(getGrupoSelecionado());
	    
	  //  modelFiltro.setSubGrupo(subGrupoModel);
	}
 	
	//System.out.println(modelFiltro.getGrupo().getDescricao());
	return modelFiltro;
    }
    
    private CategoriaModel getGrupoSelecionado() {
   	if (comboBox.getSelectedIndex() == 0) {
   	    return null;
   	} else {
   	    Object item = comboBox.getSelectedItem();
   	    String value = ((ComboItem) item).getValue();

   	    CategoriaModel model = null;

   	    for (CategoriaModel grupoModel : listaDeGrupos) {
   		if (grupoModel.getId().toString().trim().equals(value)) {
   		    model = grupoModel;
   		    break;
   		}
   	    }
   	    return model;
   	}

       }
    
    
}
