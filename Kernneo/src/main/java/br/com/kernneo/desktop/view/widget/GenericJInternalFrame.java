package br.com.kernneo.desktop.view.widget;

import javax.swing.JInternalFrame;

public class GenericJInternalFrame extends JInternalFrame {
   
    public GenericJInternalFrame() {
	setResizable(false);
	setClosable(true);
	setVisible(true);
	setMaximizable(true);
	setLocation(250, 50);
	setSize(560, 526);
	setIconifiable(true);
	//setFrameIcon(Icone.novo("btBD.png"));
    }
    
  

}
