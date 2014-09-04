package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
public class SetCompression03 extends Packet{
    public SetCompression03(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        c.threshold = Packet.readVarInt(buf);
        System.out.println("Current threshold " + c.threshold);
    }

}
