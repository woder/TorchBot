package me.woder.network;

import java.io.IOException;
import java.util.logging.Level;

import me.woder.bot.Client;
import me.woder.bot.Slot;

public class SetSlot47 extends Packet{
    public SetSlot47(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readByte();
        short slo = c.in.readShort();
        log(Level.FINEST,"Slot id is: " + slo);
        if(slo != -1){
         if(c.inventory.size() >= slo){
         //inventory.remove(slo);
         }
         new Slot(c.in, slo);
         //inventory.add(slo,new Slot(in, slo));
        }else{
          new Slot(c.in, slo);
        }
    }

}
