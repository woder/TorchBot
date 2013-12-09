package me.woder.bot;

import me.woder.world.Location;

public class Player extends Entity{
    public int entityid;
    public String playername = "";
    public int xi;
    public int yi;
    public int zi;
    public double x;
    public double y;
    public double z;
    public byte yaw;
    public byte pitch;
    public short currentitem;
    Client c;
    
    public Player(Client c){
        super(c);
        this.c = c;
    }
    
    public Player(Client c, String name, int x, int y, int z){
        super(c);
        //TODO make this work
    }
    
    @Override
    public int getEntityId(){
        return entityid;
    }
    
    @Override
    public Entity getEntity(){
        return this;    
    }
    
    @Override
    public void setLocation(Location l){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
    }
      
    public String getName(){
        return playername;
    }
    
    @Override
    public Location getLocation(){
        return new Location(c.whandle.getWorld(), x, y, z);
    }
    
    public Location getLocationUnder(){
        return new Location(c.whandle.getWorld(), x, y-1, z);
    }

}
