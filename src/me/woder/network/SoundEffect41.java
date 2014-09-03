package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class SoundEffect41 extends Packet{
    public SoundEffect41(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        System.out.println("Playing sound: " + getString(buf));
        buf.readInt();
        buf.readInt();
        buf.readInt();
        buf.readFloat();
        buf.readByte();
    }

}
