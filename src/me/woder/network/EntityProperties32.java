package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityProperties32 extends Packet{
    public EntityProperties32(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        Packet.readVarInt(buf); //Entity id
        int count = buf.readInt(); //amount of stuff thats next

        for (int i = 0; i < count; i++) { //loop over it
            getString(buf); //The key
            buf.readDouble(); //The value
            int list = Packet.readVarInt(buf);

            for (int x = 0; x < list; x++) {
                buf.readLong(); // -- 128-bit integer wtf..
                buf.readLong();
                buf.readDouble();
                buf.readByte();
            }
        }


    }

}

