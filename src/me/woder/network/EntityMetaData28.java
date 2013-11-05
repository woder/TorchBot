package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class EntityMetaData28 extends Packet{
    public EntityMetaData28(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       c.in.readInt();
       c.proc.readWatchableObjects(c.in);
    }

}

