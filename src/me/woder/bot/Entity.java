package me.woder.bot;

import me.woder.world.Location;
import me.woder.world.World;

public class Entity {
    Client c;
    int entityid;
    World world;
    double x;
    double y;
    double z;
    int ax;
    int ay;
    int az;
    int type;
    byte pitch;
    byte headpitch;
    byte yaw;
    int vx;
    int vy;
    int vz;
    
    public Entity(Client c){
        
    }
    
    public Entity(Client c, int eid, World world, int x, int y, int z, int type, byte pitch, byte headpitch, byte yaw, int vx, int vy, int vz){
        this.c = c;
        this.entityid = eid;
        this.world = world;
        this.ax = x;
        this.ay = y;
        this.az = z;
        this.x = x/32;
        this.y = y/32;
        this.z = z/32;
        this.type = type;
        this.pitch = pitch;
        this.headpitch = headpitch;
        this.yaw = yaw;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }
    
    public int getEntityId(){
        return entityid;
    }
    
    public Entity getEntity(){
        return this;
    }
    
    public void updateLocation(Location l){
        this.ax = l.getBlockX();
        this.ay = l.getBlockY();
        this.az = l.getBlockZ();
        this.x = ax/32;
        this.y = ay/32;
        this.z = az/32;
    }
    
    public void setLocation(Location l){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
    }
    
    public void setALocation(Location l){
        this.ax = l.getBlockX();
        this.ay = l.getBlockY();
        this.az = l.getBlockZ();
    }
    
    public Location getLocation(){
        return new Location(world, x, y, z);
    }
    
    public Location getALocation(){
        return new Location(world, ax, ay, az);
    }

}
