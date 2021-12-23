package br.com.kernneo.server.negocio;

import java.math.BigDecimal;
import java.util.Date;

import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.dao.MovimentacaoDAO;

public class Movimentacao extends Negocio<MovimentacaoModel, MovimentacaoDAO, MovimentacaoException>
    {

        public Movimentacao() {
            super();
            dao = new MovimentacaoDAO();
        }

        public boolean executar(MovimentacaoModel movimentacaoModel, boolean executado) throws MovimentacaoException {
            movimentacaoModel.setExecutado(executado);

            alterar(movimentacaoModel);
           

            return true;

        }
        
        public boolean alterarData(MovimentacaoModel movimentacaoModel,Date dataSelecionada) throws MovimentacaoException {
            movimentacaoModel.setDataHora(dataSelecionada);

            alterar(movimentacaoModel);
            if (1 == 1) {
             //   throw new MovimentacaoException("Usuário sem permissao para deletar esse registro!");
            }

            return true;

        }

        @Override
        public void excluir(MovimentacaoModel model) throws MovimentacaoException {

            if (model.getUsuarioDelete() != null && model.getUsuarioSave() != null) {
                FuncionarioModel funcionarioDeleteModel = null;
                try {
                    funcionarioDeleteModel = new Funcionario().obterPorId(model.getUsuarioDelete());
                    if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel() != null) {
                        if (funcionarioDeleteModel.getId().compareTo(model.getUsuarioSave().getId()) == 0) {
                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteUsuarioLancamentoFeito() == false) {
                                throw new MovimentacaoException("Usuário sem permissao para deletar esse registro!");
                            }
                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoPendente() == false) {
                                throw new MovimentacaoException("Usuário sem permissao para deletar esse registro!");
                            }
                        } else {
                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoFeito() == false) {
                                throw new MovimentacaoException("Usuário sem permissao para deletar esse registro!");
                            }
                            if (funcionarioDeleteModel.getPermissaoMovFinanceiraModel().isDeleteOutrosLancamentoPendente() == false) {
                                throw new MovimentacaoException("Usuário sem permissao para deletar esse registro!");
                            }
                        }
                    }

                } catch (FuncionarioException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new MovimentacaoException(e.getLocalizedMessage());
                }

            }
            super.excluir(model);
        }

        @Override
        public MovimentacaoModel salvar(MovimentacaoModel vo) throws MovimentacaoException {
            MovimentacaoException exc = validar(vo);
            if (exc == null) {
                try {
                    // CaixaModel caixaModel = new Caixa().obterCaixaAberto();
                    // if (caixaModel == null) {
                    // throw new MovimentacaoException("Não existe nenhum caixa aberto! Favor abrir
                    // um caixa");
                    // }
                    // vo.setCaixa(caixaModel);
                    BigDecimal valorAux = vo.getValor();
                    if (vo.getTipo() == MovimentacaoFinanceiraTypes.credito) {
                        if (valorAux.compareTo(BigDecimal.ZERO) < 0) {
                            valorAux = valorAux.negate();
                        }
                    }
                    if (vo.getTipo() == MovimentacaoFinanceiraTypes.debito) {
                        if (valorAux.compareTo(BigDecimal.ZERO) > 0) {
                            valorAux = valorAux.negate();
                        }
                    }
                    vo.setValor(valorAux);
                    dao.salvar(vo);
                    return vo;

                } catch (Exception e) {
                    throw new MovimentacaoException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
                }
            } else {
                throw exc;
            }
        }

        @Override
        public MovimentacaoException validar(MovimentacaoModel vo) {
            StringBuffer msg = new StringBuffer("");
            if (vo.getConta() == null) {
                msg.append("O campo Conta  é de preenchimento obrigatório! \n");
            }

            if (vo.getDataHora() == null) {
                msg.append("O campo Data  é de preenchimento obrigatório! \n");
            }

            if (vo.getValor() == null) {
                msg.append("O campo Valor  é de preenchimento obrigatório! \n");
            }

            if (msg.length() > 0)
                return new MovimentacaoException(msg.toString());
            else
                return null;
        }

        @Override
        public String getSqlFiltro(MovimentacaoModel vo) {
            String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
            String deletado = "and deletado = false";
            filtro += " where 1=1 " + deletado;

            // if (vo.getNome() != null && vo.getNome().trim().length() > 0) {
            // filtro += " and nome like('%" + vo.getNome() + "%')";
            // }

            filtro += " order by id asc";

            return filtro;
        }

    }
