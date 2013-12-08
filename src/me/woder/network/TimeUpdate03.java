package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class TimeUpdate03 extends Packet{
    public TimeUpdate03(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.age = c.in.readLong();
        c.time = c.in.readLong();
    }

}
