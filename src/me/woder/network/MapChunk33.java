package me.woder.network;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import me.woder.bot.Client;
import me.woder.world.Chunk;

public class MapChunk33 extends Packet{
    int datalength;
    Client c;
    byte[] data;
    short len;
    int x;
    int z;
    
    public MapChunk33(Client c){
        super(c);
        this.c = c;
    }
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
          x = buf.readInt();
          z = buf.readInt();
          boolean isGroundUp = buf.readBoolean();
          short a = buf.readShort();
          int bitmask = a & 0xffff;
          datalength = Packet.readVarInt(buf);
          data = new byte[datalength];
          buf.readFully(data, 0, datalength);
          Chunk chunke = new Chunk(c, x, z, bitmask, true, isGroundUp); 
          int extra = chunke.blocknum;
          extra += (chunke.blocknum / 2);
          if (isGroundUp){
                extra += 256;
          }  
            if (bitmask == 0) {
                // Unload chunk, save ALL the ram!
                Chunk thischunk = null;
                for (Chunk f : c.whandle.getWorld().chunklist) {
                    if (f.getX() == x && f.getZ() == z) {
                        thischunk = f;
                        break;
                    }
                }

                if (thischunk != null){ 
                    c.whandle.getWorld().chunklist.remove(thischunk);
                }
                return;
             }
                       
          chunke.getData(data);
          //c.chat.sendMessage("Added chunk: " + chunke.getX() + " " + chunke.getZ());
          c.whandle.getWorld().chunklist.add(chunke);//add it to the world :D
        
    }

}

