package me.woder.network;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

import me.woder.bot.Client;

public class LoginSuccess02 extends Packet{
    public LoginSuccess02(Client c) {
        super(c);
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        String uuid = getString(buf).replace("-", "");
        c.uuid = new UUID(new BigInteger(uuid.substring(0, 16), 16).longValue(),new BigInteger(uuid.substring(16), 16).longValue()); 
        System.out.println("And er... " + getString(buf));
        c.state = 3;
    }

}
