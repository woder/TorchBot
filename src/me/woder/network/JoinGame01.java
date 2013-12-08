package me.woder.network;

import java.io.IOException;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;

public class JoinGame01 extends Packet{
    public JoinGame01(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.entityID = c.in.readInt();
        c.gamemode = c.in.readByte();
        c.dimension = c.in.readByte();
        c.difficulty = c.in.readByte();
        c.maxplayer = c.in.readByte();
        System.out.println("Level type is: " + getString(c.in));
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        try{
         Packet.writeVarInt(buf, 21);
         Packet.writeString(buf, "en_GB");
         buf.writeByte(0);
         buf.writeByte(0);
         buf.writeBoolean(true);
         buf.writeByte(0);
         buf.writeBoolean(true);
         Packet.sendPacket(buf, c.out);
        }catch(IOException e){
          e.printStackTrace();
        }
        
    }

}
