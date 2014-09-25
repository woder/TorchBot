package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class SpawnMob15 extends Packet{
    public SpawnMob15(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int eid = readVarInt(buf);
        int type = buf.readByte() & 0xff;
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        byte yaw = buf.readByte();
        byte pitch = buf.readByte();
        byte headpitch = buf.readByte();
        short velocityx = buf.readShort();
        short velocityy = buf.readShort();
        short velocityz = buf.readShort();
        c.en.addEntity(eid, c.whandle.getWorld(), x, y, z, type, pitch, headpitch, yaw, velocityx, velocityy, velocityz);
        c.proc.readWatchableObjects(buf);
        c.ehandle.handleEvent(new Event("onSpawnEntity", new Object[] {eid, type, x, y, z, yaw, pitch, headpitch, velocityx, velocityy, velocityz}));
    }

}
