package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.woder.bot.Client;

public class LoginSuccess02 extends Packet{
    DataInputStream in;
    DataOutputStream out;
    public LoginSuccess02(Client c, DataInputStream in, DataOutputStream out) {
        super(c, in, out);
        this.in = in;
        this.out = out;
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        System.out.println("Alright so its: " + getString(in));
        System.out.println("And er... " + getString(in));
        c.state = 3;
    }

}
