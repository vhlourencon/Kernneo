package br.com.kernneo.desktop.view.ocorrencia;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.OcorrenciaDetalheModel;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class OcorrenciaDetalheNaturezaPanel extends JPanel
    {

        /**
        * 
        */
        private static final long serialVersionUID = 4602892477693713818L;

        private OcorrenciaDetalheModel detalheModel;
        private JCheckBox checkBoxTransitoColisao;
        private JTextField textField;
        private JTextField textField_1;
        private JTextField textField_2;

        private JCheckBox checkBoxTransitoCapotamento;

        private JCheckBox checkBoxTransitoQuedaMoto;

        private JCheckBox checkBoxSaldoTransitoQuedaBicicleta;

        private JCheckBox checkBoxTransitoAtropelamento;

        private JCheckBox checkBoxAgressaoSemObjetoContundente;

        private JCheckBox checkBoxAgressaoPAF;

        private JCheckBox checkBoxAgressaoFAB;

        private JCheckBox checkBoxAgressaoComObjetosContundente;

        private JCheckBox checkBoxAcidenteAfogamento;

        private JCheckBox checkBoxAcidenteQuedaMesmoNivel;

        private JCheckBox checkBoxAcidenteChoqueEletrico;

        private JCheckBox checkBoxAcidenteQuedaAltura;

        private JCheckBox checkBoxAcidenteQuimico;

        private JCheckBox checkBoxAcidenteTentativaSuicidio;

        private JCheckBox checkBoxAcidenteAereo;

        private JCheckBox checkBoxAcidenteIncendio;

        private JCheckBox checkBoxOutros;

        public OcorrenciaDetalheNaturezaPanel() {
            setPreferredSize(new Dimension(682, 569));

            setLayout(null);

            JPanel p = new JPanel();
            p.setPreferredSize(new Dimension(240, 470));

            JScrollPane srcPainel = new JScrollPane(p);
            p.setLayout(null);

            JPanel panel_4 = new JPanel();
            panel_4.setLayout(null);
            panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acidentes de Tr\u00E2nsito", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4.setBounds(10, 10, 504, 114);

            checkBoxTransitoColisao = new JCheckBox("Colisão ");
            checkBoxTransitoColisao.setBounds(16, 20, 93, 25);
            panel_4.add(checkBoxTransitoColisao);

            JPanel panel_5 = new JPanel();
            panel_5.setBackground(SystemColor.controlHighlight);
            panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
            panel_5.setBounds(10, 11, 525, 430);

            p.add(panel_5);
            panel_5.setLayout(null);
            panel_5.add(panel_4);

            JComboBox comboBox = new JComboBox();
            comboBox.setBounds(359, 20, 135, 25);
            panel_4.add(comboBox);

            checkBoxTransitoCapotamento = new JCheckBox("Capotamento");
            checkBoxTransitoCapotamento.setBounds(16, 80, 144, 25);
            panel_4.add(checkBoxTransitoCapotamento);

            checkBoxTransitoQuedaMoto = new JCheckBox("Queda de Moto");
            checkBoxTransitoQuedaMoto.setBounds(162, 80, 144, 25);
            panel_4.add(checkBoxTransitoQuedaMoto);

            checkBoxSaldoTransitoQuedaBicicleta = new JCheckBox("Queda de Bicicleta");
            checkBoxSaldoTransitoQuedaBicicleta.setBounds(333, 80, 144, 25);
            panel_4.add(checkBoxSaldoTransitoQuedaBicicleta);

            checkBoxTransitoAtropelamento = new JCheckBox("Atropelamento");
            checkBoxTransitoAtropelamento.setBounds(16, 50, 144, 25);
            panel_4.add(checkBoxTransitoAtropelamento);

            JComboBox comboBox_1 = new JComboBox();
            comboBox_1.setBounds(164, 50, 330, 25);
            panel_4.add(comboBox_1);

            JComboBox comboBox_2 = new JComboBox();
            comboBox_2.setBounds(164, 20, 144, 25);
            panel_4.add(comboBox_2);

            JLabel lblNewLabel = new JLabel("COM");
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel.setBounds(310, 20, 47, 25);
            panel_4.add(lblNewLabel);

            JPanel panel_4_1 = new JPanel();
            panel_4_1.setLayout(null);
            panel_4_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Agress\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4_1.setBounds(10, 134, 504, 75);
            panel_5.add(panel_4_1);

            checkBoxAgressaoSemObjetoContundente = new JCheckBox("Sem objeto contundente");
            checkBoxAgressaoSemObjetoContundente.setBounds(16, 43, 220, 25);
            panel_4_1.add(checkBoxAgressaoSemObjetoContundente);

            checkBoxAgressaoPAF = new JCheckBox("PAF");
            checkBoxAgressaoPAF.setBounds(244, 43, 108, 25);
            panel_4_1.add(checkBoxAgressaoPAF);

            checkBoxAgressaoFAB = new JCheckBox("FAB");
            checkBoxAgressaoFAB.setBounds(354, 43, 144, 25);
            panel_4_1.add(checkBoxAgressaoFAB);

            checkBoxAgressaoComObjetosContundente = new JCheckBox("Com outros objetos contundentes:");
            checkBoxAgressaoComObjetosContundente.setBounds(16, 15, 220, 25);
            panel_4_1.add(checkBoxAgressaoComObjetosContundente);

            textField = new JTextField();
            textField.setBounds(242, 15, 252, 25);
            panel_4_1.add(textField);
            textField.setColumns(10);

            JPanel panel_4_1_1 = new JPanel();
            panel_4_1_1.setLayout(null);
            panel_4_1_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Outros Acidentes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4_1_1.setBounds(10, 220, 504, 131);
            panel_5.add(panel_4_1_1);

            checkBoxAcidenteAfogamento = new JCheckBox("Afogamento");
            checkBoxAcidenteAfogamento.setBounds(16, 43, 109, 25);
            panel_4_1_1.add(checkBoxAcidenteAfogamento);

            checkBoxAcidenteQuedaMesmoNivel = new JCheckBox("Queda do mesmo nível");
            checkBoxAcidenteQuedaMesmoNivel.setBounds(219, 43, 153, 25);
            panel_4_1_1.add(checkBoxAcidenteQuedaMesmoNivel);

            checkBoxAcidenteChoqueEletrico = new JCheckBox("Choque elétrico");
            checkBoxAcidenteChoqueEletrico.setBounds(374, 43, 120, 25);
            panel_4_1_1.add(checkBoxAcidenteChoqueEletrico);

            checkBoxAcidenteQuedaAltura = new JCheckBox("Queda de Altura:");
            checkBoxAcidenteQuedaAltura.setBounds(16, 15, 140, 25);
            panel_4_1_1.add(checkBoxAcidenteQuedaAltura);

            textField_1 = new JTextField();
            textField_1.setColumns(10);
            textField_1.setBounds(219, 15, 275, 25);
            panel_4_1_1.add(textField_1);

             checkBoxAcidenteQuimico = new JCheckBox("Quimíco");
            checkBoxAcidenteQuimico.setBounds(16, 71, 109, 25);
            panel_4_1_1.add(checkBoxAcidenteQuimico);

             checkBoxAcidenteTentativaSuicidio = new JCheckBox("Tentativa de suicídio");
            checkBoxAcidenteTentativaSuicidio.setBounds(219, 71, 140, 25);
            panel_4_1_1.add(checkBoxAcidenteTentativaSuicidio);

             checkBoxAcidenteAereo = new JCheckBox("Aéreo");
            checkBoxAcidenteAereo.setBounds(374, 71, 120, 25);
            panel_4_1_1.add(checkBoxAcidenteAereo);

             checkBoxAcidenteIncendio = new JCheckBox("Incêndio");
            checkBoxAcidenteIncendio.setBounds(16, 99, 120, 25);
            panel_4_1_1.add(checkBoxAcidenteIncendio);

            JPanel panel_4_1_1_1 = new JPanel();
            panel_4_1_1_1.setLayout(null);
            panel_4_1_1_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " ", TitledBorder.LEADING, TitledBorder.ABOVE_BOTTOM, null, new Color(0, 0, 0)));
            panel_4_1_1_1.setBounds(10, 357, 504, 48);
            panel_5.add(panel_4_1_1_1);

            checkBoxOutros = new JCheckBox("Outros:");
            checkBoxOutros.setBounds(16, 11, 140, 25);
            panel_4_1_1_1.add(checkBoxOutros);

            textField_2 = new JTextField();
            textField_2.setColumns(10);
            textField_2.setBounds(219, 11, 275, 25);
            panel_4_1_1_1.add(textField_2);

            srcPainel.setBounds(0, 0, 564, 230);
            srcPainel.setPreferredSize(new Dimension(250, 250));

            add(srcPainel);

        }

        public OcorrenciaDetalheModel getDetalheModel() {
            detalheModel.setTransito_atropelamento(checkBoxTransitoAtropelamento.isSelected());
            detalheModel.setTransito_capotamento(checkBoxTransitoCapotamento.isSelected());
            detalheModel.setTransito_colisao(checkBoxTransitoColisao.isSelected());
            detalheModel.setTransito_queda_de_bicicleta(checkBoxSaldoTransitoQuedaBicicleta.isSelected());
            detalheModel.setTransito_queda_de_moto(checkBoxTransitoQuedaMoto.isSelected());

            detalheModel.setAgressao_com_objeto_contundente(checkBoxAgressaoComObjetosContundente.isSelected());
            detalheModel.setAgressao_sem_objeto_contundente(checkBoxAgressaoSemObjetoContundente.isSelected());
            detalheModel.setAgressao_fab(checkBoxAgressaoFAB.isSelected());
            detalheModel.setAgressao_paf(checkBoxAgressaoPAF.isSelected());
            
            detalheModel.setAcidente_aereo(checkBoxAcidenteAereo.isSelected());
            detalheModel.setAcidente_afogamento(checkBoxAcidenteAfogamento.isSelected());
            detalheModel.setAcidente_choque_eletrico(checkBoxAcidenteChoqueEletrico.isSelected());
            detalheModel.setAcidente_incendio(checkBoxAcidenteIncendio.isSelected());
            detalheModel.setAcidente_queda_altura(checkBoxAcidenteQuedaAltura.isSelected());
            detalheModel.setAcidente_queda_mesmo_nivel(checkBoxAcidenteQuedaMesmoNivel.isSelected());
            detalheModel.setAcidente_quimico(checkBoxAcidenteQuimico.isSelected());
            detalheModel.setAcidente_tentativa_suicidio(checkBoxAcidenteTentativaSuicidio.isSelected());
          
            
            detalheModel.setOutros(checkBoxOutros.isSelected());

            return detalheModel;
        }

        public void setDetalheModel(OcorrenciaDetalheModel model) {
            this.detalheModel = model;

        }

    }
