package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityHeadLook25 extends Packet{
    public EntityHeadLook25(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readInt();
        c.in.readByte();
    }

}
