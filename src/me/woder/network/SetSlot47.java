package me.woder.network;

import java.io.IOException;
import java.util.logging.Level;

import me.woder.bot.Client;
import me.woder.bot.Slot;
import me.woder.bot.SlotHandler;
import me.woder.event.Event;

public class SetSlot47 extends Packet{
    public SetSlot47(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        byte window = c.in.readByte();
        short slo = c.in.readShort();
        log(Level.FINEST,"Slot id is: " + slo);
        if(slo != -1){
            Slot e = new SlotHandler().processSlots(c.in, slo);
            c.invhandle.setSlot(e);
            c.ehandle.handleEvent(new Event("onSlotUpdate", new Object[] {window,slo,e.getId(),e.getCount(),e.getDamage()}));
        }
    }

}
