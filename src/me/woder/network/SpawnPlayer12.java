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
        String uuid = getString(buf);
        String playern = getString(buf);
        int datanumber = readVarInt(buf);
        for(int i = 0; i < datanumber; i++){
            getString(buf);
            getString(buf);
            getString(buf);
        }
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        byte yaw = buf.readByte();
        byte pitch = buf.readByte();
        short currentitem = buf.readShort();
        c.en.addPlayer(eid, c.whandle.getWorld(), x, y, z, pitch, yaw, currentitem, playern, uuid);
        c.proc.readWatchableObjects(buf);
        c.ehandle.handleEvent(new Event("onSpawnPlayer", new Object[] {playern, uuid, x, y, z, yaw, pitch, currentitem}));
    }

}
