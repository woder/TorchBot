package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class SpawnPlayer12 extends Packet{
    public SpawnPlayer12(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        int eid = readVarInt(c.in);
        String uuid = getString(c.in);
        String playern = getString(c.in);
        int x = c.in.readInt() / 32;
        int y = c.in.readInt() / 32;
        int z = c.in.readInt() / 32;
        byte yaw = c.in.readByte();
        byte pitch = c.in.readByte();
        short currentitem = c.in.readShort();
        c.en.addPlayer(eid, c.whandle.getWorld(), x, y, z, pitch, yaw, currentitem, playern, uuid);
        c.proc.readWatchableObjects(c.in);
        c.ehandle.handleEvent(new Event("onSpawnPlayer", new Object[] {playern, uuid, x, y, z, yaw, pitch, currentitem}));
    }

}
