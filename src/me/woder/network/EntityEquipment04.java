package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Slot;

public class EntityEquipment04 extends Packet{
    public EntityEquipment04(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readInt();
        short slotnum = c.in.readShort();
        new Slot(c.in, slotnum);
    }

}
