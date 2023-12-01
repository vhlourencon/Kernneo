package br.com.kernneo.server.negocio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.exception.OcorrenciaException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.OcorrenciaModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.dao.MovimentacaoDAO;
import br.com.kernneo.server.dao.OcorrenciaDAO;

public class Ocorrencia extends Negocio<OcorrenciaModel, OcorrenciaDAO, OcorrenciaException>
    {

        public Ocorrencia() {
            super();
            dao = new OcorrenciaDAO();
        }
        
        public List<OcorrenciaModel> obterPorData(Date dataSelecionada, String orderBy, boolean desc) throws OcorrenciaException {
            try {
                return dao.obterPorData(dataSelecionada, orderBy, desc);
            } catch (Exception e) {
                e.printStackTrace();
                throw new OcorrenciaException(e.getLocalizedMessage());
            }
        }

        public boolean executar(OcorrenciaModel model, boolean executado, Date dataSelecionada) throws OcorrenciaException {
          //  movimentacaoModel.setExecutado(executado);
           // movimentacaoModel.setDataHoraExecutado(dataSelecionada);

            alterar(model);

            return true;

        }

        public boolean alterarData(OcorrenciaModel model, Date dataSelecionada) throws OcorrenciaException {
            //mo(dataSelecionada);

            alterar(model);
            if (1 == 1) {
                // throw new MovimentacaoException("Usuário sem permissao para deletar esse
                // registro!");
            }

            return true;
        }

        @Override
        public void excluir(OcorrenciaModel model) throws OcorrenciaException {

//            if (model.getUsuarioDelete() != null && model.getUsuarioSave() != null) {
//                FuncionarioModel funcionarioDeleteModel = null;
//                try {
//                    funcionarioDeleteModel = new Funcionario().obterPorId(model.getUsuarioDelete());
//                    if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel() != null) {
//                        if (funcionarioDeleteModel.getId().compareTo(model.getUsuarioSave().getId()) == 0) {
//                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteUsuarioLancamentoFeito() == false) {
//                                throw new OcorrenciaException("Usuário sem permissao para deletar esse registro!");
//                            }
//                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoPendente() == false) {
//                                throw new OcorrenciaException("Usuário sem permissao para deletar esse registro!");
//                            }
//                        } else {
//                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoFeito() == false) {
//                                throw new OcorrenciaException("Usuário sem permissao para deletar esse registro!");
//                            }
//                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoPendente() == false) {
//                                throw new OcorrenciaException("Usuário sem permissao para deletar esse registro!");
//                            }
//                        }
//                    }
//
//                } catch (OcorrenciaException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    throw new OcorrenciaException(e.getLocalizedMessage());
//                }
//
//            }
            super.excluir(model);
        }

        @Override
        public OcorrenciaModel salvar(OcorrenciaModel vo) throws OcorrenciaException {
            OcorrenciaException exc = validar(vo);
            if (exc == null) {
                try {
                    // CaixaModel caixaModel = new Caixa().obterCaixaAberto();
                    // if (caixaModel == null) {
                    // throw new MovimentacaoException("Não existe nenhum caixa aberto! Favor abrir
                    // um caixa");
                    // }
                    // vo.setCaixa(caixaModel);
                   
                    dao.salvar(vo);
                    return vo;

                } catch (Exception e) {
                    throw new OcorrenciaException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
                }
            } else {
                throw exc;
            }
        }

        @Override
        public OcorrenciaException validar(OcorrenciaModel vo) {
            StringBuffer msg = new StringBuffer("");
//            if (vo.getConta() == null) {
//                msg.append("O campo Conta  é de preenchimento obrigatório! \n");
//            }
//
//            if (vo.getDataHora() == null) {
//                msg.append("O campo Data  é de preenchimento obrigatório! \n");
//            }
//
//            if (vo.getValor() == null) {
//                msg.append("O campo Valor  é de preenchimento obrigatório! \n");
//            }

            if (msg.length() > 0)
                return new OcorrenciaException(msg.toString());
            else
                return null;
        }

        @Override
        public String getSqlFiltro(OcorrenciaModel vo) {
            String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
            String deletado = "and deletado = false";
            filtro += " where 1=1 " + deletado;
//
//            if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) {
//                filtro += " and nome like('%" + vo.getDescricao() + "%')";
//            }
//            
//            if(vo.isContaMovimentacaoInicial()) {
//                filtro += " and contaMovimentacaoInicial = true"; 
//            }

            filtro += " order by id asc";

            return filtro;
        }

    }
