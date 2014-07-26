package me.woder.network;

import java.io.IOException;
import java.math.BigDecimal;

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
        int dx = c.in.readInt();
        int dy = c.in.readInt();
        int dz = c.in.readInt();
        byte yaw = c.in.readByte();
        byte pitch = c.in.readByte();
        BigDecimal x = BigDecimal.valueOf(dx);
        BigDecimal y = BigDecimal.valueOf(dy);
        BigDecimal z = BigDecimal.valueOf(dz);
        x = x.divide(new BigDecimal("32"), BigDecimal.ROUND_FLOOR);
        y = y.divide(new BigDecimal("32"), BigDecimal.ROUND_FLOOR);
        z = z.divide(new BigDecimal("32"), BigDecimal.ROUND_FLOOR);
        Entity e = c.en.findEntityId(eid);
        //c.chat.sendMessage("Coords: " + dx + ", " + dy + ", " + dz);
        if(e != null){
           e.setLocationLook(new Location(c.whandle.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue()), yaw, pitch);
        }
    }

}

