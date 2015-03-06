package me.woder.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.woder.network.Packet;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class InvHandler {
    Client c;
    public List<Slot> inventory = new ArrayList<Slot>();
    public int currentSlot;
    public Short actionNumber;
    
    public InvHandler(Client c){
        this.c = c;
        currentSlot = 36;
        actionNumber = 1;
        //this.selectSlot((short)0);
    }
    
    public List<Slot> getInventory() {
    	return inventory;
    }
    
    public int getCurrentSlot() {
    	return currentSlot;
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
    
    public Slot getItem(short id) {
    	for(Slot s: inventory) {
    		if (s.getId()==id) {
    			return s;
    		}
    	}
    	return null;
    }
    
    public boolean swapTo(short id) {
    	Slot item = getItem(id);
    	if (item!=null) {
    		swapSlots(currentSlot, item.getNum());
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void clickSlot(int num) {
    	ByteArrayDataOutput buf = ByteStreams.newDataOutput();   
    	Slot s = inventory.get(num);
        try {
         //write the clicking info
         Packet.writeVarInt(buf, 14);
         buf.writeByte(0);
         buf.writeShort(s.getNum());
         buf.writeByte(0);
         buf.writeShort(actionNumber);
         actionNumber++;
         buf.writeByte(0);
         //write the slot structure we have
         s.sendSlot(buf);
         c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
         e.printStackTrace();
        }
    }
    
    public void closeInventory() {
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        try {
         Packet.writeVarInt(buf, 13);
         buf.writeByte(0);
         c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
         e.printStackTrace();
        }
    }
    
    public void swapSlots(final int a, final int b) {
    	if(inventory.get(a)==null||inventory.get(b)==null) {
    		System.out.println("Passed slots do not exist");
    		return;
    	}
    	c.chat.sendMessage("swapping slots "+a+" and "+b);
    	int delay = 100;
    	final Timer timer = new Timer();
    	final Slot slotB = new Slot(c.invhandle.inventory.get(b).getNum(),c.invhandle.inventory.get(a).getId(),
    			c.invhandle.inventory.get(a).getCount(),c.invhandle.inventory.get(a).getDamage(),c.invhandle.inventory.get(a).getNbtlen());
    	final Slot slotA = new Slot(c.invhandle.inventory.get(a).getNum(),c.invhandle.inventory.get(b).getId(),
    			c.invhandle.inventory.get(b).getCount(),c.invhandle.inventory.get(b).getDamage(),c.invhandle.inventory.get(b).getNbtlen());
    	
    	timer.schedule(new TimerTask(){
            public void run() {
            	clickSlot(a);//pick up contents of a
            }
        }, delay);
    	
    	timer.schedule(new TimerTask(){
            public void run() {
            	clickSlot(b);//pick up B
            	            	
            }
        }, delay*2);
    	
    	timer.schedule(new TimerTask(){
            public void run() {
            	clickSlot(a);//set down B
            }
        }, delay*3);
    	
    	timer.schedule(new TimerTask(){
            public void run() {
            	setSlot(slotB);
            	setSlot(slotA);
            	closeInventory(); 
            	timer.cancel();
            }
        }, delay*4);
    	//pray to Notch that we don't need to handle or send confirmations
    }

}
