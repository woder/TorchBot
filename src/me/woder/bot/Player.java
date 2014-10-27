package me.woder.bot;

import java.util.UUID;

import me.woder.gui.RComponent;
import me.woder.world.Location;
import me.woder.world.World;

public class Player extends Entity{
    Client c;
    int eid;
    String name;
    UUID uuid;
    Slot[] equipement;
    double x = 0;
    double y = 0;
    double z = 0;
    public int sx, sy, sz;
    byte yaw, pitch;
    RComponent dot;
    double rx, ry, rz;

    public Player(Client c, int eid, String name, UUID uuid2, int x, int y, int z, byte yaw, byte pitch, short current) {        
        super(c);
        this.c = c;
        this.eid = eid;
        this.name = name;
        this.uuid = uuid2;
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
        dot = new RComponent((int)rx, (int)rz, 10, 10, "<html>Player " + name + "<br>Location: " + this.x + ", " + this.y + ", " + this.z +  "</html>", 1, c.gui.pradar, name, 2);
        c.gui.pradar.playerDot(dot);
        this.equipement = new Slot[5];
        this.equipement[0] = new Slot(0, current, (byte)1, (short)0, (byte)0);
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
        moved(l);
    }
    
    @Override
    public void setLocationLook(Location l, byte yaw, byte pitch){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        moved(l);
    }
    
    @Override
    public void setLocationRelative(World world, double x, double y, double z){
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
        moved(new Location(world, this.x, this.y, this.z));
    }
    
    @Override
    public void setLocationLookRelative(Location l, byte yaw, byte pitch){
        this.x = x + l.getX();
        this.y = y + l.getY();
        this.z = z + l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        moved(new Location(c.whandle.getWorld(), x, y, z));
    }
    
    @Override
    public void moved(Location l){
        c.force.moved(this); //put this here because this gets called when he moves, no matter what
        setRadarPosition(l);
    }
    
    public void setRadarPosition(Location l){
        rx = (c.location.getX() - l.getX()) + 131;
        ry = c.location.getY() - l.getY();
        rz = (c.location.getZ() - l.getZ()) + 116;
        tickRadar();
    }
    
    @Override
    public Location getRadarPos(){
        return new Location(c.whandle.getWorld(), rx, ry, rz);
    }
    
    @Override
    public void tickRadar() {
        dot.moveDot((int)rx, (int)rz, (int)x, (int)y, (int)z);
        StringBuilder value = new StringBuilder("<html>");
        value.append("Player ").append(name).append("<br>Location: ").append(this.x).append(", ").append(this.y).append(", ").append(this.z).append("</html>");
        dot.text = value.toString();
    }
    
    @Override
    public void destroyRadar(){
        dot.destroy();
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
    
    @Override
    public boolean isHostile(){
        if(c.friends.contains(name)){
            return false;
        }
        return true;
    }
    
    public Location getLocationUnder(){
        return new Location(c.whandle.getWorld(), x, y-1, z);
    }

    public String getName() {
        return name;
    }
    
    public short getHeldItem(){
        return this.equipement[0].getId();
    }
   
}
