package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.woder.network.Packet;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class InvHandler {
    Client c;
    public List<Slot> inventory = new ArrayList<Slot>();
    public int currentSlot;
    
    public InvHandler(Client c){
        this.c = c;
        currentSlot = 36;
        //this.selectSlot((short)0);
    }
    
    public void creativeSetSlot(short id, Slot slot){
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        try {
         Packet.writeVarInt(buf, 16);
         buf.writeShort(id);
         slot.sendSlot(buf);
         c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
         e.printStackTrace();
        }
    }
    
    //pre: id is from 0-8
    //post: sets the active hotkey slot to the given id, and stores the current slot as an inventory location to currentSlot.
    public void selectSlot(short id){
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        try {
         currentSlot = (int)id+36;
         Packet.writeVarInt(buf, 9);
         buf.writeShort(id);
         c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
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
    
    public void sendSlot(Slot slot) {//TODO: make sendSlot an actual method in InvHandler
    	boolean found = false;
        for(Slot s : inventory){
            //do something
        }
        if(!found){
           //do something else
        }
    }
    
    public Slot getItem(short id) {
    	for(Slot s: inventory) {
    		if (s.getId()==id) {
    			return s;
    		}
    	}
    	return null;
    }

}
