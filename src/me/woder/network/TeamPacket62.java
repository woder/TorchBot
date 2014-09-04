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
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
      String teamname = getString(buf);
      System.out.println("TEAM NAME: " + teamname);
        byte mode = buf.readByte();
        System.out.println("Mode: " + mode);
        if(mode == 0 || mode == 2){
            String teamdis = getString(buf);           
            String prefix = getString(buf);
            String suffix = getString(buf);
            byte friendlyfire = buf.readByte();
            System.out.println("Team,pre,s,f: " + teamdis + "," + prefix + "," + suffix + "," + friendlyfire);
        }
        
        if(mode == 0 || mode == 3 || mode == 4){
            short number = buf.readShort();
            System.out.println("LENGHT: " + number);
            for(int i = 0; i < number; i++){
                players.add(getString(buf));
            }
        }
    }
}
