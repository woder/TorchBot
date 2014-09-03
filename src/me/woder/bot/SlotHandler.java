package me.woder.bot;

import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;

public class SlotHandler {
    byte count;
    short damage;
    
    public SlotHandler(){
    }
    
    public Slot processSlots(ByteArrayDataInput in, int slotnum) throws IOException{
         short bid = in.readShort();
         if(bid != -1){
             count = in.readByte();
             System.out.println("There is: " + count + " of item " + bid);
             damage = in.readShort();
             short next = in.readShort();
             System.out.println("Next is: " + next);
              if(next != -1){
                  byte[] var2 = new byte[next];
                  in.readFully(var2);
              }
         }
         return new Slot(slotnum, bid, count, damage, (short)0);
     }

}
