package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class Effect40 extends Packet{
    public Effect40(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readInt();
        c.in.readInt();
        c.in.readByte();
        c.in.readInt();
        c.in.readInt();
        c.in.readBoolean();
    }

}
