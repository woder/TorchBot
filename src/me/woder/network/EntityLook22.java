package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityLook22 extends Packet{
    public EntityLook22(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        Packet.readVarInt(buf); //entity id
        buf.readByte();
        buf.readByte();
        buf.readBoolean();
        //buf.readByte();
    }

}
