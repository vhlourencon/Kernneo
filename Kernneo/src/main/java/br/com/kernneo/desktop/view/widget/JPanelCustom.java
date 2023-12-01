package br.com.kernneo.desktop.view.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class JPanelCustom extends JPanel
    {
        protected int strokeSize = 1;
        /** Color of shadow */
        protected Color shadowColor = Color.black;
        /** Sets if it drops shadow */
        protected boolean shady = true;
        /** Sets if it has an High Quality view */
        protected boolean highQuality = true;
        /** Double values for Horizontal and Vertical radius of corner arcs */
        protected Dimension arcs = new Dimension(20, 20);
        /** Distance between shadow border and opaque panel border */
        protected int shadowGap = 5;
        /** The offset of shadow. */
        protected int shadowOffset = 4;
        /** The transparency value of shadow. ( 0 - 255) */
        protected int shadowAlpha = 150;

       
    }
