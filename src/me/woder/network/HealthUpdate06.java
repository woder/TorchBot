package me.woder.network;

import java.io.IOException;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;
import me.woder.event.Event;

public class HealthUpdate06 extends Packet{

    public HealthUpdate06(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int health = (int)buf.readFloat();
        c.health = health;
        //c.chat.sendMessage("Health is now: " + c.health);
        c.food = Packet.readVarInt(buf);
        c.foodsat = buf.readFloat();
        c.ehandle.handleEvent(new Event("onHealthUpdate", new Object[] {c.health,c.food,c.foodsat}));
        if(c.health < 1){
           //we died
           c.en.delAll();
           try {
               ByteArrayDataOutput bufs = ByteStreams.newDataOutput();
               Packet.writeVarInt(bufs, 3); //Send client status packet
               Packet.writeVarInt(bufs, 0);
               c.net.sendPacket(bufs, c.out);
               c.chat.sendMessage("Respawned!");
           } catch (IOException e) {
               e.printStackTrace();
           }
        }
    }  

}
