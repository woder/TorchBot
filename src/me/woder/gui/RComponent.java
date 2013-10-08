package me.woder.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.ToolTipManager;

public class RComponent extends JComponent {
    private static final long serialVersionUID = 1L;
    private static final int PREF_W = 10;
    private static final int PREF_H = PREF_W;
    private int x, y, w, h, type;

    public RComponent(int x, int y, int w, int h, final String text, int type) {
       this.x = x;
       this.y = y;
       this.w = w;
       this.h = h;
       this.type = type;
       //this.setBounds(x, y, w, h);
       final Rectangle rect = new Rectangle(w, h, x, y);
       /*addMouseMotionListener(new MouseMotionListener() {  
           
           @Override  
           public void mouseMoved(MouseEvent e) {                  
               if(rect.contains(e.getPoint())){
                   setToolTipText(text); 
                   //drawToolTip(g, "Inside rected", e.getPoint());
                   System.out.println("In");
               }else{  
                   setToolTipText(null);  
                   System.out.println("Out.");
               }
               //ToolTipManager.sharedInstance().setEnabled(true);
               ToolTipManager.sharedInstance().setInitialDelay(1);
               ToolTipManager.sharedInstance().setReshowDelay(1);
               ToolTipManager.sharedInstance().mouseMoved(e);                 
           }  
             
           @Override  
           public void mouseDragged(MouseEvent e) {  
               // TODO Auto-generated method stub  
                 
           }  
       });*/
    }

    @Override
    public Dimension getPreferredSize() {
       return new Dimension(PREF_W, PREF_H);
    }

    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       Graphics2D g2 = (Graphics2D) g.create();
      if(type == 0){
       g2.setColor(Color.BLUE);
      }else if(type == 1){
       g2.setColor(Color.PINK);
      }else if(type == 2){
       g2.setColor(Color.RED);  
      }
       g2.fillRect(w, h, x, y);
       g2.dispose();
    }

 }
