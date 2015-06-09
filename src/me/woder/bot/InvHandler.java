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
    	if (id<0||id>8) {
    		System.out.println("Invalid hotbar slot!");
    		return;
    	}
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        try {
         currentSlot = id+36;
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
    		if(s.getId()==id){
    			return s;
    		}
    	}
    	return null;
    }
    
    public Slot getSlot(int id){
        for(Slot s: inventory){
            if(s.getNum() == id){
                return s;
            }
        }
        return null;
    }
    
    public int swapTo(short id) {
    	Slot mySlot = inventory.get(currentSlot);
    	if (mySlot.getId()==id) {
    		System.out.println("CurrentSlot already contains "+id);
    		return mySlot.getCount();
    	}
    	Slot item = getItem(id);
    	if (item!=null) {
    		c.chat.sendMessage("found "+item.getCount()+" of "+item.getId()+"moving to slot "+currentSlot);
    		swapSlots(currentSlot, item.getNum());
    		return item.getCount();
    	} else {
    		System.out.println(id+" not found");
    		return -1;
    	}
    	
    }
    
    public void swapSlots(final int a, final int b) {
    	if (a==b) {
    		System.out.println("Passed slots are identical reference");
    		return;
    	}
    	if(inventory.get(a)==null||inventory.get(b)==null) {
    		System.out.println("Passed slots do not exist");
    		return;
    	}
    	System.out.println("swapping slots "+a+" and "+b);
    	
    	int delay = 100;
    	final Timer timer = new Timer();
    	Slot originalA = c.invhandle.inventory.get(a);
    	Slot originalB = c.invhandle.inventory.get(b);
    	final Slot emptyA = new Slot(originalA.getNum(),(short)-1,(byte)0,(short)0,(byte)0);//the location of A, with no contents
    	final Slot slotB = new Slot(originalB.getNum(),originalA.getId(),
    			originalA.getCount(),originalA.getDamage(),originalA.getNbtlen());//the location of B, with the contents of A
    	final Slot slotA = new Slot(originalA.getNum(),originalB.getId(),
    			originalB.getCount(),originalB.getDamage(),originalB.getNbtlen());//the location of A, with the contents of B
    	System.out.println("original contents: "+originalA.getId()+","+originalB.getId());
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	leftClickSlot(a);//pick up contents of a
            	setSlot(emptyA);
            }
        }, delay);
    	
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	leftClickSlot(b);//pick up B's contents ,set down A's contents in B's location
            	setSlot(slotB);
            	            	
            }
        }, delay*2);
    	
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	leftClickSlot(a);//set down B's contents in A's original location
            	setSlot(slotA);
            	
            }
        }, delay*3);
    	
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	closeInventory(); 
            	timer.cancel();
            }
        }, delay*4);
    	
    	
    	System.out.println("new contents: "+inventory.get(a).getId()+","+inventory.get(b).getId());
    	//pray to Notch that we don't need to handle or send confirmations
    }
    
    public void leftClickSlot(int num) {
    	click((byte)0,(short)num,(byte)0,(byte)0);
    	/*ByteArrayDataOutput buf = ByteStreams.newDataOutput();   
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
        }*/
    }
    
    public void click(byte windowID, short num, byte button, byte mode) {
    	ByteArrayDataOutput buf = ByteStreams.newDataOutput();   
    	Slot s;
    	if (num>0&&num<=44) {
    		s = inventory.get(num);
    	} else {
    		s = new Slot(-999,(short)-1,(byte)0,(short)0,(byte)0);
    	}
        try {
         //write the clicking info
         Packet.writeVarInt(buf, 14);
         buf.writeByte(windowID);
         buf.writeShort(num);
         buf.writeByte(button);
         buf.writeShort(actionNumber);
         actionNumber++;
         buf.writeByte(mode);
         //write the slot structure we have
         s.sendSlot(buf);
         c.net.sendPacket(buf, c.out);
        } catch (IOException e) {
         e.printStackTrace();
        }
    }
    
    public void clickOutside() {
    	click((byte)0,(short)-999,(byte)0,(byte)4);
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
    
    public void drop(final int slot) {
    	if (inventory.get(slot).getId()==-1) {
    		System.out.println("Nothing found in passed slot");
    	} else {
    		c.chat.sendMessage("dropping");
    	}
    	int delay = 100;
    	final Timer timer = new Timer();
    	final Slot empty = new Slot(slot, (short)-1, (byte) 0, (short) 0, (byte) 0);
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	leftClickSlot(slot);
            	setSlot(empty);
            }
        }, delay);
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	clickOutside();
            }
        }, delay*2);
    	timer.schedule(new TimerTask(){
            @Override
            public void run() {
            	closeInventory();
            }
        }, delay*3);
    }
    
    

}
