package me.woder.network;

import java.io.IOException;
import java.util.logging.Level;

import me.woder.bot.Client;
import me.woder.world.Location;

public class PlayerPosLook08 extends Packet{
    public PlayerPosLook08(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        double X = c.in.readDouble();
        double Y = c.in.readDouble();
        double Z = c.in.readDouble();
        c.yaw = c.in.readFloat();
        c.pitch = c.in.readFloat();
        c.onground = c.in.readBoolean();
        log(Level.FINEST,"Location is: " + X + "," + Y + "," + Z);
        //c.chat.sendMessage("I updated my location to: " + X + ", " + Y + ", " + Z);
        c.move.move(X, Y, Z);
        c.location = new Location(c.whandle.getWorld(), X, Y, Z);
        System.out.println("Location updated to: " + X + "," + Y + "," + Z);
    }

}
