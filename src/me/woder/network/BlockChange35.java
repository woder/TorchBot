package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class BlockChange35 extends Packet{
    
    public BlockChange35(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        long val = buf.readLong();
        int x = (int) (val >> 38);
        int y = (int) (val << 26 >> 52);
        int z = (int) (val << 38 >> 38);
        int bid = readVarInt(buf);
        c.gui.addText("Block id: " + bid);
        c.whandle.getWorld().setBlock(x, y, z, bid, 0);
        c.ehandle.handleEvent(new Event("onBlockChange", new Object[] {x,y,z,bid,0}));
    }

}
