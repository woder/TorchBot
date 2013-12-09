package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class PlayerListItem56 extends Packet{
    public PlayerListItem56(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        String player = getString(c.in);
        boolean online = c.in.readBoolean();
        c.in.readShort();
        /*if(!c.onplayers.contains(player)){
           c.onplayers.add(player); 
        }
        if(!online){
           c.onplayers.remove(player);
        }*/
    }

}
