package me.woder.network;

import java.io.IOException;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;
public class KeepAlive00 extends Packet{
    public KeepAlive00(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        long temp = buf.readLong();
        ByteArrayDataOutput buff = ByteStreams.newDataOutput();
        writeVarInt(buff, 11);
        buff.writeLong(temp);
        c.net.sendPacket(buff, c.out);
    }

}
