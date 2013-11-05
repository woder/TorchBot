package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class HeldItemChange09 extends Packet{
    public HeldItemChange09(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.selectedslot = c.in.readByte();
    }

}

