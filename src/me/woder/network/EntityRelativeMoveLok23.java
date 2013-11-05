package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityRelativeMoveLok23 extends Packet{
    public EntityRelativeMoveLok23(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       c.in.readInt();
       c.in.readByte();
       c.in.readByte();
       c.in.readByte();
       c.in.readByte();
       c.in.readByte();
    }

}
