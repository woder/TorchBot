package me.woder.bot;

import me.woder.gui.RComponent;
import me.woder.world.Location;

public class Player extends Entity{
    Client c;
    int eid;
    String name, uuid;
    double x, y, z;
    byte yaw, pitch;
    short current;
    RComponent dot;
    double rx, ry, rz;

    public Player(Client c, int eid, String name, String uuid, int x, int y, int z, byte yaw, byte pitch, short current) {        
        super(c);
        this.c = c;
        this.eid = eid;
        this.name = name;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.current = current;
        this.rx = (x - c.location.getX()) + 131;
        this.ry = y - c.location.getY();
        this.rz = (z - c.location.getZ()) + 116;
        dot = new RComponent((int)rx, (int)rz, 10, 10, "<html>Player " + name + "<br>Location: " + rx + ", " + ry + ", " + rz +  "</html>", 1, c.gui.pradar);
        c.gui.pradar.playerDot(dot);
    }
    
    @Override
    public void setLocation(Location l){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        setRadarPos(l);
    }
    
    @Override
    public void setLocationLook(Location l, byte yaw, byte pitch){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        setRadarPos(l);
    }
    
    @Override
    public void setLocationRelative(Location l){
        this.x = x + l.getX();
        this.y = y + l.getY();
        this.z = z + l.getZ();
        setRadarPos(new Location(c.whandle.getWorld(), x, y, z));
    }
    
    @Override
    public void setLocationLookRelative(Location l, byte yaw, byte pitch){
        this.x = x + l.getX();
        this.y = y + l.getY();
        this.z = z + l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        setRadarPos(new Location(c.whandle.getWorld(), x, y, z));
    }
    
    @Override
    public void setRadarPos(Location l){
        rx = (l.getX() - c.location.getX()) + 131;
        ry = c.location.getY() - l.getY();
        rz = (l.getZ() - c.location.getZ()) + 116;
        //c.chat.sendMessage("relativepos: " + rx + "," + rz);
    }
    
    public Location getRadarPos(){
        return new Location(c.whandle.getWorld(), rx, ry, rz);
    }
    
    @Override
    public void tickRadar() {
        dot.moveDot((int)rx, (int)rz);    
        dot.text = "<html>Player " + name + "<br>Location: " + rx + ", " + ry + ", " + rz +  "</html>";
        dot.repaint();
    }
    
    @Override
    public int getEntityId(){
        return eid;
    }
    
    @Override
    public Entity getEntity(){
        return this;    
    }
    
    @Override
    public Location getLocation(){
        return new Location(c.whandle.getWorld(), x, y, z);
    }
    
    public Location getLocationUnder(){
        return new Location(c.whandle.getWorld(), x, y-1, z);
    }

    public String getName() {
        return name;
    }
    
    public short getHeldItem(){
        return current;
    }
   
}
