package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.event.Event;
import me.woder.world.Location;

public class EntityRelativeMove21 extends Packet{
    
    public EntityRelativeMove21(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
       int eid = Packet.readVarInt(buf);
       int sx = buf.readShort()/ (128 * 32);
       int sy = buf.readShort()/ (128 * 32);
       int sz = buf.readShort()/ (128 * 32);
       boolean onground = buf.readBoolean();
       Entity e = c.en.findEntityId(eid);
       if(e != null){
          e.sx += sx;
          e.sy += sy;
          e.sz += sz;
          e.setLocation(new Location(c.whandle.getWorld(), e.sx/32.0D, e.sy/32.0D, e.sz/32.0D));
          c.ehandle.handleEvent(new Event("onEntityMove", new Object[] {eid,e.sx/32.0D, e.sy/32.0D, e.sz/32.0D}));
       }
    }

}
