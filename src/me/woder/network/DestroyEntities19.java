package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class DestroyEntities19 extends Packet {
    public DestroyEntities19(Client c) {
        super(c);
    }

    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int count = Packet.readVarInt(buf); //the count
        for (int i = 0; i < count; i++) { //read all of them in
            Packet.readVarInt(buf); //TODO make this do something; this is IMPORTANT
        }
    }

}
