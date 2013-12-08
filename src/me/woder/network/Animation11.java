package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class Animation11 extends Packet{
    public Animation11(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        readVarInt(c.in);
        c.in.readByte();
    }

}