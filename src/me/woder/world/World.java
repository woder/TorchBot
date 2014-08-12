package me.woder.world;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.woder.bot.Client;
import me.woder.bot.Slot;
import me.woder.event.Event;
import me.woder.network.Packet;

public class World {
    public List<Chunk> chunklist = new ArrayList<Chunk>();
    public Import importer = new Import();
    Client c;
    
    public World(Client c){
        this.c = c;
    }
    
    public Import getImport(){
        return importer;
    }
    
    public void placeBlock(int x, int y, int z, int id){
       ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
       c.chat.sendMessage("Attempting to send block at: " + x + ", " + y + ", " + z);
       try {
        Packet.writeVarInt(buf, 8);
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        buf.writeByte(0);
        new Slot(0,(short)1,(byte)1,(short)0,(short)0).sendSlot(buf);
        buf.writeByte(1);
        buf.writeByte(1);
        buf.writeByte(1);
        Packet.sendPacket(buf, c.out);
       } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }
    }
    
    public Block getBlock(Location l){
        BigDecimal ChunkX = BigDecimal.valueOf(l.getX());
        BigDecimal ChunkZ = BigDecimal.valueOf(l.getZ());
        ChunkX = ChunkX.divide(new BigDecimal("16"), BigDecimal.ROUND_FLOOR);
        ChunkZ = ChunkZ.divide(new BigDecimal("16"), BigDecimal.ROUND_FLOOR);
        
        Chunk thisChunk = null;
        Block thisblock = null;

        for(Chunk b : chunklist) {
            if (b.getX() == ChunkX.intValue() & b.getZ() == ChunkZ.intValue()) {  
                thisChunk = b;
                break;
            }
        }

       if(thisChunk != null){
        
        thisblock = thisChunk.getBlock(l.getBlockX(),l.getBlockY(),l.getBlockZ());
       }
        return thisblock;
    }
    
    public Block getBlock(int x, int y, int z){
        BigDecimal ChunkX = BigDecimal.valueOf(x);
        BigDecimal ChunkZ = BigDecimal.valueOf(z);
        ChunkX = ChunkX.divide(new BigDecimal("16"), BigDecimal.ROUND_FLOOR);
        ChunkZ = ChunkZ.divide(new BigDecimal("16"), BigDecimal.ROUND_FLOOR);
        
        Chunk thisChunk = null;
        Block thisblock = null;

        for(Chunk b : chunklist) {
            if (b.getX() == ChunkX.intValue() & b.getZ() == ChunkZ.intValue()) {          
                thisChunk = b;
                break;
            }
        }
       if(thisChunk != null){
        thisblock = thisChunk.getBlock(x,y,z);
       }
        return thisblock;
    }
    
    public void setBlock(int x, int y, int z, int id, int meta){
        BigDecimal ChunkX = BigDecimal.valueOf(x);
        BigDecimal ChunkZ = BigDecimal.valueOf(z);
        ChunkX = ChunkX.divide(new BigDecimal("16"), BigDecimal.ROUND_FLOOR);
        ChunkZ = ChunkZ.divide(new BigDecimal("16"), BigDecimal.ROUND_FLOOR);
        
        Chunk thisChunk = null;

        for(Chunk b : chunklist) {
            if (b.getX() == ChunkX.intValue() & b.getZ() == ChunkZ.intValue()) {          
                thisChunk = b;
                break;
            }
        }
      if(thisChunk != null){        
        thisChunk.updateBlock(x, y, z, id, meta);
        c.chandle.impor.onBlockChange(new Event("onBlockChange", new Object[]{x,y,z,id,meta}), c);
      }
    }
    

}
