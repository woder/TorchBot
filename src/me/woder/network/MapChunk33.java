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
    public void read(Client c, int len){
        try {
            x = c.in.readInt();
            z = c.in.readInt();
            boolean isGroundUp = c.in.readBoolean();
            short a = c.in.readShort();
            int bitmask = a & 0xffff;
            short as = c.in.readShort();
            int addmask = as & 0xffff;
            datalength = c.in.readInt();
            data = new byte[datalength];
            c.in.readFully(data);
            Chunk chunke = new Chunk(c, x, z, bitmask, addmask, true, isGroundUp); 
            int extra = chunke.blocknum;
            
            extra += (chunke.blocknum / 2);
       
            if (isGroundUp){
                extra += 256;
            }
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            byte[] chunk = new byte[chunke.blocknum + extra];
             try{
                System.out.println("De compressed " + inflater.inflate(chunk));
             }catch(DataFormatException dataformatexception){
                throw new IOException("Bad compressed data format");
             }finally{
                inflater.end();
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
                       
            chunke.getData(chunk);
            //c.chat.sendMessage("Added chunk: " + chunke.getX() + " " + chunke.getZ());
            c.whandle.getWorld().chunklist.add(chunke);//add it to the world :D
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}

