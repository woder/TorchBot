package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class PluginMessage63 extends Packet{
    byte[] data;
    String channel;
    
    public PluginMessage63(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        channel = getString(buf);
        System.out.println("Channel: " + channel);
        data = c.readBytesFromStream(buf);
    }

}

