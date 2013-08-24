package me.woder.world;

import me.woder.bot.Client;

public class WorldHandler {
    private World world;
    private Client c;
    
    public WorldHandler(Client c){
        this.c = c;
        world = new World();
    }
    
    public World getWorld(){
        return world;
    }

}
