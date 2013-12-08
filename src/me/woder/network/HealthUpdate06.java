package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class HealthUpdate06 extends Packet{

    public HealthUpdate06(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.health = c.in.readFloat();
        c.food = c.in.readShort();
        
        //c.ehandle.handleEvent(new Event("onBlockChange", new Object[] {x,y,z,bid,meta}));
    }  

}
