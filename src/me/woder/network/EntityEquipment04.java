package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.bot.Slot;
import me.woder.bot.SlotHandler;
import me.woder.event.Event;

public class EntityEquipment04 extends Packet{
    public EntityEquipment04(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int eid = Packet.readVarInt(buf);
        short slotnum = buf.readShort();
        Slot s = new SlotHandler().processSlots(buf, slotnum);
        Entity e = c.en.findEntityId(eid);
        if(e != null){
           e.setEquipement(slotnum, s);
           c.ehandle.handleEvent(new Event("onEntityEquipement", new Object[] {eid,slotnum,s.getId(),s.getCount(),s.getDamage()}));
        }
    }

}
