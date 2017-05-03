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
       double x = buf.readShort()/(double)(128*32);
       double y = buf.readShort()/(double)(128*32);
       double z = buf.readShort()/(double)(128*32);
       System.out.println("Delta was: " + x + " " + y + " " + z);
       byte yaw = buf.readByte();
       byte pitch = buf.readByte();
       boolean onground = buf.readBoolean();
       Entity e = c.en.findEntityId(eid);
       System.out.println("EID ewas: " + eid);
       if(e != null){
          System.out.println("FOUND EID was: " + eid);
          e.sx += x;
          e.sy += y;
          e.sz += z;          
          e.setLocationLook(new Location(c.whandle.getWorld(), (e.getLocation().getX()+x), (e.getLocation().getY()+x), (e.getLocation().getZ()+x)), yaw, pitch);
          c.ehandle.handleEvent(new Event("onEntityMoveLook", new Object[] {eid,e.sx/32.0D, e.sy/32.0D, e.sz/32.0D, yaw, pitch}));
       }
    }

}
