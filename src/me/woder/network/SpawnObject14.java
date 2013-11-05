package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SpawnObject14 extends Packet{
    
    public SpawnObject14(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        readVarInt(c.in);
        c.in.readByte();
        c.in.readInt();
        c.in.readInt();
        c.in.readInt();
        c.in.readByte();
        c.in.readByte();
        int valuee = c.in.readInt();
        if (valuee != 0){
         c.in.readShort();
         c.in.readShort();
         c.in.readShort();
        }
    }

}
