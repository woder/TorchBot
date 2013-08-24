package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;

public class Slot {
	DataInputStream in;
	int slotnum;
	byte count;
	short damage;
	
	public Slot(DataInputStream in, int slotnum){
	    this.in = in;
	    this.slotnum = slotnum;
	    try {
			processSlots(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processSlots(DataInputStream in) throws IOException{
		 short bid = in.readShort();
		 if(bid != -1){
		     count = in.readByte();
			 System.out.println("There is: " + count + " of item " + bid);
			 damage = in.readShort();
			 short next = in.readShort();
			  if(next != -1){
				  byte[] var2 = new byte[next];
		          in.readFully(var2);
			  }
		 }
	 }

}
