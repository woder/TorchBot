package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class SpawnMob15 extends Packet{
    public SpawnMob15(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        readVarInt(buf);
        buf.readByte();
        buf.readInt();
        buf.readInt();
        buf.readInt();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readShort();
        buf.readShort();
        buf.readShort();
        c.proc.readWatchableObjects(buf);    
    }

}
