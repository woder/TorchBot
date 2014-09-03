package me.woder.network;

import java.io.IOException;
import java.util.HashMap;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class Statistics55 extends Packet{
    HashMap<String, Integer> stats = new HashMap<String, Integer>();
    
    public Statistics55(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        int count = readVarInt(buf);
        for(int i = 0; i < count; i++){
            stats.put(getString(buf), readVarInt(buf));
        }
    }

}
