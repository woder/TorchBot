/*
 World class writted by woder, edited to include BlockInfoManager by nuvasuper
 24/02/2015 dd/mm/yy 
 This was to allow the BlockInfoManager to be created as few times as possible,
 and for world to give the blocks returned by getBlock(Location l) to already
 have the appropriate hardness, tool, name, etc.
 
 */

package me.woder.world;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    BlockInfoManager bim;
    
    public World(Client c){
        this.c = c;
        try {
			this.bim = new BlockInfoManager();
		} catch (IOException e) {
			System.out.println("BlockManager couldn't find BlockInfo.txt, ending the world...");
			c.error.displayError(e.getMessage(), "IOException", e.getCause().getMessage());
		}
    }
    
    public Import getImport(){
        return importer;
    }
    
    public void placeBlock(int x, int y, int z, int id){
        placeBlock(x, y, z, id, 1);
    }
    
    public void placeBlock(int x, int y, int z, int id, int face){
       ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
       try {
        long pos = ((long)(x & 0x3FFFFFF) << 38) | ((long)(y & 0xFFF) << 26) | (long)(z & 0x3FFFFFF);
        Packet.writeVarInt(buf, 8);
        buf.writeLong(pos);
        buf.writeByte(face);
        new Slot(0,(short)id,(byte)1,(short)0,(byte)0).sendSlot(buf);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        c.net.sendPacket(buf, c.out);
        buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 10);
        c.net.sendPacket(buf, c.out);
       } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }
    }
    
    public void digBlock(int x, int y, int z, final byte face){
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();    
        final Timer timer = new Timer();
        try {
         final long pos = ((long)(x & 0x3FFFFFF) << 38) | ((long)(y & 0xFFF) << 26) | (long)(z & 0x3FFFFFF);
         Block thisBlock = this.getBlock(x,y,z);
         if (thisBlock!=null){
             Packet.writeVarInt(buf, 7);
             buf.writeByte(0);
             buf.writeLong(pos);
             buf.writeByte(1);
             c.net.sendPacket(buf, c.out);
             buf = ByteStreams.newDataOutput();
             Packet.writeVarInt(buf, 10);
             c.net.sendPacket(buf, c.out);
             int slot = c.invhandle.currentSlot;
             int tool = c.invhandle.inventory.get(slot).getId();
             //System.out.println("HEY got the id its "+tool);
             int breakTime = thisBlock.getBreakTime(tool);
             if(breakTime>=0) {
                 timer.schedule(new TimerTask(){
                   public void run() {
                     delayedDig(pos, face);
                     timer.cancel(); //Terminate the timer thread
                   }
             	 }, breakTime);
          	 }
         }
        } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
     }
    
    public void delayedDig(long pos, byte face){
      try {
        ByteArrayDataOutput buf = ByteStreams.newDataOutput();
        Packet.writeVarInt(buf, 7);
        buf.writeByte(2);
        buf.writeLong(pos);
        buf.writeByte(face);
        c.net.sendPacket(buf, c.out);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
    }
    
    public Block getBlock(Location l){
        BigDecimal ChunkX = BigDecimal.valueOf(l.getX());
        BigDecimal ChunkZ = BigDecimal.valueOf(l.getZ());   
        ChunkX = ChunkX.divide(new BigDecimal("16"), 0, BigDecimal.ROUND_FLOOR);
        ChunkZ = ChunkZ.divide(new BigDecimal("16"), 0, BigDecimal.ROUND_FLOOR);
        
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
       
       if (thisblock!=null) {
           bim.addInfo(thisblock, false);
       }
       return thisblock;
    }
    
    public Block getBlock(int x, int y, int z){
        BigDecimal ChunkX = BigDecimal.valueOf(x);
        BigDecimal ChunkZ = BigDecimal.valueOf(z);
        ChunkX = ChunkX.divide(new BigDecimal("16"), 0, BigDecimal.ROUND_FLOOR);
        ChunkZ = ChunkZ.divide(new BigDecimal("16"), 0, BigDecimal.ROUND_FLOOR);
        
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
       if (thisblock!=null) {
       bim.addInfo(thisblock, false);
       }
       return thisblock;
    }
    
    public void setBlock(int x, int y, int z, int id, int meta){
        BigDecimal ChunkX = BigDecimal.valueOf(x);
        BigDecimal ChunkZ = BigDecimal.valueOf(z);
        ChunkX = ChunkX.divide(new BigDecimal("16"), 0, BigDecimal.ROUND_FLOOR);
        ChunkZ = ChunkZ.divide(new BigDecimal("16"), 0, BigDecimal.ROUND_FLOOR);
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
