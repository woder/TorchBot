package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;

public class Player {
	public int playerid;
	public String playername = "";
	public int x;
	public int y;
	public int z;
	public byte yaw;
	public byte pitch;
	public short currentitem;
	Client c;
	
	public Player(Client c){
		this.c = c;
		try {
			parsePacket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parsePacket() throws IOException{
		playerid = c.in.readInt();
		short len = c.in.readShort();
		playername = getString(c.in, len, 17);
		x = c.in.readInt();
		y = c.in.readInt();
		z = c.in.readInt();
		yaw = c.in.readByte();
		pitch = c.in.readByte();
		currentitem = c.in.readShort();
		System.out.println("Player " + playername + " spawned next to me :D and is holding: " + currentitem);
		c.proc.readWatchableObjects(c.in);
	}
	
	public static String getString(DataInputStream datainputstream, int length,
			int max) throws IOException {
		if (length > max)
			throw new IOException(
					"Received string length longer than maximum allowed ("
							+ length + " > " + max + ")");
		if (length < 0) {
			throw new IOException(
					"Received string length is less than zero! Weird string!");
		}
		StringBuilder stringbuilder = new StringBuilder();

		for (int j = 0; j < length; j++) {
			stringbuilder.append(datainputstream.readChar());
		}

		return stringbuilder.toString();
	}

}
