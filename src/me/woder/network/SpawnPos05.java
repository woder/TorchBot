package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class SpawnPos05 extends Packet{
    int x, y, z;
    
    public SpawnPos05(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        x = buf.readInt();
        y = buf.readInt();
        x = buf.readInt();
    }

}

