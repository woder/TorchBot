package me.woder.network;

import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;

import me.woder.bot.Client;

public class UpdateScore61 extends Packet{ 
    
     public UpdateScore61(Client c) {
        super(c);
    }

    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
          getString(buf);
          byte mode = buf.readByte();
          System.out.println("Mode: " + mode);
          if(mode == 0){
            getString(buf);           
            buf.readInt();
          }
     }

}
