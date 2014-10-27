package me.woder.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import me.woder.bot.Client;

public class PRadar extends JPanel {
    private static final long serialVersionUID = 1L;
    public BufferedImage background;
    public CopyOnWriteArrayList<RComponent> radardots = new CopyOnWriteArrayList<RComponent>();
    public RComponent dbot = new RComponent(126,111,10,10,"<html></html>", 0, this, "", 3);
    public Client c;
    public double viewPort = 10;

    public PRadar(Client c) {
        this.c = c;
        background = new BufferedImage(266, 233, BufferedImage.TYPE_INT_ARGB);          
        playerDot(dbot);
        
        addMouseMotionListener(new MouseMotionListener() {  
            
            @Override  
            public void mouseMoved(MouseEvent e) {  
                boolean tooltip = false;
                for(RComponent d : radardots){
                    if(d.isInside(e.getPoint())){
                        setToolTipText(d.text); 
                        tooltip = true;
                    }
                }
                
                if(!tooltip){
                   setToolTipText(null);  
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
        //this.add(comp);
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(266, 233);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Graphics2D g2 = background.createGraphics();
        g2.setColor(Color.black);
        g2.drawLine(background.getWidth() / 2, 0, background.getWidth() / 2, background.getHeight());
        g2.drawLine(0, background.getHeight() / 2, background.getWidth(), background.getHeight() / 2);
        g2.drawLine(0, 0, background.getWidth(), 0);
        g2.drawLine(background.getWidth()-1, 0, background.getWidth()-1, background.getHeight()-1);
        g2.drawLine(background.getWidth()-1, background.getHeight()-1, 0, background.getHeight()-1);
        g2.drawLine(0, background.getHeight(), 0, 0);
        int x = (getWidth() - background.getWidth()) / 2;
        int y = (getHeight() - background.getHeight()) / 2;    
        Object[] array = radardots.toArray();
        Arrays.sort(array);
        for (Object d : array) {//TODO fix this, keeps throwing java.util.ConcurrentModificationException - seems to be fixed
         if(d instanceof RComponent){
          RComponent r = (RComponent) d;
          if(r.shouldPaint()){
            g2.setColor(r.color);
            g2.fillRect(r.x, r.y, r.w, r.h);
          }
         }
        }
        g2d.drawImage(background, x, y, this);
        g2.dispose();
        g2d.dispose();
        background = new BufferedImage(262, 233, BufferedImage.TYPE_INT_ARGB);
    }
    
}
