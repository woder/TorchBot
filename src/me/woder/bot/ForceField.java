package me.woder.bot;

import java.io.IOException;

import me.woder.network.Packet;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class ForceField {
    Client c;
    
    public ForceField(Client c){
        this.c = c;
    }
    
    public void tick(){
        //find entities that are in range
        for(Entity e : c.en.entities){
            if(c.location.inRange(e.getLocation(), 4.0)){      
                if(e.isHostile()){
                    ByteArrayDataOutput buf = ByteStreams.newDataOutput();
                    try {
                        Packet.writeVarInt(buf, 2);
                        Packet.writeVarInt(buf, e.getEntityId());
                        Packet.writeVarInt(buf, 1);
                        c.net.sendPacket(buf, c.out);
                        buf = ByteStreams.newDataOutput();
                        Packet.writeVarInt(buf, 10);
                        c.net.sendPacket(buf, c.out);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }                                      
                }
            }
        }
    }
    
    public void moved(Entity e){
        if(e.isHostile()){
            if(c.location.inRange(e.getLocation(), 4.0)){      
                ByteArrayDataOutput buf = ByteStreams.newDataOutput();
                try {
                    Packet.writeVarInt(buf, 2);
                    Packet.writeVarInt(buf, e.getEntityId());
                    Packet.writeVarInt(buf, 1);
                    c.net.sendPacket(buf, c.out);
                    buf = ByteStreams.newDataOutput();
                    Packet.writeVarInt(buf, 10);
                    c.net.sendPacket(buf, c.out);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }                                      
            }
        }
    }

}
