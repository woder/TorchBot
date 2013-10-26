package me.woder.world;

import java.util.ArrayList;
import java.util.List;

import me.woder.bot.Client;

public class Import {
    List<Block> blocks = new ArrayList<Block>();
    public void importb(Client c, int x, int y, int z, int x2, int y2, int z2){
        System.out.println("HEHE: done");
        int d1, d2, d3;
        d1 = 1;
        d2 = 1;
        d3 = 1;
        if(x > x2){
            d1 = -1;
        }
        if(y > y2){
            d2 = -1;
        }
        if(z > z2){
            d3 = -1;
        }        
        System.out.println("We got here");
        for(int ix = x; ix == x2; ix = ix + d1){
          for(int iy = y; iy == y2; iy = iy + d2){
             for(int iz = z; iz == z2; iz = iz + d3){
                 System.out.println("Attempting to get block at: " + ix + " " + iy + " " + iz);
                 blocks.add(c.whandle.getWorld().getBlock(ix, iy, iz));
             }
          }
        }
        
        for(int i = 0; i < blocks.size(); i++){
             System.out.println("Block id: " + blocks.get(i));
        }
        
    }

}
