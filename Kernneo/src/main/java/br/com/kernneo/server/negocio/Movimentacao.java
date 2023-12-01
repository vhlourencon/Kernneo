package br.com.kernneo.server.negocio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.types.MotivoDesICMS;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.client.types.MovimentacaoRecorrenciaTypes;
import br.com.kernneo.server.dao.MovimentacaoDAO;

public class Movimentacao extends Negocio<MovimentacaoModel, MovimentacaoDAO, MovimentacaoException>
    {

        public Movimentacao() {
            super();
            dao = new MovimentacaoDAO();
        }

        public boolean executar(MovimentacaoModel movimentacaoModel, boolean executado, Date dataSelecionada) throws MovimentacaoException {
            movimentacaoModel.setExecutado(executado);
            movimentacaoModel.setDataHoraExecutado(dataSelecionada);

            alterar(movimentacaoModel);

            return true;

        }

        public boolean alterarData(MovimentacaoModel movimentacaoModel, Date dataSelecionada) throws MovimentacaoException {
            movimentacaoModel.setDataHora(dataSelecionada);

            alterar(movimentacaoModel);
            if (1 == 1) {
                // throw new MovimentacaoException("Usuário sem permissao para deletar esse
                // registro!");
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
		public MovimentacaoModel merge(MovimentacaoModel vo) throws MovimentacaoException {
			if(vo.getId() == null && vo.getRecorrenciaQuantidade() > 1) { 
				int recorrencia = 1; 
				String decricaoAux  = vo.getDescricao(); 
				vo.setDescricao(decricaoAux + " (" +  recorrencia + "/" + vo.getRecorrenciaQuantidade()+")");
				recorrencia++;
				vo = super.merge(vo);				
			
				int calendarTypeOfTime = Calendar.MONTH; 
				if(vo.getRecorrenciaTipo() == MovimentacaoRecorrenciaTypes.semanal) {
					calendarTypeOfTime =Calendar.WEEK_OF_YEAR;
				} else if(vo.getRecorrenciaTipo() == MovimentacaoRecorrenciaTypes.diaria) {
					calendarTypeOfTime = Calendar.DAY_OF_YEAR;
				}else if(vo.getRecorrenciaTipo() == MovimentacaoRecorrenciaTypes.mensal) {
					calendarTypeOfTime =Calendar.MONTH;
				}else if(vo.getRecorrenciaTipo() == MovimentacaoRecorrenciaTypes.anual) {
					calendarTypeOfTime =Calendar.YEAR;
				}
				
        		
        		Calendar calendar = Calendar.getInstance();
        		calendar.setTime(vo.getDataHora());
        		for (int i = recorrencia; i <= vo.getRecorrenciaQuantidade() ; i++) {
        			calendar.add(calendarTypeOfTime, 1);
        			Date proximoDiaUtil = getProximoDiaUtil(calendar.getTime());
					MovimentacaoModel movimentacaoModelRecorrente = getMovimentacaoModelRecorrente(vo);
					movimentacaoModelRecorrente.setDescricao(decricaoAux + " (" +  i + "/" + vo.getRecorrenciaQuantidade()+")");	
					movimentacaoModelRecorrente.setDataHora(proximoDiaUtil);
					movimentacaoModelRecorrente.setRecorrenciaParcela(i);
					if(vo.getRecorrenciaTipo() == MovimentacaoRecorrenciaTypes.diaria) {
						calendar.setTime(proximoDiaUtil);
					}
					super.merge(movimentacaoModelRecorrente);
				}
        	} else { 
        		vo = super.merge(vo);
        	}
			return vo;
		}
        
        private Date getProximoDiaUtil(Date date) {
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(date);
        	while (!isDiaUtil(calendar.getTime())) {
        		calendar.add(Calendar.DAY_OF_YEAR, 1);
        	}
        	return calendar.getTime(); 	
		}
        private boolean isDiaUtil(Date date) { 
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(date);
        	if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        			|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) { 
        		return false; 
        	}
        	return true; 
        }

		private MovimentacaoModel getMovimentacaoModelRecorrente(MovimentacaoModel model) { 
        	MovimentacaoModel movimentacaoModel = new MovimentacaoModel(); 
        	movimentacaoModel.setCategoria(model.getCategoria());
        	movimentacaoModel.setCliente(model.getCliente());
        	movimentacaoModel.setConta(model.getConta());
        	movimentacaoModel.setUsuarioSave(model.getUsuarioSave());
        	movimentacaoModel.setTipo(model.getTipo());
        	movimentacaoModel.setValor(model.getValor());
        	
        	return movimentacaoModel; 
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

            if (vo.getDescricao() != null && vo.getDescricao().trim().length() > 0) {
                filtro += " and descricao like('%" + vo.getDescricao() + "%')";
            }

            if (vo.getCategoria() != null) {
                filtro += " and id_categoria = " + vo.getCategoria().getId();
            }

            if (vo.getCliente() != null) {
                filtro += " and id_cliente = " + vo.getCliente().getId();
            }

            if (vo.getConta() != null) {
                filtro += " and id_conta = " + vo.getConta().getId();
            }

            if (vo.isContaMovimentacaoInicial()) {
                filtro += " and contaMovimentacaoInicial = true";
            } else {
                filtro += " and contaMovimentacaoInicial = false";
            }

            if (vo.getTipo() != null) {
                filtro += " and tipo = '" + vo.getTipo().toString() + "'";
            }

            if (vo.getFiltroExecutado() != null) {
                if (vo.getFiltroExecutado()) {
                    filtro += " and executado = true";
                } else {
                    filtro += " and executado = false";
                }
            }
            
            if(vo.getDataHora() != null && vo.getDataHoraExecutado() !=null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataInicial = sdf.format(vo.getDataHora());
                String dataFinal = sdf.format(vo.getDataHoraExecutado());
                
                filtro += " and date(g.dataHora) between date('" + dataInicial + "') AND date('" + dataFinal + "')";
            }

            filtro += " order by dataHora,cliente.nome asc";

            return filtro;
        }

    }
