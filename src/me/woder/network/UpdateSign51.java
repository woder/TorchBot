package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class UpdateSign51 extends Packet{
    public UpdateSign51(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        long val = buf.readLong();
        int x = (int) (val >> 38);
        int y = (int) (val << 26 >> 52);
        int z = (int) (val << 38 >> 38);
        String l1 = getString(buf);
        String l2 = getString(buf);
        String l3 = getString(buf);
        String l4 = getString(buf);
        c.ehandle.handleEvent(new Event("onSignUpdate", new Object[] {x,y,z,l1,l2,l3,l4}));
    }

}
