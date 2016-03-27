package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
public class KeepAlive00 extends Packet{
    public KeepAlive00(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int temp = Packet.readVarInt(buf);
        ByteArrayDataOutput buff = ByteStreams.newDataOutput();
        writeVarInt(buff, 11);
        Packet.writeVarInt(buff, temp);
        c.net.sendPacket(buff, c.out);
    }

}
