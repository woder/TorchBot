package me.woder.bot;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import me.woder.world.Chunk;

public class BChunk {
    int datalength;
    Client c;
    byte[] data;
    short len;
    int x;
    int z;
    DataInputStream in;
    
    public BChunk(DataInputStream in, Client c){
        this.in = in;
        this.c = c;
        parseChunk();
    }
    
    public void parseChunk(){
        try {
            x = in.readInt();
            z = in.readInt();
            boolean isGroundUp = in.readBoolean();
            short a = in.readShort();
            int bitmask = a & 0xffff;
            short as = in.readShort();
            int addmask = as & 0xffff;
            datalength = in.readInt();
            data = new byte[datalength];
            in.readFully(data);
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

