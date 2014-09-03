package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

import com.google.common.io.ByteArrayDataInput;

public class LoginSuccess02 extends Packet{
    public LoginSuccess02(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInput buf) throws IOException{
        System.out.println("Alright so its: " + getString(buf));
        System.out.println("And er... " + getString(buf));
        c.state = 3;
    }

}
