package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.world.Location;

public class EntityTeleport24 extends Packet{
    public EntityTeleport24(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        int eid = c.in.readInt();
        int x = c.in.readInt()/32;
        int y = c.in.readInt()/32;
        int z = c.in.readInt()/32;
        byte yaw = c.in.readByte();
        byte pitch = c.in.readByte();
        Entity e = c.en.findEntityId(eid);
        if(e != null){
           e.setLocationLook(new Location(c.whandle.getWorld(), x, y, z), yaw, pitch);
        }
    }

}

