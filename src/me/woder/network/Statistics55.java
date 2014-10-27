package me.woder.network;

import java.io.IOException;
import java.util.HashMap;

import me.woder.bot.Client;

public class Statistics55 extends Packet{
    HashMap<String, Integer> stats = new HashMap<String, Integer>();
    
    public Statistics55(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int count = readVarInt(buf);
        for(int i = 0; i < count; i++){
            String s = getString(buf);
            stats.put(s, readVarInt(buf));
        }
    }

}
