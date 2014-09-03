package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class Disconnect64 extends Packet{
    public Disconnect64(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        String reason = getString(buf);
        c.gui.addText("§4Kicked: " + reason);
        c.stopBot();
    }

}
