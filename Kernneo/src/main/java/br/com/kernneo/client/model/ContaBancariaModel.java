package br.com.kernneo.client.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "conta_bancaria")
public class ContaBancariaModel extends GenericModel {

    private String descricao;
    @ManyToOne
    @JoinColumn(name = "id_banco")
    private BancoModel banco;
    private Date abertura;
    private String tipoConta;
    private String agencia;
    private String digitoAgencia;
    private String conta;
    private String digitoConta;
    private String limite;

    public String getDescricao() {
	return descricao;
    }

    public Date getAbertura() {
        return abertura;
    }
    
    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public void setAbertura(Date abertura) {
        this.abertura = abertura;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }
    
    public BancoModel getBanco() {
	if(banco == null)
	    banco = new BancoModel();
        return banco;
    }

    public void setBanco(BancoModel banco) {
	
        this.banco = banco;
    }
    
    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	
	if (banco == null) {
	    record.setAttribute("bancoNome", "Não tem.");

	} else {
	    record.setAttribute("bancoNome", getBanco().getNome());
	}
	if (descricao == null) {
	    record.setAttribute("descricao", "Não tem.");

	} else {
	    record.setAttribute("descricao", getDescricao());
	}
	if (abertura == null) {
	    record.setAttribute("abertura", "Não tem.");

	} else {
	    record.setAttribute("abertura", getAbertura());
	}
	if (agencia == null) {
	    record.setAttribute("agencia", "Não tem.");

	} else {
	    String agencia = getAgencia() + "-" + getDigitoAgencia();
	    record.setAttribute("agencia", agencia);
	}
	if (digitoAgencia == null) {
	    record.setAttribute("digitoAgencia", "Não tem.");

	} else {
	    record.setAttribute("digitoAgencia", getDigitoAgencia());
	}
	if (conta == null) {
	    record.setAttribute("conta", "Não tem.");

	} else {
	    String conta = getConta() + "-" + getDigitoConta();
	    record.setAttribute("conta", conta);
	}
	if (digitoConta == null) {
	    record.setAttribute("digitoConta", "Não tem.");

	} else {
	    record.setAttribute("digitoConta", getDigitoConta());
	}
	if (limite == null) {
	    record.setAttribute("limite", "Não tem.");

	} else {
	    record.setAttribute("limite", getLimite());
	}
	if (tipoConta == null) {
	    record.setAttribute("tipoConta", "Não tem.");

	} else {
	    record.setAttribute("tipoConta", getTipoConta());
	}

	return record;
    }

}
