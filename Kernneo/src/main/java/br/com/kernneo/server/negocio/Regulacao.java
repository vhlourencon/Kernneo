package br.com.kernneo.server.negocio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.kernneo.client.exception.FuncionarioException;
import br.com.kernneo.client.exception.MovimentacaoException;
import br.com.kernneo.client.exception.OcorrenciaException;
import br.com.kernneo.client.exception.RegulacaoException;
import br.com.kernneo.client.model.CaixaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.OcorrenciaModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.dao.MovimentacaoDAO;
import br.com.kernneo.server.dao.OcorrenciaDAO;
import br.com.kernneo.server.dao.RegulacaoDAO;

public class Regulacao extends Negocio<RegulacaoModel, RegulacaoDAO, RegulacaoException>
    {

		
		
        public Regulacao() {
            super();
            dao = new RegulacaoDAO();
        }
        
        public List<RegulacaoModel> obterPorDataEusuario(Date dataSelecionada,FuncionarioModel funcionarioModel,  String orderBy, boolean desc) throws RegulacaoException {
            try {
                return dao.obterPorDataEusuario(dataSelecionada, funcionarioModel, orderBy, desc);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RegulacaoException(e.getLocalizedMessage());
            }
        }

        
        
       

        @Override
		public RegulacaoModel merge(RegulacaoModel vo) throws RegulacaoException {
			if(vo.getId() == null) {
				dao.resolverRegulacoesAnteriores(vo);
			}
			return super.merge(vo);
		}

		@Override
        public void excluir(RegulacaoModel model) throws RegulacaoException {

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
        public RegulacaoModel salvar(RegulacaoModel vo) throws RegulacaoException {
        	RegulacaoException exc = validar(vo);
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
                    throw new RegulacaoException("Ocorreu um erro ao tentar salvar:\n" + e.getMessage());
                }
            } else {
                throw exc;
            }
        }

        @Override
        public RegulacaoException validar(RegulacaoModel vo) {
            StringBuffer msg = new StringBuffer("");
            
            if(vo.getCodigo() == null || vo.getCodigo().trim().equals("")) {
            	 msg.append("O campo Código  é de preenchimento obrigatório! \n");
            
            }
            if (vo.getCidade() == null) {
                msg.append("O campo Cidade  é de preenchimento obrigatório! \n");
            }
            
            if(vo.getFuncionario() == null) { 
            	 msg.append("O campo Usuário  é de preenchimento obrigatório! \n");
            }

            if (vo.getDataHora() == null) {
                msg.append("O campo Data  é de preenchimento obrigatório! \n");
            }

            if (vo.getEspecialidade() == null) {
                msg.append("O campo Especialidade  é de preenchimento obrigatório! \n");
            }
            if (vo.getHospital() == null) {
                msg.append("O campo Local de Atendimento  é de preenchimento obrigatório! \n");
            }

            if (msg.length() > 0)
                return new RegulacaoException(msg.toString());
            else
                return null;
        }

        @Override
        public String getSqlFiltro(RegulacaoModel vo) {
            String filtro = "FROM " + vo.getClass().getCanonicalName() + " g ";
            String deletado = "and deletado = false";
            filtro += " where 1=1 " + deletado;
            
            if (vo.getCodigo() != null && vo.getCodigo().trim().length() > 0) {
                filtro += " and codigo ='" + vo.getCodigo() + "'";
            }

            if (vo.getEspecialidade() != null) {
                filtro += " and id_especialidade = " + vo.getEspecialidade().getId();
            }

            if (vo.getHospital() != null) {
                filtro += " and id_hospital = " + vo.getHospital().getId();
            }

            if (vo.getCidade() != null) {
                filtro += " and id_cidade = " + vo.getCidade().getId();
            }
            
            if (vo.getUsuarioSave() != null) {
                filtro += " and id_usuario_save = " + vo.getUsuarioSave().getId();
            }

           

            if (vo.getFiltroResolvido() != null) {
                if (vo.getFiltroResolvido()) {
                    filtro += " and resolvido = true";
                } else {
                    filtro += " and resolvido = false";
                }
            }
            
            if (vo.getFiltroLiminar() != null) {
                if (vo.getFiltroLiminar()) {
                    filtro += " and liminar = true";
                } else {
                    filtro += " and liminar = false";
                }
            }
            
            if(vo.getDataHora() != null && vo.getFiltroDataHoraFinal() !=null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataInicial = sdf.format(vo.getDataHora());
                String dataFinal = sdf.format(vo.getFiltroDataHoraFinal());
                
                filtro += " and date(g.dataHora) between date('" + dataInicial + "') AND date('" + dataFinal + "')";
            }

            filtro += " order by dataHora asc";

            return filtro;
        }

		public void alterarData(RegulacaoModel modelSelecionado, Date dataSelecionada) {
			// TODO Auto-generated method stub
			
		}

    }
