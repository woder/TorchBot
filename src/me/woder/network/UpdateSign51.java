package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class UpdateSign51 extends Packet{
    public UpdateSign51(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        int x = c.in.readInt();
        short y = c.in.readShort();
        int z = c.in.readInt();
        String l1 = getString(c.in);
        String l2 = getString(c.in);
        String l3 = getString(c.in);
        String l4 = getString(c.in);
        c.ehandle.handleEvent(new Event("onSignUpdate", new Object[] {x,y,z,l1,l2,l3,l4}));
    }

}
