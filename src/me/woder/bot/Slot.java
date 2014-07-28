package me.woder.bot;

import com.google.common.io.ByteArrayDataOutput;

public class Slot {
    int slotnum;
    short id;
    byte count;
    short damage;
    short nbtlen;
    
    public Slot(int slotnum, short id, byte count, short damage, short nbtlen){
        this.slotnum = slotnum;
        this.id = id;
        this.count = count;
        this.damage = damage;
        this.nbtlen = nbtlen;
    }

    public void sendSlot(ByteArrayDataOutput buf) {
        buf.writeShort(id);
        buf.writeByte(count);
        buf.writeShort(damage);
        buf.writeShort(nbtlen); //this does NOT support actual nbt at this current time       
    }

    public void setSlot(Slot slot) {
        this.slotnum = slot.slotnum;
        this.id = slot.id;
        this.count = slot.count;
        this.damage = slot.damage;
        this.nbtlen = slot.nbtlen;
    }

}
