package me.woder.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.google.common.io.ByteArrayDataOutput;

import me.woder.bot.Client;

public class Packet {
    Client c;
    int state;
    
    public Packet(Client c, DataInputStream in, DataOutputStream out){
        this.c = c;
    }
    
    public void read(Client c, int len) throws IOException{
        //allow child to override
    }
    
    public String getString(DataInputStream in) throws IOException {
        int length = readVarInt(in);
        if (length > 3000)
            throw new IOException(
                   "Received string length longer than maximum allowed ("
                           + length + " > " + 3000 + ")");
        if (length < 0) {
           throw new IOException(
                   "Received string length is less than zero! Weird string!");
        } 
        StringBuilder stringbuilder = new StringBuilder();

        for (int j = 0; j < length; j++) {
           stringbuilder.append(in.readChar());
        }

       return stringbuilder.toString();
    }
    
    public static void writeString(DataOutputStream out, String s) throws IOException{
        writeVarInt(out, s.length());
        out.writeChars(s);
    }
    
    public static void writeString(ByteArrayDataOutput out, String s) throws IOException{
        writeVarInt(out, s.length());
        out.write(s.getBytes("UTF-8"));
    }
    
    public static int readVarInt(DataInputStream ins) throws IOException{
      int i = 0;
      int j = 0;
      while (true){
        int k = ins.readByte();
   
        i |= (k & 0x7F) << j++ * 7;
   
        if (j > 5) throw new RuntimeException("VarInt too big");
   
        if ((k & 0x80) != 128) break;
      }
   
      return i;
    }
   
    public static void writeVarInt(DataOutputStream outs, int paramInt) throws IOException{
      while (true) {
        if ((paramInt & 0xFFFFFF80) == 0) {
          outs.writeByte(paramInt);
          return;
        }
   
        outs.writeByte(paramInt & 0x7F | 0x80);
        paramInt >>>= 7;
      }
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
