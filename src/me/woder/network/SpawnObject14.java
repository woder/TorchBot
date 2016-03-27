package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SpawnObject14 extends Packet{
    
    public SpawnObject14(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        readVarInt(buf);
        buf.readLong();
        buf.readByte();
        buf.readInt();
        buf.readInt();
        buf.readInt();
        buf.readByte();
        buf.readByte();
        int valuee = buf.readInt();
        if (valuee != 0){
         buf.readShort();
         buf.readShort();
         buf.readShort();
        }
    }

}
