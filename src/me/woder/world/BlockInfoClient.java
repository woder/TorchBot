package me.woder.world;
import java.io.*;
import java.util.*;
/*
This is the BlockInfoClient class.  It is used for testing the BlockInfoManager and BlockInfo classes
An int is requested via the console.  The first int is used as a blockid, 1=stone, 2= grass, etc.
The next id is used as a specific tool item id.  It will return the breaktime of the established block 
for the provided tool.  This loops until the user enters a -1 as the block id.
 
 */
public class BlockInfoClient {
   public static void main(String[] args) throws FileNotFoundException {
      BlockInfoManager bim= new BlockInfoManager();
      Scanner s = new Scanner(System.in);
      int input = 1;
      input = s.nextInt();
      while(input!=-1) {
         //BlockInfo bi = bim.getInfo(input);
         Block b = new Block(null, 0,0,0,input,0);
    	 BlockInfo bi = bim.getInfo(b,false);
    	 
    	 System.out.println("name = " +bi.getBlockName());
         System.out.println("id = " +bi.getID());
         System.out.println("hardness = "+bi.getHardness());
         System.out.println("tool = "+bi.getTool());
         System.out.println("time = "+bi.getBreakTime(s.nextInt()));
         input=s.nextInt();
      }
      s.close();
     
   }
}