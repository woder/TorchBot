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
        int eid = Packet.readVarInt(buf);
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        byte yaw = buf.readByte();
        byte pitch = buf.readByte();  
        boolean onground = buf.readBoolean();
        Entity e = c.en.findEntityId(eid);
        //c.chat.sendMessage("Coords: " + dx + ", " + dy + ", " + dz);
        if(e != null){
           e.setLocationLook(new Location(c.whandle.getWorld(), x, y, z), yaw, pitch);
           c.ehandle.handleEvent(new Event("onEntityTeleport", new Object[] {eid,x, y, z, onground}));
        }
    }

}

