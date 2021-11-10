package br.com.kernneo.client.caravana.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.types.Sexo;
import br.com.kernneo.client.utils.StringUtils;

@Table
@Entity(name = "caravana_participante")
public class CaravanaParticipanteModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private CidadeModel cidade;

	private String nome;
	private String nomeDaMae;
	private String telefone;
	private String cpf;
	private String endereco;
	private boolean preCadastrado = false;
	private Date dataHoraCadastro;
	private String cartaoSus;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Temporal(TemporalType.DATE)
	private Date dataDeNascimento;

	public CidadeModel getCidade() {
		return cidade;
	}

	public void setCidade(CidadeModel cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeDaMae() {
		return nomeDaMae;
	}

	public void setNomeDaMae(String nomeDaMae) {
		this.nomeDaMae = nomeDaMae;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("nomeDaMae", getNomeDaMae());
		record.setAttribute("telefone", getTelefone());
		record.setAttribute("cpf", getCpf());
		record.setAttribute("dataHoraCadastro", StringUtils.getDataHoraFormatada(getDataHoraCadastro()));
		record.setAttribute("dataHoraNascimento", StringUtils.getDataFormatada(getDataDeNascimento()));
		record.setAttribute("endereco", getEndereco());

		if (getCidade() != null) {
			record.setAttribute("cidade", getCidade().getNome() + " - " + getCidade().getEstado().getSigla());
		}

		if (getSexo() != null) {
			record.setAttribute("sexo", getSexo());

			if (getSexo() == Sexo.feminino) {
				record.setAttribute("sexoAbreviado", "F");
			} else if (getSexo() == Sexo.masculino) {
				record.setAttribute("sexoAbreviado", "M");
			}
		}

		return record;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public boolean isPreCadastrado() {
		return preCadastrado;
	}

	public void setPreCadastrado(boolean preCadastrado) {
		this.preCadastrado = preCadastrado;
	}

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public String getCartaoSus() {
		return cartaoSus;
	}

	public void setCartaoSus(String cartaoSus) {
		this.cartaoSus = cartaoSus;
	}

}
