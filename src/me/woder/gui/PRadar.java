package me.woder.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PRadar extends JPanel {
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    private boolean isRed;
    private String s = "";
    public List<RComponent> radardots = new ArrayList<RComponent>();
    //GroupLayout lay = (GroupLayout)this.getLayout();

    public PRadar() {
        //playerDot(new RDot("<html>Player woder22<br>Location: 40, 10, 20</html>", 1, new Point(10,10)));
        /*playerDot(new RDot("<html>Player nah<br>Location: 40, 10, 20</html>", 2, new Point(15,10)));
        playerDot(new RDot("<html>Player evil<br>Location: 40, 10, 20</html>", 2, new Point(12,19)));
        playerDot(new RDot("<html>Player bro<br>Location: 40, 10, 20</html>", 1, new Point(30,20)));*/
        this.setLayout(new GridBagLayout());
        /*playerDot(new RComponent(0, 0, 5, 5, "<html>Player woder22<br>Location: 30, 50, 20</html>", 0));
        playerDot(new RComponent(30, 50, 5, 5, "<html>Player woder22<br>Location: 30, 50, 20</html>", 0));*/
        playerDot(new RComponent(10, 10, 10, 10, "<html>Player Unreal34<br>Location: 40, 10, 20</html>", 1));
        
    }
    
    public void playerDot(RComponent comp){
        /*comp.setLocation(p);
        comp.location = p;*/
        //comp.setBounds(10, 20, 5, 5);
        radardots.add(comp);
        this.add(comp);
        comp.setVisible(true);
    }

    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        for(RComponent d : radardots){
            d.repaint();
        }
        /*isRed = random.nextBoolean();

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
                getHeight() / 2 + g.getFontMetrics().getHeight() / 2);*/
    }
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setTitle("Sometimes Red, Sometimes Blue");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new PRadar());
        f.pack();
        f.setVisible(true);
    }
    
    
}
