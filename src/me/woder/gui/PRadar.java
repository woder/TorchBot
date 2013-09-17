package me.woder.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class PRadar extends JPanel {
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    private boolean isRed;
    private String s = "";

    public PRadar() {
    }

    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        isRed = random.nextBoolean();

        //set the background to blue
        setBackground(Color.BLUE);
        s = "BLUE";

        //if 'isRed' is true, set the background to red
        if (isRed) {
            setBackground(Color.RED);
            s = "RED";
        }
        //write either "RED" or "BLUE" using graphics
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 60));
        g.drawOval(0, 0, 300, 300);
        g.drawString(s, getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2,
                getHeight() / 2 + g.getFontMetrics().getHeight() / 2);
    }
}
