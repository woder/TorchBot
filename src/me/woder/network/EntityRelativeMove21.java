package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.bot.Entity;
import me.woder.world.Location;

public class EntityRelativeMove21 extends Packet{
    
    public EntityRelativeMove21(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
       int eid = c.in.readInt();
       int x = c.in.readByte();
       int y = c.in.readByte();
       int z = c.in.readByte();
       Entity e = c.en.findEntityId(eid);    
       if(e != null){
          e.setLocationRelative(new Location(c.whandle.getWorld(), x,y,z));
       }else{
          c.gui.addText("§1Entity is null! (id: " + eid + ")");
       }
    }

}
