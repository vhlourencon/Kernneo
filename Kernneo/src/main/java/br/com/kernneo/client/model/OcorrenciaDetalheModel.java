package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

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
@Entity(name = "ocorrencia_detalhe")
public class OcorrenciaDetalheModel extends GenericModel
    {

        @JoinColumn(name = "id_ocorrencia", referencedColumnName = "id")
        @OneToOne
        private OcorrenciaModel ocorrencia;
        
        private boolean transito_colisao; 
        private boolean transito_atropelamento; 
        private boolean transito_capotamento; 
        private boolean transito_queda_de_moto; 
        private boolean transito_queda_de_bicicleta; 
        
        private boolean agressao_sem_objeto_contundente; 
        private boolean agressao_com_objeto_contundente;
        private boolean agressao_paf;
        private boolean agressao_fab; 
        
        private boolean acidente_queda_altura; 
        private boolean acidente_afogamento; 
        private boolean acidente_quimico; 
        private boolean acidente_incendio;
        private boolean acidente_queda_mesmo_nivel; 
        private boolean acidente_tentativa_suicidio; 
        private boolean acidente_aereo; 
        private boolean acidente_choque_eletrico; 
        
        private boolean outros; 
        private String outros_descricao;
        
        
        
        
        

        public boolean isTransito_colisao() {
            return transito_colisao;
        }

        public void setTransito_colisao(boolean transito_colisao) {
            this.transito_colisao = transito_colisao;
        }

        public boolean isTransito_atropelamento() {
            return transito_atropelamento;
        }

        public void setTransito_atropelamento(boolean transito_atropelamento) {
            this.transito_atropelamento = transito_atropelamento;
        }

        public boolean isTransito_capotamento() {
            return transito_capotamento;
        }

        public void setTransito_capotamento(boolean transito_capotamento) {
            this.transito_capotamento = transito_capotamento;
        }

        public boolean isTransito_queda_de_moto() {
            return transito_queda_de_moto;
        }

        public void setTransito_queda_de_moto(boolean transito_queda_de_moto) {
            this.transito_queda_de_moto = transito_queda_de_moto;
        }

        public boolean isTransito_queda_de_bicicleta() {
            return transito_queda_de_bicicleta;
        }

        public void setTransito_queda_de_bicicleta(boolean transito_queda_de_bicicleta) {
            this.transito_queda_de_bicicleta = transito_queda_de_bicicleta;
        }

        public boolean isAgressao_sem_objeto_contundente() {
            return agressao_sem_objeto_contundente;
        }

        public void setAgressao_sem_objeto_contundente(boolean agressao_sem_objeto_contundente) {
            this.agressao_sem_objeto_contundente = agressao_sem_objeto_contundente;
        }

        

        public boolean isAgressao_com_objeto_contundente() {
            return agressao_com_objeto_contundente;
        }

        public void setAgressao_com_objeto_contundente(boolean agressao_com_objeto_contundente) {
            this.agressao_com_objeto_contundente = agressao_com_objeto_contundente;
        }

        public boolean isAgressao_paf() {
            return agressao_paf;
        }

        public void setAgressao_paf(boolean agressao_paf) {
            this.agressao_paf = agressao_paf;
        }

        public boolean isAgressao_fab() {
            return agressao_fab;
        }

        public void setAgressao_fab(boolean agressao_fab) {
            this.agressao_fab = agressao_fab;
        }

        public boolean isAcidente_queda_altura() {
            return acidente_queda_altura;
        }

        public void setAcidente_queda_altura(boolean acidente_queda_altura) {
            this.acidente_queda_altura = acidente_queda_altura;
        }

        public boolean isAcidente_afogamento() {
            return acidente_afogamento;
        }

        public void setAcidente_afogamento(boolean acidente_afogamento) {
            this.acidente_afogamento = acidente_afogamento;
        }

        public boolean isAcidente_quimico() {
            return acidente_quimico;
        }

        public void setAcidente_quimico(boolean acidente_quimico) {
            this.acidente_quimico = acidente_quimico;
        }

        public boolean isAcidente_incendio() {
            return acidente_incendio;
        }

        public void setAcidente_incendio(boolean acidente_incendio) {
            this.acidente_incendio = acidente_incendio;
        }

        public boolean isAcidente_queda_mesmo_nivel() {
            return acidente_queda_mesmo_nivel;
        }

        public void setAcidente_queda_mesmo_nivel(boolean acidente_queda_mesmo_nivel) {
            this.acidente_queda_mesmo_nivel = acidente_queda_mesmo_nivel;
        }

        public boolean isAcidente_tentativa_suicidio() {
            return acidente_tentativa_suicidio;
        }

        public void setAcidente_tentativa_suicidio(boolean acidente_tentativa_suicidio) {
            this.acidente_tentativa_suicidio = acidente_tentativa_suicidio;
        }

        public boolean isAcidente_aereo() {
            return acidente_aereo;
        }

        public void setAcidente_aereo(boolean acidente_aereo) {
            this.acidente_aereo = acidente_aereo;
        }

        public boolean isAcidente_choque_eletrico() {
            return acidente_choque_eletrico;
        }

        public void setAcidente_choque_eletrico(boolean acidente_choque_eletrico) {
            this.acidente_choque_eletrico = acidente_choque_eletrico;
        }

        public boolean isOutros() {
            return outros;
        }

        public void setOutros(boolean outros) {
            this.outros = outros;
        }

        public String getOutros_descricao() {
            return outros_descricao;
        }

        public void setOutros_descricao(String outros_descricao) {
            this.outros_descricao = outros_descricao;
        }

        public OcorrenciaModel getOcorrencia() {
            return ocorrencia;
        }

        public void setOcorrencia(OcorrenciaModel ocorrencia) {
            this.ocorrencia = ocorrencia;
        }

        @Override
        public Record toRecord() {
            Record record = new Record();
            return record;
        }

    }
