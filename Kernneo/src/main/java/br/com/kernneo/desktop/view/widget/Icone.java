package br.com.kernneo.desktop.view.widget;

import java.net.URL;

import javax.swing.ImageIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeUri;

public class Icone {

    public static String dirImages = "/br/com/kernneo/desktop/view/images/";

    public Icone() {
    }

    public static ImageIcon novo(String nome) {
	ImageIcon retorno = null;
	try {
	    URL url = Icone.class.getResource(dirImages + nome);

	    if (url == null)
		System.out.println("Não foi possivel carregar a imagem: '" + nome + "'");
	    else
		retorno = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(url));

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return retorno;
    }

    public static ImageResource getImageResource(final String pathImage, final int height, final int width) {
	return new ImageResource() {

	    public String getName() {
		return pathImage;
	    }

	    public boolean isAnimated() {
		return false;
	    }

	    public int getWidth() {
		return width;
	    }

	    public String getURL() {
		return null;
	    }

	    public int getTop() {
		return 0;
	    }

	    public SafeUri getSafeUri() {
		return new SafeUri() {

		    public String asString() {
			return pathImage;
		    }
		};
	    }

	    public int getLeft() {
		return 0;
	    }

	    public int getHeight() {
		return height;
	    }
	};
    }
}