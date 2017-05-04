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
       int eid = Packet.readVarInt(buf);
       double dx = buf.readShort()/(double)(128*32);
       double dy = buf.readShort()/(double)(128*32);
       double dz = buf.readShort()/(double)(128*32);
       System.out.println("Delta was: " + dx + " " + dy + " " + dz);
       byte yaw = buf.readByte();
       byte pitch = buf.readByte();
       boolean onground = buf.readBoolean();
       Entity e = c.en.findEntityId(eid);
       System.out.println("EID ewas: " + eid);
       if(e != null){
          System.out.println("FOUND EID was: " + eid);
          //this location is the one we will translate, we don't really want to apply the translation to the existing location inside the entity object
          Location l = new Location(c.whandle.getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ());
          //apply the translation
          l.translate(dx, dy, dz);
          //set the location to our translated location
          e.setLocationLook(l, yaw, pitch);
          c.ehandle.handleEvent(new Event("onEntityMoveLook", new Object[] {eid,l.getX(), l.getY(), l.getZ(), yaw, pitch, onground}));
       }
    }

}
