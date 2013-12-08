package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class HandShake00 extends Packet{
    public HandShake00(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.protocol = readVarInt(c.in);
        getString(c.in);
        c.in.readShort();
        c.state = readVarInt(c.in);
    }

}
