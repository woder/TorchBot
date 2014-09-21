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
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        c.yaw = buf.readFloat();
        c.pitch = buf.readFloat();
        byte flags = buf.readByte();
        log(Level.FINEST,"Location is: " + x + "," + y + "," + z);
        c.ehandle.handleEvent(new Event("onPlayerPosLook", new Object[] {x, y, z, c.yaw, c.pitch, c.onground}));
        c.location = new Location(c.whandle.getWorld(), x, y, z);
    }

}
