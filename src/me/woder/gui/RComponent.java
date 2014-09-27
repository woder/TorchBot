package me.woder.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class RComponent implements Comparable<RComponent>{
    int x;
    int y;
    int w;
    int h;
    int importance;
    private int oy = 0;
    private PRadar img;
    public boolean destroyed;
    public String text;
    public String name;
    Color color;
    Rectangle rect;
    Color[] colors = {Color.ORANGE, Color.PINK, Color.RED, Color.BLUE};

    public RComponent(int x, int y, int w, int h, final String text, int type, PRadar img, String name, int importance) {
       this.x = x;
       this.y = y;
       this.w = w;
       this.h = h;
       this.img = img;
       this.color = colors[type];
       this.text = text;
       this.name = name;
       rect = new Rectangle(x, y, w, h);
       this.importance = importance;
       destroyed = false;
    }
    
    public void moveDot(int x, int y, int ox, int oy, int oz){
        this.x = x;
        this.y = y;
        this.oy = oy;
        updateText(name, ox, oy, oz);
        rect = new Rectangle(x, y, w, h);
    }
    
    public void updateText(String name2, int ox, int oy, int oz) {
        this.text = "<html>Player " + name2 + "<br>Location: " + ox + ", " + oy + ", " + oz + "</html>";
    }
    
    public void updateName(String n){
        this.name = n;
    }

    public boolean isInside(Point e){
        return rect.contains(e.x, e.y);
    }
    
    public void destroy(){
        this.destroyed = true;
        img.radardots.remove(this);
    }
    
    public boolean shouldPaint(){
        boolean should = false;
        if(name.equalsIgnoreCase(img.c.username))return true;
        if(destroyed)return false;
        if(img.c.chunksloaded && img.c.location != null){
           int fy = oy - img.c.location.getBlockY();
           if(img.viewPort > Math.abs(fy)){
               should = true;
           }
        }else{
           should = true;
        }
        return should;
    }

    @Override
    public int compareTo(RComponent r) {
        return (this.importance - r.importance);
    }

 }
