package me.woder.network;

import java.io.IOException;
import java.util.logging.Level;

import me.woder.bot.Client;
import me.woder.event.Event;
import me.woder.world.Location;

public class PlayerPosLook08 extends Packet{
    public PlayerPosLook08(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        double x = c.in.readDouble();
        double y = c.in.readDouble();
        double z = c.in.readDouble();
        c.yaw = c.in.readFloat();
        c.pitch = c.in.readFloat();
        c.onground = c.in.readBoolean();
        log(Level.FINEST,"Location is: " + x + "," + y + "," + z);
        c.ehandle.handleEvent(new Event("onPlayerPosLook", new Object[] {x, y, z, c.yaw, c.pitch, c.onground}));
        c.location = new Location(c.whandle.getWorld(), x, y, z);
    }

}
