package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class ChatMessage02 extends Packet{
    public ChatMessage02(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        String messages = getString(buf);
        byte position = buf.readByte();
        if(position == 0){ //if this message is for the chat box, display it here
          c.chat.formatMessage(messages);   
        }
    }

}
