package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class ChangeGameState43 extends Packet{
    public ChangeGameState43(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readByte();
        c.in.readFloat();
    }

}
