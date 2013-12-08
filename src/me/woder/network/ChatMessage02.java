package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class ChatMessage02 extends Packet{
    public ChatMessage02(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        String messages = getString(c.in);
        c.chat.formatMessage(messages);       
    }

}
