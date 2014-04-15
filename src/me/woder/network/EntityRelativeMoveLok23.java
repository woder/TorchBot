package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.world.Location;

public class EntityRelativeMoveLok23 extends Packet{
    public EntityRelativeMoveLok23(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       int eid = c.in.readInt();
       double x = c.in.readByte() / 32;
       double y = c.in.readByte() / 32;
       double z = c.in.readByte() / 32;
       byte yaw = c.in.readByte();
       byte pitch = c.in.readByte();
       Entity e = c.en.findEntityId(eid);
       if(e != null){
          e.setLocationLookRelative(new Location(c.whandle.getWorld(), x, y, z), yaw, pitch);
       }
    }

}
