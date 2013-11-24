package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class BlockChange35 extends Packet{
    
    public BlockChange35(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.gui.addText("packet received");
        int x = c.in.readInt();
        int y = (c.in.readByte() & 0xff);
        int z = c.in.readInt();
        int bid = readVarInt(c.in);
        int meta = (c.in.readByte() & 0xff);
        c.whandle.getWorld().setBlock(x, y, z, bid, meta);
        c.ehandle.handleEvent(new Event("onBlockChange", new Object[] {x,y,z,bid,meta}));
    }

}
