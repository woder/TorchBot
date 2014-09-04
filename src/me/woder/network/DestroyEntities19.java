package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class DestroyEntities19 extends Packet {
    public DestroyEntities19(Client c) {
        super(c);
    }

    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        byte count = buf.readByte();
        for (int i = 0; i < count; i++) {
            buf.readInt();
        }
    }

}
