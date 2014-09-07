package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;
import me.woder.event.Event;

public class SpawnPlayer12 extends Packet{
    public SpawnPlayer12(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int eid = readVarInt(buf);
        String uuid = Packet.readUUID(buf);
        String playern = "";
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        byte yaw = buf.readByte();
        byte pitch = buf.readByte();
        short currentitem = buf.readShort();
        c.en.addPlayer(eid, c.whandle.getWorld(), x, y, z, pitch, yaw, currentitem, playern, uuid); //TODO fix the playername issue
        c.proc.readWatchableObjects(buf);
        c.ehandle.handleEvent(new Event("onSpawnPlayer", new Object[] {playern, uuid, x, y, z, yaw, pitch, currentitem}));
    }

}
