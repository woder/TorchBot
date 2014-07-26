package me.woder.bot;

import java.util.ArrayList;

import me.woder.world.World;

public class EntityTracker {    
    Client c;
    ArrayList<Entity> entities;

    public EntityTracker(Client c){
        this.c = c;
        entities = new ArrayList<Entity>();
    }
    
    public void addEntity(int eid, World world, int x, int y, int z, int type, byte pitch, byte headpitch, byte yaw, int vx, int vy, int vz){
       if(findEntityId(eid) == null){
        entities.add(new Entity(c, eid, world, x, y, z, type, pitch, headpitch, yaw, vx, vy, vz));
        //c.chat.sendMessage("Adding entity id: " + eid);
       }
    }
    
    public void addPlayer(int eid, World world, double x, double y, double z, byte pitch, byte yaw, short current, String name, String uuid){
       if(findEntityId(eid) == null){
        entities.add(new Player(c, eid, name, uuid, x, y, z, yaw, pitch, current));
        //c.chat.sendMessage("Adding player's entity id: " + eid + " and... " + entities.size());
        c.chat.sendMessage("Player " + name + " is at " + x + ", " + y + "," + z);
       }
    }
    
    public void tickRadar(){
        for(Entity e : entities){
            e.tickRadar();
        }
        c.gui.pradar.repaint();
    }
    
    public Player findPlayer(String name){
        Player p = null;
        c.chat.sendMessage("We are on a search for: " + name);
        for(Entity s : entities){
           if(s.getEntity() instanceof Player){
            Player a = (Player)s;
            c.chat.sendMessage(a.getName());
            if(a.getName().equalsIgnoreCase(name)){
                p = a;
                break;
            }
           }
        }
        return p;
    }
    
    public Entity findEntityId(int id){      
        for(Entity e : entities){
            if(e.getEntityId() == id){
                return e;
            }
        }
        return null;
    }

}
