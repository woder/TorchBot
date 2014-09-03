package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class EntityHeadLook25 extends Packet{
    public EntityHeadLook25(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        buf.readInt();
        buf.readByte();
    }

}
