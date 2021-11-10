package br.com.kernneo.client.model.seguranca;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "permissao_modulo")
public class PermissaoModuloModel extends GenericModel {

    private ModuloModel modulo;
    private PerfilModel perfil;
    private boolean status;
    
    public ModuloModel getModulo() {
        return modulo;
    }
    
    public void setModulo(ModuloModel modulo) {
        this.modulo = modulo;
    }
    
    public PerfilModel getPerfil() {
        return perfil;
    }
    
    public void setPerfil(PerfilModel perfil) {
        this.perfil = perfil;
    }
    
    public boolean getStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }
}
