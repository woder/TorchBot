package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class JoinGame01 extends Packet{
    public JoinGame01(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.entityID = c.in.readInt();
        c.gamemode = c.in.readByte();
        c.dimension = c.in.readByte();
        c.difficulty = c.in.readByte();
        c.maxplayer = c.in.readByte();
        System.out.println("Level type is: " + getString(c.in));
    }

}
