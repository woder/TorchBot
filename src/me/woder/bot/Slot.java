package me.woder.bot;

import com.google.common.io.ByteArrayDataOutput;

public class Slot {
    int slotnum;
    private short id;
    private byte count;
    private short damage;
    byte nbtlen;
    
    public Slot(int slotnum, short id, byte count, short damage, byte nbtlen){
        this.slotnum = slotnum;
        this.setId(id);
        this.setCount(count);
        this.setDamage(damage);
        this.nbtlen = nbtlen;
    }

    public void sendSlot(ByteArrayDataOutput buf) {
        buf.writeShort(getId());
        if(getId() > 0){ //only send if we actually have data
         buf.writeByte(getCount());
         buf.writeShort(getDamage());
         buf.writeByte(nbtlen); //this does NOT support actual nbt at this current time       
        }
    }

    public void setSlot(Slot slot) {
        this.slotnum = slot.slotnum;
        this.setId(slot.getId());
        this.setCount(slot.getCount());
        this.setDamage(slot.getDamage());
        this.nbtlen = slot.nbtlen;
    }

    public short getDamage() {
        return damage;
    }

    public void setDamage(short damage) {
        this.damage = damage;
    }

    public short getId() {
    	//System.out.println("got the ID, its "+id+"at slot"+slotnum);
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public byte getCount() {
        return count;
    }

    public void setCount(byte count) {
        this.count = count;
    }

}
