package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;
import me.woder.bot.CryptManager;

public class EncryptionRequest253 extends Packet{
    DataInputStream in;
    DataOutputStream out;
    Client c;
    Logger log = Logger.getLogger("me.woder.network");
    public EncryptionRequest253(Client c, DataInputStream in, DataOutputStream out) {
        super(c, in, out);
        this.in = in;
        this.out = out;
        this.c = c;
    }
    
    @Override
    public void read(Client c, int len) throws IOException{
        String serverid = getString(in);
        log.log(Level.FINEST,"Reading server id: " + serverid);
        c.publickey = CryptManager.decodePublicKey(c.readBytesFromStream(in));//read the public key**taken from the original minecraft code**
        byte[] verifytoken = c.readBytesFromStream(in);//read the verify token        
        c.secretkey = CryptManager.createNewSharedKey();//generate a secret key
        c.sharedkey = c.secretkey;
        log.log(Level.FINEST,"Secret key is: " + c.secretkey);
        String var5 = (new BigInteger(CryptManager.getServerIdHash(serverid.trim(), c.publickey, c.secretkey))).toString(16);
        String var6 = c.sendSessionRequest(c.username, "token:" + c.accesstoken + ":" + c.profile, var5);
        log.log(Level.FINEST,var6);
        byte[] sharedSecret = new byte[0];
        byte[] verifyToken = new byte[0];
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 0xFC);//send an encryption response
        sharedSecret = CryptManager.encryptData(c.publickey, c.secretkey.getEncoded());
        verifyToken = CryptManager.encryptData(c.publickey, verifytoken);
        buf.writeShort(sharedSecret.length);
        buf.write(sharedSecret);
        buf.writeShort(verifyToken.length);
        buf.write(verifyToken);
        ByteArrayDataOutput send1 = ByteStreams.newDataOutput();
        Packet.writeVarInt(send1, buf.toByteArray().length);
        send1.write(buf.toByteArray());
        out.write(send1.toByteArray());
        out.flush();
    }

}
