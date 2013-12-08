package me.woder.world;

import me.woder.bot.Client;

public class WorldHandler {
    private World world;
    @SuppressWarnings("unused")
    private Client c;
    
    public WorldHandler(Client c){
        this.c = c;
        world = new World(c);
    }
    
    public World getWorld(){
        return world;
    }

}
