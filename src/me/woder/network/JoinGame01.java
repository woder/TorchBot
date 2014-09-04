package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class JoinGame01 extends Packet{
    public JoinGame01(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        c.entityID = buf.readInt();
        c.gamemode = buf.readByte();
        c.dimension = buf.readByte();
        c.difficulty = buf.readByte();
        c.maxplayer = buf.readByte();
        System.out.println("Level type is: " + getString(buf));
        ByteArrayDataOutput buff = ByteStreams.newDataOutput();
        try{
         Packet.writeVarInt(buff, 21);
         Packet.writeString(buff, "en_GB");
         buff.writeByte(0);
         buff.writeByte(0);
         buff.writeBoolean(true);
         buff.writeByte(0);
         c.net.sendPacket(buff, c.out);
        }catch(IOException e){
          e.printStackTrace();
        }
        
    }

}
