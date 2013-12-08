package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityProperties32 extends Packet{
    public EntityProperties32(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.in.readInt();
        int Count = c.in.readInt();

        for (int i = 0; i < Count; i++) {
            System.out.println(getString(c.in));
            c.in.readDouble();
            short elements = c.in.readShort(); // -- Yeah fuck this packet in particular.

            for (int x = 0; x < elements; x++) {
                //string UUID = mc.nh.wSock.readString();
                c.in.readLong(); // -- 128-bit integer wtf..
                c.in.readLong();
                c.in.readDouble();
                c.in.readByte();
            }
        }


    }

}

