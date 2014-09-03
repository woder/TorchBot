package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class HandShake00 extends Packet{
    public HandShake00(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        c.protocol = readVarInt(buf);
        getString(buf);
        buf.readShort();
        c.state = readVarInt(buf);
    }

}
