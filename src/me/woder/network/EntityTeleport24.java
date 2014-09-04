package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.event.Event;
import me.woder.world.Location;

public class EntityTeleport24 extends Packet{
    public EntityTeleport24(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int eid = buf.readInt();
        int dx = buf.readInt();
        int dy = buf.readInt();
        int dz = buf.readInt();
        byte yaw = buf.readByte();
        byte pitch = buf.readByte();       
        Entity e = c.en.findEntityId(eid);
        //c.chat.sendMessage("Coords: " + dx + ", " + dy + ", " + dz);
        if(e != null){
           e.sx = dx;
           e.sy = dy;
           e.sz = dz;
           e.setLocationLook(new Location(c.whandle.getWorld(), e.sx/32.0D, e.sy/32.0D, e.sz/32.0D), yaw, pitch);
           c.ehandle.handleEvent(new Event("onEntityTeleport", new Object[] {eid,e.sx/32.0D, e.sy/32.0D, e.sz/32.0D}));
        }
    }

}

