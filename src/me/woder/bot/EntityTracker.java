package me.woder.bot;

import java.util.HashMap;

public class EntityTracker {    
    Client c;
    HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

    public EntityTracker(Client c){
        this.c = c;
    }
    
    public Player findPlayer(String name){
        Player p = null;
        for(int i = 0; i < entities.size(); i++){
           Entity s = entities.get(i);
           if(s.getEntity() instanceof Player){
            Player a = (Player)s;
            if(a.getName().equals(name)){
                p = a;
                break;
            }
           }
        }
        return p;
    }
    
    public Entity findEntityId(int id){
        Entity e = null;
        for(int i = 0; i < entities.size(); i++){
            Entity s = entities.get(i);
            if(s.getEntityId() == id){
                e = s;
                break;
            }
        }
        return e;
    }

}
