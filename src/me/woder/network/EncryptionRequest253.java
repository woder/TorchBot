package me.woder.network;

import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.woder.bot.Client;
import me.woder.bot.CryptManager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class EncryptionRequest253 extends Packet{
    Client c;
    Logger log = Logger.getLogger("me.woder.network");
    public EncryptionRequest253(Client c) {
        super(c);
        this.c = c;
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        String serverid = getString(buf);
        log.log(Level.FINEST,"Reading server id: " + serverid);
        System.out.println("Server id is !" + serverid + "!");
        c.publickey = CryptManager.decodePublicKey(c.readBytesFromStreamV(buf));//read the public key**taken from the original minecraft code**
        byte[] verifytoken = c.readBytesFromStreamV(buf);//read the verify token        
        c.secretkey = CryptManager.createNewSharedKey();//generate a secret key
        c.sharedkey = c.secretkey;
        log.log(Level.FINEST,"Secret key is: " + c.secretkey);
        String var5 = (new BigInteger(CryptManager.getServerIdHash(serverid.trim(), c.publickey, c.secretkey))).toString(16);
        String var6 = c.sendSessionRequest(c.username, "token:" + c.accesstoken + ":" + c.profile, var5);
        log.log(Level.FINEST,var6);
        
        byte[] sharedSecret = new byte[0];
        byte[] verifyToken = new byte[0];
        ByteArrayDataOutput buff = ByteStreams.newDataOutput();
        Packet.writeVarInt(buff, 0x01);//send an encryption response
        sharedSecret = CryptManager.encryptData(c.publickey, c.secretkey.getEncoded());
        verifyToken = CryptManager.encryptData(c.publickey, verifytoken);
        Packet.writeVarInt(buff, sharedSecret.length);
        buff.write(sharedSecret);
        Packet.writeVarInt(buff, verifyToken.length);
        buff.write(verifyToken);
        c.net.sendPacket(buff, c.out);
        c.activateEncryption();
        c.decryptInputStream();
    }

}
