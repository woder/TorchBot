package me.woder.network;

import java.io.IOException;
import java.util.logging.Level;

import me.woder.bot.Client;

public class ChatMessage02 extends Packet{
    public ChatMessage02(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        String messages = getString(c.in);
        String finas = c.chat.formatMessage(messages);
        if(messages.contains("!")){
            c.chandle.processCommand(finas);
            log.log(Level.FINEST,finas);
        }
        log.log(Level.FINEST,messages);
        c.gui.addText(finas);
    }

}
