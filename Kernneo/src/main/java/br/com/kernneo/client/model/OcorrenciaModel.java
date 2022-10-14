package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
        
        @Temporal(TemporalType.TIME)
        private Date hora;
        
        @ManyToOne
        @JoinColumn(name = "id_cidade")
        private CidadeModel cidade;

        private String numero;
        private boolean qta;

        private String observacao;

        @Override
        public Record toRecord() {
            Record record = new Record();
            return record;
        }

    }
