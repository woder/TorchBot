package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class PlayerAbilites57 extends Packet{
    public PlayerAbilites57(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.flags = c.in.readByte();
        c.flyspeed = c.in.readFloat();
        c.walkspeed = c.in.readFloat();
        c.ehandle.handleEvent(new Event("onPlayerAbilities", new Object[] {c.flags,c.flyspeed, c.walkspeed}));
    }

}

