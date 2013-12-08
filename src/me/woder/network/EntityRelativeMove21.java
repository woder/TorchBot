package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityRelativeMove21 extends Packet{
    public EntityRelativeMove21(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       c.in.readInt();
       c.in.readByte();
       c.in.readByte();
       c.in.readByte();
    }

}
