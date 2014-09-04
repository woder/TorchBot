package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.event.Event;
import me.woder.world.Location;

public class EntityRelativeMoveLok23 extends Packet{
    public EntityRelativeMoveLok23(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
       int eid = buf.readInt();
       byte x = buf.readByte();
       byte y = buf.readByte();
       byte z = buf.readByte();
       byte yaw = buf.readByte();
       byte pitch = buf.readByte();
       Entity e = c.en.findEntityId(eid);
       if(e != null){
          e.sx += x;
          e.sy += y;
          e.sz += z;          
          e.setLocationLook(new Location(c.whandle.getWorld(), e.sx/32.0D, e.sy/32.0D, e.sz/32.0D), yaw, pitch);
          c.ehandle.handleEvent(new Event("onEntityMoveLook", new Object[] {eid,e.sx/32.0D, e.sy/32.0D, e.sz/32.0D, yaw, pitch}));
       }
    }

}
