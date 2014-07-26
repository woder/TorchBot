package me.woder.network;

import java.io.IOException;
import java.math.BigDecimal;

import me.woder.bot.Client;
import me.woder.bot.Entity;

public class EntityRelativeMove21 extends Packet{
    
    public EntityRelativeMove21(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       int eid = c.in.readInt();
       double dx = c.in.readByte();
       double dy = c.in.readByte();
       double dz = c.in.readByte();
       BigDecimal x = BigDecimal.valueOf(dx);
       BigDecimal y = BigDecimal.valueOf(dy);
       BigDecimal z = BigDecimal.valueOf(dz);
       x = x.divide(new BigDecimal("32"), BigDecimal.ROUND_FLOOR);
       y = y.divide(new BigDecimal("32"), BigDecimal.ROUND_FLOOR);
       z = z.divide(new BigDecimal("32"), BigDecimal.ROUND_FLOOR);
       Entity e = c.en.findEntityId(eid);
       if(e != null){
          e.setLocationRelative(c.whandle.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue());
       }else{
          c.gui.addText("§1Entity is null! (id: " + eid + ")");
       }
    }

}
