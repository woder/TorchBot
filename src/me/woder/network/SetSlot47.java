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
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        byte window = buf.readByte();
        short slo = buf.readShort();
        log(Level.FINEST,"Slot id is: " + slo);
        System.out.println("Slot id is: " + slo);
        if(slo != -1){
            Slot e = new SlotHandler().processSlots(buf, slo);
            c.invhandle.setSlot(e);
            c.ehandle.handleEvent(new Event("onSlotUpdate", new Object[] {window,slo,e.getId(),e.getCount(),e.getDamage()}));
        }
    }

}
