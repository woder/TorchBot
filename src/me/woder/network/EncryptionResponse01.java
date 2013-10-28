package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;

public class EncryptionResponse01 extends Packet{
    Client c;
    DataInputStream in;
    DataOutputStream out;

    public EncryptionResponse01(Client c, DataInputStream in, DataOutputStream out) {
        super(c, in, out);
        this.c = c;
        this.in = in;
        this.out = out;
    }
    
    @Override
    public void write(){
        /*ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 0);
        //Packet.writeString(buf);
        ByteArrayDataOutput send1 = ByteStreams.newDataOutput();
        send1 = ByteStreams.newDataOutput();
        Packet.writeVarInt(send1, buf.toByteArray().length);
        send1.write(buf.toByteArray());*/
    }
    

}
