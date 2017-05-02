package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class MultiBlockChange16 extends Packet{
    
    public MultiBlockChange16(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
    	int x = buf.readInt();
    	int y = buf.readInt();
    	int count = readVarInt(buf);
    	
    	for(int i = 0; i < count; i++){
    		int horizPos = buf.readByte() & 0xff;
    		int vertPos = buf.readByte() & 0xff;
    		int bid = readVarInt(buf);
    		int block = (bid & 0xfff0) >> 4;
            int meta = (bid & 0xf);
    		int worldX = (horizPos >> 4 & 15) + (x * 16);
    		int worldY = vertPos;
    		int worldZ = (horizPos & 15) + (y * 16);
    		c.whandle.getWorld().setBlock(worldX, worldY, worldZ, block, meta);
    		c.ehandle.handleEvent(new Event("onBlockChange", new Object[] {worldX, worldY, worldZ,block,meta}));
    	}

    }

}