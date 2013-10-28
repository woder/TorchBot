package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.woder.bot.Client;

public class TeamPacket209 extends Packet{
    
    public TeamPacket209(Client c, DataInputStream in, DataOutputStream out) {
        super(c, in, out);
    }

    List<String> players = new ArrayList<String>();
    @Override
    public void read(Client c, int len){
      String teamname = c.chat.readString();
      System.out.println("TEAM NAME: " + teamname);
      try {
        byte mode = c.in.readByte();
        System.out.println("Mode: " + mode);
        if(mode == 0 || mode == 2){
            String teamdis = c.chat.readString();           
            String prefix = c.chat.readString();
            String suffix = c.chat.readString();
            byte friendlyfire = c.in.readByte();
            System.out.println("Team,pre,s,f: " + teamdis + "," + prefix + "," + suffix + "," + friendlyfire);
        }
        
        if(mode == 0 || mode == 3 || mode == 4){
            short number = c.in.readShort();
            System.out.println("LENGHT: " + number);
            for(int i = 0; i < number; i++){
                players.add(c.chat.readString());
            }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }      
    }
}
