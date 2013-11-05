package me.woder.network;

import java.io.IOException;

import me.woder.bot.Client;

public class LoginSuccess02 extends Packet{
    public LoginSuccess02(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        System.out.println("Alright so its: " + getString(c.in));
        System.out.println("And er... " + getString(c.in));
        c.state = 3;
    }

}
