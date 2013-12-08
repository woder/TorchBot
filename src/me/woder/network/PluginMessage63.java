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
    public void read(Client c, int len) throws IOException{
        channel = getString(c.in);
        System.out.println("Channel: " + channel);
        data = c.readBytesFromStream(c.in);
    }

}

