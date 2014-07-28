package me.woder.bot;

import me.woder.gui.RComponent;
import me.woder.world.Location;
import me.woder.world.World;

public class Player extends Entity{
    Client c;
    int eid;
    String name, uuid;
    Slot[] equipement;
    double x, y, z;
    public int sx, sy, sz;
    byte yaw, pitch;
    RComponent dot;
    double rx, ry, rz;

    public Player(Client c, int eid, String name, String uuid, int x, int y, int z, byte yaw, byte pitch, short current) {        
        super(c);
        this.c = c;
        this.eid = eid;
        this.name = name;
        this.uuid = uuid;
        this.sx = x;
        this.sy = y;
        this.sz = z;
        this.x = x/32.0D;
        this.y = y/32.0D;
        this.z = z/32.0D;
        this.yaw = yaw;
        this.pitch = pitch;
        this.rx = (x - c.location.getX()) + 131;
        this.ry = y - c.location.getY();
        this.rz = (z - c.location.getZ()) + 116;
        dot = new RComponent((int)rx, (int)rz, 10, 10, "<html>Player " + name + "<br>Location: " + this.x + ", " + this.y + ", " + this.z +  "</html>", 1, c.gui.pradar);
        c.gui.pradar.playerDot(dot);
        this.equipement = new Slot[5];
        this.equipement[0] = new Slot(0, current, (byte)1, (short)0, (short)0);
    }
    
    @Override
    public void setEquipement(int id, Slot slot){
        this.equipement[id] = slot;
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
    public void setLocationRelative(World world, double x, double y, double z){
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
        setRadarPos(new Location(world, this.x, this.y, this.z));
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
    
    @Override
    public Location getRadarPos(){
        return new Location(c.whandle.getWorld(), rx, ry, rz);
    }
    
    @Override
    public void tickRadar() {
        dot.moveDot((int)rx, (int)rz);    
        dot.text = "<html>Player " + name + "<br>Location: " + this.x + ", " + this.y + ", " + this.z +  "</html>";
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
        return this.equipement[0].id;
    }
   
}
