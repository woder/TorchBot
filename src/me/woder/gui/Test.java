package me.woder.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * A frame containing a panel that is sometimes red and sometimes 
 * blue. Also, it displays the word to go with it. 
 * 
 * Inspired by www.sometimesredsometimesblue.com.
 *
 */
public class Test extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    private boolean isRed;
    private String s = "";

    public Test() {
        //randomly determine whether the background should be red
        isRed = random.nextBoolean();

        //set the background to blue
        setBackground(Color.BLUE);
        s = "BLUE";

        //if 'isRed' is true, set the background to red
        if (isRed) {
            setBackground(Color.RED);
            s = "RED";
        }
    }

    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //write either "RED" or "BLUE" using graphics
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 60));
        g.drawString(s, getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2,
                getHeight() / 2 + g.getFontMetrics().getHeight() / 2);
    }

    //main method: create an instance of TestPanel and output it on a JFrame
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setTitle("Sometimes Red, Sometimes Blue");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new Test());
        f.setVisible(true);
    }
}
