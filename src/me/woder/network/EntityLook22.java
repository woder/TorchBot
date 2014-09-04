package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityLook22 extends Packet{
    public EntityLook22(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        buf.readInt();
        buf.readByte();
        buf.readByte();
    }

}
