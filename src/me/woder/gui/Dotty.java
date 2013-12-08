package me.woder.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Dotty {

    public static void main(String[] args) {
        new Dotty();
    }

    public Dotty() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private static final long serialVersionUID = 1L;
        private BufferedImage background;
        public List<RComponent> radardots = new ArrayList<RComponent>();

        public TestPane() {
            background = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);          
            playerDot(new RComponent(30, 30, 10, 10, "this is text", 1, background));
            playerDot(new RComponent(60,20,10,10,"<html>Player Unreal34<br>Location: 40, 10, 20</html>", 2, background));

            addMouseMotionListener(new MouseMotionListener() {  
                
                @Override  
                public void mouseMoved(MouseEvent e) {  
                    boolean tooltip = false;
                    for(RComponent d : radardots){
                        if(d.isInside(e.getPoint())){
                            setToolTipText(d.text); 
                            System.out.println("In"); 
                            tooltip = true;
                        }
                    }
                    
                    if(!tooltip){
                       setToolTipText(null);  
                       System.out.println("Out.");
                    }
                    
                    ToolTipManager.sharedInstance().setInitialDelay(1);
                    ToolTipManager.sharedInstance().setReshowDelay(1);
                    ToolTipManager.sharedInstance().mouseMoved(e);                 
                }  
                  
                @Override  
                public void mouseDragged(MouseEvent e) {  
                    // TODO Auto-generated method stub  
                      
                }  
            });
        }
        
        public void playerDot(RComponent comp){
            radardots.add(comp);
            this.add(comp);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int x = (getWidth() - background.getWidth()) / 2;
            int y = (getHeight() - background.getHeight()) / 2;
            g2d.drawImage(background, x, y, this);
            g2d.dispose();
        }
    }
}
