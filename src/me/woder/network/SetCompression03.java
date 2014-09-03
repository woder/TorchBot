package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;
public class SetCompression03 extends Packet{
    public SetCompression03(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        c.threshold = Packet.readVarInt(buf);
        System.out.println("Current threshold " + c.threshold);
    }

}
