package me.woder.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.woder.bot.Client;
import me.woder.bot.Player;
import me.woder.playerlist.PlayerL;
import me.woder.playerlist.Property;

public class PlayerListItem56 extends Packet{
    public PlayerListItem56(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        int action = Packet.readVarInt(buf);
        int length = Packet.readVarInt(buf);
        
        for(int e = 0; e < length; e++){
         UUID uuid = Packet.readUUID(buf);
         PlayerL p = null;
         p = c.plist.players.get(uuid);
         if(p == null){
           p = new PlayerL(uuid, null, null, 0, 0, false, null);
         }
         List<Property> properties = new ArrayList<Property>();
         
         switch(action){
           case 0:
             String name = Packet.getString(buf);
             System.out.println("Name: " + name);
             int prop = Packet.readVarInt(buf);
             for(int i = 0; i < prop; i++){
                 String propname = Packet.getString(buf);
                 String value = Packet.getString(buf);
                 boolean issignature = buf.readBoolean();
                 String signature = "";
                 if(issignature){
                     signature = Packet.getString(buf);
                 }
                 properties.add(new Property(propname, value, issignature, signature));
             }
             int gamemode = Packet.readVarInt(buf);
             int ping = Packet.readVarInt(buf);
             boolean hasdisplayname = buf.readBoolean();
             String displayname = null;
             if(hasdisplayname){
                 displayname = Packet.getString(buf);
             }
             if(c.plist.players.containsKey(uuid)){
               c.plist.players.get(uuid).setEverything(uuid, name, properties, gamemode, ping, hasdisplayname, displayname);
             }else{
               c.plist.players.put(uuid, new PlayerL(uuid, name, properties, gamemode, ping, hasdisplayname, displayname));
             }
             break;
           case 1:
             p.setGamemode(Packet.readVarInt(buf));
             break;
           case 2:
             p.setPing(Packet.readVarInt(buf));
             break;
           case 3:
             boolean hasdisplay = buf.readBoolean();
             String displayn = "";
             if(hasdisplay){
               displayn = Packet.getString(buf);
             }
             p.setHasdisplayname(hasdisplay);
             p.setDisplayname(displayn);
             break;
           case 4:
             c.plist.players.remove(p);
             break;
         }
        }
    }

}
