package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "veiculo")
public class VeiculoModel extends GenericModel
    {

        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        @Override
        public Record toRecord() {
            Record record = new Record();
            record.setAttribute("id", getId());
            return record;
        }

    }