package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.woder.network.Packet;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class InvHandler {
    Client c;
    List<Slot> inventory = new ArrayList<Slot>();
    
    public InvHandler(Client c){
        this.c = c;
    }
    
    public void creativeSetSlot(short id, Slot slot){
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        try {
         Packet.writeVarInt(buf, 16);
         buf.writeShort(id);
         slot.sendSlot(buf);
         Packet.sendPacket(buf, c.out);
        } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
    }
    
    public void selectSlot(short id){
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        try {
         Packet.writeVarInt(buf, 9);
         buf.writeShort(id);
         Packet.sendPacket(buf, c.out);
        } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
    }
    
    public void setSlot(Slot slot){
        boolean found = false;
        for(Slot s : inventory){
            if(s.slotnum == slot.slotnum){
                found = true;
                s.setSlot(slot);
            }
        }
        if(!found){
           inventory.add(slot);
        }
    }

}
