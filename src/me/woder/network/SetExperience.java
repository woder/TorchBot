package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SetExperience extends Packet{
    public SetExperience(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        float experienceBar = buf.readFloat();
        int level = Packet.readVarInt(buf);
        int totalXp = Packet.readVarInt(buf);
    }

}
