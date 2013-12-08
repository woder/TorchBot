package me.woder.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.woder.bot.Client;

public class Import {
    HashMap<Location,Block> blocks = new HashMap<Location,Block>();
    
    public void exportb(Client c, int x, int y, int z, int x2, int y2, int z2, String name){
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
        int rx, ry, rz;
        rx = 0;
        ry = 0;
        rz = 0;
        try {
            PrintWriter writer = new PrintWriter(name + ".tbs", "UTF-8");
            c.chat.sendMessage("Now writing to file " + name + ".tbs");
            writer.write("#TorchBot block export\n");
            for(int ix = x; ix != (x2+d1); ix = ix+d1){
                for(int iy = y; iy != (y2+d2); iy = iy+d2){
                    for(int iz = z; iz != (z2+d3); iz = iz+d3){                    
                        c.chat.sendMessage("Attempting to get blocks at: " + ix + " " + iy + " " + iz + " : " + rx + " " + ry + " " + rz + ""); 
                        Block b = c.whandle.getWorld().getBlock(ix, iy, iz);
                        writer.write(rx + "," + ry + "," + rz + "," + b.getTypeId() + "," + b.getMetaData() + "\n");
                        rz = rz + d3; 
                    }
                  rz = 0;
                  ry = ry + d2;
                }
                ry = 0;
                rx = rx + d1;
           }  
           writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  
        
        
    }
    
    public void importb(Client c, String name){
        List<Block> blocks = new ArrayList<Block>();      
        try {
            BufferedReader br = new BufferedReader(new FileReader(name + ".tbs"));
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] stu = line.split(",");
                c.chat.sendMessage(line);
                //create a fake block to hold our data **NOTICE THAT THESE COORDS ARE RELATIVE AND NOT REAL WORLD**
                Block b = new Block(c.whandle.getWorld(), Integer.parseInt(stu[0]), Integer.parseInt(stu[1]), Integer.parseInt(stu[2]), Integer.parseInt(stu[3]), Integer.parseInt(stu[4]));
                blocks.add(b);
                line = br.readLine();
            }
             
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
      for(Block b : blocks){
          c.chat.sendMessage("id: " + b.getTypeId() + " " + b.getX() + "," + b.getY() + "," + b.getZ() );
      }
    }
    
    

}
