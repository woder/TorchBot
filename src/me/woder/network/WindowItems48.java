package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Slot;

public class WindowItems48 extends Packet{
    public WindowItems48(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readByte();
        short count = c.in.readShort();
        for(int i = 0; i < count;i++){
            c.inventory.add(new Slot(c.in,i));
        }
    }

}
