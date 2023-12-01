package br.com.kernneo.desktop.view.financeiro;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.ContaBancaria;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class MovimentacaoFiltroPanel extends GenericFiltroPanel<MovimentacaoModel>
    {

        private JTextField textField;
        private UtilDateModel modelDataInicial;

        private JDatePickerImpl datePickerDataInicial;
        private JDatePickerImpl datePickerDataFinal;
        private UtilDateModel modelDataFinal;
        protected ArrayList<CategoriaModel> listaDeCategoria;
        protected ArrayList<ClienteModel> listaDeClientes;
        protected ArrayList<ContaBancariaModel> listaDeContasSelecionadas;
        private JComboBox<ClienteModel> comboBoxCliente;
        private JComboBox<CategoriaModel> comboBoxCategoria;
        private JComboBox<ContaBancariaModel> comboBoxConta;
        private JComboBox<String> comboBoxTipo;
        private JComboBox<String> comboBoxRecebidosPagos;

        public MovimentacaoFiltroPanel() throws Exception {
            buttonOk.setText("Pesquisar");
            buttonOk.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
            buttonOk.setSize(235, 45);
            buttonOk.setLocation(374, 245);

            setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            setLayout(null);
            setPreferredSize(new Dimension(619, 303));

            JLabel lblNewLabel_1 = new JLabel("Descrição:");
            lblNewLabel_1.setBounds(10, 102, 101, 14);
            add(lblNewLabel_1);

            textField = new JTextField();
            textField.setBounds(10, 117, 352, 40);
            add(textField);
            textField.setColumns(10);

            JLabel lblNewLabel_1_1 = new JLabel("Data Inicial: ");
            lblNewLabel_1_1.setBounds(10, 24, 101, 14);
            add(lblNewLabel_1_1);

            modelDataInicial = new UtilDateModel();
            modelDataInicial.setSelected(true);

            // model.setDate(20,04,2014);
            // Need this...
            Properties p = new Properties();
            p.put("text.today", "Hoje");
            p.put("text.month", "Mês");
            p.put("text.year", "Ano");

            JDatePanelImpl datePanelDataInicial = new JDatePanelImpl(modelDataInicial, p);
            datePanelDataInicial.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));

            datePickerDataInicial = new JDatePickerImpl(datePanelDataInicial, new DateLabelFormatter());
            datePickerDataInicial.setLocation(24, 21);
            datePickerDataInicial.setSize(267, 37);
            datePickerDataInicial.setTextEditable(true);
            datePickerDataInicial.setBounds(10, 41, 171, 40);

            modelDataFinal = new UtilDateModel();
            modelDataFinal.setSelected(true);

            JDatePanelImpl datePanelDataFinal = new JDatePanelImpl(modelDataFinal, p);
            datePanelDataFinal.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));

            datePickerDataFinal = new JDatePickerImpl(datePanelDataFinal, new DateLabelFormatter());
            datePickerDataFinal.setLocation(24, 21);
            datePickerDataFinal.setSize(267, 37);
            datePickerDataFinal.setTextEditable(true);
            datePickerDataFinal.setBounds(191, 41, 171, 40);

            add(datePickerDataInicial);
            add(datePickerDataFinal);

            JLabel lblNewLabel_1_1_1 = new JLabel("Data Final:");
            lblNewLabel_1_1_1.setBounds(191, 24, 99, 14);
            add(lblNewLabel_1_1_1);

            comboBoxConta = new JComboBox<ContaBancariaModel>();
            AutoCompleteDecorator.decorate(comboBoxConta);
            comboBoxConta.setBounds(374, 194, 235, 40);
            add(comboBoxConta);

            JLabel lblNewLabel_1_2 = new JLabel("Categoria:");
            lblNewLabel_1_2.setBounds(374, 102, 101, 14);
            add(lblNewLabel_1_2);

            JLabel lblNewLabel_1_2_1 = new JLabel("Cliente:");
            lblNewLabel_1_2_1.setBounds(374, 24, 101, 14);
            add(lblNewLabel_1_2_1);

            comboBoxCliente = new JComboBox<ClienteModel>();
            AutoCompleteDecorator.decorate(comboBoxCliente);
            comboBoxCliente.setBounds(372, 41, 237, 40);
            add(comboBoxCliente);

            JLabel lblNewLabel_1_2_1_1 = new JLabel("Conta:");
            lblNewLabel_1_2_1_1.setBounds(374, 176, 101, 14);
            add(lblNewLabel_1_2_1_1);

            comboBoxCategoria = new JComboBox<CategoriaModel>();
            AutoCompleteDecorator.decorate(comboBoxCategoria);
            comboBoxCategoria.setBounds(372, 117, 237, 40);
            add(comboBoxCategoria);

            JLabel labelTipo = new JLabel("Tipo:");
            labelTipo.setBounds(10, 176, 46, 15);
            add(labelTipo);

             comboBoxTipo = new JComboBox<String>();
            comboBoxTipo.addItem("- TODOS -");
            comboBoxTipo.addItem("CRÉDITO");
            comboBoxTipo.addItem("DEBITO");

            comboBoxTipo.setBounds(10, 194, 171, 40);
            add(comboBoxTipo);
            
             comboBoxRecebidosPagos = new JComboBox<String>();
            comboBoxRecebidosPagos.setBounds(191, 194, 171, 40);
            comboBoxRecebidosPagos.addItem("- TODOS -");
            comboBoxRecebidosPagos.addItem("SIM");
            comboBoxRecebidosPagos.addItem("NÃO");
            add(comboBoxRecebidosPagos);
            
            JLabel lblRecebidospagos = new JLabel("Recebidos/Pagos:");
            lblRecebidospagos.setBounds(191, 176, 144, 15);
            add(lblRecebidospagos);
            
            Calendar c = Calendar.getInstance();  
            c.set(Calendar.DAY_OF_MONTH, 1);
           
            modelDataInicial.setValue(c.getTime());
            
            c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
            modelDataFinal.setValue(c.getTime());
            
            acaoObterComboboxListas();

        }

        private void acaoObterComboboxListas() throws Exception {
            try {
                Conexao.Executar(new Comando() {

                    private ArrayList<ContaBancariaModel> listaContaBancaria;

                    @Override
                    public void execute(Session session) throws Exception {

                        CategoriaModel categoriaTodosModel = new CategoriaModel();
                        categoriaTodosModel.setCategoria_nome_portugues("- TODOS - ");
                        comboBoxCategoria.addItem(categoriaTodosModel);
                        listaDeCategoria = new Categoria().obterTodos(CategoriaModel.class);
                        for (CategoriaModel categoriaModel : listaDeCategoria) {
                            comboBoxCategoria.addItem(categoriaModel);
                        }

                        ContaBancariaModel contaBancariaTodosModel = new ContaBancariaModel();
                        contaBancariaTodosModel.setNome("- TODOS - ");
                        comboBoxConta.addItem(contaBancariaTodosModel);

                        listaContaBancaria = new ContaBancaria().obterTodos(ContaBancariaModel.class);
                        for (ContaBancariaModel contaBancariaModel : listaContaBancaria) {
                            comboBoxConta.addItem(contaBancariaModel);
                        }

                        ClienteModel clienteTodosModel = new ClienteModel();
                        clienteTodosModel.setNome("- TODOS - ");
                        comboBoxCliente.addItem(clienteTodosModel);

                        listaDeClientes = new Cliente().obterTodos(ClienteModel.class);
                        for (ClienteModel clienteModel : listaDeClientes) {
                            comboBoxCliente.addItem(clienteModel);
                        }

                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

                throw e1;
            }

        }

        @Override
        public MovimentacaoModel getModelFiltro() {
            if (modelFiltro == null) {
                setModelFiltro(new MovimentacaoModel());
                getModelFiltro().setDescricao(null);
            }

            modelFiltro.setDescricao(textField.getText());
            // modelFiltro.setConta();

            ContaBancariaModel contaBancariaSelecionada = (ContaBancariaModel) comboBoxConta.getSelectedItem();
            if(contaBancariaSelecionada.getId() == null) {
                contaBancariaSelecionada = null; 
            }
            ClienteModel clienteSelecionado = (ClienteModel) comboBoxCliente.getSelectedItem();
            if(clienteSelecionado.getId() == null) {
                clienteSelecionado = null; 
            }
            CategoriaModel categoriaSelecionada = (CategoriaModel) comboBoxCategoria.getSelectedItem();
            if(categoriaSelecionada.getId() == null) 
                categoriaSelecionada = null; 
            
            modelFiltro.setConta(contaBancariaSelecionada);
            modelFiltro.setCategoria(categoriaSelecionada);
            modelFiltro.setCliente(clienteSelecionado);
            
            if(comboBoxTipo.getSelectedIndex()==0) {
                modelFiltro.setTipo(null);
            } else if (comboBoxTipo.getSelectedIndex() ==1) { 
                modelFiltro.setTipo(MovimentacaoFinanceiraTypes.credito);
            } else if ( comboBoxTipo.getSelectedIndex() ==2) {
                modelFiltro.setTipo(MovimentacaoFinanceiraTypes.debito);
            }
            
            if(comboBoxRecebidosPagos.getSelectedIndex()==0) {
                modelFiltro.setFiltroExecutado(null);
            } else if (comboBoxRecebidosPagos.getSelectedIndex() ==1) { 
                modelFiltro.setFiltroExecutado(true);
            } else if ( comboBoxRecebidosPagos.getSelectedIndex() ==2) {
                modelFiltro.setFiltroExecutado(false);
            }
            
            modelFiltro.setDataHora(modelDataInicial.getValue());
            modelFiltro.setDataHoraExecutado(modelDataFinal.getValue());
        
            return modelFiltro;
        }

		public JComboBox<ClienteModel> getComboBoxCliente() {
			return comboBoxCliente;
		}

		public void setComboBoxCliente(JComboBox<ClienteModel> comboBoxCliente) {
			this.comboBoxCliente = comboBoxCliente;
		}

		public JComboBox<CategoriaModel> getComboBoxCategoria() {
			return comboBoxCategoria;
		}

		public void setComboBoxCategoria(JComboBox<CategoriaModel> comboBoxCategoria) {
			this.comboBoxCategoria = comboBoxCategoria;
		}

		public JComboBox<ContaBancariaModel> getComboBoxConta() {
			return comboBoxConta;
		}

		public void setComboBoxConta(JComboBox<ContaBancariaModel> comboBoxConta) {
			this.comboBoxConta = comboBoxConta;
		}

		public JComboBox<String> getComboBoxTipo() {
			return comboBoxTipo;
		}

		public void setComboBoxTipo(JComboBox<String> comboBoxTipo) {
			this.comboBoxTipo = comboBoxTipo;
		}

		public JComboBox<String> getComboBoxRecebidosPagos() {
			return comboBoxRecebidosPagos;
		}

		public void setComboBoxRecebidosPagos(JComboBox<String> comboBoxRecebidosPagos) {
			this.comboBoxRecebidosPagos = comboBoxRecebidosPagos;
		}
        
        
    }



