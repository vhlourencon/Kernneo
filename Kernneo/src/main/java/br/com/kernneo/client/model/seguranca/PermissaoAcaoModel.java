package br.com.kernneo.client.model.seguranca;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "permissao_acao")
public class PermissaoAcaoModel extends GenericModel {

    private AcaoModel acao;
    private PermissaoModuloModel permissaoModulo;
    private boolean status;
    
    public AcaoModel getAcao() {
        return acao;
    }

    public void setAcao(AcaoModel acao) {
        this.acao = acao;
    }

    public PermissaoModuloModel getPermissaoModulo() {
        return permissaoModulo;
    }

    public void setPermissaoModulo(PermissaoModuloModel permissaoModulo) {
        this.permissaoModulo = permissaoModulo;
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
