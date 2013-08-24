package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;

public class SpawnEntity {
	DataInputStream in;
	Client c;
	public SpawnEntity(DataInputStream in, Client c){
		this.in = in;
		this.c = c;
		try {
			parseData(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public void parseData(DataInputStream in) throws IOException{
		  int eids = in.readInt();
		  System.out.println("Entity id: "+eids);
		  byte typed = in.readByte();
		  int exs = in.readInt();
		  int eys = in.readInt();
		  int ezs = in.readInt();
		  byte pitch = in.readByte();
		  byte hp = in.readByte();
		  byte yaw = in.readByte();
		  short volocityx = in.readShort();
		  short volocityy = in.readShort();
		  short volocityz = in.readShort();
		  c.proc.readWatchableObjects(in);//read that metadata
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
