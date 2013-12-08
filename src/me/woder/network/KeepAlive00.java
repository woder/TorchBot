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
    public void read(Client c, int len) throws IOException{
        int temp = c.in.readInt();
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        writeVarInt(buf, 0);
        buf.writeInt(temp);
        sendPacket(buf, c.out);
    }

}
