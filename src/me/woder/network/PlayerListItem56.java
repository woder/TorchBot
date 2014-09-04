package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class PlayerListItem56 extends Packet{
    public PlayerListItem56(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int action = Packet.readVarInt(buf);
        int length = Packet.readVarInt(buf);
        long uuid1 = buf.readLong();
        long uuid2 = buf.readLong();
        
        switch(action){
           case 0:
             String name = Packet.getString(buf);
             System.out.println("Name: " + name);
             int prop = Packet.readVarInt(buf);
             for(int i = 0; i < prop; i++){
                 String propname = Packet.getString(buf);
                 String value = Packet.getString(buf);
                 if(buf.readBoolean()){
                     String signature = Packet.getString(buf);
                 }
             }
             int gamemode = Packet.readVarInt(buf);
             int ping = Packet.readVarInt(buf);
             break;
        }
    }

}
