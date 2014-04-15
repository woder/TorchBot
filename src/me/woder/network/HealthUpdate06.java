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
        int health = (int)c.in.readFloat();
        c.health = health;
        c.chat.sendMessage("Health is now: " + c.health);
        c.food = c.in.readShort();
        c.foodsat = c.in.readFloat();
        c.ehandle.handleEvent(new Event("onHealthUpdate", new Object[] {c.health,c.food,c.foodsat}));
    }  

}
