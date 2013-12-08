package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;

public class Packet {
    Client c;
    int state;
    Logger log;
    
    public Packet(Client c){
        this.c = c;
        log = Logger.getLogger("me.woder.network");
    }
    
    public void read(Client c, int len) throws IOException{
        //allow child to override
    }
    
    public void log(Level lvl, String s){
        log.log(lvl, s);
    }
    
    public static String getString(DataInputStream in) {
        int length;
        String s = "";
       try {
            length = readVarInt(in);       
        if (length < 0) {
           throw new IOException(
                   "Received string length is less than zero! Weird string!");
        } 
        
        if(length == 0){
            return "";
        }
        byte[] b = new byte[length];
        in.readFully(b, 0, length);
        s = new String(b, "UTF-8");
       } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }
       return s;
    }
    
    public static void sendPacket(ByteArrayDataOutput buf, DataOutputStream out) throws IOException{
        ByteArrayDataOutput send1 = ByteStreams.newDataOutput();
        writeVarInt(send1, buf.toByteArray().length);
        send1.write(buf.toByteArray());
        out.write(send1.toByteArray());
        out.flush();
    }
    
    public static void writeString(ByteArrayDataOutput out, String s) throws IOException{
        writeVarInt(out, s.length());
        out.write(s.getBytes("UTF-8"));
    }
    
    public static int readVarInt(DataInputStream ins) throws IOException{
      int i = 0;
      int j = 0;
      while (true){
        int k = ins.read();
   
        i |= (k & 0x7F) << j++ * 7;
   
        if (j > 5) throw new RuntimeException("VarInt too big");
   
        if ((k & 0x80) != 128) break;
      }
   
      return i;
    }
     
    public static void writeVarInt(ByteArrayDataOutput outs, int paramInt) throws IOException{
        while (true) {
          if ((paramInt & 0xFFFFFF80) == 0) {
            outs.writeByte((byte) paramInt);
            return;
          }
     
          outs.writeByte((byte) (paramInt & 0x7F | 0x80));
          paramInt >>>= 7;
        }
      }

    public void write() {
        // Allow child to write   
    }
}
