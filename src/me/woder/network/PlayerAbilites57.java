package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class PlayerAbilites57 extends Packet{
    public PlayerAbilites57(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.flags = c.in.readByte();
        c.flyspeed = c.in.readFloat();
        c.walkspeed = c.in.readFloat();
    }

}

