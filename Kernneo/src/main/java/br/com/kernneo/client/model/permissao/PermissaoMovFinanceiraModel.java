package br.com.kernneo.client.model.permissao;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ComposicaoModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "permissao_mov_financeira")
public class PermissaoMovFinanceiraModel extends GenericModel
    {

        @OneToOne
        @JoinColumn(name = "id_funcionario")
        private FuncionarioModel funcionario;

        private boolean deleteUsuarioLancamentoPendente;
        private boolean deleteUsuarioLancamentoFeito;
        private boolean deleteOutrosLancamentoPendente;
        private boolean deleteOutrosLancamentoFeito;

        private boolean alterarDataUserPendente;
        private boolean alterarDataUserFeito;
        private boolean alterarDataOutrosPendente;
        private boolean alterarDataOutrosFeito;

        private boolean alterarPagamentoUserPendente;
        private boolean alterarPagamentoUserFeito;
        private boolean alterarPagamentoOutrosPendente;
        private boolean alterarPagamentoOutrosFeito;

        public boolean isAlterarDataUserPendente() {
            return alterarDataUserPendente;
        }

        public void setAlterarDataUserPendente(boolean alterarDataUserPendente) {
            this.alterarDataUserPendente = alterarDataUserPendente;
        }

        public boolean isAlterarDataUserFeito() {
            return alterarDataUserFeito;
        }

        public void setAlterarDataUserFeito(boolean alterarDataUserFeito) {
            this.alterarDataUserFeito = alterarDataUserFeito;
        }

        public boolean isAlterarDataOutrosPendente() {
            return alterarDataOutrosPendente;
        }

        public void setAlterarDataOutrosPendente(boolean alterarDataOutrosPendente) {
            this.alterarDataOutrosPendente = alterarDataOutrosPendente;
        }

        public boolean isAlterarDataOutrosFeito() {
            return alterarDataOutrosFeito;
        }

        public void setAlterarDataOutrosFeito(boolean alterarDataOutrosFeito) {
            this.alterarDataOutrosFeito = alterarDataOutrosFeito;
        }

        public boolean isAlterarPagamentoUserPendente() {
            return alterarPagamentoUserPendente;
        }

        public void setAlterarPagamentoUserPendente(boolean alterarPagamentoUserPendente) {
            this.alterarPagamentoUserPendente = alterarPagamentoUserPendente;
        }

        public boolean isAlterarPagamentoUserFeito() {
            return alterarPagamentoUserFeito;
        }

        public void setAlterarPagamentoUserFeito(boolean alterarPagamentoUserFeito) {
            this.alterarPagamentoUserFeito = alterarPagamentoUserFeito;
        }

        public boolean isAlterarPagamentoOutrosPendente() {
            return alterarPagamentoOutrosPendente;
        }

        public void setAlterarPagamentoOutrosPendente(boolean alterarPagamentoOutrosPendente) {
            this.alterarPagamentoOutrosPendente = alterarPagamentoOutrosPendente;
        }

        public boolean isAlterarPagamentoOutrosFeito() {
            return alterarPagamentoOutrosFeito;
        }

        public void setAlterarPagamentoOutrosFeito(boolean alterarPagamentoOutrosFeito) {
            this.alterarPagamentoOutrosFeito = alterarPagamentoOutrosFeito;
        }

        private boolean permiteAcesso;
        private boolean visualizarSaldoConta;

        public boolean isPermiteAcesso() {
            return permiteAcesso;
        }

        public void setPermiteAcesso(boolean permiteAcesso) {
            this.permiteAcesso = permiteAcesso;
        }

        public boolean isVisualizarSaldoConta() {
            return visualizarSaldoConta;
        }

        public void setVisualizarSaldoConta(boolean visualizarSaldoConta) {
            this.visualizarSaldoConta = visualizarSaldoConta;
        }

        public FuncionarioModel getFuncionario() {
            return funcionario;
        }

        public void setFuncionario(FuncionarioModel funcionario) {
            this.funcionario = funcionario;
        }

        public boolean isDeleteUsuarioLancamentoPendente() {
            return deleteUsuarioLancamentoPendente;
        }

        public void setDeleteUsuarioLancamentoPendente(boolean deleteUsuarioLancamentoPendente) {
            this.deleteUsuarioLancamentoPendente = deleteUsuarioLancamentoPendente;
        }

        public boolean isDeleteUsuarioLancamentoFeito() {
            return deleteUsuarioLancamentoFeito;
        }

        public void setDeleteUsuarioLancamentoFeito(boolean deleteUsuarioLancamentoFeito) {
            this.deleteUsuarioLancamentoFeito = deleteUsuarioLancamentoFeito;
        }

        public boolean isDeleteOutrosLancamentoPendente() {
            return deleteOutrosLancamentoPendente;
        }

        public void setDeleteOutrosLancamentoPendente(boolean deleteOutrosLancamentoPendente) {
            this.deleteOutrosLancamentoPendente = deleteOutrosLancamentoPendente;
        }

        public boolean isDeleteOutrosLancamentoFeito() {
            return deleteOutrosLancamentoFeito;
        }

        public void setDeleteOutrosLancamentoFeito(boolean deleteOutrosLancamentoFeito) {
            this.deleteOutrosLancamentoFeito = deleteOutrosLancamentoFeito;
        }

        @Override
        public Record toRecord() {
            // TODO Auto-generated method stub
            return null;
        }

    }
