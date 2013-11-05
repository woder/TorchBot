package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class DestroyEntities19 extends Packet {
    public DestroyEntities19(Client c) {
        super(c);
    }

    @Override
    public void read(Client c, int len) throws IOException {
        byte count = c.in.readByte();
        for (int i = 0; i < count; i++) {
            c.in.readInt();
        }
    }

}
