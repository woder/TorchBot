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
    MobTypes typ;
    double x, y, z, rx, ry, rz;
    public int sx, sy, sz;
    int type;
    byte pitch, headpitch, yaw;
    
    public Entity(Client c){
        this.c = c;
    }
    
    public Entity(Client c, int eid, World world, double x2, double y2, double z2, int type, byte pitch, byte headpitch, byte yaw, int vx, int vy, int vz){
        this.c = c;
        this.entityid = eid;
        this.world = world;
        this.x = x2;
        this.y = y2;
        this.z = z2;
        this.type = type;
        this.typ = MobTypes.findByType(type);
        this.pitch = pitch;
        this.headpitch = headpitch;
        this.yaw = yaw;
        this.rx = x2 - c.location.getX();
        this.ry = y2 - c.location.getY();
        this.rz = z2 - c.location.getZ();
        dot = new RComponent((int)rx, (int)rz, 10, 10, "<html>Entity " + typ.getFullName() + "<br>Location: " + rx + ", " + ry + ", " + rz +  "</html>", getColour(), c.gui.pradar, typ.getFullName(), 1);
        c.gui.pradar.playerDot(dot);
        this.equipement = new Slot[5];
    }
    
    private int getColour() {
        return MobTypes.findByType(type).isHostile()?2:3;
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
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        moved(l);
    }
    
    public void setLocationRelative(World world, double x, double y, double z){
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
        moved(new Location(world, this.x, this.y, this.z));
    }
    
    public void setLocationLookRelative(Location l, byte yaw, byte pitch){
        this.x = x + l.getX();
        this.y = y + l.getY();
        this.z = z + l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        moved(new Location(c.whandle.getWorld(), x, y, z));
    }
    
    public void setLocationLook(Location l, byte yaw, byte pitch){
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.yaw = yaw;
        this.pitch = pitch;
        moved(l);
    }
    
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
    
    public Location getRadarPos(){
        return new Location(c.whandle.getWorld(), rx, ry, rz);
    }
    
    public Location getLocation(){
        return new Location(world, x, y, z);
    }

    public void tickRadar() {
        dot.moveDot((int)rx, (int)rz, (int)x, (int)y, (int)z);
        StringBuilder value = new StringBuilder("<html>");
        value.append(typ.getFullName()).append("<br>Location: ").append(this.x).append(", ").append(this.y).append(", ").append(this.z).append("</html>");
        dot.text = value.toString();
    }
    
    public void destroyRadar(){
        dot.destroy();
    }

    public boolean isHostile() {
        MobTypes m = MobTypes.findByType(type);
        if(m != null){
            return m.isHostile();
        }
        return true;
    }

}
