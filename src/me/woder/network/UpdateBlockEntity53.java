package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;


//TODO: Figure out what the fuck this even does

public class UpdateBlockEntity53 extends Packet{
    public UpdateBlockEntity53(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
       buf.readInt();
       buf.readShort(); //guess the guy who made this decided he wanted short
       buf.readInt();
       buf.readByte();
       c.readBytesFromStream(buf);
    }

}
