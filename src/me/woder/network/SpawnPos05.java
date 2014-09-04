package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SpawnPos05 extends Packet{
    int x, y, z;
    
    public SpawnPos05(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        long val = buf.readLong();
        x = (int) (val >> 38);
        y = (int) (val << 26 >> 52);
        z = (int) (val << 38 >> 38);
        c.gui.addText("Loc: " + x + " " + y + " " + z);
    }

}

