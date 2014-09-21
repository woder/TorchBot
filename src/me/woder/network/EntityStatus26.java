package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityStatus26 extends Packet{
    public EntityStatus26(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        buf.readInt();
        buf.readByte();
    }

}
