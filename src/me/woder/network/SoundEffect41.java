package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class SoundEffect41 extends Packet{
    public SoundEffect41(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        System.out.println("Playing sound: " + getString(c.in));
        c.in.readInt();
        c.in.readInt();
        c.in.readInt();
        c.in.readFloat();
        c.in.readByte();
    }

}
