package me.woder.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.woder.bot.Client;

public class TeamPacket62 extends Packet{
    
    public TeamPacket62(Client c) {
        super(c);
    }

    List<String> players = new ArrayList<String>();
    @Override
    public void read(Client c, int len){
      String teamname = getString(c.in);
      System.out.println("TEAM NAME: " + teamname);
      try {
        byte mode = c.in.readByte();
        System.out.println("Mode: " + mode);
        if(mode == 0 || mode == 2){
            String teamdis = getString(c.in);           
            String prefix = getString(c.in);
            String suffix = getString(c.in);
            byte friendlyfire = c.in.readByte();
            System.out.println("Team,pre,s,f: " + teamdis + "," + prefix + "," + suffix + "," + friendlyfire);
        }
        
        if(mode == 0 || mode == 3 || mode == 4){
            short number = c.in.readShort();
            System.out.println("LENGHT: " + number);
            for(int i = 0; i < number; i++){
                players.add(getString(c.in));
            }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }      
    }
}
