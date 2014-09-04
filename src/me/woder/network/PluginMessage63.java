package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class PluginMessage63 extends Packet{
    byte[] data;
    String channel;
    
    public PluginMessage63(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        channel = getString(buf);
        System.out.println("Channel: " + channel);
        data = new byte[buf.getAvailable()];
        buf.readFully(data);
    }

}

