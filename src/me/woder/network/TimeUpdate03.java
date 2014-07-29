package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class TimeUpdate03 extends Packet{
    public TimeUpdate03(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.age = c.in.readLong();
        c.time = c.in.readLong();
        c.ehandle.handleEvent(new Event("onTimeUpdate", new Object[] {c.age, c.time}));
    }

}
