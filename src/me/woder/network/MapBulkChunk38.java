package me.woder.network;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import me.woder.bot.Client;
import me.woder.world.Chunk;

public class MapBulkChunk38 extends Packet{
    int datalength;
    boolean skylight;
    Client c;
    byte[] data;
    short len;
    int x;
    int z;
    
    public MapBulkChunk38(Client c){
        super(c);
        this.c = c;
    }
    
   /* @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
        try {
          len = buf.readShort();
          datalength = buf.readInt();
          Chunk[] chunks = new Chunk[len];
          skylight = buf.readBoolean();
          data = new byte[datalength];
          buf.readFully(data);
          Inflater inflater = new Inflater();
          inflater.setInput(data);            
          
          //step one, pull all data from the stream
          for(int i = 0; i < len; i++){
            x = buf.readInt();
            z = buf.readInt();
            int pmap = (buf.readShort() & 0xffff);
            int amap = (buf.readShort() & 0xffff);
            chunks[i] = new Chunk(c, x, z, pmap, amap, skylight, true);         
            //save all the chunks
          }
           int chunksize = 0;
           int extra = 0;
          //extra stuff we no care about
          //step 2, calculate the size of this buffer
          for(int i = 0; i < len; i++){
           chunksize += chunks[i].blocknum;
           extra += chunks[i].blocknum;          
            if(skylight){
              extra += (chunks[i].blocknum / 2);
            }
              extra += 256;
          }
          //step 3, decompress it now that we know the size
          
          byte[] chunk = new byte[chunksize + extra];
          try{
                inflater.inflate(chunk);
          }catch(DataFormatException dataformatexception){
                throw new IOException("Bad compressed data format");
          }finally{
                inflater.end();
          }
          
          //step 4, actually add the new chunks
          for(int i = 0; i < len; i++){
              chunk = chunks[i].getData(chunk);//takes what it needs and leaves the rest
              //System.out.println("Adding the chunks... ");
              c.whandle.getWorld().chunklist.add(chunks[i]);//add it to the world :D
          }
          
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }*/
    
    @Override
    public void read(Client c, int len, ByteArrayDataInputWrapper buf) throws IOException{
          boolean skylight = buf.readBoolean();
          len = Packet.readVarInt(buf);
          Chunk[] chunks = new Chunk[len];
          
          for(int i = 0; i < len; i++){
              int chunkx = buf.readInt();
              int chunkz = buf.readInt();
              int bitmask = (buf.readShort() & 0xffff);
              chunks[i] = new Chunk(c, x, z, bitmask, skylight, true);
          }
    }

}
