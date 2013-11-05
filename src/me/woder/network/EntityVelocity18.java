package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityVelocity18 extends Packet{
    public EntityVelocity18(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       c.in.readInt();
       c.in.readShort();
       c.in.readShort();
       c.in.readShort();
    }

}
