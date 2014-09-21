package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class PlayerAbilites57 extends Packet{
    public PlayerAbilites57(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        c.flags = buf.readByte();
        c.flyspeed = buf.readFloat();
        c.walkspeed = buf.readFloat();
        c.ehandle.handleEvent(new Event("onPlayerAbilities", new Object[] {c.flags,c.flyspeed, c.walkspeed}));
    }

}

