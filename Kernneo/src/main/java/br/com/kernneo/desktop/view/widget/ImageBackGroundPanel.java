package br.com.kernneo.desktop.view.widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImageBackGroundPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage bgImage;

    public ImageBackGroundPanel(String imagem) {
	try {
	    bgImage = ImageIO.read(getClass().getResourceAsStream("" + imagem));
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    public void paintComponent(Graphics g) {
	Dimension d = getSize();
	g.drawImage(bgImage, 0, 0, (int) d.getWidth(), (int) d.getHeight(), this);
    }
}