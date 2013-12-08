package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SpawnMob15 extends Packet{
    public SpawnMob15(Client c) {
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
        c.in.readByte();
        c.in.readShort();
        c.in.readShort();
        c.in.readShort();
        c.proc.readWatchableObjects(c.in);
        
    }

}
