package br.com.kernneo.desktop.view.widget;

import java.awt.Dimension;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class SampleDesktopMgr extends DefaultDesktopManager {

    // This is called anytime a frame is moved. This
    // implementation keeps the frame from leaving the desktop.
    public void dragFrame(JComponent f, int x, int y) {
	
	if (f instanceof JInternalFrame) { // Deal only w/internal frames
	    JInternalFrame frame = (JInternalFrame) f;
	    JDesktopPane desk = frame.getDesktopPane();
	    if (frame.isIconifiable() == true) {
		Dimension d = desk.getSize();

		// Nothing all that fancy below, just figuring out how to adjust
		// to keep the frame on the desktop.
		if (x < 0) { // too far left?
		    x = 0; // flush against the left side
		} else {
		    if (x + frame.getWidth() > d.width) { // too far right?
			x = 0; // flush against right
			       // side
		    }
		}
		if (y < 0) { // too high?
		    y = 0; // flush against the top
		} else {
		    if (y + frame.getHeight() > d.height) { // too low?
			y = 0; // flush against the
			       // bottom
		    }
		}
	    }
	}

	// Pass along the (possibly cropped) values to the normal drag handler.
	super.dragFrame(f, x, y);
    }

}
