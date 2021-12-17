package br.com.kernneo.desktop.view.widget;

import javax.swing.*;
import java.awt.*;

public class CheckboxPanel extends JPanel {

    class ToggleIcon implements Icon{
        boolean state;
        public ToggleIcon(boolean s){
            state = s;
        }
        public void paintIcon(Component c, Graphics g, int x, int y){
            int width = getIconWidth();
            int height = getIconHeight();
            g.setColor(Color.darkGray);
            if(state)
                g.fillOval(x, y, width, height);
            else
                g.drawOval(x, y, width, height);
        }
        public int getIconWidth(){
            return 15;
        }
        public int getIconHeight(){
            return 15;
        }
    }

    Icon unchecked = new ToggleIcon(false);
    Icon checked = new ToggleIcon(true);
    // ToggleIcon é a classe que desenha a caixa de seleção

    public CheckboxPanel() {
        setLayout(new GridLayout(2, 1));
        JCheckBox cb1 = new JCheckBox("Choose Me", true);
        cb1.setIcon(unchecked);
        cb1.setSelectedIcon(checked);
        JCheckBox cb2 = new JCheckBox("No Choose Me", false);
        cb2.setIcon(unchecked);
        cb2.setSelectedIcon(checked);
        add(cb1);
        add(cb2);
    }
}