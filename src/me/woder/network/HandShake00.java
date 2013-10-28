package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.woder.bot.Client;

public class HandShake00 extends Packet{
    DataInputStream in;
    DataOutputStream out;
    public HandShake00(Client c, DataInputStream in, DataOutputStream out) {
        super(c, in, out);
        this.in = in;
        this.out = out;
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        c.protocol = readVarInt(in);
        getString(in);
        in.readShort();
        c.state = readVarInt(in);
    }

}
