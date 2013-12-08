package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SpawnPos05 extends Packet{
    int x, y, z;
    
    public SpawnPos05(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        x = c.in.readInt();
        y = c.in.readInt();
        x = c.in.readInt();
    }

}

