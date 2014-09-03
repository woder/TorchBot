package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class PlayerListItem56 extends Packet{
    public PlayerListItem56(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        String player = getString(buf);
        boolean online = buf.readBoolean();
        buf.readShort();
        /*if(!c.onplayers.contains(player)){
           c.onplayers.add(player); 
        }
        if(!online){
           c.onplayers.remove(player);
        }*/
    }

}
