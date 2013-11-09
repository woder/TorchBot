package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class Disconnect00 extends Packet{
    public Disconnect00(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        String reason = getString(c.in);
        c.gui.addText("§4Kicked: " + reason);
        c.stopBot();
    }

}
