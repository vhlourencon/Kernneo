package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "ocorrencia")
public class OcorrenciaModel extends GenericModel
    {

        @ManyToOne
        @JoinColumn(name = "id_veiculo")
        private VeiculoModel veiculo;

        @ManyToOne
        @JoinColumn(name = "id_funcionario")
        private FuncionarioModel funcionario;

        @Temporal(TemporalType.TIMESTAMP)
        private Date dataHora;

        @ManyToOne
        @JoinColumn(name = "id_cidade")
        private CidadeModel cidade;

        private String numero;
        private boolean qta;

        private String observacao;

        @OneToOne(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
        private OcorrenciaDetalheModel detalhe;

        public OcorrenciaDetalheModel getDetalhe() {
            if (detalhe == null) {
                detalhe = new OcorrenciaDetalheModel();
                detalhe.setOcorrencia(this);
            }
            return detalhe;
        }

        public VeiculoModel getVeiculo() {
            return veiculo;
        }

        public void setVeiculo(VeiculoModel veiculo) {
            this.veiculo = veiculo;
        }

        public FuncionarioModel getFuncionario() {
            return funcionario;
        }

        public void setFuncionario(FuncionarioModel funcionario) {
            this.funcionario = funcionario;
        }

        public Date getDataHora() {
            return dataHora;
        }

        public void setDataHora(Date dataHora) {
            this.dataHora = dataHora;
        }

        public CidadeModel getCidade() {
            return cidade;
        }

        public void setCidade(CidadeModel cidade) {
            this.cidade = cidade;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public boolean isQta() {
            return qta;
        }

        public void setQta(boolean qta) {
            this.qta = qta;
        }

        public String getObservacao() {
            return observacao;
        }

        public void setObservacao(String observacao) {
            this.observacao = observacao;
        }

        public void setDetalhe(OcorrenciaDetalheModel detalhe) {
            this.detalhe = detalhe;
        }

        @Override
        public Record toRecord() {
            Record record = new Record();
            return record;
        }  

    }
