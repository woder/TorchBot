package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class UpdateScore207 { 
     @SuppressWarnings("unused")
     public void read(Client c){
          String itemname = c.chat.readString();
          try {
            byte mode = c.in.readByte();
            System.out.println("Mode: " + mode);
            if(mode == 0){
                String scorename = c.chat.readString();           
                c.in.readInt();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }      
     }

}
