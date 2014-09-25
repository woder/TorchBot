package me.woder.bot;

import java.util.ArrayList;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import me.woder.world.World;

public class EntityTracker {    
    Client c;
    ArrayList<Entity> entities;
    HashBiMap<UUID, String> names = HashBiMap.create();

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
    
    public void addPlayer(int eid, World world, int x, int y, int z, byte pitch, byte yaw, short current, String name, UUID uuid){
       if(findEntityId(eid) == null){
        entities.add(new Player(c, eid, name, uuid, x, y, z, yaw, pitch, current));
       }
    }
    
    public void delEntity(int eid) {
       Entity e = findEntityId(eid);
       if(e != null){
           entities.remove(e);
       }
    }
    
    public void delAll() {      
        entities.clear();
    }
    
    public void tickRadar(){
        for(Entity e : entities){
            e.tickRadar();
        }
        c.gui.pradar.repaint();
    }
    
    public Player findPlayer(String name){
        Player p = null;
        for(Entity s : entities){
           if(s.getEntity() instanceof Player){
            Player a = (Player)s;
            c.chat.sendMessage("Name is: " + a.getName());
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
    
    public String getNameUUID(UUID u){
        String name = "";
        if(names.containsKey(u)){
            name = names.get(u);
        }else{
            String result = c.sendGetRequest("https://api.mojang.com/user/profiles/" + u.toString().replace("-", "") + "/names");
            JSON jsonr = JSONSerializer.toJSON(result);
            if(jsonr.isArray()){
                JSONArray js = (JSONArray) jsonr;
                Object o = js.get(0);
                if(o instanceof String){
                    name = (String) o;
                    names.put(u, name);
                }
            }
        }
        return name;
    }
    
    public UUID getUUIDName(String name){
        BiMap<String, UUID>invmap= names.inverse();
        UUID u = null;
        if(invmap.containsKey(name)){
            u = invmap.get(name);
        }else{
            String result = c.sendGetRequest("https://api.mojang.com/users/profiles/minecraft/" + name);
            JSON jsonr = JSONSerializer.toJSON(result);
            if(!jsonr.isArray()){
                JSONObject js = (JSONObject) jsonr;
                u = UUID.fromString(js.getString("id"));
                names.put(u, name);
            }
        }
        return u;
    }  

}
