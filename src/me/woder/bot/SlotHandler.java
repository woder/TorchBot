package me.woder.bot;

import java.io.IOException;

import me.woder.network.ByteArrayDataInputWrapper;

public class SlotHandler {
    byte count;
    short damage;
    
    public SlotHandler(){
    }
    
    public Slot processSlots(ByteArrayDataInputWrapper in, int slotnum) throws IOException{
         short bid = in.readShort();
         if(bid != -1){
             count = in.readByte();
             //System.out.println("There is: " + count + " of item " + bid);
             damage = in.readShort();
             byte next = in.readByte();
             //System.out.println("Next is: " + next);
             /*if(next != -1){
                  byte[] var2 = new byte[next];
                  in.readFully(var2, 0, next);
             }*/
         }
         return new Slot(slotnum, bid, count, damage, (byte)0);
     }

}
