package me.woder.world;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class World {
    public List<Chunk> chunklist = new ArrayList<Chunk>();
    
    public World(){
        
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
       System.out.println("Getting block at: " + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + " cx:" + ChunkX + " cz: " + ChunkZ);

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
      }
    }
    

}
