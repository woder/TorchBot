package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class EntityLook22 extends Packet{
    public EntityLook22(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        buf.readInt();
        buf.readByte();
        buf.readByte();
    }

}
