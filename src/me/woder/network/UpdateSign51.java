package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

import com.google.common.io.ByteArrayDataInput;

public class UpdateSign51 extends Packet{
    public UpdateSign51(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        int x = buf.readInt();
        short y = buf.readShort();
        int z = buf.readInt();
        String l1 = getString(buf);
        String l2 = getString(buf);
        String l3 = getString(buf);
        String l4 = getString(buf);
        c.ehandle.handleEvent(new Event("onSignUpdate", new Object[] {x,y,z,l1,l2,l3,l4}));
    }

}
