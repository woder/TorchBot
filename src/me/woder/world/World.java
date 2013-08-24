package me.woder.world;

import java.util.ArrayList;
import java.util.List;

public class World {
    public List<Chunk> chunklist = new ArrayList<Chunk>();
    
    public World(){
        
    }
    
    public void spit(){
        for(Chunk ch : chunklist){
            for(Part p : ch.parts){
               for(int i = 0; i < p.blocks.length; i++){
                System.out.println("Block at " + p.y + " " + p.blocks[i]);
               }
            }
        }
    }
    
    public Block getBlock(Location l){
        double ChunkX = l.getBlockX()/16;
        double ChunkZ = l.getBlockZ()/16;

        ChunkX = Math.ceil(ChunkX);
        ChunkZ = Math.ceil(ChunkZ);
        Chunk thisChunk = null;
        Block thisblock = null;

        for(Chunk b : chunklist) {
            if (b.getX() == ChunkX & b.getZ() == ChunkZ) {
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
        double ChunkX = x/16;
        double ChunkZ = z/16;

        ChunkX = Math.ceil(ChunkX);
        ChunkZ = Math.ceil(ChunkZ);     
        Chunk thisChunk = null;
        Block thisblock = null;

        for(Chunk b : chunklist) {
            if (b.getX() == ChunkX & b.getZ() == ChunkZ) {          
                thisChunk = b;
                break;
            }
        }
       if(thisChunk != null){
        thisblock = thisChunk.getBlock(x,y,z);
       }
        return thisblock;
    }
    
    public void setBlock(int x, int y, int z, int id){
        double ChunkX = x/16;
        double ChunkZ = z/16;

        ChunkX = Math.ceil(ChunkX);
        ChunkZ = Math.ceil(ChunkZ);     
        Chunk thisChunk = null;

        for(Chunk b : chunklist) {
            if (b.getX() == (int)ChunkX & b.getZ() == (int)ChunkZ) {          
                thisChunk = b;
                break;
            }
        }
      if(thisChunk != null){        
        thisChunk.updateBlock(x, y, z, id);
      }
    }
    

}
