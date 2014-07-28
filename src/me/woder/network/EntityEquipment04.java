package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.bot.Slot;
import me.woder.bot.SlotHandler;

public class EntityEquipment04 extends Packet{
    public EntityEquipment04(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        int eid = c.in.readInt();
        short slotnum = c.in.readShort();
        Slot s = new SlotHandler().processSlots(c.in, slotnum);
        Entity e = c.en.findEntityId(eid);
        if(e != null){
           e.setEquipement(slotnum, s);
        }
    }

}
