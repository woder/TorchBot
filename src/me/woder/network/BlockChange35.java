package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

import com.google.common.io.ByteArrayDataInput;

public class BlockChange35 extends Packet{
    
    public BlockChange35(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        int x = buf.readInt();
        int y = (buf.readByte() & 0xff);
        int z = buf.readInt();
        int bid = readVarInt(buf);
        int meta = (buf.readByte() & 0xff);
        c.whandle.getWorld().setBlock(x, y, z, bid, meta);
        c.ehandle.handleEvent(new Event("onBlockChange", new Object[] {x,y,z,bid,meta}));
    }

}
