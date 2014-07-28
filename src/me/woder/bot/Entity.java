package me.woder.bot;

import me.woder.gui.RComponent;
import me.woder.world.Location;
import me.woder.world.World;

public class Entity {
    Client c;
    int entityid;
    Slot[] equipement;
    RComponent dot;
    World world;
    double x, y, z, rx, ry, rz;
    public int sx, sy, sz;
    int type;
    byte pitch, headpitch, yaw;
    int vx, vy, vz;
    
    public Entity(Client c){
        this.c = c;
    }
    
    public Entity(Client c, int eid, World world, int x, int y, int z, int type, byte pitch, byte headpitch, byte yaw, int vx, int vy, int vz){
        this.c = c;
        this.entityid = eid;
        this.world = world;
        this.sx = x;
        this.sy  = y;
        this.sz = z;
        this.x = x/32.0D;
        this.y = y/32.0D;
        this.z = z/32.0D;
        this.type = type;
        this.pitch = pitch;
        this.headpitch = headpitch;
        this.yaw = yaw;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.rx = x - c.location.getX();
        this.ry = y - c.location.getY();
        this.rz = z - c.location.getZ();
        dot = new RComponent((int)rx, (int)rz, 10, 10, "<html>Entity<br>Location: " + rx + ", " + ry + ", " + rz +  "</html>", 0, c.gui.pradar);
        c.gui.pradar.playerDot(dot);
        this.equipement = new Slot[5];
    }
    
    public int getEntityId(){
        return entityid;
    }
    
    public Entity getEntity(){
        return this;
    }
    
    public void setEquipement(int id, Slot slot){
        this.equipement[id] = slot;
    }
    
    public void setLocation(Location l){
        c.chat.sendMessage("Location was: " + this.x + " location was set to: " + l.getX());
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        setRadarPos(l);
    }
    
    public void setLocationRelative(World world, double x, double y, double z){
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
        setRadarPos(new Location(world, this.x, this.y, this.z));
    }
    
    public void setLocationLookRelative(Location l, byte yaw, byte pitch){
        c.chat.sendMessage("Location was: " + this.x + " location is being changed by: " + l.getX());
        this.x = x + l.getX();
        this.y = y + l.getY();
        this.z = z + l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        setRadarPos(new Location(c.whandle.getWorld(), x, y, z));
    }
    
    public void setLocationLook(Location l, byte yaw, byte pitch){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        setRadarPos(l);
    }
    
    public void setRadarPos(Location l){
        rx = (c.location.getX() - l.getX()) + 131;
        ry = c.location.getY() - l.getY();
        rz = (c.location.getZ() - l.getZ()) + 116;
    }
    
    public Location getRadarPos(){
        return new Location(c.whandle.getWorld(), rx, ry, rz);
    }
    
    public Location getLocation(){
        return new Location(world, x, y, z);
    }

    public void tickRadar() {
        dot.moveDot((int)rx, (int)rz);    
        dot.repaint();
    }

}
