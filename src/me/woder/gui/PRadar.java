package me.woder.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class PRadar extends JPanel {
    private static final long serialVersionUID = 1L;
    public BufferedImage background;
    public List<RComponent> radardots = new ArrayList<RComponent>();
    public RComponent dbot = new RComponent(131,116,10,10,"<html>Player Unreal34<br>Location: 40, 10, 20</html>", 0, this);

    public PRadar() {
        background = new BufferedImage(266, 233, BufferedImage.TYPE_INT_ARGB);          
        playerDot(new RComponent(30, 30, 10, 10, "this is text", 1, this));
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
        this.add(comp);
    }
    
    public void moveDots(int x, int y){
        for(RComponent r : radardots){
            r.moveDot(x, y);
            r.repaint();
        }
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
        g2d.drawImage(background, x, y, this);
        g2d.dispose();
        background = new BufferedImage(262, 233, BufferedImage.TYPE_INT_ARGB);
    }
    
}
