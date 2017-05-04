package me.woder.network;

import java.io.IOException;
import java.util.UUID;

import me.woder.bot.Client;
import me.woder.event.Event;

public class SpawnPlayer12 extends Packet{
    public SpawnPlayer12(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int eid = readVarInt(buf);
        UUID uuid = Packet.readUUID(buf);
        String playern = c.en.getNameUUID(uuid);
        System.out.println("Lol " + playern);
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        byte yaw = buf.readByte();
        byte pitch = buf.readByte();
        //c.chat.sendMessage("New player " + playern + " @ " + x + " " + y + " " + z);
        short currentitem = 0; //TODO currentitem IS NOT supported anymore!!! ALWAYS ZERO
        c.en.addPlayer(eid, c.whandle.getWorld(), x, y, z, pitch, yaw, currentitem, playern, uuid); //TODO fix the playername issue
        //c.proc.readWatchableObjects(buf);
        c.ehandle.handleEvent(new Event("onSpawnPlayer", new Object[] {playern, uuid, x, y, z, yaw, pitch, currentitem}));
    }

}
