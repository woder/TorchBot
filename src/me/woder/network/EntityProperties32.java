package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityProperties32 extends Packet{
    public EntityProperties32(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        buf.readInt();
        int Count = buf.readInt();

        for (int i = 0; i < Count; i++) {
            System.out.println(getString(buf));
            buf.readDouble();
            short elements = buf.readShort(); // -- Yeah fuck this packet in particular.

            for (int x = 0; x < elements; x++) {
                //string UUID = mc.nh.wSock.readString();
                buf.readLong(); // -- 128-bit integer wtf..
                buf.readLong();
                buf.readDouble();
                buf.readByte();
            }
        }


    }

}

