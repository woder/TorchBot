package me.woder.bot;

import java.io.IOException;

import me.woder.world.World;

public class EntityVehicle extends Entity{
    int eid;
    Client c;
    World world;
    int type;
    int x;
    int y;
    int z;
    byte pitch;
    byte yaw;

    public EntityVehicle(Client c) {
        super(c);
        this.c = c;
        try {
            parsePacket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void parsePacket() throws IOException{
        eid = c.in.readInt();
        type = c.in.readByte();
        x = c.in.readInt();
        y = c.in.readInt();
        z = c.in.readInt();
        pitch = c.in.readByte();
        yaw = c.in.readByte();
        int valuee = c.in.readInt();
        if (valuee != 0){
         c.in.readShort();
         c.in.readShort();
         c.in.readShort();
        }
    }

    
}
