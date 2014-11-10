package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityMetaData28 extends Packet{
    public EntityMetaData28(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
       Packet.readVarInt(buf); //Entity id
       //c.proc.readWatchableObjects(buf);
    }

}

