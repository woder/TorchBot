package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class BlockChange35 extends Packet{
    public BlockChange35(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        int x = c.in.readInt();
        int y = (c.in.readByte() & 0xff);
        int z = c.in.readInt();
        int bid = readVarInt(c.in);
        int meta = (c.in.readByte() & 0xff);
        c.whandle.getWorld().setBlock(x, y, z, bid, meta);
    }

}
